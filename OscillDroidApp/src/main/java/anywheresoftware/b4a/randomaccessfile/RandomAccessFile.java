package anywheresoftware.b4a.randomaccessfile;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.collections.Map;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

@BA.Version(1.32f)
@BA.ShortName("RandomAccessFile")
public class RandomAccessFile {
    private static final byte ARRAY_TYPE = 5;
    private static final byte B4ATYPE_TYPE = 7;
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte COMPRESS = 1;
    private static final byte ENCRYPT = 2;
    private static final byte[] IV = {116, 13, 72, -50, 77, 45, -3, -72, -117, 32, 23, 19, 72, 21, 111, 22};
    private static final String KEYGEN_ALGORITHM = "PBEWITHSHAAND256BITAES-CBC-BC";
    private static final byte LIST_TYPE = 1;
    private static final byte MAP_TYPE = 4;
    private static final byte NOT_WRAPPED = 3;
    private static final byte NULL_TYPE = 8;
    private static final byte[] SALT = {12, 54, 23, 45, 23, 52, 12};
    private static final byte SERIALIZED_TYPE = 6;
    private static final byte WRAPPED = 2;
    public long CurrentPosition;
    private ByteBuffer bb4;
    private ByteBuffer bb8;
    private FileChannel channel;

    public void Initialize(String Dir, String File, boolean ReadOnly) throws FileNotFoundException {
        Initialize2(Dir, File, ReadOnly, false);
    }

    public void Initialize2(String Dir, String File, boolean ReadOnly, boolean LittleEndian) throws FileNotFoundException {
        this.channel = new java.io.RandomAccessFile(new File(Dir, File), ReadOnly ? "r" : "rw").getChannel();
        this.bb4 = ByteBuffer.allocateDirect(4);
        this.bb8 = ByteBuffer.allocateDirect(8);
        if (LittleEndian) {
            this.bb4.order(ByteOrder.LITTLE_ENDIAN);
            this.bb8.order(ByteOrder.LITTLE_ENDIAN);
        }
        this.CurrentPosition = 0;
    }

    public void Initialize3(byte[] Buffer, boolean LittleEndian) {
        this.channel = new ByteArrayChannel(Buffer);
        this.bb4 = ByteBuffer.allocateDirect(4);
        this.bb8 = ByteBuffer.allocateDirect(8);
        if (LittleEndian) {
            this.bb4.order(ByteOrder.LITTLE_ENDIAN);
            this.bb8.order(ByteOrder.LITTLE_ENDIAN);
        }
        this.CurrentPosition = 0;
    }

    public long getSize() throws IOException {
        return this.channel.size();
    }

    public void Close() throws IOException {
        this.channel.close();
    }

    public void Flush() throws IOException {
        this.channel.force(true);
    }

    public int ReadInt(long Position) throws IOException {
        this.bb4.clear();
        this.channel.read(this.bb4, Position);
        this.bb4.flip();
        this.CurrentPosition = 4 + Position;
        return this.bb4.getInt();
    }

    public float ReadFloat(long Position) throws IOException {
        this.bb4.clear();
        this.channel.read(this.bb4, Position);
        this.bb4.flip();
        this.CurrentPosition = 4 + Position;
        return this.bb4.getFloat();
    }

    public short ReadShort(long Position) throws IOException {
        this.bb4.clear();
        this.bb4.limit(2);
        this.channel.read(this.bb4, Position);
        this.bb4.flip();
        this.CurrentPosition = 2 + Position;
        return this.bb4.getShort();
    }

    public long ReadLong(long Position) throws IOException {
        this.bb8.clear();
        this.channel.read(this.bb8, Position);
        this.bb8.flip();
        this.CurrentPosition = 8 + Position;
        return this.bb8.getLong();
    }

    public double ReadDouble(long Position) throws IOException {
        this.bb8.clear();
        this.channel.read(this.bb8, Position);
        this.bb8.flip();
        this.CurrentPosition = 8 + Position;
        return this.bb8.getDouble();
    }

