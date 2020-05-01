package oscilldroid.inel;

import android.graphics.Bitmap;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Bit;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.DateTime;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.KeyCodes;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.ListViewWrapper;
import anywheresoftware.b4a.objects.Timer;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import com.hoho.android.usbserial.driver.FtdiSerialDriver;
import de.amberhome.AHLocale;
import java.lang.reflect.Method;
import java.util.HashMap;

public class oscill extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public int _alignsweep = 0;
    public boolean _connect_flg = false;
    public converter _converter = null;
    public int _delayedsweep = 0;
    public String _devinfo = "";
    public int _divpix = 0;
    public String _event_menu = "";
    public String _event_rx = "";
    public boolean _f_en = false;
    public files _files = null;
    public boolean _flgstart = false;
    public int _fps = 0;
    public long _freq = 0;
    public int _frmcnt = 0;
    public long _frmtime = 0;
    public boolean _hfilter = false;
    public int _levelsync = 0;
    public boolean _lfilter = false;
    public List[] _lst_data = null;
    public int[] _lst_pos = null;
    public ListViewWrapper[] _lst_str = null;
    public Object _m_obj = null;
    public main _main = null;
    public int _mcycle = 0;
    public String _message = "";
    public int _minnumpas_strob = 0;
    public int _numpas_avgpick = 0;
    public long _period = 0;
    public long _pwm = 0;
    public boolean _pwm_en = false;
    public float _q = Common.Density;
    public boolean _q_en = false;
    public int _rx_idx = 0;
    public byte[] _rxbuff = null;
    public int _rxsize = 0;
    public String _rxstr = "";
    public int _samplmethod = 0;
    public int _sampper = 0;
    public boolean _senden = false;

    public void setSenden(boolean senden) {
        if (_senden != senden) {
            _senden = senden;
        }
    }
    public int _shift = 0;
    public int _sizesample = 0;
    public int _sizesampler = 0;
    public int _speed = 0;
    public boolean _start_auto = false;
    public int _sync_rels = 0;
    public int _sync_z = 0;
    public boolean _t_all_dis = false;
    public boolean _t_en = false;
    public int _te_time = 0;
    public long _time = 0;
    public long _timeout = 0;
    public Timer _timer1 = null;
    public String _tinfo = "";
    public long _tneg = 0;
    public long _to_data = 0;
    public boolean _to_disable = false;
    public int _to_time = 0;
    public int _tosinca = 0;
    public int _tosincw = 0;
    public boolean _tp_en = false;
    public long _tpos = 0;
    public long _tstep = 0;
    public String _tx_old = "";
    public List _txlist = null;
    public boolean _vamp_en = false;
    public float _vampl = Common.Density;
    public float _vavg = Common.Density;
    public boolean _vavg_en = false;
    public String _vinfo = "";
    public float _vmax = Common.Density;
    public boolean _vmax_en = false;
    public float _vmin = Common.Density;
    public boolean _vmin_en = false;
    public float _vrms = Common.Density;
    public boolean _vrms_en = false;
    public float _vstep = Common.Density;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.oscill");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public static boolean _bytecompare(byte[] bArr, byte[] bArr2, int count, int offset) throws Exception {
        if (bArr2.length == 0) {
            return false;
        }
        int d = count - 1;
        for (int idx = 0; idx <= d; idx++) {
            if (bArr[offset + idx] != bArr2[idx]) {
                return false;
            }
        }
        return true;
    }

    public String _class_globals() throws Exception {
        this._vstep = Common.Density;
        this._vmax = Common.Density;
        this._vmin = Common.Density;
        this._vampl = Common.Density;
        this._vrms = Common.Density;
        this._vavg = Common.Density;
        this._vinfo = "";
        this._tinfo = "";
        this._speed = 0;
        this._vmax_en = false;
        this._vmin_en = false;
        this._vamp_en = false;
        this._vavg_en = false;
        this._vrms_en = false;
        this._tstep = 0;
        this._sync_z = 0;
        this._sync_rels = 0;
        this._period = 0;
        this._freq = 0;
        this._tpos = 0;
        this._tneg = 0;
        this._pwm = 0;
        this._q = Common.Density;
        this._t_all_dis = false;
        this._t_en = false;
        this._f_en = false;
        this._tp_en = false;
        this._q_en = false;
        this._pwm_en = false;
        this._to_time = 0;
        this._te_time = 0;
        this.setSenden(false);
        this._lst_str = new ListViewWrapper[6];
        int length = this._lst_str.length;
        for (int i = 0; i < length; i++) {
            this._lst_str[i] = new ListViewWrapper();
        }
        this._lst_data = new List[6];
        int length2 = this._lst_data.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this._lst_data[i2] = new List();
        }
        this._lst_pos = new int[6];
        this._hfilter = false;
        this._lfilter = false;
        this._shift = 0;
        this._sizesample = 0;
        this._sizesampler = 0;
        this._tosinca = 0;
        this._tosincw = 0;
        this._levelsync = 0;
        this._delayedsweep = 0;
        this._alignsweep = 0;
        this._divpix = 0;
        this._time = 0;
        this._mcycle = 0;
        this._sampper = 0;
        this._samplmethod = 0;
        this._minnumpas_strob = 0;
        this._numpas_avgpick = 0;
        this._devinfo = "";
        this._txlist = new List();
        this._rxbuff = new byte[4096];
        this._rx_idx = 0;
        this._rx_idx = 0;
        this._rxsize = 0;
        this._rxsize = 0;
        this._rxstr = "";
        this._connect_flg = false;
        this._message = "";
        this._flgstart = false;
        this._start_auto = false;
        this._start_auto = true;
        this._tx_old = "";
        this._fps = 0;
        this._timeout = 0;
        this._to_data = 0;
        this._to_disable = false;
        this._to_disable = false;
        this._frmcnt = 0;
        this._frmtime = 0;
        this._timer1 = new Timer();
        this._event_menu = "";
        this._event_rx = "";
        this._m_obj = new Object();
        return "";
    }

    public String _fordraw_hires(int[] iArr) throws Exception {
        int ShiftLeft = 32768 - Bit.ShiftLeft(this._shift, 8);
        int i = 0;
        int d = (this._sizesample * 2) - 1;
        int j = 0;
        long j2 = 0;
        long j3 = 0;
        int j4 = 65535;
        for (int i2 = 0; i2 <= d; i2 = i2 + 2) {
            iArr[i] = Bit.ShiftLeft(converter._bytetounsignedbyte(this._rxbuff[i2 + 14]), 8);
            int _bytetounsignedbyte = converter._bytetounsignedbyte(this._rxbuff[i2 + 15]);
            iArr[i] = Bit.Or(iArr[i], _bytetounsignedbyte);
            int i3 = iArr[i] - ShiftLeft;
            j2 += (long) (i3 * i3);
            j3 += (long) i3;
            if (iArr[i] > j) {
                j = iArr[i];
            }
            if ((iArr[i]) < j4) {
                j4 = iArr[i];
            }
            i++;
        }
        int i4 = (j - j4) / 2;
        this._sync_z = (int) (j - ((long) i4));
        this._sync_rels = i4 / 4;
        this._vmax = ((float) (j - ((long) ShiftLeft))) * this._vstep;
        this._vmin = ((float) (j4 - ((long) ShiftLeft))) * this._vstep;
        this._vampl = this._vmax - this._vmin;
        this._vrms = (float) (Common.Sqrt(((double) j2) / ((double) i)) * ((double) this._vstep));
        this._vavg = (float) (((double) this._vstep) * (((double) j3) / ((double) i)));
        _vinfoset(this._vmax, this._vmin, this._vampl, this._vrms, this._vavg);
        return "";
    }

    public String _fordraw_peak(int[] iArr) throws Exception {
        int i;
        int i2 = FtdiSerialDriver.USB_ENDPOINT_IN - this._shift;
        int d = ((this._sizesample * 2) - 1);
        int i3 = 0;
        int i4 = 0;
        boolean z = true;
        long j = 0;
        long j2 = 0;
        int i5 = 65535;
        while (i3 <= d) {
            iArr[i3] = converter._bytetounsignedbyte(this._rxbuff[i3 + 14]);
            if (z) {
                int i6 = iArr[i3] - i2;
                j += (long) (i6 * i6);
                j2 += (long) i6;
                if (iArr[i3] > i4) {
                    i = iArr[i3];
                } else {
                    i = i4;
                }
                if (iArr[i3] < i5) {
                    i5 = iArr[i3];
                }
                i4 = i;
                z = false;
            } else {
                z = true;
            }
            i3++;
        }
        int i7 = (i4 - i5) / 2;
        this._sync_z = i4 - i7;
        this._sync_rels = i7 / 2;
        this._vmax = this._vstep * ((float) (i4 - i2));
        this._vmin = this._vstep * ((float) (i5 - i2));
        this._vampl = this._vmax - this._vmin;
        double d2 = (double) this._vstep;
        this._vrms = (float) (Common.Sqrt(((double) j) / (((double) i3) / 2.0d)) * d2);
        this._vavg = (float) (((double) this._vstep) * (((double) j2) / (((double) i3) / 2.0d)));
        _vinfoset(this._vmax, this._vmin, this._vampl, this._vrms, this._vavg);
        return "";
    }

    public String _fordraw_sample(int[] iArr) throws Exception {
        int i = FtdiSerialDriver.USB_ENDPOINT_IN - this._shift;
        int d = this._sizesample - 1;
        int i2 = 0;
        long j = 0;
        int i3 = 65535;
        int i4 = 0;
        long j2 = 0;
        while (i2 <= d) {
            iArr[i2] = converter._bytetounsignedbyte(this._rxbuff[i2 + 14]);
            int i5 = iArr[i2] - i;
            j += (long) (i5 * i5);
            j2 += (long) i5;
            if (iArr[i2] > i4) {
                i4 = iArr[i2];
            }
            if (iArr[i2] < i3) {
                i3 = iArr[i2];
            }
            i2++;
        }
        int i6 =(i4 - i3) / 2;
        this._sync_z = i4 - i6;
        this._sync_rels = i6 / 2;
        this._vmax = ((float) (i4 - i)) * this._vstep;
        this._vmin = ((float) (i3 - i)) * this._vstep;
        this._vampl = this._vmax - this._vmin;
        this._vrms = (float) (Common.Sqrt(((double) j) / ((double) i2)) * ((double) this._vstep));
        this._vavg = (float) ((((double) j2) / ((double) i2)) * ((double) this._vstep));
        _vinfoset(this._vmax, this._vmin, this._vampl, this._vrms, this._vavg);
        return "";
    }

    public String _getfreq(int[] iArr, int i, int i2, int i3) throws Exception {
        int i4;
        int i5;
        int i6;
        int i7 = 0;
        if (this._t_all_dis) {
            return "";
        }
        if (i3 < 0) {
            i4 = i3 * -1;
        } else {
            i4 = i3;
        }
        int i8 = i2 + i4;
        int i9 = i2 - i4;
        if (iArr[i] > i2) {
            int d = this._sizesample - 1;
            int i10 = i;
            while (true) {
                if (i10 > d) {
                    i5 = i10;
                    break;
                } else if (iArr[i10] < i9) {
                    i5 = i10;
                    break;
                } else {
                    i10++;
                }
            }
        } else {
            i5 = i;
        }
        int d2 = this._sizesample - 1;
        char c = 0;
        int i11 = 0;
        for (int i12 = i5; i12 <= d2; i12++) {
            switch (c) {
                case 0:
                    if (iArr[i12] <= i9) {
                        break;
                    } else {
                        c = 1;
                        break;
                    }
                case 1:
                    if (iArr[i12] >= i9) {
                        if (iArr[i12] <= i2) {
                            break;
                        } else {
                            c = 2;
                            i11 = i12;
                            break;
                        }
                    } else {
                        c = 0;
                        break;
                    }
                case 2:
                    if (iArr[i12] <= i8) {
                        break;
                    } else {
                        c = 3;
                        break;
                    }
                case 3:
                    if (iArr[i12] >= i8) {
                        break;
                    } else {
                        c = 4;
                        break;
                    }
                case 4:
                    if (iArr[i12] <= i8) {
                        if (iArr[i12] >= i2) {
                            break;
                        } else {
                            i7 = i12 - i11;
                            c = 5;
                            break;
                        }
                    } else {
                        c = 3;
                        break;
                    }
                case 5:
                    if (iArr[i12] >= i9) {
                        break;
                    } else {
                        c = 6;
                        break;
                    }
                case 6:
                    if (iArr[i12] <= i9) {
                        break;
                    } else {
                        c = 7;
                        break;
                    }
                case 7:
                    if (iArr[i12] >= i9) {
                        if (iArr[i12] <= i2) {
                            break;
                        } else {
                            if (this._lst_pos[3] == 3) {
                                i6 = (i12 - i11) / 2;
                                i7 = i7 / 2;
                            } else {
                                i6 = i12 - i11;
                            }
                            this._period = ((long) i6) * this._tstep;
                            this._tpos = ((long) i7) * this._tstep;
                            this._q = (float) (((double) this._period) / ((double) this._tpos));
                            this._pwm = (long) (100.0d / ((double) this._q));
                            this._freq = (long) (1.0E12d / ((double) this._period));
                            StringBuilder res = new StringBuilder(1024);
                            if (this._t_en) {
                                res.append("T = ");
                                res.append(converter._timetostr(this._period));
                                res.append(Common.CRLF);
                            }
                            if (this._f_en) {
                                res.append("F = ").append(BA.NumberToString(this._freq)).append("Hz");
                                res.append(Common.CRLF);
                            }
                            if (this._tp_en) {
                                res.append("Tp = ");
                                res.append(converter._timetostr(this._tpos));
                                res.append(Common.CRLF);
                            }
                            if (this._q_en) {
                                res.append("Q = ");
                                res.append(Common.NumberFormat((double) this._q, 0, 2));
                                res.append(Common.CRLF);
                            }
                            if (this._pwm_en) {
                                res.append("PWM = ").append(BA.NumberToString(this._pwm)).append("%");
                            }
                            this._tinfo = res.toString();
                            return "";
                        }
                    } else {
                        c = 6;
                        break;
                    }
            }
        }
        this._tinfo = "";
        return "";
    }

    public String _initialize(BA ba, Object obj, String str, String str2, float f) throws Exception {
        innerInitialize(ba);
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        this._frmtime = DateTime.getNow();
        this._m_obj = obj;
        this._event_menu = str;
        this._event_rx = str2;
        this._txlist.Initialize();
        this._lst_pos[0] = 10;
        this._lst_pos[1] = 5;
        this._lst_pos[2] = 1;
        this._lst_pos[3] = 0;
        this._lst_pos[4] = 0;
        this._lst_pos[5] = 0;
        bitmapWrapper.Initialize(File.getDirAssets(), "sidebar.png");
        for (int i = 0; ((double) i) <= 5.0d; i = (int) (((double) i) + 1.0d)) {
            this._lst_data[i].Initialize();
            this._lst_str[i].Initialize(this.ba, "lvMenu");
            this._lst_str[i].setTag(i);
            LabelWrapper labelWrapper = this._lst_str[i].getSingleLineLayout().Label;
            labelWrapper.setTextColor(Colors.Yellow);
            ListViewWrapper listViewWrapper = this._lst_str[i];
            listViewWrapper.setColor(Colors.Black);
            ListViewWrapper listViewWrapper2 = this._lst_str[i];
            listViewWrapper2.setScrollingBackgroundColor(0);
            this._lst_str[i].SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
            this._lst_str[i].getSingleLineLayout().Label.setTextSize(this._lst_str[i].getSingleLineLayout().Label.getTextSize() * f);
        }
        this._lst_str[0].AddSingleLine("500nS");
        this._lst_str[0].AddSingleLine("1uS");
        this._lst_str[0].AddSingleLine("2uS");
        this._lst_str[0].AddSingleLine("5uS");
        this._lst_str[0].AddSingleLine("10uS");
        this._lst_str[0].AddSingleLine("20uS");
        this._lst_str[0].AddSingleLine("50uS");
        this._lst_str[0].AddSingleLine("100uS");
        this._lst_str[0].AddSingleLine("200uS");
        this._lst_str[0].AddSingleLine("500uS");
        this._lst_str[0].AddSingleLine("1mS");
        this._lst_str[0].AddSingleLine("2mS");
        this._lst_str[0].AddSingleLine("5mS");
        this._lst_str[0].AddSingleLine("10mS");
        this._lst_str[0].AddSingleLine("20mS");
        this._lst_str[0].AddSingleLine("50mS");
        this._lst_str[0].AddSingleLine("100mS");
        this._lst_str[0].AddSingleLine("200mS");
        this._lst_str[0].AddSingleLine("500mS");
        this._lst_data[0].Add(0);
        this._lst_data[0].Add(1);
        this._lst_data[0].Add(2);
        this._lst_data[0].Add(5);
        this._lst_data[0].Add(10);
        this._lst_data[0].Add(20);
        this._lst_data[0].Add(50);
        this._lst_data[0].Add(100);
        this._lst_data[0].Add(200);
        this._lst_data[0].Add(500);
        this._lst_data[0].Add(1000);
        this._lst_data[0].Add(2000);
        this._lst_data[0].Add(5000);
        this._lst_data[0].Add(10000);
        this._lst_data[0].Add(20000);
        this._lst_data[0].Add(50000);
        this._lst_data[0].Add(100000);
        this._lst_data[0].Add(200000);
        this._lst_data[0].Add(500000);
        this._lst_data[1].Add(20);
        this._lst_data[1].Add(50);
        this._lst_data[1].Add(100);
        this._lst_data[1].Add(200);
        this._lst_data[1].Add(500);
        this._lst_data[1].Add(1000);
        this._lst_data[1].Add(2000);
        this._lst_data[1].Add(5000);
        this._lst_data[1].Add(10000);
        this._lst_str[1].AddSingleLine("20mV");
        this._lst_str[1].AddSingleLine("50mV");
        this._lst_str[1].AddSingleLine("100mV");
        this._lst_str[1].AddSingleLine("200mV");
        this._lst_str[1].AddSingleLine("500mV");
        this._lst_str[1].AddSingleLine("1V");
        this._lst_str[1].AddSingleLine("2V");
        this._lst_str[1].AddSingleLine("5V");
        this._lst_str[1].AddSingleLine("10V");
        this._lst_data[2].Add(2);
        this._lst_data[2].Add(0);
        this._lst_data[2].Add(1);
        this._lst_str[2].AddSingleLine("AC");
        this._lst_str[2].AddSingleLine("DC");
        this._lst_str[2].AddSingleLine("GND");
        this._lst_data[3].Add(4);
        this._lst_data[3].Add(0);
        this._lst_data[3].Add(1);
        this._lst_data[3].Add(3);
        this._lst_str[3].AddSingleLine("Sample");
        this._lst_str[3].AddSingleLine("AVG");
        this._lst_str[3].AddSingleLine("HiRes");
        this._lst_str[3].AddSingleLine("Peak");
        this._lst_data[4].Add(32);
        this._lst_data[4].Add(16);
        this._lst_data[4].Add(48);
        bitmapWrapper.Initialize(File.getDirAssets(), "pos.png");
        this._lst_str[4].AddTwoLinesAndBitmap("pos.png", "", (Bitmap) bitmapWrapper.getObject());
        bitmapWrapper.Initialize(File.getDirAssets(), "neg.png");
        this._lst_str[4].AddTwoLinesAndBitmap("neg.png", "", (Bitmap) bitmapWrapper.getObject());
        bitmapWrapper.Initialize(File.getDirAssets(), "pn.png");
        this._lst_str[4].AddTwoLinesAndBitmap("pn.png", "", (Bitmap) bitmapWrapper.getObject());
        LabelWrapper labelWrapper2 = this._lst_str[4].getTwoLinesAndBitmap().Label;
        labelWrapper2.setVisible(false);
        this._lst_data[5].Add(2);
        this._lst_data[5].Add(0);
        this._lst_data[5].Add(1);
        this._lst_data[5].Add(3);
        this._lst_str[5].AddSingleLine("FREE");
        this._lst_str[5].AddSingleLine("TO");
        this._lst_str[5].AddSingleLine("TE");
        this._lst_str[5].AddSingleLine("WAIT");
        this._hfilter = false;
        this._lfilter = false;
        this._shift = 0;
        this._sizesample = 332;
        this._sizesampler = 332;
        this._tosinca = 3338675;
        this._tosincw = 3338675;
        this._levelsync = 127;
        this._delayedsweep = 0;
        this._alignsweep = 38;
        this._divpix = 32;
        this._samplmethod = 0;
        this._minnumpas_strob = 15;
        this._numpas_avgpick = 0;
        this._to_data = (long) (((BA.ObjectToNumber(this._lst_data[0].Get(this._lst_pos[0])) / 1000.0d) / ((double) this._divpix)) * ((double) this._sizesample));
        this._vstep = (float) (BA.ObjectToNumber(this._lst_data[1].Get(this._lst_pos[1])) / 32.0d);
        this._timer1.Initialize(this.ba, "Timer1", 50);
        return "";
    }

    public String _lvmenu_itemclick(int i, Object obj) throws Exception {
        ListViewWrapper listViewWrapper = new ListViewWrapper();
        listViewWrapper.setObject((ListViewWrapper.SimpleListView) Common.Sender(this.ba));
        this._lst_pos[(int) BA.ObjectToNumber(listViewWrapper.getTag())] = i;
        Common.CallSubNew2(this.ba, this._m_obj, this._event_menu, listViewWrapper.getTag());
        return "";
    }

    public String _messages(int i) throws Exception {
        AHLocale aHLocale = new AHLocale();
        aHLocale.Initialize();
        if (aHLocale.getLanguage().equals("ru")) {
            switch (i) {
                case 0:
                    return "Oscill подключен.";
                case 1:
                    return "Только режим Sample. Size = 166.";
                case 2:
                    return "Peak режим недоступен. Peak = Sample.";
                case 3:
                    return "Oscill не отвечает.";
            }
        } else {
            switch (i) {
                case 0:
                    return "Oscill connected";
                case 1:
                    return "Only Sample mode. Size = 166.";
                case 2:
                    return "Peak mode is not available! Peak = Sample.";
                case 3:
                    return "Oscill does not respond.";
            }
        }
        return "";
    }

    public String _on(boolean z) throws Exception {
        this._txlist.Clear();
        this.setSenden(true);
        this._flgstart = false;
        this._rx_idx = 0;
        this._rxsize = 0;
        this._to_disable = false;
        this._timer1.setEnabled(z);
        return "";
    }

    public String _rx(byte[] bArr) throws Exception {
        if (this._senden) {
            return "";
        }
        int length = bArr.length;
        System.arraycopy(bArr, 0, this._rxbuff, this._rx_idx, bArr.length);

        this._rx_idx = length + this._rx_idx;
        if (this._rxsize == 0 && this._rx_idx > 2) {
            this._rxsize = converter._bytetoint(this._rxbuff, 1);
        }
        if (this._rx_idx > this._rxsize - 1 && this._rxsize > 0) {
            this._rx_idx = 0;
            Common.CallSubNew2(this.ba, this._m_obj, this._event_rx, _rxhandler());
            this._rxsize = 0;
        }
        return "";
    }

    public int _rxhandler() throws Exception {
        long j;
        this.setSenden(true);
        if (this._txlist.getSize() != 0 || !this._flgstart) {
            this.setSenden(true);
        } else if (this._start_auto) {
            _txnextnow();
            this.setSenden(false);
        } else if (this._rxsize < 100) {
            _txnextnow();
            this._flgstart = true;
        } else {
            this._flgstart = false;
        }
        if (this._rxsize > 100) {
            this._frmcnt++;
            return 12;
        }
        switch (this._rxsize) {
            case 3:
                break;
            case 4:
                if (converter._bytestohexstr(this._rxbuff, 0, this._rxsize - 1).substring(0, 6).equals("0F0004")) {
                    this._speed = (int) ((1842000.0d / ((double) converter._bytetounsignedbyte(this._rxbuff[3]))) + 0.5d);
                    return 22;
                }
                break;
            case 7:
                if (_bytecompare(this._rxbuff, new byte[]{-96, 0, 7, 16, 0, 0, 24}, 7, 0)) {
                    this._connect_flg = true;
                    this._message = _messages(0);
                    return 0;
                }
                break;
            case 10:
                int _bytetounsignedbyte = converter._bytetounsignedbyte(this._rxbuff[9]);
                if (_bytecompare(this._rxbuff, new byte[]{-96, 0, 10, 113, 0, 5, 0, 0, 0, 0}, 6, 0)) {
                    if (this._rxbuff[6] == 79 && this._rxbuff[7] == 49) {
                        _rxinput(_bytetounsignedbyte);
                        return 2;
                    } else if (this._rxbuff[6] == 77 && this._rxbuff[7] == 49) {
                        _rxsamplemode(_bytetounsignedbyte);
                        return 3;
                    } else if (this._rxbuff[6] == 83 && this._rxbuff[7] == 49) {
                        this._levelsync = _bytetounsignedbyte;
                        return 4;
                    } else if (this._rxbuff[6] == 82 && this._rxbuff[7] == 84) {
                        _rxtypesync(_bytetounsignedbyte);
                        return 5;
                    } else if (this._rxbuff[6] != 84 || this._rxbuff[7] != 49) {
                        if (this._rxbuff[6] != 82 || this._rxbuff[7] != 83) {
                            if (this._rxbuff[6] != 65 || this._rxbuff[7] != 82) {
                                if (this._rxbuff[6] == 65 && this._rxbuff[7] == 80) {
                                    this._numpas_avgpick = _bytetounsignedbyte;
                                    break;
                                }
                            } else {
                                this._minnumpas_strob = _bytetounsignedbyte;
                                break;
                            }
                        } else {
                            this._samplmethod = _bytetounsignedbyte;
                            break;
                        }
                    } else {
                        _rxsync(_bytetounsignedbyte);
                        return 6;
                    }
                }
                break;
            case 13:
                String _bytestohexstr = converter._bytestohexstr(this._rxbuff, 0, this._rxsize - 1);
                long ParseInt = (long) Bit.ParseInt(_bytestohexstr.substring(18, 26), 16);
                switch (BA.switchObjectToInt(_bytestohexstr.substring(0, 18), "A0000D7100055631F0", "A0000D7100055031F0", "A0000D7100055153F0", "A0000D7100055441F1", "A0000D7100055457F1", "A0000D7100055444F1", "A0000D7100055443F0", "A0000D7100054D43F0", "A0000D7100055453F1")) {
                    case 0:
                        int i = 0;
                        while (true) {
                            if (((double) i) <= 8.0d) {
                                if (this._lst_data[1].Get(i).equals(ParseInt)) {
                                    this._lst_pos[1] = i;
                                } else {
                                    i = (int) (((double) i) + 1.0d);
                                }
                            } else {
                                break;
                            }
                        }
                        if (this._lst_pos[3] == 2d) {
                            double ObjectToNumber = BA.ObjectToNumber(this._lst_data[1].Get(this._lst_pos[1]));
                            this._vstep = (float) (ObjectToNumber / ((double) Bit.ShiftLeft(32, 8)));
                        } else {
                            this._vstep = (float) (BA.ObjectToNumber(this._lst_data[1].Get(this._lst_pos[1])) / 32.0d);
                        }
                        return 7;
                    case 1:
                        if (ParseInt > 32768) {
                            j = ParseInt - 65536;
                        } else {
                            j = ParseInt;
                        }
                        this._shift = (int) j;
                        return 8;
                    case 2:
                        this._sizesample = (int) ParseInt;
                        this._tstep = (long) ((((double) this._sampper) / 256.0d) * ((double) this._mcycle) * 10.0d);
                        this._time = this._tstep * ((long) this._divpix);
                        if (this._alignsweep > this._sizesample) {
                            this._alignsweep = 20;
                            _txalignsweep();
                        }
                        if (this._lst_pos[0] < 4) {
                            this._message = _messages(1);
                        } else if (this._lst_pos[0] < 6) {
                            this._message = _messages(2);
                        } else {
                            this._message = this._devinfo;
                        }
                        this._to_data = (long) (((BA.ObjectToNumber(this._lst_data[0].Get(this._lst_pos[0])) / 1000.0d) / ((double) this._divpix)) * ((double) this._sizesample));
                        if (this._flgstart) {
                            this._timeout = DateTime.getNow() + 1000 + (this._to_data * 3);
                        }
                        return 9;
                    case 3:
                        this._tosinca = (int) ParseInt;
                        break;
                    case 4:
                        this._tosincw = (int) ParseInt;
                        break;
                    case 5:
                        this._delayedsweep = (int) ParseInt;
                        break;
                    case 6:
                        this._alignsweep = (int) ParseInt;
                        return 10;
                    case 7:
                        this._mcycle = (int) ParseInt;
                        this._sampper = (int) ((((double) converter._strtotime(String.valueOf(this._lst_str[0].GetItem(this._lst_pos[0])))) / ((double) ((this._mcycle * 10) * this._divpix))) * 256.0d);
                        _txsampper();
                        _txsizesample();
                        break;
                    case 8:
                        this._sampper = (int) ParseInt;
                        break;
                }
            case 14:
                String _bytestohexstr2 = converter._bytestohexstr(this._rxbuff, 10, 3);
                String _hextoascii = converter._hextoascii(_bytestohexstr2);
                if (_bytecompare(this._rxbuff, new byte[]{-96, 0, 14, 112, 0, 6, 86}, 7, 0)) {
                    if (this._rxbuff[7] != 78 || this._rxbuff[8] != 77) {
                        if (this._rxbuff[7] != 83 || this._rxbuff[8] != 78) {
                            if (this._rxbuff[7] == 72 && this._rxbuff[8] == 87) {
                                StringBuilder append = new StringBuilder().append(this._devinfo).append("Hard Version: ").append(_hextoascii);
                                this._devinfo = append.append(Common.CRLF).toString();
                                break;
                            } else if (this._rxbuff[7] == 83 && this._rxbuff[8] == 87) {
                                StringBuilder append2 = new StringBuilder().append(this._devinfo).append("Soft Version: ").append(_hextoascii);
                                this._devinfo = append2.append(Common.CRLF).toString();
                                String str = this._devinfo;
                                Common.ToastMessageShow(str, true);
                                this._message = this._devinfo;
                                return 1;
                            }
                        } else {
                            StringBuilder append3 = new StringBuilder().append(this._devinfo).append("Serial Number: ").append(_hextoascii);
                            this._devinfo = append3.append(Common.CRLF).toString();
                            break;
                        }
                    } else {
                        StringBuilder append4 = new StringBuilder().append("Model: ").append(_hextoascii);
                        this._devinfo = append4.append(Common.CRLF).toString();
                        break;
                    }
                }
                break;
            default:
                Common.Msgbox(converter._bytestohexstr(this._rxbuff, 0, this._rxsize - 1), "", this.ba);
                break;
        }
        return -1;
    }

    public String _rxinput(int i) throws Exception {
        switch (BA.switchObjectToInt(Bit.And(i, 3), 0, 1, 2)) {
            case 0:
                this._lst_pos[2] = 1;
                break;
            case 1:
                this._lst_pos[2] = 2;
                break;
            case 2:
                this._lst_pos[2] = 0;
                break;
        }
        if (Bit.And(i, 4) > 0) {
            this._hfilter = true;
        } else {
            this._hfilter = false;
        }
        if (Bit.And(i, 8) > 0) {
            this._lfilter = true;
            return "";
        }
        this._lfilter = false;
        return "";
    }

    public String _rxsamplemode(int i) throws Exception {
        switch (i) {
            case 0:
                this._lst_pos[3] = 1;
                return "";
            case 1:
                this._lst_pos[3] = 2;
                return "";
            case 3:
                this._lst_pos[3] = 3;
                return "";
            case 4:
                this._lst_pos[3] = 0;
                return "";
            default:
                return "";
        }
    }

    public String _rxsync(int i) throws Exception {
        switch (i) {
            case 0:
                this._lst_pos[4] = 3;
                return "";
            case 16:
                this._lst_pos[4] = 1;
                return "";
            case 32:
                this._lst_pos[4] = 0;
                return "";
            case 48:
                this._lst_pos[4] = 2;
                return "";
            default:
                return "";
        }
    }

    public String _rxtypesync(int i) throws Exception {
        this._to_disable = false;
        switch (i) {
            case 0:
                this._lst_pos[5] = 1;
                return "";
            case 1:
                this._lst_pos[5] = 2;
                return "";
            case 2:
                this._lst_pos[5] = 0;
                return "";
            case 3:
                this._lst_pos[5] = 3;
                this._to_disable = true;
                return "";
            default:
                return "";
        }
    }

    public String _sets(String str, int i, int i2, int i3) throws Exception {
        String str2;
        int i4 = i3 * 2;
        String upperCase = Bit.ToHexString(i).toUpperCase();
        if (upperCase.length() < i4) {
            int length = i4 - upperCase.length();
            String str3 = str;
            for (int i5 = 1; i5 <= length; i5++) {
                str3 = str3 + "0";
            }
            str2 = str3;
        } else {
            str2 = str;
        }
        return str2 + upperCase;
    }

    public String _timer1_tick() throws Exception {
        _tx();
        if (DateTime.getNow() - this._frmtime > 999) {
            this._fps = this._frmcnt;
            this._frmcnt = 0;
            this._frmtime = DateTime.getNow();
            Common.CallSubNew2(this.ba, this._m_obj, this._event_rx, 20);
        }
        if (this._to_disable) {
            this._timeout = DateTime.getNow() + 1000;
            return "";
        }
        if (DateTime.getNow() > this._timeout) {
            boolean z = this._senden;
            if (!z) {
                if (this._tx_old.length() > 1) {
                    byte[] bArr = new byte[((int) (((double) this._tx_old.length()) / 2.0d))];
                    int _strhextobyte = converter._strhextobyte(this._tx_old, bArr);
                    this._rx_idx = 0;
                    this._rxsize = 0;
                    if (main._bt._isconnected) {
                        main._bt._tx(bArr, 0, _strhextobyte);
                    } else {
                        if (main._usb1._isconnected) {
                            main._usb1._tx(bArr, 0, _strhextobyte);
                        }
                    }
                    this._timeout = DateTime.getNow() + 1000;
                    this._tx_old = "";
                } else {
//                    _on(false);
                    this._message = _messages(3);
//                    Common.CallSubNew2(this.ba, this._m_obj, this._event_rx, 21);
                }
            }
        }
        return "";
    }

    public String _tx() throws Exception {
        if (!main._bt._isconnected) {
            if (!main._usb1._isconnected) {
                return "";
            }
        }
        if (!this._senden || this._txlist.getSize() <= 0) {
            return "";
        }
        this._tx_old = String.valueOf(this._txlist.Get(0));
        byte[] bArr = new byte[this._tx_old.length() / 2];
        int _strhextobyte = converter._strhextobyte(this._tx_old, bArr);
        if (main._bt._isconnected) {
            main._bt._tx(bArr, 0, _strhextobyte);
        } else {
            if (main._usb1._isconnected) {
                main._usb1._tx(bArr, 0, _strhextobyte);
            }
        }
        this._txlist.RemoveAt(0);
        this._timeout = DateTime.getNow() + 1000;
        if (this._tx_old.equals("83000772000444")) {
            this._timeout = DateTime.getNow() + 1000 + (this._to_data * 3);
        }
        this.setSenden(false);
        return "";
    }

    public String _txalignsweep() throws Exception {
        this._txlist.Add(_sets("83000D7100055443F0", this._alignsweep, 13, 4));
        return "";
    }

    public String _txchangespeed() throws Exception {
        this._txlist.Add(_sets("910004", (int) ((1842000.0d / ((double) this._speed)) + 0.5d), 4, 1));
        return "";
    }

    public String _txconnect() throws Exception {
        this._txlist.Add("80000710001000");
        return "";
    }

    public String _txdelayedsweep() throws Exception {
        this._txlist.Add(_sets("83000D7100055444F1", this._delayedsweep, 13, 4));
        return "";
    }

    public String _txdevinfo() throws Exception {
        this._txlist.Add("830009700006564E4D");
        this._txlist.Add("83000970000656534E");
        this._txlist.Add("830009700006564857");
        this._txlist.Add("830009700006565357");
        return "";
    }

    public String _txinput() throws Exception {
        int ObjectToNumber = (int) BA.ObjectToNumber(this._lst_data[2].Get(this._lst_pos[2]));
        if (this._hfilter) {
            ObjectToNumber = Bit.Or(ObjectToNumber, 4);
        }
        if (this._lfilter) {
            ObjectToNumber = Bit.Or(ObjectToNumber, 8);
        }
        this._txlist.Add(_sets("83000A7100054F31B1", ObjectToNumber, 10, 1));
        return "";
    }

    public String _txlevelsync() throws Exception {
        this._txlist.Add(_sets("83000A7100055331B1", this._levelsync, 10, 1));
        return "";
    }

    public String _txmcycle() throws Exception {
        this._txlist.Add(_sets("83000D7100054D43F0", this._mcycle, 13, 4));
        return "";
    }

    public String _txminnumpas_strob() throws Exception {
        this._txlist.Add(_sets("83000A7100054152B1", this._minnumpas_strob, 10, 1));
        return "";
    }

    public String _txnext() throws Exception {
        this._txlist.Add("83000772000444");
        return "";
    }

    public String _txnextnow() throws Exception {
        this.setSenden(false);
        byte[] bArr2 = {-125, 0, 7, 114, 0, 4, 68};
        if (main._bt._isconnected) {
            main._bt._tx(bArr2, 0, 7);
        } else {
            if (main._usb1._isconnected) {
                main._usb1._tx(bArr2, 0, 7);
            }
        }
        this._timeout = DateTime.getNow() + 1000 + (this._to_data * 3);
        return "";
    }

    public String _txnumpas_avgpick() throws Exception {
        this._txlist.Add(_sets("83000A7100054150B1", this._numpas_avgpick, 10, 1));
        return "";
    }

    public String _txsamplemode() throws Exception {
        this._txlist.Add(_sets("83000A7100054D31B1", (int) BA.ObjectToNumber(this._lst_data[3].Get(this._lst_pos[3])), 10, 1));
        return "";
    }

    public String _txsamplmethod() throws Exception {
        this._txlist.Add(_sets("83000A7100055253B1", this._samplmethod, 10, 1));
        return "";
    }

    public String _txsampper() throws Exception {
        this._txlist.Add(_sets("83000D7100055453F1", this._sampper, 13, 4));
        return "";
    }

    public int _txsens() throws Exception {
        this._txlist.Add(_sets("83000D7100055631F0", (int) BA.ObjectToNumber(this._lst_data[1].Get(this._lst_pos[1])), 13, 4));
        return 0;
    }

    public int _txshift() throws Exception {
        if (this._shift < 0) {
            this._shift = 65536 + this._shift;
        }
        this._txlist.Add(_sets("83000D7100055031F0", this._shift, 13, 4));
        return 0;
    }

    public String _txsizesample() throws Exception {
        this._txlist.Add(_sets("83000D7100055153F0", this._sizesample, 13, 4));
        return "";
    }

    public String _txstart() throws Exception {
        this._txlist.Add("82000772000443");
        return "";
    }

    public String _txsync() throws Exception {
        this._txlist.Add(_sets("83000A7100055431B1", (int) BA.ObjectToNumber(this._lst_data[4].Get(this._lst_pos[4])), 10, 1));
        return "";
    }

    public String _txtimediv() throws Exception {
        this._divpix = 32;
        this._sizesample = this._sizesampler;
        switch (this._lst_pos[0]) {
            case 0:
            case 1:
            case 2:
                this._divpix = 16;
                this._mcycle = 1562;
                this._sizesample = 166;
                break;
            case 3:
                this._divpix = 16;
                this._mcycle = 1255;
                this._sizesample = 166;
                break;
            case 4:
                this._mcycle = 930;
                break;
            case 5:
                this._mcycle = 1280;
                break;
            case 6:
                this._mcycle = 1200;
                break;
            case 7:
                this._mcycle = 1200;
                break;
            case 8:
                this._mcycle = 1250;
                break;
            case 9:
                this._mcycle = 1248;
                break;
            case 10:
                this._mcycle = 1248;
                break;
            case 11:
                this._mcycle = 1248;
                break;
            case 12:
                this._mcycle = 1248;
                break;
            case 13:
                this._mcycle = 1248;
                break;
            case 14:
                this._mcycle = 1248;
                break;
            case 15:
                this._mcycle = 1248;
                break;
            case 16:
                this._mcycle = 1248;
                break;
            case 17:
                this._mcycle = 1248;
                break;
            case 18:
                this._mcycle = 1248;
                break;
        }
        _txmcycle();
        return "";
    }

    public String _txtosinca() throws Exception {
        this._txlist.Add(_sets("83000D7100055441F1", this._tosinca, 13, 4));
        return "";
    }

    public String _txtosincw() throws Exception {
        this._txlist.Add(_sets("83000D7100055457F1", this._tosincw, 13, 4));
        return "";
    }

    public String _txtypesync() throws Exception {
        this._txlist.Add(_sets("83000A7100055254B1", (int) BA.ObjectToNumber(this._lst_data[5].Get(this._lst_pos[5])), 10, 1));
        return "";
    }

    public String _vinfoset(float f, float f2, float f3, float f4, float f5) throws Exception {
        StringBuilder res = new StringBuilder(1024);
        if (this._vmax_en) {
            res.append("Umax = ");
            res.append(converter._inttovolt(f));
            res.append(Common.CRLF);
        }
        if (this._vmin_en) {
            res.append("Umin = ");
            res.append(converter._inttovolt(f2));
            res.append(Common.CRLF);
        }
        if (this._vamp_en) {
            res.append("Uamp = ");
            res.append(converter._inttovolt(f3));
            res.append(Common.CRLF);
        }
        if (this._vrms_en) {
            res.append("Urms = ");
            res.append(converter._inttovolt(f4));
            res.append(Common.CRLF);
        }
        if (this._vavg_en) {
            res.append("Uavg = ");
            res.append(converter._inttovolt(f5));
        }
        this._vinfo = res.toString();
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
