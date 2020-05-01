package anywheresoftware.b4a.objects;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.ParcelFileDescriptor;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.constants.KeyCodes;

import com.hoho.android.usbserial.driver.Cp2102SerialDriver;
import com.hoho.android.usbserial.driver.FtdiSerialDriver;
import com.hoho.android.usbserial.driver.UsbId;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;

@BA.Version(2.3f)
@BA.ShortName("UsbSerial")
public class UsbSerial {
    private static final double version = 2.3d;
    public final int DATABITS_5 = 5;
    public final int DATABITS_6 = 6;
    public final int DATABITS_7 = 7;
    public final int DATABITS_8 = 8;
    public final int DRIVER_CDCACM = 3;
    public final int DRIVER_FTDI = UsbId.DRIVER_CUSTOM;
    public final int DRIVER_PROLIFIC = 1;
    public final int DRIVER_SILABS = 2;
    public final int PARITY_EVEN = 2;
    public final int PARITY_MARK = 3;
    public final int PARITY_NONE = 0;
    public final int PARITY_ODD = 1;
    public final int PARITY_SPACE = 4;
    public final int STOPBITS_1 = 1;
    public final int STOPBITS_1_5 = 3;
    public final int STOPBITS_2 = 2;
    /* access modifiers changed from: private */
    public int TIMEOUT = 200;
    public final int USB_ACCESSORY = 2;
    public final int USB_DEVICE = 1;
    public final int USB_NONE = 0;
    private boolean accOpen = false;
    UsbAccessory accessory;
    /* access modifiers changed from: private */
    public volatile UsbSerialDriver driver;
    private boolean driverOpen = false;
    private ParcelFileDescriptor pfd;

    public double getVersion() {
        return version;
    }

    public void LIBRARY_DOC() {
    }

    public static UsbManager getUsbManager() {
        return (UsbManager) BA.applicationContext.getSystemService(Context.USB_SERVICE);
    }

    public int Open(BA ba, int BaudRate) throws IOException {
        UsbManager manager = getUsbManager();
        this.driver = UsbSerialProber.acquire(manager);
        if (this.driver != null) {
            this.driver.open();
            this.driver.setBaudRate(BaudRate);
            this.driverOpen = true;
            return 1;
        }
        UsbAccessory[] a = manager.getAccessoryList();
        if (a == null) {
            return 0;
        }
        this.accessory = a[0];
        this.pfd = manager.openAccessory(this.accessory);
        this.accOpen = true;
        return 2;
    }

    public void Close() throws IOException {
        if (this.driver != null) {
            this.driver.close();
            this.driver = null;
        }
        if (this.accessory != null) {
            this.pfd.close();
            this.accessory = null;
        }
        this.driverOpen = false;
        this.accOpen = false;
    }

    public InputStream GetInputStream() {
        if (this.accOpen) {
            return new FileInputStream(this.pfd.getFileDescriptor());
        }
        return new InputStream() {
            public int read() throws IOException {
                throw new RuntimeException("This method is not supported.");
            }

            public int read(byte[] b) throws IOException {
                return UsbSerial.this.driver.read(b, UsbSerial.this.TIMEOUT);
            }

            public void close() throws IOException {
                UsbSerial.this.Close();
            }
        };
    }

    public OutputStream GetOutputStream() {
        if (this.accOpen) {
            return new FileOutputStream(this.pfd.getFileDescriptor());
        }
        return new OutputStream() {
            public void write(int b) throws IOException {
                throw new RuntimeException("This method is not supported.");
            }

            public void write(byte[] b) throws IOException {
                UsbSerial.this.driver.write(b, UsbSerial.this.TIMEOUT);
            }

            public void close() throws IOException {
                UsbSerial.this.Close();
            }
        };
    }

    public boolean checkDevice(UsbDevice usbDevice) {
        return usbDevice.getVendorId() == UsbId.VENDOR_CUSTOM && usbDevice.getProductId() == UsbId.PRODUCT_CUSTOM;
    }

