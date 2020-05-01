package oscilldroid.inel;

import android.os.SystemClock;

import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.UsbSerial;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.phone.Phone;
import anywheresoftware.b4a.randomaccessfile.AsyncStreams;
import de.amberhome.AHLocale;
import java.lang.reflect.Method;
import java.util.HashMap;

public class usb extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public AsyncStreams _astreams = null;
    public converter _converter = null;
    public files _files = null;
    public boolean _isconnected = false;
    public main _main = null;
    public String _message = "";
    public Phone _ph = null;
    public int _pid = 0;
    public String _rz_event = "";
    public Object _sbmodule = null;
    public String _usb_info = "";
    public UsbSerial _usbser = null;
    public int _vid = 0;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.usb");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _astreams_newdata(byte[] bArr) throws Exception {
        Common.CallSubNew2(getActivityBA(), this._sbmodule, this._rz_event, bArr);
        return "";
    }

    public String _class_globals() throws Exception {
        this._message = "";
        this._isconnected = false;
        this._usb_info = "";
        this._vid = 0;
        this._pid = 0;
        this._usbser = new UsbSerial();
        this._astreams = new AsyncStreams();
        this._sbmodule = new Object();
        this._rz_event = "";
        this._ph = new Phone();
        return "";
    }

    public boolean _connect(int BaudRate) throws Exception {
        if (Phone.getSdkVersion() < 14) {
            this._message = _messages(5);
            return false;
        }
        UsbSerial usbSerial = this._usbser;
        usbSerial.SetCustomDevice(2, this._vid, this._pid);
        int UsbPresent = this._usbser.UsbPresent();
        if (UsbPresent == 0) {
            this._message = _messages(3);
            return false;
        } else if (this._usbser.HasPermission()) {
            this._usb_info = this._usbser.DeviceInfo();
            if (this._usb_info == null) {
                this._message = _messages(3);
                return false;
            }
            int Open = this._usbser.Open(this.ba, BaudRate);
            UsbSerial usbSerial2 = this._usbser;
            usbSerial2.SetParameters(BaudRate, 8, 1, 0);
            this._usbser.setUsbTimeout(200);
            if (Open != 0) {
                this._message = _messages(1);
                this._isconnected = true;
                this._astreams.Initialize(this.ba, this._usbser.GetInputStream(), this._usbser.GetOutputStream(), "astreams");
                return true;
            }
            this._message = _messages(2);
            return false;
        } else {
            this._usbser.RequestPermission();
            return false;
        }
    }

    public String _disconnect() throws Exception {
        if (Phone.getSdkVersion() < 14) {
            this._message = _messages(5);
            return "";
        }
        this._isconnected = false;
        this._astreams.Close();
        SystemClock.sleep(200L);
        this._usbser.Close();
        SystemClock.sleep(200L);
        this._isconnected = false;
        return "";
    }

    public int _getspeed(int i) throws Exception {
        int i2;
        Phone phone = this._ph;
        if (Phone.getSdkVersion() < 14) {
            this._message = _messages(5);
            return -1;
        }
        int UsbPresent = this._usbser.UsbPresent();
        this._usbser.getClass();
        if (UsbPresent == 0) {
            this._message = _messages(3);
            return -1;
        }
        List list = new List();
        list.Initialize();
        list.Add(9600);
        list.Add(19200);
        list.Add(38400);
        list.Add(57600);
        list.Add(115200);
        list.Add(230400);
        list.Add(460800);
        list.Add(921600);
        switch (i) {
            case 9594:
                i2 = 0;
                break;
            case 19188:
                i2 = 1;
                break;
            case 38375:
                i2 = 2;
                break;
            case 57563:
                i2 = 3;
                break;
            case 115125:
                i2 = 4;
                break;
            case 230250:
                i2 = 5;
                break;
            case 460500:
                i2 = 6;
                break;
            case 921000:
                i2 = 7;
                break;
            default:
                i2 = -1;
                break;
        }
        Common common = this.__c;
        int InputList = Common.InputList(list, _messages(0), i2, getActivityBA());
        if (InputList >= 0) {
            return (int) BA.ObjectToNumber(list.Get(InputList));
        }
        this._message = _messages(4);
        return -1;
    }

    public String _initialize(BA ba, Object obj, String str) throws Exception {
        innerInitialize(ba);
        Common common = this.__c;
        this._isconnected = false;
        this._sbmodule = obj;
        this._rz_event = str;
        return "";
    }

    public String _messages(int i) throws Exception {
        AHLocale aHLocale = new AHLocale();
        aHLocale.Initialize();
        if (aHLocale.getLanguage().equals("ru")) {
            switch (i) {
                case 0:
                    return "Выберите скорость";
                case 1:
                    return "USB:Успешно подключен";
                case 2:
                    return "USB:Ошибка открытия порта";
                case 3:
                    return "USB:Устройство не обнаружено";
                case 4:
                    return "USB:Скорость не выбрана";
                case 5:
                    return "USB:Старая версия Android";
            }
        } else {
            switch (i) {
                case 0:
                    return "Choose speed";
                case 1:
                    return "USB:Connected successfully";
                case 2:
                    return "USB:Error opening USB port";
                case 3:
                    return "USB:No device";
                case 4:
                    return "USB:Speed not select";
                case 5:
                    return "USB:Old Android version";
            }
        }
        return "";
    }

    public String _tx(byte[] bArr, int i, int i2) throws Exception {
        this._astreams.Write2(bArr, i, i2);
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
