package com.hoho.android.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import java.io.IOException;

abstract class CommonUsbSerialDriver implements UsbSerialDriver {
    public static final int DEFAULT_READ_BUFFER_SIZE = 16384;
    public static final int DEFAULT_WRITE_BUFFER_SIZE = 16384;
    protected final UsbDeviceConnection mConnection;
    protected final UsbDevice mDevice;
    protected byte[] mReadBuffer;
    protected final Object mReadBufferLock = new Object();
    protected byte[] mWriteBuffer;
    protected final Object mWriteBufferLock = new Object();

    public abstract void close() throws IOException;

    public abstract boolean getCD() throws IOException;

    public abstract boolean getCTS() throws IOException;

    public abstract boolean getDSR() throws IOException;

    public abstract boolean getDTR() throws IOException;

    public abstract boolean getRI() throws IOException;

    public abstract boolean getRTS() throws IOException;

    public abstract void open() throws IOException;

    public abstract int read(byte[] bArr, int i) throws IOException;

    @Deprecated
    public abstract int setBaudRate(int i) throws IOException;

    public abstract void setDTR(boolean z) throws IOException;

    public abstract void setParameters(int i, int i2, int i3, int i4) throws IOException;

    public abstract void setRTS(boolean z) throws IOException;

    public abstract int write(byte[] bArr, int i) throws IOException;

    public CommonUsbSerialDriver(UsbDevice device, UsbDeviceConnection connection) {
        this.mDevice = device;
        this.mConnection = connection;
        this.mReadBuffer = new byte[16384];
        this.mWriteBuffer = new byte[16384];
    }

    public final UsbDevice getDevice() {
        return this.mDevice;
    }

    public final void setReadBufferSize(int bufferSize) {
        synchronized (this.mReadBufferLock) {
            if (bufferSize != this.mReadBuffer.length) {
                this.mReadBuffer = new byte[bufferSize];
            }
        }
    }

    public final void setWriteBufferSize(int bufferSize) {
        synchronized (this.mWriteBufferLock) {
            if (bufferSize != this.mWriteBuffer.length) {
                this.mWriteBuffer = new byte[bufferSize];
            }
        }
    }
}
