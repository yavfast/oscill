package com.hoho.android.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CdcAcmSerialDriver extends CommonUsbSerialDriver {
    private static final int GET_LINE_CODING = 33;
    private static final int SEND_BREAK = 35;
    private static final int SET_CONTROL_LINE_STATE = 34;
    private static final int SET_LINE_CODING = 32;
    private static final int USB_RECIP_INTERFACE = 1;
    private static final int USB_RT_ACM = 33;
    private final String TAG = CdcAcmSerialDriver.class.getSimpleName();
    private int mBaudRate;
    private UsbEndpoint mControlEndpoint;
    private UsbInterface mControlInterface;
    private int mDataBits;
    private UsbInterface mDataInterface;
    private boolean mDtr = false;
    private int mParity;
    private UsbEndpoint mReadEndpoint;
    private boolean mRts = false;
    private int mStopBits;
    private UsbEndpoint mWriteEndpoint;

    public CdcAcmSerialDriver(UsbDevice device, UsbDeviceConnection connection) {
        super(device, connection);
    }

    public void open() throws IOException {
        Log.d(this.TAG, "claiming interfaces, count=" + this.mDevice.getInterfaceCount());
        Log.d(this.TAG, "Claiming control interface.");
        this.mControlInterface = this.mDevice.getInterface(0);
        Log.d(this.TAG, "Control iface=" + this.mControlInterface);
        if (!this.mConnection.claimInterface(this.mControlInterface, true)) {
            throw new IOException("Could not claim control interface.");
        }
        this.mControlEndpoint = this.mControlInterface.getEndpoint(0);
        Log.d(this.TAG, "Control endpoint direction: " + this.mControlEndpoint.getDirection());
        Log.d(this.TAG, "Claiming data interface.");
        this.mDataInterface = this.mDevice.getInterface(1);
        Log.d(this.TAG, "data iface=" + this.mDataInterface);
        if (!this.mConnection.claimInterface(this.mDataInterface, true)) {
            throw new IOException("Could not claim data interface.");
        }
        this.mReadEndpoint = this.mDataInterface.getEndpoint(1);
        Log.d(this.TAG, "Read endpoint direction: " + this.mReadEndpoint.getDirection());
        this.mWriteEndpoint = this.mDataInterface.getEndpoint(0);
        Log.d(this.TAG, "Write endpoint direction: " + this.mWriteEndpoint.getDirection());
        if (this.mReadEndpoint.getDirection() == 0) {
            UsbEndpoint temp = this.mReadEndpoint;
            this.mReadEndpoint = this.mWriteEndpoint;
            this.mWriteEndpoint = temp;
            Log.d(this.TAG, "Swapped endpoints");
        }
        Log.d(this.TAG, "Setting line coding to 115200/8N1");
        this.mBaudRate = 115200;
        this.mDataBits = 8;
        this.mParity = 0;
        this.mStopBits = 1;
        setParameters(this.mBaudRate, this.mDataBits, this.mStopBits, this.mParity);
    }

    private int sendAcmControlMessage(int request, int value, byte[] buf) {
        int i;
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (buf != null) {
            i = buf.length;
        } else {
            i = 0;
        }
        return usbDeviceConnection.controlTransfer(33, request, value, 0, buf, i, 5000);
    }

    public void close() throws IOException {
        this.mConnection.close();
    }

    public int read(byte[] dest, int timeoutMillis) throws IOException {
        synchronized (this.mReadBufferLock) {
            int numBytesRead = this.mConnection.bulkTransfer(this.mReadEndpoint, this.mReadBuffer, Math.min(dest.length, this.mReadBuffer.length), timeoutMillis);
            if (numBytesRead < 0) {
                return 0;
            }
            System.arraycopy(this.mReadBuffer, 0, dest, 0, numBytesRead);
            return numBytesRead;
        }
    }

    public int write(byte[] src, int timeoutMillis) throws IOException {
        int writeLength;
        byte[] writeBuffer;
        int amtWritten;
        int offset = 0;
        while (offset < src.length) {
            synchronized (this.mWriteBufferLock) {
                writeLength = Math.min(src.length - offset, this.mWriteBuffer.length);
                if (offset == 0) {
                    writeBuffer = src;
                } else {
                    System.arraycopy(src, offset, this.mWriteBuffer, 0, writeLength);
                    writeBuffer = this.mWriteBuffer;
                }
                amtWritten = this.mConnection.bulkTransfer(this.mWriteEndpoint, writeBuffer, writeLength, timeoutMillis);
            }
            if (amtWritten <= 0) {
                throw new IOException("Error writing " + writeLength + " bytes at offset " + offset + " length=" + src.length);
            }
            Log.d(this.TAG, "Wrote amt=" + amtWritten + " attempted=" + writeLength);
            offset += amtWritten;
        }
        return offset;
    }

    @Deprecated
    public int setBaudRate(int baudRate) throws IOException {
        this.mBaudRate = baudRate;
        setParameters(this.mBaudRate, this.mDataBits, this.mStopBits, this.mParity);
        return this.mBaudRate;
    }

    public void setParameters(int baudRate, int dataBits, int stopBits, int parity) {
        byte stopBitsByte;
        byte parityBitesByte;
        switch (stopBits) {
            case 1:
                stopBitsByte = 1;
                break;
            case 2:
                stopBitsByte = 3;
                break;
            case 3:
                stopBitsByte = 2;
                break;
            default:
                throw new IllegalArgumentException("Bad value for stopBits: " + stopBits);
        }
        switch (parity) {
            case 0:
                parityBitesByte = 0;
                break;
            case 1:
                parityBitesByte = 1;
                break;
            case 2:
                parityBitesByte = 2;
                break;
            case 3:
                parityBitesByte = 3;
                break;
            case 4:
                parityBitesByte = 4;
                break;
            default:
                throw new IllegalArgumentException("Bad value for parity: " + parity);
        }
        sendAcmControlMessage(32, 0, new byte[]{(byte) (baudRate & 255), (byte) ((baudRate >> 8) & 255), (byte) ((baudRate >> 16) & 255), (byte) ((baudRate >> 24) & 255), stopBitsByte, parityBitesByte, (byte) dataBits});
    }

    public boolean getCD() throws IOException {
        return false;
    }

    public boolean getCTS() throws IOException {
        return false;
    }

    public boolean getDSR() throws IOException {
        return false;
    }

    public boolean getDTR() throws IOException {
        return this.mDtr;
    }

    public void setDTR(boolean value) throws IOException {
        this.mDtr = value;
        setDtrRts();
    }

    public boolean getRI() throws IOException {
        return false;
    }

    public boolean getRTS() throws IOException {
        return this.mRts;
    }

    public void setRTS(boolean value) throws IOException {
        this.mRts = value;
        setDtrRts();
    }

    private void setDtrRts() {
        int i;
        if (this.mRts) {
            i = 2;
        } else {
            i = 0;
        }
        sendAcmControlMessage(34, i | (this.mDtr ? 1 : 0), (byte[]) null);
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        Map<Integer, int[]> supportedDevices = new LinkedHashMap<>();
        supportedDevices.put(Integer.valueOf(UsbId.VENDOR_ARDUINO), new int[]{1, 67, 16, 66, 59, 68, 63, 68, UsbId.ARDUINO_LEONARDO});
        supportedDevices.put(Integer.valueOf(UsbId.VENDOR_VAN_OOIJEN_TECH), new int[]{1155});
        supportedDevices.put(1003, new int[]{UsbId.ATMEL_LUFA_CDC_DEMO_APP, UsbId.ATMEL_ROBOCLAW});
        if (UsbId.DRIVER_CUSTOM == 3) {
            supportedDevices.put(Integer.valueOf(UsbId.VENDOR_CUSTOM), new int[]{UsbId.PRODUCT_CUSTOM});
        }
        return supportedDevices;
    }
}
