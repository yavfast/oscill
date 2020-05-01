package com.hoho.android.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import java.util.Map;

public enum UsbSerialProber {
    PROLIFIC_SERIAL {
        public UsbSerialDriver getDevice(UsbManager manager, UsbDevice usbDevice) {
            if (!UsbSerialProber.testIfSupported(usbDevice, ProlificSerialDriver.getSupportedDevices())) {
                return null;
            }
            UsbDeviceConnection connection = manager.openDevice(usbDevice);
            if (connection == null) {
                return null;
            }
            return new ProlificSerialDriver(usbDevice, connection);
        }
    },
    FTDI_SERIAL {
        public UsbSerialDriver getDevice(UsbManager manager, UsbDevice usbDevice) {
            if (!UsbSerialProber.testIfSupported(usbDevice, FtdiSerialDriver.getSupportedDevices())) {
                return null;
            }
            UsbDeviceConnection connection = manager.openDevice(usbDevice);
            if (connection == null) {
                return null;
            }
            return new FtdiSerialDriver(usbDevice, connection);
        }
    },
    CDC_ACM_SERIAL {
        public UsbSerialDriver getDevice(UsbManager manager, UsbDevice usbDevice) {
            if (!UsbSerialProber.testIfSupported(usbDevice, CdcAcmSerialDriver.getSupportedDevices())) {
                return null;
            }
            UsbDeviceConnection connection = manager.openDevice(usbDevice);
            if (connection == null) {
                return null;
            }
            return new CdcAcmSerialDriver(usbDevice, connection);
        }
    },
    SILAB_SERIAL {
        public UsbSerialDriver getDevice(UsbManager manager, UsbDevice usbDevice) {
            if (!UsbSerialProber.testIfSupported(usbDevice, Cp2102SerialDriver.getSupportedDevices())) {
                return null;
            }
            UsbDeviceConnection connection = manager.openDevice(usbDevice);
            if (connection == null) {
                return null;
            }
            return new Cp2102SerialDriver(usbDevice, connection);
        }
    };

    public abstract UsbSerialDriver getDevice(UsbManager usbManager, UsbDevice usbDevice);

    public static UsbSerialDriver acquire(UsbManager usbManager) {
        for (UsbDevice usbDevice : usbManager.getDeviceList().values()) {
            UsbSerialDriver probedDevice = acquire(usbManager, usbDevice);
            if (probedDevice != null) {
                return probedDevice;
            }
        }
        return null;
    }

    public static UsbSerialDriver acquire(UsbManager usbManager, UsbDevice usbDevice) {
        for (UsbSerialProber prober : values()) {
            UsbSerialDriver probedDevice = prober.getDevice(usbManager, usbDevice);
            if (probedDevice != null) {
                return probedDevice;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static boolean testIfSupported(UsbDevice usbDevice, Map<Integer, int[]> supportedDevices) {
        int[] supportedProducts = supportedDevices.get(Integer.valueOf(usbDevice.getVendorId()));
        if (supportedProducts == null) {
            return false;
        }
        int productId = usbDevice.getProductId();
        for (int supportedProductId : supportedProducts) {
            if (productId == supportedProductId) {
                return true;
            }
        }
        return false;
    }
}
