package com.hoho.android.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.util.Log;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FtdiSerialDriver extends CommonUsbSerialDriver {
    private static final int DEFAULT_BAUD_RATE = 115200;
    private static final int DEFAULT_DATA_BITS = 8;
    private static final int DEFAULT_PARITY = 0;
    private static final int DEFAULT_STOP_BITS = 1;
    private static final boolean ENABLE_ASYNC_READS = false;
    public static final int FTDI_DEVICE_IN_REQTYPE = 192;
    public static final int FTDI_DEVICE_OUT_REQTYPE = 64;
    private static final int MODEM_STATUS_HEADER_LENGTH = 2;
    private static final int SIO_MODEM_CTRL_REQUEST = 1;
    private static final int SIO_RESET_REQUEST = 0;
    private static final int SIO_RESET_SIO = 0;
    private static final int SIO_SET_BAUD_RATE_REQUEST = 3;
    private static final int SIO_SET_DATA_REQUEST = 4;
    private static final int SIO_SET_FLOW_CTRL_REQUEST = 2;
    public static final int USB_ENDPOINT_IN = 128;
    public static final int USB_ENDPOINT_OUT = 0;
    public static final int USB_READ_TIMEOUT_MILLIS = 5000;
    public static final int USB_RECIP_DEVICE = 0;
    public static final int USB_RECIP_ENDPOINT = 2;
    public static final int USB_RECIP_INTERFACE = 1;
    public static final int USB_RECIP_OTHER = 3;
    public static final int USB_TYPE_CLASS = 0;
    public static final int USB_TYPE_RESERVED = 0;
    public static final int USB_TYPE_STANDARD = 0;
    public static final int USB_TYPE_VENDOR = 0;
    public static final int USB_WRITE_TIMEOUT_MILLIS = 5000;
    private final String TAG = FtdiSerialDriver.class.getSimpleName();
    private int mBaudRate;
    private int mDataBits;
    private int mInterface = 0;
    private int mMaxPacketSize = 64;
    private int mParity;
    private int mStopBits;
    private DeviceType mType = null;

    private enum DeviceType {
        TYPE_BM,
        TYPE_AM,
        TYPE_2232C,
        TYPE_R,
        TYPE_2232H,
        TYPE_4232H
    }

    public FtdiSerialDriver(UsbDevice usbDevice, UsbDeviceConnection usbConnection) {
        super(usbDevice, usbConnection);
    }

    public void reset() throws IOException {
        int result = this.mConnection.controlTransfer(64, 0, 0, 0, (byte[]) null, 0, 5000);
        if (result != 0) {
            throw new IOException("Reset failed: result=" + result);
        }
        this.mType = DeviceType.TYPE_R;
    }

    public void open() throws IOException {
        boolean opened = false;
        int i = 0;
        while (i < this.mDevice.getInterfaceCount()) {
            try {
                if (this.mConnection.claimInterface(this.mDevice.getInterface(i), true)) {
                    Log.d(this.TAG, "claimInterface " + i + " SUCCESS");
                } else {
                    Log.d(this.TAG, "claimInterface " + i + " FAIL");
                }
                i++;
            } finally {
                if (!opened) {
                    close();
                }
            }
        }
        reset();
        setParameters(DEFAULT_BAUD_RATE, 8, 1, 0);
        opened = true;
    }

    public void close() {
        this.mConnection.close();
    }

    public int read(byte[] dest, int timeoutMillis) throws IOException {
        int totalBytesRead;
        UsbEndpoint endpoint = this.mDevice.getInterface(0).getEndpoint(0);
        this.mMaxPacketSize = endpoint.getMaxPacketSize();
        int readAmt = Math.min(dest.length, this.mReadBuffer.length);
        synchronized (this.mReadBufferLock) {
            totalBytesRead = this.mConnection.bulkTransfer(endpoint, this.mReadBuffer, readAmt, timeoutMillis);
        }
        if (totalBytesRead >= 2) {
            return filterModemStatus(this.mReadBuffer, dest, Math.min(readAmt, totalBytesRead));
        }
        throw new IOException("Expected at least 2 bytes");
    }

    /* access modifiers changed from: protected */
    public int filterModemStatus(byte[] src, byte[] dest, int total) {
        int i;
        int dofs = 0;
        int sofs = 0 + 2;
        while (sofs < total) {
            if (total - sofs >= this.mMaxPacketSize - 2) {
                i = this.mMaxPacketSize - 2;
            } else {
                i = total - sofs;
            }
            int len = i;
            System.arraycopy(src, sofs, dest, dofs, len);
            sofs = sofs + len + 2;
            dofs += len;
        }
        return dofs;
    }

    public int write(byte[] src, int timeoutMillis) throws IOException {
        int writeLength;
        byte[] writeBuffer;
        int amtWritten;
        UsbEndpoint endpoint = this.mDevice.getInterface(0).getEndpoint(1);
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
                amtWritten = this.mConnection.bulkTransfer(endpoint, writeBuffer, writeLength, timeoutMillis);
            }
            if (amtWritten <= 0) {
                throw new IOException("Error writing " + writeLength + " bytes at offset " + offset + " length=" + src.length);
            }
            Log.d(this.TAG, "Wrote amtWritten=" + amtWritten + " attempted=" + writeLength);
            offset += amtWritten;
        }
        return offset;
    }

    @Deprecated
    public int setBaudRate(int baudRate) throws IOException {
        long[] vals = convertBaudrate(baudRate);
        long actualBaudrate = vals[0];
        long index = vals[1];
        int result = this.mConnection.controlTransfer(64, 3, (int) vals[2], (int) index, (byte[]) null, 0, 5000);
        if (result == 0) {
            return (int) actualBaudrate;
        }
        throw new IOException("Setting baudrate failed: result=" + result);
    }

    public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException {
        int config;
        int config2;
        this.mBaudRate = setBaudRate(baudRate);
        int config3 = dataBits;
        switch (parity) {
            case 0:
                config = config3 | 0;
                break;
            case 1:
                config = config3 | 256;
                break;
            case 2:
                config = config3 | 512;
                break;
            case 3:
                config = config3 | 768;
                break;
            case 4:
                config = config3 | 1024;
                break;
            default:
                throw new IllegalArgumentException("Unknown parity value: " + parity);
        }
        switch (stopBits) {
            case 1:
                config2 = config | 0;
                break;
            case 2:
                config2 = config | 4096;
                break;
            case 3:
                config2 = config | 2048;
                break;
            default:
                throw new IllegalArgumentException("Unknown stopBits value: " + stopBits);
        }
        int result = this.mConnection.controlTransfer(64, 4, config2, 0, (byte[]) null, 0, 5000);
        if (result != 0) {
            throw new IOException("Setting parameters failed: result=" + result);
        }
        this.mParity = parity;
        this.mStopBits = stopBits;
        this.mDataBits = dataBits;
    }

    private long[] convertBaudrate(int baudrate) {
        int baudDiff;
        long index;
        int divisor = 24000000 / baudrate;
        int bestDivisor = 0;
        int bestBaud = 0;
        int bestBaudDiff = 0;
        int[] fracCode = new int[8];
        fracCode[1] = 3;
        fracCode[2] = 2;
        fracCode[3] = 4;
        fracCode[4] = 1;
        fracCode[5] = 5;
        fracCode[6] = 6;
        fracCode[7] = 7;
        for (int i = 0; i < 2; i++) {
            int tryDivisor = divisor + i;
            if (tryDivisor <= 8) {
                tryDivisor = 8;
            } else if (this.mType != DeviceType.TYPE_AM && tryDivisor < 12) {
                tryDivisor = 12;
            } else if (divisor < 16) {
                tryDivisor = 16;
            } else if (this.mType != DeviceType.TYPE_AM && tryDivisor > 131071) {
                tryDivisor = 131071;
            }
            int baudEstimate = (24000000 + (tryDivisor / 2)) / tryDivisor;
            if (baudEstimate < baudrate) {
                baudDiff = baudrate - baudEstimate;
            } else {
                baudDiff = baudEstimate - baudrate;
            }
            if (i == 0 || baudDiff < bestBaudDiff) {
                bestDivisor = tryDivisor;
                bestBaud = baudEstimate;
                bestBaudDiff = baudDiff;
                if (baudDiff == 0) {
                    break;
                }
            }
        }
        long encodedDivisor = (long) ((bestDivisor >> 3) | (fracCode[bestDivisor & 7] << 14));
        if (encodedDivisor == 1) {
            encodedDivisor = 0;
        } else if (encodedDivisor == 16385) {
            encodedDivisor = 1;
        }
        long value = encodedDivisor & 65535;
        if (this.mType == DeviceType.TYPE_2232C || this.mType == DeviceType.TYPE_2232H || this.mType == DeviceType.TYPE_4232H) {
            index = ((encodedDivisor >> 8) & 65535 & 65280) | 0;
        } else {
            index = (encodedDivisor >> 16) & 65535;
        }
        return new long[]{(long) bestBaud, index, value};
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
        return false;
    }

    public void setDTR(boolean value) throws IOException {
    }

    public boolean getRI() throws IOException {
        return false;
    }

    public boolean getRTS() throws IOException {
        return false;
    }

    public void setRTS(boolean value) throws IOException {
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        Map<Integer, int[]> supportedDevices = new LinkedHashMap<>();
        supportedDevices.put(Integer.valueOf(UsbId.VENDOR_FTDI), new int[]{24577});
        if (UsbId.DRIVER_CUSTOM == 4) {
            supportedDevices.put(Integer.valueOf(UsbId.VENDOR_CUSTOM), new int[]{UsbId.PRODUCT_CUSTOM});
        }
        return supportedDevices;
    }
}