    public int ReadUnsignedByte(long Position) throws IOException {
        this.CurrentPosition = 1 + Position;
        return ReadSignedByte(Position) & 255;
    }

    public byte ReadSignedByte(long Position) throws IOException {
        this.CurrentPosition = 1 + Position;
        this.bb4.clear();
        this.bb4.limit(1);
        this.channel.read(this.bb4, Position);
        this.bb4.flip();
        return this.bb4.get();
    }

    public int ReadBytes(byte[] Buffer, int StartOffset, int Length, long Position) throws IOException {
        int c = this.channel.read(ByteBuffer.wrap(Buffer, StartOffset, Length), Position);
        this.CurrentPosition = ((long) c) + Position;
        return c;
    }

    public void WriteInt(int Value, long Position) throws IOException {
        this.bb4.clear();
        this.bb4.putInt(Value);
        this.bb4.flip();
        this.CurrentPosition = 4 + Position;
        this.channel.write(this.bb4, Position);
    }

    public void WriteFloat(float Value, long Position) throws IOException {
        this.bb4.clear();
        this.bb4.putFloat(Value);
        this.bb4.flip();
        this.CurrentPosition = 4 + Position;
        this.channel.write(this.bb4, Position);
    }

    public void WriteShort(short Value, long Position) throws IOException {
        this.bb4.clear();
        this.bb4.putShort(Value);
        this.bb4.flip();
        this.CurrentPosition = 2 + Position;
        this.channel.write(this.bb4, Position);
    }

    public void WriteLong(long Value, long Position) throws IOException {
        this.bb8.clear();
        this.bb8.putLong(Value);
        this.bb8.flip();
        this.CurrentPosition = 8 + Position;
        this.channel.write(this.bb8, Position);
    }

    public void WriteDouble(double Value, long Position) throws IOException {
        this.bb8.clear();
        this.bb8.putDouble(Value);
        this.bb8.flip();
        this.CurrentPosition = 8 + Position;
        this.channel.write(this.bb8, Position);
    }

    public void WriteByte(byte Byte, long Position) throws IOException {
        this.bb4.clear();
        this.bb4.put(Byte);
        this.bb4.flip();
        this.CurrentPosition = 1 + Position;
        this.channel.write(this.bb4, Position);
    }

    public int WriteBytes(byte[] Buffer, int StartOffset, int Length, long Position) throws IOException {
        int c = this.channel.write(ByteBuffer.wrap(Buffer, StartOffset, Length), Position);
        this.CurrentPosition = ((long) c) + Position;
        return c;
    }

    public void WriteObject(Object Object, boolean Compress, long Position) throws IllegalArgumentException, IllegalAccessException, IOException {
        writeHelper(Compress ? (byte) 1 : 0, Object, (String) null, Position);
    }

    public void WriteEncryptedObject(Object Object, String Password, long Position) throws IllegalArgumentException, IOException, IllegalAccessException {
        writeHelper((byte) 2, Object, Password, Position);
    }

    private void writeHelper(byte mode, Object Object, String password, long Position) throws IOException, IllegalArgumentException, IllegalAccessException {
        ObjectOutputStream out;
        int c;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        if (mode == 1 || mode == 2) {
            out = new ObjectOutputStream(new GZIPOutputStream(bo));
        } else {
            out = new ObjectOutputStream(bo);
        }
        writeObject(out, Object);
        out.close();
        byte[] b = bo.toByteArray();
        if (mode == 2) {
            b = encrypt(b, password);
        }
        int size = b.length + 1;
        WriteInt(size, Position);
        WriteByte(mode, 4 + Position);
        ByteBuffer bb = ByteBuffer.wrap(b);
        int offset = 0;
        do {
            c = this.channel.write(bb, 5 + Position + ((long) offset));
            offset += c;
        } while (c > 0);
        this.CurrentPosition = ((((long) size) + Position) - 1) + 5;
    }

