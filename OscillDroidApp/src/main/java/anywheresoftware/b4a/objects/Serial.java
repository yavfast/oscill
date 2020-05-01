package anywheresoftware.b4a.objects;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.Map;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@BA.Version(1.23f)
@BA.ShortName("Serial")
public class Serial implements BA.CheckForReinitialize {
    private BluetoothAdapter blueAdapter;
    /* access modifiers changed from: private */
    public String eventName;
    /* access modifiers changed from: private */
    public volatile BluetoothServerSocket serverSocket;
    /* access modifiers changed from: private */
    public BluetoothSocket socketClientConnecting;
    /* access modifiers changed from: private */
    public volatile BluetoothSocket workingSocket;

    public void Initialize(String EventName) {
        this.eventName = EventName.toLowerCase(BA.cul);
        this.blueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean IsInitialized() {
        return this.blueAdapter != null;
    }

    public boolean IsEnabled() {
        if (this.blueAdapter == null) {
            return false;
        }
        return this.blueAdapter.isEnabled();
    }

    public String getAddress() {
        return this.blueAdapter.getAddress();
    }

    public String getName() {
        return this.blueAdapter.getName();
    }

    public Map GetPairedDevices() {
        Map m = new Map();
        m.Initialize();
        for (BluetoothDevice b : this.blueAdapter.getBondedDevices()) {
            if (m.ContainsKey(b.getName())) {
                m.Put(b.getName() + " (" + b.getAddress() + ")", b.getAddress());
            } else {
                m.Put(b.getName(), b.getAddress());
            }
        }
        return m;
    }

    public void Connect(BA ba, String MacAddress) throws IOException {
        Connect2(ba, MacAddress, "00001101-0000-1000-8000-00805F9B34FB");
    }

    public void Connect3(BA ba, String MacAddress, int Port) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        BluetoothDevice bd = this.blueAdapter.getRemoteDevice(MacAddress);
        this.socketClientConnecting = (BluetoothSocket) BluetoothDevice.class.getMethod("createRfcommSocket", new Class[]{Integer.TYPE}).invoke(bd, new Object[]{Port});
        afterConnection(ba);
    }

