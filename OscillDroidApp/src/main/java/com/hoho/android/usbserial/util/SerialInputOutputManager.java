package com.hoho.android.usbserial.util;

import android.util.Log;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SerialInputOutputManager implements Runnable {
    private static final int BUFSIZ = 4096;
    private static final boolean DEBUG = true;
    private static final int READ_WAIT_MILLIS = 200;
    private static final String TAG = SerialInputOutputManager.class.getSimpleName();
    private final UsbSerialDriver mDriver;
    private Listener mListener;
    private final ByteBuffer mReadBuffer;
    private State mState;
    private final ByteBuffer mWriteBuffer;

    public interface Listener {
        void onNewData(byte[] bArr);

        void onRunError(Exception exc);
    }

    private enum State {
        STOPPED,
        RUNNING,
        STOPPING
    }

    public SerialInputOutputManager(UsbSerialDriver driver) {
        this(driver, (Listener) null);
    }

    public SerialInputOutputManager(UsbSerialDriver driver, Listener listener) {
        this.mReadBuffer = ByteBuffer.allocate(BUFSIZ);
        this.mWriteBuffer = ByteBuffer.allocate(BUFSIZ);
        this.mState = State.STOPPED;
        this.mDriver = driver;
        this.mListener = listener;
    }

    public synchronized void setListener(Listener listener) {
        this.mListener = listener;
    }

    public synchronized Listener getListener() {
        return this.mListener;
    }

    public void writeAsync(byte[] data) {
        synchronized (this.mWriteBuffer) {
            this.mWriteBuffer.put(data);
        }
    }

    public synchronized void stop() {
        if (getState() == State.RUNNING) {
            Log.i(TAG, "Stop requested");
            this.mState = State.STOPPING;
        }
    }

    private synchronized State getState() {
        return this.mState;
    }

    public void run() {
        synchronized (this) {
            if (getState() != State.STOPPED) {
                throw new IllegalStateException("Already running.");
            }
            this.mState = State.RUNNING;
        }
        Log.i(TAG, "Running ..");
        while (getState() == State.RUNNING) {
            try {
                step();
            } catch (Exception e) {
                Exception e2 = e;
                Log.w(TAG, "Run ending due to exception: " + e2.getMessage(), e2);
                Listener listener = getListener();
                if (listener != null) {
                    listener.onRunError(e2);
                }
                synchronized (this) {
                    this.mState = State.STOPPED;
                    Log.i(TAG, "Stopped.");
                    return;
                }
            } catch (Throwable th) {
                synchronized (this) {
                    this.mState = State.STOPPED;
                    Log.i(TAG, "Stopped.");
                    throw th;
                }
            }
        }
        Log.i(TAG, "Stopping mState=" + getState());
        synchronized (this) {
            this.mState = State.STOPPED;
            Log.i(TAG, "Stopped.");
        }
    }

    private void step() throws IOException {
        int len = this.mDriver.read(this.mReadBuffer.array(), READ_WAIT_MILLIS);
        if (len > 0) {
            Log.d(TAG, "Read data len=" + len);
            Listener listener = getListener();
            if (listener != null) {
                byte[] data = new byte[len];
                this.mReadBuffer.get(data, 0, len);
                listener.onNewData(data);
            }
            this.mReadBuffer.clear();
        }
        byte[] outBuff = null;
        synchronized (this.mWriteBuffer) {
            if (this.mWriteBuffer.position() > 0) {
                len = this.mWriteBuffer.position();
                outBuff = new byte[len];
                this.mWriteBuffer.rewind();
                this.mWriteBuffer.get(outBuff, 0, len);
                this.mWriteBuffer.clear();
            }
        }
        if (outBuff != null) {
            Log.d(TAG, "Writing data len=" + len);
            this.mDriver.write(outBuff, READ_WAIT_MILLIS);
        }
    }
}
