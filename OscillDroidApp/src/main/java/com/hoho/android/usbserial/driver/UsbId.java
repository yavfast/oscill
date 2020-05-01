package com.hoho.android.usbserial.driver;

public final class UsbId {
    public static final int ARDUINO_LEONARDO = 32822;
    public static final int ARDUINO_MEGA_2560 = 16;
    public static final int ARDUINO_MEGA_2560_R3 = 66;
    public static final int ARDUINO_MEGA_ADK = 63;
    public static final int ARDUINO_MEGA_ADK_R3 = 68;
    public static final int ARDUINO_SERIAL_ADAPTER = 59;
    public static final int ARDUINO_SERIAL_ADAPTER_R3 = 68;
    public static final int ARDUINO_UNO = 1;
    public static final int ARDUINO_UNO_R3 = 67;
    public static final int ATMEL_LUFA_CDC_DEMO_APP = 8260;
    public static final int ATMEL_ROBOCLAW = 9220;
    public static final int DRIVER_CDCACM = 3;
    public static int DRIVER_CUSTOM = 0;
    public static final int DRIVER_FTDI = 4;
    public static final int DRIVER_NONE = 0;
    public static final int DRIVER_PROLIFIC = 1;
    public static final int DRIVER_SILABS = 2;
    public static final int FTDI_FT232R = 24577;
    public static final int LEAFLABS_MAPLE = 4;
    public static int PRODUCT_CUSTOM = 0;
    public static final int PROLIFIC_PL2303 = 8963;
    public static final int SILAB_CP2102 = 60000;
    public static final int VAN_OOIJEN_TECH_TEENSYDUINO_SERIAL = 1155;
    public static final int VENDOR_ARDUINO = 9025;
    public static final int VENDOR_ATMEL = 1003;
    public static int VENDOR_CUSTOM = 0;
    public static final int VENDOR_FTDI = 1027;
    public static final int VENDOR_LEAFLABS = 7855;
    public static final int VENDOR_PROLIFIC = 1659;
    public static final int VENDOR_SILAB = 4292;
    public static final int VENDOR_VAN_OOIJEN_TECH = 5824;

    private UsbId() {
        throw new IllegalAccessError("Non-instantiable class.");
    }
}