    public void ConnectInsecure(BA ba, BluetoothAdmin Admin, String MacAddress, int Port) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        BluetoothDevice bd = this.blueAdapter.getRemoteDevice(MacAddress);
        this.socketClientConnecting = (BluetoothSocket) BluetoothDevice.class.getMethod("createInsecureRfcommSocket", new Class[]{Integer.TYPE}).invoke(bd, new Object[]{Port});
        afterConnection(ba);
    }

    public void Connect2(BA ba, String MacAddress, String UUID) throws IOException {
        this.socketClientConnecting = this.blueAdapter.getRemoteDevice(MacAddress).createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
        afterConnection(ba);
    }

    private void afterConnection(final BA ba) {
        BA.submitRunnable(new Runnable() {
            public void run() {
                try {
                    Serial.this.socketClientConnecting.connect();
                    synchronized (this) {
                        Serial.this.workingSocket = Serial.this.socketClientConnecting;
                        Serial.this.socketClientConnecting = null;
                    }
                    ba.raiseEventFromDifferentThread(Serial.this, Serial.this, 0, Serial.this.eventName + "_connected", false, new Object[]{true});
                } catch (Exception e) {
                    Exception e2 = e;
                    if (Serial.this.workingSocket == null) {
                        e2.printStackTrace();
                        ba.setLastException(e2);
                        ba.raiseEventFromDifferentThread(Serial.this, Serial.this, 0, Serial.this.eventName + "_connected", false, new Object[]{false});
                    }
                }
            }
        }, this, 0);
    }

    public void Disconnect() throws IOException {
        safeClose(this.socketClientConnecting);
        StopListening();
        safeClose(this.workingSocket);
        this.socketClientConnecting = null;
        this.serverSocket = null;
        this.workingSocket = null;
    }

    private void safeClose(Closeable c) {
        if (c != null) {
            int retries = 3;
            while (retries > 0) {
                try {
                    c.close();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    retries--;
                }
            }
        }
    }

    public void StopListening() throws IOException {
        if (this.serverSocket != null) {
            BluetoothServerSocket b = this.serverSocket;
            this.serverSocket = null;
            safeClose(b);
        }
    }

    public void Listen(BA ba) throws IOException {
        Listen2("B4A", "00001101-0000-1000-8000-00805F9B34FB", ba);
    }

    public void Listen2(String Name, String UUID, BA ba) throws IOException {
        if (this.serverSocket == null) {
            this.serverSocket = this.blueAdapter.listenUsingRfcommWithServiceRecord(Name, java.util.UUID.fromString(UUID));
            BA.submitRunnable(new ListenToConnection(ba), this, 1);
        }
    }

    public void ListenInsecure(BA ba, BluetoothAdmin Admin, int Port) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        if (this.serverSocket == null) {
            this.serverSocket = (BluetoothServerSocket) BluetoothAdapter.class.getMethod("listenUsingInsecureRfcommOn", new Class[]{Integer.TYPE}).invoke(this.blueAdapter, new Object[]{Port});
            BA.submitRunnable(new ListenToConnection(ba), this, 1);
        }
    }

    public InputStream getInputStream() throws IOException {
        return this.workingSocket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.workingSocket.getOutputStream();
    }

    private class ListenToConnection implements Runnable {
        private BA ba;

        public ListenToConnection(BA ba2) throws IOException {
            this.ba = ba2;
        }

        public void run() {
            while (Serial.this.serverSocket != null) {
                try {
                    BluetoothSocket bs = Serial.this.serverSocket.accept();
                    synchronized (Serial.this) {
                        Serial.this.workingSocket = bs;
                        if (Serial.this.socketClientConnecting != null) {
                            Serial.this.socketClientConnecting.close();
                        }
                        Serial.this.socketClientConnecting = null;
                    }
                    this.ba.raiseEventFromDifferentThread(Serial.this, Serial.this, 0, String.valueOf(Serial.this.eventName) + "_connected", false, new Object[]{true});
                } catch (IOException e) {
                    if (Serial.this.workingSocket == null && Serial.this.serverSocket != null) {
                        this.ba.setLastException(e);
                        this.ba.raiseEventFromDifferentThread(Serial.this, Serial.this, 0, String.valueOf(Serial.this.eventName) + "_connected", false, new Object[]{false});
                    }
                    try {
                        if (Serial.this.serverSocket != null) {
                            Thread.sleep(2000);
                        }
                    } catch (InterruptedException e3) {
                    }
                }
            }
        }
    }

    @BA.ShortName("BluetoothAdmin")
    public static class BluetoothAdmin {
        public static final int STATE_OFF = 10;
        public static final int STATE_ON = 12;
        public static final int STATE_TURNING_OFF = 13;
        public static final int STATE_TURNING_ON = 11;
        private BluetoothAdapter blueAdapter;
        /* access modifiers changed from: private */
        public String eventName;

        public void Initialize(final BA ba, String EventName) {
            this.eventName = EventName.toLowerCase(BA.cul);
            if (this.blueAdapter == null) {
                this.blueAdapter = BluetoothAdapter.getDefaultAdapter();
                BroadcastReceiver br = new BroadcastReceiver() {
                    public void onReceive(Context context, final Intent intent) {
                        Handler handler = BA.handler;
                        handler.post(new BA.B4ARunnable() {
                            public void run() {
                                BluetoothDevice bd;
                                String action = intent.getAction();
                                if (action != null) {
                                    if (action.equals("android.bluetooth.adapter.action.DISCOVERY_STARTED")) {
                                        ba.raiseEvent(BluetoothAdmin.this, BluetoothAdmin.this.eventName + "_discoverystarted", new Object[0]);
                                    } else if (action.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
                                        ba.raiseEvent(BluetoothAdmin.this, BluetoothAdmin.this.eventName + "_discoveryfinished", new Object[0]);
                                    } else if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                                        ba.raiseEvent(BluetoothAdmin.this, BluetoothAdmin.this.eventName + "_statechanged",
                                                intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1),
                                                intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -1));
                                    } else if (action.equals("android.bluetooth.device.action.FOUND") && (bd = intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) != null) {
                                        String name = bd.getName();
                                        if (name == null) {
                                            name = "";
                                        }
                                        ba.raiseEvent(BluetoothAdmin.this, BluetoothAdmin.this.eventName + "_devicefound", name, bd.getAddress());
                                    }
                                }
                            }
                        });
                    }
                };
                IntentFilter f = new IntentFilter();
                f.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
                f.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
                f.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
                f.addAction("android.bluetooth.device.action.FOUND");
                BA.applicationContext.registerReceiver(br, f);
            }
        }

        public boolean IsInitialized() {
            return this.blueAdapter != null;
        }

        public boolean Enable() {
            return this.blueAdapter.enable();
        }

        public boolean Disable() {
            return this.blueAdapter.disable();
        }

        public boolean IsEnabled() {
            return this.blueAdapter.isEnabled();
        }

        public boolean CancelDiscovery() {
            return this.blueAdapter.cancelDiscovery();
        }

        public boolean StartDiscovery() {
            return this.blueAdapter.startDiscovery();
        }
    }
}
