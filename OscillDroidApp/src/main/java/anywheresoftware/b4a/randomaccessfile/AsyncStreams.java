package anywheresoftware.b4a.randomaccessfile;

import anywheresoftware.b4a.BA;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@BA.ShortName("AsyncStreams")
public class AsyncStreams {
    private AIN ain;
    private AOUT aout;
    /* access modifiers changed from: private */
    public BA ba;
    /* access modifiers changed from: private */
    public String eventName;
    private Thread tin;
    private Thread tout;

    public void Initialize(BA ba2, InputStream In, OutputStream Out, String EventName) throws IOException {
        shared(ba2, In, Out, EventName, false, false);
    }

    public void InitializePrefix(BA ba2, InputStream In, boolean BigEndian, OutputStream Out, String EventName) throws IOException {
        shared(ba2, In, Out, EventName, BigEndian, true);
    }

    private void shared(BA ba2, InputStream In, OutputStream Out, String EventName, boolean BigEndian, boolean Prefix) throws IOException {
        if (IsInitialized()) {
            Close();
        }
        this.ba = ba2;
        this.eventName = EventName.toLowerCase(BA.cul);
        if (In != null) {
            this.ain = new AIN(In, BigEndian, Prefix);
            this.tin = new Thread(this.ain, "ReadThread");
            this.tin.setDaemon(true);
            this.tin.start();
        }
        if (Out != null) {
            this.aout = new AOUT(Out, BigEndian, Prefix);
            this.tout = new Thread(this.aout, "WriteThread");
            this.tout.setDaemon(true);
            this.tout.start();
        }
    }

    public boolean IsInitialized() {
        return this.ain != null || this.aout != null;
    }

    public boolean Write(byte[] Buffer) {
        return Write2(Buffer, 0, Buffer.length);
    }

    public boolean Write2(byte[] Buffer, int Start, int Length) {
        if (this.aout == null) {
            return false;
        }
        return this.aout.put(Buffer, Start, Length);
    }

    public int getOutputQueueSize() {
        if (this.aout == null) {
            return 0;
        }
        return this.aout.queue.size();
    }

    public void Close() throws IOException {
        if (!(this.tin == null || this.ain == null)) {
            this.ain.close();
            if (Thread.currentThread() != this.tin) {
                this.tin.interrupt();
            }
        }
        if (!(this.tout == null || this.aout == null)) {
            this.aout.close();
            if (Thread.currentThread() != this.tout) {
                this.tout.interrupt();
            }
        }
        this.ain = null;
        this.aout = null;
    }

    private class AIN implements Runnable {
        private ByteBuffer bb;
        private byte[] buffer = new byte[8192];
        private String ev;
        private final InputStream in;
        private final boolean prefix;
        private volatile boolean working = true;

        public AIN(InputStream in2, boolean bigEndian, boolean prefix2) {
            this.ev = AsyncStreams.this.eventName + "_newdata";
            this.in = in2;
            this.prefix = prefix2;
            if (prefix2) {
                this.bb = ByteBuffer.wrap(new byte[4]);
                this.bb.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.lang.Object[]} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            byte[] data;
            int count = 0;
            while (this.working) {
                try {
                    if (!this.prefix) {
                        count = this.in.read(this.buffer);
                        if (count == 0) {
                            continue;
                        } else if (count < 0) {
                            closeUnexpected();
                            return;
                        } else if (this.working) {
                            data = new byte[count];
                            System.arraycopy(this.buffer, 0, data, 0, count);
                        } else {
                            return;
                        }
                    } else {
                        while (true) {
                            if (count >= 4) {
                                break;
                            }
                            int c = this.in.read(this.buffer, count, this.buffer.length - count);
                            if (c == -1) {
                                closeUnexpected();
                                break;
                            }
                            count += c;
                        }
                        if (this.working) {
                            this.bb.clear();
                            this.bb.put(this.buffer, 0, 4);
                            int msgLength = this.bb.getInt(0);
                            if (msgLength > this.buffer.length) {
                                byte[] newbuffer = new byte[(msgLength + 4)];
                                System.arraycopy(this.buffer, 0, newbuffer, 0, count);
                                this.buffer = newbuffer;
                            }
                            while (true) {
                                if (count >= msgLength + 4) {
                                    break;
                                }
                                int c2 = this.in.read(this.buffer, count, this.buffer.length - count);
                                if (c2 == -1) {
                                    closeUnexpected();
                                    break;
                                }
                                count += c2;
                            }
                            data = new byte[msgLength];
                            System.arraycopy(this.buffer, 4, data, 0, data.length);
                            if (count > msgLength + 4) {
                                int nextSegment = count - (msgLength + 4);
                                System.arraycopy(this.buffer, msgLength + 4, this.buffer, 0, nextSegment);
                                count = nextSegment;
                            } else {
                                count = 0;
                            }
                        } else {
                            return;
                        }
                    }
                    AsyncStreams.this.ba.raiseEventFromDifferentThread(AsyncStreams.this, null, 0, this.ev, true, new Object[]{data});
                } catch (Exception e) {
                    Exception e2 = e;
                    if (this.working) {
                        AsyncStreams.this.ba.setLastException(e2);
                        AsyncStreams.this.ba.raiseEventFromDifferentThread(AsyncStreams.this, null, 0, AsyncStreams.this.eventName + "_error", false, null);
                        return;
                    }
                    return;
                }
            }
        }

        private void closeUnexpected() throws IOException {
            AsyncStreams.this.ba.raiseEventFromDifferentThread(AsyncStreams.this, (Object) null, 0, String.valueOf(AsyncStreams.this.eventName) + "_terminated", false, (Object[]) null);
            AsyncStreams.this.Close();
        }

        public void close() throws IOException {
            this.working = false;
            this.in.close();
        }
    }

    private class AOUT implements Runnable {
        private ByteBuffer bb;
        private final OutputStream out;
        private boolean prefix;
        /* access modifiers changed from: private */
        public ArrayBlockingQueue<byte[]> queue = new ArrayBlockingQueue<>(100);
        private volatile boolean working = true;

        public AOUT(OutputStream out2, boolean bigEndian, boolean prefix2) {
            this.out = out2;
            this.prefix = prefix2;
            if (prefix2) {
                this.bb = ByteBuffer.wrap(new byte[4]);
                this.bb.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
        }

        public void run() {
            while (this.working) {
                try {
                    this.out.write(this.queue.take());
                } catch (Exception e) {
                    Exception e2 = e;
                    if (this.working) {
                        AsyncStreams.this.ba.setLastException(e2);
                        AsyncStreams.this.ba.raiseEventFromDifferentThread(AsyncStreams.this, (Object) null, 0, String.valueOf(AsyncStreams.this.eventName) + "_error", false, (Object[]) null);
                    }
                }
            }
        }

        public boolean put(byte[] buffer, int start, int len) {
            byte[] b;
            if (!this.prefix) {
                b = new byte[len];
                System.arraycopy(buffer, start, b, 0, len);
            } else {
                b = new byte[(len + 4)];
                synchronized (this.bb) {
                    this.bb.putInt(0, len);
                    System.arraycopy(this.bb.array(), 0, b, 0, 4);
                }
                System.arraycopy(buffer, start, b, 4, len);
            }
            try {
                return this.queue.offer(b, 100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void close() throws IOException {
            this.working = false;
            this.out.close();
        }
    }
}