    public boolean HasPermission() {
        UsbManager manager = getUsbManager();
        HashMap<String, UsbDevice> h = manager.getDeviceList();
        if (h.size() > 0) {
            Iterator<UsbDevice> it = h.values().iterator();
            while (it.hasNext()) {
                UsbDevice usbDevice = it.next();
                if (checkDevice(usbDevice)) {
                    return manager.hasPermission(usbDevice);
                }
            }
        }
//        UsbAccessory[] a = manager.getAccessoryList();
//        if (a != null) {
//            return manager.hasPermission(a[0]);
//        }
        return false;
    }

    public void RequestPermission() {
        UsbManager manager = getUsbManager();
        HashMap<String, UsbDevice> h = manager.getDeviceList();
        if (h.size() > 0) {
            Iterator<UsbDevice> it = h.values().iterator();
            while (it.hasNext()) {
                UsbDevice usbDevice = it.next();
                if (checkDevice(usbDevice)) {
                    manager.requestPermission(usbDevice, PendingIntent.getBroadcast(BA.applicationContext, 0, new Intent("com.android.example.USB_PERMISSION"), 0));
                    return;
                }
            }
        }
//        UsbAccessory[] a = manager.getAccessoryList();
//        if (a != null) {
//            manager.requestPermission(a[0], PendingIntent.getBroadcast(BA.applicationContext, 0, new Intent("com.android.example.USB_PERMISSION"), 0));
//        }
    }

    public void SetParameters(int baudRate, int dataBits, int stopBits, int parity) throws Exception {
        if (this.driverOpen) {
            this.driver.setParameters(baudRate, dataBits, stopBits, parity);
        }
    }

    public int UsbPresent() {
        UsbManager manager = getUsbManager();
        if (manager.getDeviceList().size() > 0) {
            return 1;
        }
        if (manager.getAccessoryList() != null) {
            return 2;
        }
        return 0;
    }

    public String DeviceInfo() throws Exception {
        StringBuilder sb = new StringBuilder();
        UsbManager manager = getUsbManager();
        HashMap<String, UsbDevice> h = manager.getDeviceList();
        if (h.size() == 0) {
            return "No device found. Is an Accessory connected?";
        }
        UsbDevice usb = null;
        Iterator<UsbDevice> it = h.values().iterator();
        while (it.hasNext()) {
            UsbDevice usbDevice = it.next();
            if (checkDevice(usbDevice)) {
                usb = usbDevice;
                break;
            }
        }

        if (usb == null) {
            return null;
        }

/*
        UsbInterface iface = usb.getInterface(0);
        UsbDeviceConnection conn = manager.openDevice(usb);
        conn.claimInterface(iface, false);
        byte[] rawdescs = conn.getRawDescriptors();
        byte idx = rawdescs[14];
        byte[] buffer = new byte[rawdescs[7]];
        conn.controlTransfer(FtdiSerialDriver.USB_ENDPOINT_IN, 6, idx + 768, 0, buffer, buffer.length, 100);
        sb.append("Manufacturer : ").append(bytes0ToString(buffer, idx)).append(10);
        byte idx2 = rawdescs[15];
        byte[] buffer2 = new byte[rawdescs[7]];
        conn.controlTransfer(FtdiSerialDriver.USB_ENDPOINT_IN, 6, idx2 + 768, 0, buffer2, buffer2.length, 100);
        sb.append("Product : ").append(bytes0ToString(buffer2, idx2)).append(10);
        byte idx3 = rawdescs[16];
        byte[] buffer3 = new byte[rawdescs[7]];
        conn.controlTransfer(FtdiSerialDriver.USB_ENDPOINT_IN, 6, idx3 + 768, 0, buffer3, buffer3.length, 100);
        sb.append("Serial : ").append(bytes0ToString(buffer3, idx3)).append(10);
*/
        sb.append('\n');
        sb.append("DeviceName : ").append(usb.getDeviceName()).append('\n');
        sb.append("DeviceClass : ").append(usbClass(usb.getDeviceClass())).append('\n');
        sb.append("DeviceSubClass : ").append(usb.getDeviceSubclass()).append('\n');
        sb.append("Device ID : ").append(toHex(usb.getDeviceId())).append('\n');
        sb.append("ProductId : ").append(toHex(usb.getProductId())).append('\n');
        sb.append("VendorId  :").append(toHex(usb.getVendorId())).append('\n');
        sb.append('\n');
        for (int i = 0; i < usb.getInterfaceCount(); i++) {
            UsbInterface ifce = usb.getInterface(i);
            sb.append("  B4aInterfaceNumber : ").append(i).append('\n');
            sb.append("  InterfaceClass : ").append(usbClass(ifce.getInterfaceClass())).append('\n');
            sb.append("  InterfaceSubClass : ").append(ifce.getInterfaceSubclass()).append('\n');
            sb.append("  InterfaceProtocol : ").append(ifce.getInterfaceProtocol()).append('\n');
            sb.append('\n');
            for (int j = 0; j < ifce.getEndpointCount(); j++) {
                UsbEndpoint endpoint = ifce.getEndpoint(j);
                sb.append("    EndpointNumber : ").append(endpoint.getEndpointNumber()).append('\n');
                sb.append("    EndpointDirection : ").append(usbDirection(endpoint.getDirection())).append('\n');
                sb.append("    EndpointType : ").append(usbEndpoint(endpoint.getType())).append('\n');
                sb.append("    EndpointAttribute : ").append(endpoint.getAttributes()).append('\n');
                sb.append("    EndpointInterval : ").append(endpoint.getInterval()).append('\n');
                sb.append("    EndpointMaxPacketSize : ").append(endpoint.getMaxPacketSize()).append('\n');
                sb.append('\n');
            }
        }
//        conn.close();
        return sb.toString();
    }

