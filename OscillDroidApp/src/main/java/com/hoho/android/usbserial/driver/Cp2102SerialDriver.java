package com.hoho.android.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cp2102SerialDriver extends CommonUsbSerialDriver {
    private static final int BAUD_RATE_GEN_FREQ = 3686400;
    private static final int CONTROL_WRITE_DTR = 256;
    private static final int CONTROL_WRITE_RTS = 512;
    private static final int DEFAULT_BAUD_RATE = 9600;
    private static final int MCR_ALL = 3;
    private static final int MCR_DTR = 1;
    private static final int MCR_RTS = 2;
    private static final int REQTYPE_HOST_TO_DEVICE = 65;
    private static final int SILABSER_IFC_ENABLE_REQUEST_CODE = 0;
    private static final int SILABSER_SET_BAUDDIV_REQUEST_CODE = 1;
    private static final int SILABSER_SET_BAUDRATE = 30;
    private static final int SILABSER_SET_LINE_CTL_REQUEST_CODE = 3;
    private static final int SILABSER_SET_MHS_REQUEST_CODE = 7;
    private static final String TAG = Cp2102SerialDriver.class.getSimpleName();
    private static final int UART_DISABLE = 0;
    private static final int UART_ENABLE = 1;
    private static final int USB_WRITE_TIMEOUT_MILLIS = 5000;
    private UsbEndpoint mReadEndpoint;
    private UsbEndpoint mWriteEndpoint;

    public Cp2102SerialDriver(UsbDevice device, UsbDeviceConnection connection) {
        super(device, connection);
    }

    private int setConfigSingle(int request, int value) {
        return this.mConnection.controlTransfer(65, request, value, 0, null, 0, 5000);
    }

    public void open() throws IOException {
        boolean opened = false;
        int i = 0;
        while (i < this.mDevice.getInterfaceCount()) {
            try {
                if (this.mConnection.claimInterface(this.mDevice.getInterface(i), true)) {
                    Log.d(TAG, "claimInterface " + i + " SUCCESS");
                    opened = true;
                } else {
                    Log.d(TAG, "claimInterface " + i + " FAIL");
                }
                i++;
            } finally {
                if (!opened) {
                    close();
                }
            }
        }
        UsbInterface dataIface = this.mDevice.getInterface(this.mDevice.getInterfaceCount() - 1);
        for (int i2 = 0; i2 < dataIface.getEndpointCount(); i2++) {
            UsbEndpoint ep = dataIface.getEndpoint(i2);
            if (ep.getType() == 2) {
                if (ep.getDirection() == 128) {
                    this.mReadEndpoint = ep;
                } else {
                    this.mWriteEndpoint = ep;
                }
            }
        }
        setConfigSingle(0, 1);
        setConfigSingle(7, 771);
        setConfigSingle(1, 384);
        opened = true;
    }

    public void close() throws IOException {
        setConfigSingle(0, 0);
        this.mConnection.close();
    }

    private static String bytesToHexStr(byte[] bytes, int length) {
        StringBuilder sb = new StringBuilder(length * 3);
        String hexByte;
        byte b;
        for (int idx = 0; idx < length; idx++) {
            b = bytes[idx];
            hexByte = Integer.toHexString(b & 0xff);
            if (hexByte.length() == 1) {
                sb.append('0');
            }
            sb.append(hexByte).append(' ');
        }
        return sb.toString();
    }

    private static String bytesToStr(byte[] bytes, int length) {
        StringBuilder sb = new StringBuilder(length);
        byte b;
        for (int idx = 0; idx < length; idx++) {
            b = bytes[idx];
            sb.append((char)b).append(' ');
        }
        return sb.toString();
    }

    public int read(byte[] dest, int timeoutMillis) throws IOException {
        synchronized (mReadBufferLock) {
            int numBytesRead = mConnection.bulkTransfer(mReadEndpoint, mReadBuffer, Math.min(dest.length, mReadBuffer.length), timeoutMillis);
            if (numBytesRead <= 0) {
                return 0;
            }
            Log.i(TAG, "Read from USB: " + bytesToHexStr(dest, numBytesRead));
            System.arraycopy(mReadBuffer, 0, dest, 0, numBytesRead);
            return numBytesRead;
        }
    }

    public int write(byte[] src, int timeoutMillis) throws IOException {
        int writeLength;
        byte[] writeBuffer;
        int amtWritten;
        int offset = 0;
        Log.i(TAG, "Write to USB: " + bytesToHexStr(src, src.length) + " [" + bytesToStr(src, src.length) + "]");
        while (offset < src.length) {
            synchronized (mWriteBufferLock) {
                writeLength = Math.min(src.length - offset, mWriteBuffer.length);
                if (offset == 0) {
                    writeBuffer = src;
                } else {
                    System.arraycopy(src, offset, mWriteBuffer, 0, writeLength);
                    writeBuffer = mWriteBuffer;
                }
                amtWritten = mConnection.bulkTransfer(mWriteEndpoint, writeBuffer, writeLength, timeoutMillis);
            }
            if (amtWritten <= 0) {
                throw new IOException("Error writing " + writeLength + " bytes at offset " + offset + " length=" + src.length);
            }
//            Log.d(TAG, "Wrote amt=" + amtWritten + " attempted=" + writeLength);
            offset += amtWritten;
        }
        return offset;
    }

    @Deprecated
    public int setBaudRate(int baudRate) throws IOException {
        mConnection.controlTransfer(65, 30, 0, 0, new byte[]{(byte) (baudRate & 255), (byte) ((baudRate >> 8) & 255), (byte) ((baudRate >> 16) & 255), (byte) ((baudRate >> 24) & 255)}, 4, 5000);
        return baudRate;
    }

    public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException {
        int configDataBits;
        setBaudRate(baudRate);
        switch (dataBits) {
            case 5:
                configDataBits = 1280;
                break;
            case 6:
                configDataBits = 1536;
                break;
            case 7:
                configDataBits = 1792;
                break;
            case 8:
                configDataBits = 2048;
                break;
            default:
                configDataBits = 2048;
                break;
        }
        setConfigSingle(3, configDataBits);
        int configParityBits = 0;
        switch (parity) {
            case 1:
                configParityBits = 16;
                break;
            case 2:
                configParityBits = 32;
                break;
        }
        setConfigSingle(3, configParityBits);
        int configStopBits = 0;
        switch (stopBits) {
            case 1:
                configStopBits = 0;
                break;
            case 2:
                configStopBits = 2;
                break;
        }
        setConfigSingle(3, configStopBits);
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
        return true;
    }

    public void setDTR(boolean value) throws IOException {
    }

    public boolean getRI() throws IOException {
        return false;
    }

    public boolean getRTS() throws IOException {
        return true;
    }

    public void setRTS(boolean value) throws IOException {
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        Map<Integer, int[]> supportedDevices = new LinkedHashMap<>();
        supportedDevices.put(UsbId.VENDOR_SILAB, new int[]{60000});
        if (UsbId.DRIVER_CUSTOM == 2) {
            supportedDevices.put(UsbId.VENDOR_CUSTOM, new int[]{UsbId.PRODUCT_CUSTOM});
        }
        return supportedDevices;
    }
}