    private byte[] encrypt(byte[] data, String password) {
        try {
            SecretKey secret = new SecretKeySpec(SecretKeyFactory.getInstance(KEYGEN_ALGORITHM).generateSecret(new PBEKeySpec(password.toCharArray(), SALT, 1024, 256)).getEncoded(), "AES");
            Cipher e = Cipher.getInstance(CIPHER_ALGORITHM);
            e.init(1, secret, new IvParameterSpec(IV));
            return e.doFinal(data);
        } catch (GeneralSecurityException e2) {
            throw new RuntimeException("Invalid environment", e2);
        }
    }

    public Object ReadObject(long Position) throws IOException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        return readHelper((String) null, Position);
    }

    public Object ReadEncryptedObject(String Password, long Position) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return readHelper(Password, Position);
    }

    private Object readHelper(String password, long Position) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ObjectInputStream oin;
        int size = ReadInt(Position);
        byte mode = ReadSignedByte(4 + Position);
        byte[] b = new byte[(size - 1)];
        ByteBuffer bb = ByteBuffer.wrap(b);
        int i = this.channel.read(bb, 5 + Position);
        while (i < size) {
            int c = this.channel.read(bb, 5 + Position + ((long) i));
            if (c == 0) {
                break;
            }
            i += c;
        }
        this.CurrentPosition = ((5 + Position) + ((long) size)) - 1;
        if (mode == 2) {
            if (password == null) {
                throw new RuntimeException("Data was encrypted. You should provide password.");
            }
            b = decrypt(b, password);
        }
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        if (mode == 1 || mode == 2) {
            oin = new ObjectInputStream(new GZIPInputStream(in));
        } else {
            oin = new ObjectInputStream(in);
        }
        Object o = readObject(oin);
        oin.close();
        return o;
    }

    private byte[] decrypt(byte[] b, String password) {
        try {
            SecretKey secret = new SecretKeySpec(SecretKeyFactory.getInstance(KEYGEN_ALGORITHM).generateSecret(new PBEKeySpec(password.toCharArray(), SALT, 1024, 256)).getEncoded(), "AES");
            Cipher d = Cipher.getInstance(CIPHER_ALGORITHM);
            d.init(2, secret, new IvParameterSpec(IV));
            return d.doFinal(b);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Error decrypting object.", e);
        }
    }

    private void writeObject(ObjectOutputStream out, Object o) throws IOException, IllegalArgumentException, IllegalAccessException {
        Object oo;
        if (o instanceof ObjectWrapper) {
            oo = ((ObjectWrapper) o).getObject();
        } else {
            oo = o;
        }
        if (oo instanceof Map) {
            out.write(4);
            writeMap(out, o);
        } else if (oo instanceof List) {
            out.write(1);
            writeList(out, o);
        } else if (o != null && o.getClass().isArray()) {
            out.write(5);
            writeArray(out, o);
        } else if (o instanceof Serializable) {
            out.write(6);
            out.writeObject(o);
        } else if (o == null || !o.getClass().getPackage().getName().equals(BA.packageName)) {
            out.write(8);
        } else {
            out.write(7);
            writeType(out, o);
        }
    }

    private Object readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        switch (in.read()) {
            case 1:
                return readList(in);
            case 4:
                return readMap(in);
            case 5:
                return readArray(in);
            case 6:
                return in.readObject();
            case 7:
                return readType(in);
            case 8:
                return null;
            default:
                return null;
        }
    }

    private void writeType(ObjectOutputStream out, Object type) throws IOException, IllegalArgumentException, IllegalAccessException {
        out.writeObject(type.getClass().getName());
        Map.MyMap map = new Map.MyMap();
        for (Field f : type.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            map.put(f.getName(), f.get(type));
        }
        writeMap(out, map);
    }

    private Object readType(ObjectInputStream in) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Map.MyMap map = (Map.MyMap) readMap(in);
        Class<?> c = Class.forName((String) in.readObject());
        Object o = c.newInstance();
        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
            f.set(o, map.get(f.getName()));
        }
        return o;
    }

    private void writeMap(ObjectOutputStream out, Object m) throws IOException, IllegalArgumentException, IllegalAccessException {
        java.util.Map<Object, Object> map;
        if (m instanceof ObjectWrapper) {
            map = (java.util.Map) ((ObjectWrapper) m).getObject();
            out.write(2);
        } else {
            map = (java.util.Map) m;
            out.write(3);
        }
        out.writeInt(map.size());
        for (java.util.Map.Entry e : map.entrySet()) {
            writeObject(out, e.getKey());
            writeObject(out, e.getValue());
        }
    }

    private Object readMap(ObjectInputStream in) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        boolean shouldWrap = in.readByte() == 2;
        Map.MyMap Map = new Map.MyMap();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Map.put(readObject(in), readObject(in));
        }
        if (!shouldWrap) {
            return Map;
        }
        anywheresoftware.b4a.objects.collections.Map m = new anywheresoftware.b4a.objects.collections.Map();
        m.setObject(Map);
        return m;
    }

    private void writeArray(ObjectOutputStream out, Object array) throws IOException, IllegalArgumentException, IllegalAccessException {
        out.writeObject(array.getClass().getComponentType());
        int size = Array.getLength(array);
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            writeObject(out, Array.get(array, i));
        }
    }

    private Object readArray(ObjectInputStream in) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        int size = in.readInt();
        Object arr = Array.newInstance((Class) in.readObject(), size);
        for (int i = 0; i < size; i++) {
            Array.set(arr, i, readObject(in));
        }
        return arr;
    }

    private void writeList(ObjectOutputStream out, Object l) throws IOException, IllegalArgumentException, IllegalAccessException {
        List list;
        if (l instanceof ObjectWrapper) {
            list = (List) ((ObjectWrapper) l).getObject();
            out.write(2);
        } else {
            list = (List) l;
            out.write(3);
        }
        out.writeInt(list.size());
        for (int i = 0; i < list.size(); i++) {
            writeObject(out, list.get(i));
        }
    }

    private Object readList(ObjectInputStream in) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        boolean shouldWrap = in.readByte() == 2;
        List list = new ArrayList();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            list.add(readObject(in));
        }
        if (!shouldWrap) {
            return list;
        }
        anywheresoftware.b4a.objects.collections.List l = new anywheresoftware.b4a.objects.collections.List();
        l.setObject(list);
        return l;
    }

    private static class ByteArrayChannel extends FileChannel {
        private byte[] buffer;

        public ByteArrayChannel(byte[] buffer2) {
            this.buffer = buffer2;
        }

        public void force(boolean metaData) throws IOException {
        }

        public FileLock lock(long position, long size, boolean shared) throws IOException {
            return null;
        }

        public MappedByteBuffer map(FileChannel.MapMode mode, long position, long size) throws IOException {
            return null;
        }

        public long position() throws IOException {
            return 0;
        }

        public FileChannel position(long newPosition) throws IOException {
            return null;
        }

        public int read(ByteBuffer dst) throws IOException {
            throw new UnsupportedOperationException();
        }

        public int read(ByteBuffer dst, long position) throws IOException {
            int pos = (int) position;
            while (dst.hasRemaining()) {
                dst.put(this.buffer[pos]);
                pos++;
            }
            return pos - ((int) position);
        }

        public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
            return 0;
        }

        public long size() throws IOException {
            return (long) this.buffer.length;
        }

        public long transferFrom(ReadableByteChannel src, long position, long count) throws IOException {
            return 0;
        }

        public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
            return 0;
        }

        public FileChannel truncate(long size) throws IOException {
            return null;
        }

        public FileLock tryLock(long position, long size, boolean shared) throws IOException {
            return null;
        }

        public int write(ByteBuffer src) throws IOException {
            return 0;
        }

        public int write(ByteBuffer src, long position) throws IOException {
            int pos = (int) position;
            while (src.hasRemaining()) {
                this.buffer[pos] = src.get();
                pos++;
            }
            return pos - ((int) position);
        }

        public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
            return 0;
        }

        /* access modifiers changed from: protected */
        public void implCloseChannel() throws IOException {
        }
    }
}