    public int getUsbTimeout() {
        return this.TIMEOUT;
    }

    public void setUsbTimeout(int mSecs) {
        this.TIMEOUT = mSecs;
    }

    public void SetCustomDevice(int driverID, int vendorID, int productID) {
        UsbId.DRIVER_CUSTOM = driverID;
        UsbId.PRODUCT_CUSTOM = productID;
        UsbId.VENDOR_CUSTOM = vendorID;
    }

    private static String bytes0ToString(byte[] data, int idx) throws Exception {
        if (idx == 0 || data[0] == 0) {
            return "not available";
        }
        if (data[1] != 3) {
            return "bad descriptor type " + (data[1] & 255);
        }
        return new String(data, 0, data.length, "UTF-16LE").substring(1);
    }

    private static String toHex(int number) {
        return "0x" + Integer.toHexString(number).toUpperCase();
    }

    private static String usbDirection(int direction) {
        if (direction == 0) {
            return "out";
        }
        return "In";
    }

    private static String usbEndpoint(int eptype) {
        switch (eptype) {
            case 0:
                return "USB_ENDPOINT_XFER_CONTROL (control)";
            case 1:
                return "USB_ENDPOINT_XFER_ISOC (isochronous )";
            case 2:
                return "USB_ENDPOINT_XFER_BULK (bulk)";
            case 3:
                return "USB_ENDPOINT_XFER_INT (interrupt)";
            default:
                return "Unknown end point type " + eptype;
        }
    }

    private static String usbClass(int usbclass) {
        switch (usbclass) {
            case 0:
                return "USB_CLASS_PER_INTERFACE (per-interface basis)";
            case 1:
                return "USB_CLASS_AUDIO (audio)";
            case 2:
                return "USB_CLASS_COM (communication device)";
            case 3:
                return "USB_CLASS_COMM (human interface";
            case 5:
                return "USB_CLASS_PHYSICA (physical)";
            case 6:
                return "USB_CLASS_STILL_IMAGE (camera)";
            case 7:
                return "USB_CLASS_PRINTER (printer)";
            case 8:
                return "USB_CLASS_MASS_STORAGE (mass storage)";
            case KeyCodes.KEYCODE_2:
                return "USB_CLASS_HUB (hub)";
            case 10:
                return "USB_CLASS_CDC_DATA(CDC device)";
            case 11:
                return "USB_CLASS_CSCID (smart card)";
            case 13:
                return "USB_CLASS_CONTENT_SEC (content security)";
            case KeyCodes.KEYCODE_7:
                return "USB_CLASS_VIDEO (video)";
            case 224:
                return "USB_CLASS_WIRELESS_CONTROLLER (wireless controller)";
            case 239:
                return "USB_CLASS_MISC (miscellaneous";
            case 254:
                return "USB_CLASS_APP_SPEC (application specific)";
            case 255:
                return "USB_CLASS_VENDOR_SPEC (vendor specific)";
            default:
                return "Unknown class " + usbclass;
        }
    }
}
