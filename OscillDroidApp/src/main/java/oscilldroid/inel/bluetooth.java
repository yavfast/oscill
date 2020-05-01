package oscilldroid.inel;

import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.Serial;
import anywheresoftware.b4a.objects.Timer;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.streams.File;
import de.amberhome.AHLocale;
import java.lang.reflect.Method;
import java.util.HashMap;

public class bluetooth extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public Serial.BluetoothAdmin _adminbt = null;
    public int _bt_device = 0;
    public String _bt_event = "";
    public boolean _bt_start_en = false;
    public converter _converter = null;
    public files _files = null;
    public File.InputStreamWrapper _ioread = null;
    public File.OutputStreamWrapper _iowrite = null;
    public boolean _isconnected = false;
    public main _main = null;
    public String _message = "";
    public String _rz_event = "";
    public Object _sbmodule = null;
    public Serial _serbt = null;
    public Timer _timer1 = null;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.bluetooth");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _class_globals() throws Exception {
        this._message = "";
        this._isconnected = false;
        this._bt_start_en = false;
        this._adminbt = new Serial.BluetoothAdmin();
        this._serbt = new Serial();
        this._bt_device = 0;
        this._ioread = new File.InputStreamWrapper();
        this._iowrite = new File.OutputStreamWrapper();
        this._sbmodule = new Object();
        this._bt_event = "";
        this._rz_event = "";
        this._timer1 = new Timer();
        return "";
    }

    public boolean _connect(String str) throws Exception {
        boolean IsEnabled;
        boolean IsInitialized = this._adminbt.IsInitialized();
        if (!IsInitialized) {
            this._message = _messages(6);
            return false;
        } else if (str.equals("")) {
            this._message = _messages(7);
            return false;
        } else {
            boolean IsEnabled2 = this._adminbt.IsEnabled();
            if (!IsEnabled2) {
                boolean Enable = this._adminbt.Enable();
                if (!Enable) {
                    this._message = _messages(5);
                    return false;
                }
                do {
                    IsEnabled = this._adminbt.IsEnabled();
                } while (!IsEnabled);
            }
            this._serbt.Connect(this.ba, str);
            this._message = _messages(1);
            return true;
        }
    }

    public String _dev_select() throws Exception {
        boolean IsEnabled;
        boolean IsInitialized = this._adminbt.IsInitialized();
        if (!IsInitialized) {
            this._message = _messages(6);
            return "";
        }
        boolean IsEnabled2 = this._adminbt.IsEnabled();
        if (!IsEnabled2) {
            boolean Enable = this._adminbt.Enable();
            if (!Enable) {
                this._message = _messages(5);
                return "";
            }
            do {
                IsEnabled = this._adminbt.IsEnabled();
            } while (!IsEnabled);
        }
        new Map();
        Map GetPairedDevices = this._serbt.GetPairedDevices();
        List list = new List();
        list.Initialize();
        double size = (double) (GetPairedDevices.getSize() - 1);
        for (int i = 0; ((double) i) <= size; i = (int) (((double) i) + 1.0d)) {
            list.Add(GetPairedDevices.GetKeyAt(i));
        }
        int InputList = Common.InputList(list, _messages(4), -1, getActivityBA());
        if (InputList < 0) {
            this._message = _messages(2);
        }
        return String.valueOf(GetPairedDevices.Get(GetPairedDevices.GetKeyAt(InputList)));
    }

    public String _disconnect() throws Exception {
        boolean IsInitialized = this._adminbt.IsInitialized();
        if (!IsInitialized) {
            this._message = _messages(6);
            return "";
        }
        this._isconnected = false;
        Timer timer = this._timer1;
        timer.setEnabled(false);
        boolean z = this._bt_start_en;
        if (!z) {
            this._adminbt.Disable();
        }
        this._serbt.Disconnect();
        return "";
    }

    public String _initialize(BA ba, Object obj, String str, String str2) throws Exception {
        innerInitialize(ba);
        this._sbmodule = obj;
        this._bt_event = str;
        this._rz_event = str2;
        this._isconnected = false;
        this._serbt.Initialize("SerBT");
        this._adminbt.Initialize(this.ba, "AdminBT");
        boolean IsInitialized = this._adminbt.IsInitialized();
        if (!IsInitialized) {
            this._message = _messages(6);
            return "";
        }
        this._bt_start_en = this._serbt.IsEnabled();
        this._timer1.Initialize(this.ba, "Timer1", 100);
        Timer timer = this._timer1;
        timer.setEnabled(false);
        return "";
    }

    public String _messages(int i) throws Exception {
        AHLocale aHLocale = new AHLocale();
        aHLocale.Initialize();
        if (aHLocale.getLanguage().equals("ru")) {
            switch (i) {
                case 0:
                    return "Пожалуйста включите Bluetooth.";
                case 1:
                    return "BT:Подключаюсь к устройству";
                case 2:
                    return "BT:Устройство не выбрано";
                case 3:
                    return "BT:Ошибка при подключении";
                case 4:
                    return "Выберите устройство";
                case 5:
                    return "Не могу включить Bluetooth адаптер";
                case 6:
                    return "BT:Admin не инициализирован";
                case 7:
                    return "BT:Нет устройства для соединения";
                case 8:
                    return "BT:Соединение установлено";
            }
        } else {
            switch (i) {
                case 0:
                    return "Please enable Bluetooth";
                case 1:
                    return "BT:Connecting to Oscill";
                case 2:
                    return "BT:The device is not selected";
                case 3:
                    return "BT:ERROR CONNECTING!";
                case 4:
                    return "Choose device";
                case 5:
                    return "Error enabling Bluetooth adapter";
                case 6:
                    return "BT:Admin not initialize";
                case 7:
                    return "BT:No device for connect";
                case 8:
                    return "BT:Connection succesful";
            }
        }
        return "";
    }

    public String _serbt_connected(boolean z) throws Exception {
        if (z) {
            this._ioread.setObject(this._serbt.getInputStream());
            this._iowrite.setObject(this._serbt.getOutputStream());
            this._iowrite.Flush();
            this._isconnected = true;
            Timer timer = this._timer1;
            timer.setEnabled(true);
            this._message = _messages(8);
            BA activityBA = getActivityBA();
            Object obj = this._sbmodule;
            String str = this._bt_event;
            Common.CallSubNew2(activityBA, obj, str, true);
            return "";
        }
        _disconnect();
        this._message = _messages(3);
        BA activityBA2 = getActivityBA();
        Object obj2 = this._sbmodule;
        String str2 = this._bt_event;
        Common.CallSubNew2(activityBA2, obj2, str2, false);
        return "";
    }

    public String _timer1_tick() throws Exception {
        if (!this._ioread.IsInitialized()) {
            return "";
        }
        int BytesAvailable = this._ioread.BytesAvailable();
        if (BytesAvailable <= 0) {
            return "";
        }
        byte[] bArr = new byte[BytesAvailable];
        this._ioread.ReadBytes(bArr, 0, BytesAvailable);
        Common.CallSubNew2(getActivityBA(), this._sbmodule, this._rz_event, bArr);
        return "";
    }

    public String _tx(byte[] bArr, int i, int i2) throws Exception {
        this._iowrite.WriteBytes(bArr, i, i2);
        this._iowrite.Flush();
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
