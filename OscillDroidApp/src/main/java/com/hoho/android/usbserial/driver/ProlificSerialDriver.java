package com.hoho.android.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProlificSerialDriver extends CommonUsbSerialDriver {
    private static final int GET_LINE_CODING = 33;
    private static final int SEND_BREAK = 35;
    private static final int SET_CONTROL_LINE_STATE = 34;
    private static final int SET_LINE_CODING = 32;
    private static final int TIME_OUT = 200;
    private static final int USB_RECIP_INTERFACE = 1;
    private static final int USB_RT_ACM = 33;
    private final String TAG = CdcAcmSerialDriver.class.getSimpleName();
    private int mBaudRate;
    private int mDataBits;
    private boolean mDtr = false;
    private UsbEndpoint mIntEndpoint;
    private UsbInterface mInterface;
    private int mParity;
    private UsbEndpoint mReadEndpoint;
    private boolean mRts = false;
    private int mStopBits;
    private UsbEndpoint mWriteEndpoint;

    public ProlificSerialDriver(UsbDevice device, UsbDeviceConnection connection) {
        super(device, connection);
    }

    public void open() throws IOException {
        Log.d(this.TAG, "claiming interfaces, count=" + this.mDevice.getInterfaceCount());
        Log.d(this.TAG, "Claiming interface.");
        this.mInterface = this.mDevice.getInterface(0);
        Log.d(this.TAG, "Control iface=" + this.mInterface);
        if (!this.mConnection.claimInterface(this.mInterface, true)) {
            throw new IOException("Could not claim interface.");
        }
        this.mIntEndpoint = this.mInterface.getEndpoint(0);
        Log.d(this.TAG, "Read endpoint direction: " + this.mIntEndpoint.getDirection());
        this.mWriteEndpoint = this.mInterface.getEndpoint(1);
        Log.d(this.TAG, "Write endpoint direction: " + this.mWriteEndpoint.getDirection());
        this.mReadEndpoint = this.mInterface.getEndpoint(2);
        Log.d(this.TAG, "Read endpoint direction: " + this.mReadEndpoint.getDirection());
        byte[] rawdescs = this.mConnection.getRawDescriptors();
        int chiptype = 0;
        if (rawdescs[4] == 2) {
            chiptype = 0;
        } else if (rawdescs[7] == 64) {
            chiptype = 2;
            vendorWrite(8, 0);
            vendorWrite(9, 0);
        } else if (rawdescs[4] == 0) {
            chiptype = 1;
        } else if (rawdescs[4] == 255) {
            chiptype = 1;
        }
        byte[] buf = new byte[1];
        vendorRead(33924, 0, buf);
        vendorWrite(1028, 0);
        vendorRead(33924, 0, buf);
        vendorRead(33667, 0, buf);
        vendorRead(33924, 0, buf);
        vendorWrite(1028, 1);
        vendorRead(33924, 0, buf);
        vendorRead(33667, 0, buf);
        vendorWrite(0, 1);
        vendorWrite(1, 0);
        if (chiptype == 2) {
            vendorWrite(2, 68);
        } else {
            vendorWrite(2, 36);
        }
        Log.d(this.TAG, "Setting line coding to 115200/8N1");
        this.mBaudRate = 115200;
        this.mDataBits = 8;
        this.mParity = 0;
        this.mStopBits = 1;
        setParameters(this.mBaudRate, this.mDataBits, this.mStopBits, this.mParity);
    }

    private int sendProlificControlMessage(int request, int value, byte[] buf) {
        int i;
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (buf != null) {
            i = buf.length;
        } else {
            i = 0;
        }
        return usbDeviceConnection.controlTransfer(33, request, value, 0, buf, i, 5000);
    }

    private void vendorRead(int value, int index, byte[] buf) {
        this.mConnection.controlTransfer(FtdiSerialDriver.FTDI_DEVICE_IN_REQTYPE, 1, value, index, buf, 1, TIME_OUT);
    }

    private void vendorWrite(int value, int index) {
        this.mConnection.controlTransfer(64, 1, value, index, (byte[]) null, 0, TIME_OUT);
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
                stopBitsByte = 0;
                break;
            case 2:
                stopBitsByte = 2;
                break;
            case 3:
                stopBitsByte = 1;
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
        sendProlificControlMessage(32, 0, new byte[]{(byte) (baudRate & 255), (byte) ((baudRate >> 8) & 255), (byte) ((baudRate >> 16) & 255), (byte) ((baudRate >> 24) & 255), stopBitsByte, parityBitesByte, (byte) dataBits});
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
        sendProlificControlMessage(34, i | (this.mDtr ? 1 : 0), (byte[]) null);
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        Map<Integer, int[]> supportedDevices = new LinkedHashMap<>();
        supportedDevices.put(Integer.valueOf(UsbId.VENDOR_PROLIFIC), new int[]{8963});
        if (UsbId.DRIVER_CUSTOM == 1) {
            supportedDevices.put(Integer.valueOf(UsbId.VENDOR_CUSTOM), new int[]{UsbId.PRODUCT_CUSTOM});
        }
        return supportedDevices;
    }
}
