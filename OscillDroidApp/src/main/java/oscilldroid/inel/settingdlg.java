package oscilldroid.inel;

import android.graphics.Bitmap;
import android.view.View;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.CompoundButtonWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ScrollViewWrapper;
import anywheresoftware.b4a.objects.SpinnerWrapper;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import de.amberhome.AHLocale;
import java.lang.reflect.Method;
import java.util.HashMap;

public class settingdlg extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public LabelWrapper _b_close = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_f = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_pwm = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_q = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_t = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_tp = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_vamp = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_vavg = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_vmax = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_vmin = null;
    public CompoundButtonWrapper.CheckBoxWrapper _ch_vrms = null;
    public CompoundButtonWrapper.CheckBoxWrapper _check_3k = null;
    public CompoundButtonWrapper.CheckBoxWrapper _check_3m = null;
    public String _close_event = "";
    public converter _converter = null;
    public files _files = null;
    public main _main = null;
    public Object _sbmodule = null;
    public PanelWrapper _setting_pan = null;
    public ScrollViewWrapper _sv = null;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.settingdlg");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _but_close_click() throws Exception {
        Common common = this.__c;
        Common.CallSubNew(this.ba, this._sbmodule, this._close_event);
        return "";
    }

    public String _buton(LabelWrapper labelWrapper, boolean z) throws Exception {
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        if (z) {
            Common common = this.__c;
            File file = Common.File;
            bitmapWrapper.Initialize(File.getDirAssets(), "recton.png");
            labelWrapper.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
            Common common2 = this.__c;
            Colors colors = Common.Colors;
            labelWrapper.setTextColor(Colors.Yellow);
            Common common3 = this.__c;
            labelWrapper.setEnabled(true);
            return "";
        }
        Common common4 = this.__c;
        File file2 = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "rectoff.png");
        labelWrapper.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        Common common5 = this.__c;
        Colors colors2 = Common.Colors;
        labelWrapper.setTextColor(Colors.Gray);
        Common common6 = this.__c;
        labelWrapper.setEnabled(false);
        return "";
    }

    public String _ch_f_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#F_en:", valueOf, true);
        return "";
    }

    public String _ch_pwm_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#PWM_en:", valueOf, true);
        return "";
    }

    public String _ch_q_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Q_en:", valueOf, true);
        return "";
    }

    public String _ch_t_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#T_en:", valueOf, true);
        return "";
    }

    public String _ch_tp_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Tp_en:", valueOf, true);
        return "";
    }

    public String _ch_vamp_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Vamp_en:", valueOf, true);
        return "";
    }

    public String _ch_vavg_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Vavg_en:", valueOf, true);
        return "";
    }

    public String _ch_vmax_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Vmax_en:", valueOf, true);
        return "";
    }

    public String _ch_vmin_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Vmin_en:", valueOf, true);
        return "";
    }

    public String _ch_vrms_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Vrms_en:", valueOf, true);
        return "";
    }

    public String _check_3k_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Fl_3k:", valueOf, true);
        return "";
    }

    public String _check_3m_checkedchange(boolean z) throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(z);
        Common common = this.__c;
        files._datafile(ba, "osc.cfg", "#Fl_3m:", valueOf, true);
        return "";
    }

    public String _check_style(CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper, String str) throws Exception {
        Common common = this.__c;
        Colors colors = Common.Colors;
        checkBoxWrapper.setColor(Colors.Black);
        Common common2 = this.__c;
        Colors colors2 = Common.Colors;
        checkBoxWrapper.setTextColor(-1);
        Common common3 = this.__c;
        Gravity gravity = Common.Gravity;
        checkBoxWrapper.setGravity(3);
        checkBoxWrapper.setText(str);
        return "";
    }

    public String _class_globals() throws Exception {
        this._setting_pan = new PanelWrapper();
        this._sv = new ScrollViewWrapper();
        this._sbmodule = new Object();
        this._close_event = "";
        this._b_close = new LabelWrapper();
        this._ch_vmax = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_vmin = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_vamp = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_vavg = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_vrms = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_t = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_f = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_tp = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_q = new CompoundButtonWrapper.CheckBoxWrapper();
        this._ch_pwm = new CompoundButtonWrapper.CheckBoxWrapper();
        this._check_3k = new CompoundButtonWrapper.CheckBoxWrapper();
        this._check_3m = new CompoundButtonWrapper.CheckBoxWrapper();
        return "";
    }

    public String _initialize(BA ba, ActivityWrapper activityWrapper, Object obj, String str) throws Exception {
        innerInitialize(ba);
        LabelWrapper labelWrapper = new LabelWrapper();
        SpinnerWrapper spinnerWrapper = new SpinnerWrapper();
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        this._sbmodule = obj;
        this._close_event = str;
        this._setting_pan.Initialize(this.ba, "Setting");
        PanelWrapper panelWrapper = this._setting_pan;
        Common common = this.__c;
        Colors colors = Common.Colors;
        panelWrapper.setColor(Colors.Black);
        int width = activityWrapper.getWidth();
        Common common2 = this.__c;
        Common common3 = this.__c;
        activityWrapper.AddView((View) this._setting_pan.getObject(), (int) (((double) (width - Common.DipToCurrent(224))) / 2.0d), 0, Common.DipToCurrent(224), activityWrapper.getHeight());
        Common common4 = this.__c;
        File file = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "dlg.png");
        this._setting_pan.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        labelWrapper.Initialize(this.ba, "");
        Common common5 = this.__c;
        Gravity gravity = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common6 = this.__c;
        Colors colors2 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common7 = this.__c;
        Colors colors3 = Common.Colors;
        labelWrapper.setTextColor(Colors.Yellow);
        labelWrapper.setTextSize(labelWrapper.getTextSize() * 2.0f);
        labelWrapper.setText(_langstr("Setting"));
        Common common8 = this.__c;
        File file2 = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "recton.png");
        labelWrapper.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        int width2 = this._setting_pan.getWidth();
        Common common9 = this.__c;
        this._setting_pan.AddView((View) labelWrapper.getObject(), 0, 0, width2, Common.DipToCurrent(40));
        ScrollViewWrapper scrollViewWrapper = this._sv;
        BA ba2 = this.ba;
        Common common10 = this.__c;
        scrollViewWrapper.Initialize(ba2, Common.DipToCurrent(500));
        Common common11 = this.__c;
        int DipToCurrent = Common.DipToCurrent(10);
        Common common12 = this.__c;
        int DipToCurrent2 = Common.DipToCurrent(50);
        int width3 = this._setting_pan.getWidth();
        Common common13 = this.__c;
        int DipToCurrent3 = width3 - Common.DipToCurrent(20);
        int height = this._setting_pan.getHeight();
        Common common14 = this.__c;
        this._setting_pan.AddView((View) this._sv.getObject(), DipToCurrent, DipToCurrent2, DipToCurrent3, height - Common.DipToCurrent(110));
        this._b_close.Initialize(this.ba, "But_Close");
        this._b_close.setText(_langstr("Close"));
        LabelWrapper labelWrapper2 = this._b_close;
        Common common15 = this.__c;
        Gravity gravity2 = Common.Gravity;
        labelWrapper2.setGravity(17);
        LabelWrapper labelWrapper3 = this._b_close;
        Common common16 = this.__c;
        _buton(labelWrapper3, true);
        Common common17 = this.__c;
        int DipToCurrent4 = Common.DipToCurrent(10);
        int height2 = this._setting_pan.getHeight();
        Common common18 = this.__c;
        int DipToCurrent5 = height2 - Common.DipToCurrent(50);
        int width4 = this._setting_pan.getWidth();
        Common common19 = this.__c;
        int DipToCurrent6 = width4 - Common.DipToCurrent(20);
        Common common20 = this.__c;
        this._setting_pan.AddView((View) this._b_close.getObject(), DipToCurrent4, DipToCurrent5, DipToCurrent6, Common.DipToCurrent(40));
        labelWrapper.Initialize(this.ba, "");
        Common common21 = this.__c;
        Gravity gravity3 = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common22 = this.__c;
        Colors colors4 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common23 = this.__c;
        Colors colors5 = Common.Colors;
        labelWrapper.setTextColor(Colors.Green);
        labelWrapper.setTextSize(labelWrapper.getTextSize() * 2.0f);
        labelWrapper.setText("_" + _langstr("Filters") + "_");
        Common common24 = this.__c;
        int DipToCurrent7 = Common.DipToCurrent(204);
        Common common25 = this.__c;
        this._sv.getPanel().AddView((View) labelWrapper.getObject(), 0, 0, DipToCurrent7, Common.DipToCurrent(40));
        Common common26 = this.__c;
        int height3 = labelWrapper.getHeight() + 0 + Common.DipToCurrent(10);
        this._check_3k.Initialize(this.ba, "check_3k");
        CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper = this._check_3k;
        Common common27 = this.__c;
        Gravity gravity4 = Common.Gravity;
        checkBoxWrapper.setGravity(3);
        this._check_3k.setText("3kHz");
        CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper2 = this._check_3k;
        Common common28 = this.__c;
        Colors colors6 = Common.Colors;
        checkBoxWrapper2.setColor(Colors.Black);
        CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper3 = this._check_3k;
        Common common29 = this.__c;
        Colors colors7 = Common.Colors;
        checkBoxWrapper3.setTextColor(-1);
        files files = this._files;
        BA ba3 = this.ba;
        Common common30 = this.__c;
        String valueOf = String.valueOf(false);
        Common common31 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba3, "osc.cfg", "#Fl_3k:", valueOf, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper4 = this._check_3k;
            Common common32 = this.__c;
            checkBoxWrapper4.setChecked(true);
        }
        Common common33 = this.__c;
        int DipToCurrent8 = Common.DipToCurrent(100);
        Common common34 = this.__c;
        this._sv.getPanel().AddView((View) this._check_3k.getObject(), 0, height3, DipToCurrent8, Common.DipToCurrent(40));
        Common common35 = this.__c;
        int DipToCurrent9 = 0 + Common.DipToCurrent(104);
        this._check_3m.Initialize(this.ba, "check_3m");
        CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper5 = this._check_3m;
        Common common36 = this.__c;
        Gravity gravity5 = Common.Gravity;
        checkBoxWrapper5.setGravity(3);
        this._check_3m.setText("3MHz");
        CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper6 = this._check_3m;
        Common common37 = this.__c;
        Colors colors8 = Common.Colors;
        checkBoxWrapper6.setColor(Colors.Black);
        CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper7 = this._check_3m;
        Common common38 = this.__c;
        Colors colors9 = Common.Colors;
        checkBoxWrapper7.setTextColor(-1);
        files files2 = this._files;
        BA ba4 = this.ba;
        Common common39 = this.__c;
        String valueOf2 = String.valueOf(false);
        Common common40 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba4, "osc.cfg", "#Fl_3m:", valueOf2, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper8 = this._check_3m;
            Common common41 = this.__c;
            checkBoxWrapper8.setChecked(true);
        }
        Common common42 = this.__c;
        int DipToCurrent10 = Common.DipToCurrent(100);
        Common common43 = this.__c;
        this._sv.getPanel().AddView((View) this._check_3m.getObject(), DipToCurrent9, height3, DipToCurrent10, Common.DipToCurrent(40));
        Common common44 = this.__c;
        int height4 = this._check_3m.getHeight() + height3 + Common.DipToCurrent(10);
        labelWrapper.Initialize(this.ba, "");
        Common common45 = this.__c;
        Gravity gravity6 = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common46 = this.__c;
        Colors colors10 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common47 = this.__c;
        Colors colors11 = Common.Colors;
        labelWrapper.setTextColor(Colors.Green);
        labelWrapper.setTextSize(labelWrapper.getTextSize() * 2.0f);
        labelWrapper.setText("_" + _langstr("Voltage Info") + "_");
        Common common48 = this.__c;
        int DipToCurrent11 = Common.DipToCurrent(204);
        Common common49 = this.__c;
        this._sv.getPanel().AddView((View) labelWrapper.getObject(), 0, height4, DipToCurrent11, Common.DipToCurrent(40));
        Common common50 = this.__c;
        int height5 = labelWrapper.getHeight() + height4 + Common.DipToCurrent(10);
        this._ch_vmax.Initialize(this.ba, "ch_Vmax");
        _check_style(this._ch_vmax, "Umax");
        files files3 = this._files;
        BA ba5 = this.ba;
        Common common51 = this.__c;
        String valueOf3 = String.valueOf(true);
        Common common52 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba5, "osc.cfg", "#Vmax_en:", valueOf3, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper9 = this._ch_vmax;
            Common common53 = this.__c;
            checkBoxWrapper9.setChecked(true);
        }
        Common common54 = this.__c;
        int DipToCurrent12 = Common.DipToCurrent(100);
        Common common55 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_vmax.getObject(), 0, height5, DipToCurrent12, Common.DipToCurrent(40));
        Common common56 = this.__c;
        int DipToCurrent13 = 0 + Common.DipToCurrent(104);
        this._ch_vmin.Initialize(this.ba, "ch_Vmin");
        _check_style(this._ch_vmin, "Umin");
        files files4 = this._files;
        BA ba6 = this.ba;
        Common common57 = this.__c;
        String valueOf4 = String.valueOf(true);
        Common common58 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba6, "osc.cfg", "#Vmin_en:", valueOf4, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper10 = this._ch_vmin;
            Common common59 = this.__c;
            checkBoxWrapper10.setChecked(true);
        }
        Common common60 = this.__c;
        int DipToCurrent14 = Common.DipToCurrent(100);
        Common common61 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_vmin.getObject(), DipToCurrent13, height5, DipToCurrent14, Common.DipToCurrent(40));
        Common common62 = this.__c;
        int DipToCurrent15 = height5 + Common.DipToCurrent(42);
        this._ch_vamp.Initialize(this.ba, "ch_Vamp");
        _check_style(this._ch_vamp, "Uamp");
        files files5 = this._files;
        BA ba7 = this.ba;
        Common common63 = this.__c;
        String valueOf5 = String.valueOf(true);
        Common common64 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba7, "osc.cfg", "#Vamp_en:", valueOf5, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper11 = this._ch_vamp;
            Common common65 = this.__c;
            checkBoxWrapper11.setChecked(true);
        }
        Common common66 = this.__c;
        int DipToCurrent16 = Common.DipToCurrent(100);
        Common common67 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_vamp.getObject(), 0, DipToCurrent15, DipToCurrent16, Common.DipToCurrent(40));
        Common common68 = this.__c;
        int DipToCurrent17 = 0 + Common.DipToCurrent(104);
        this._ch_vavg.Initialize(this.ba, "ch_Vavg");
        _check_style(this._ch_vavg, "Uavg");
        files files6 = this._files;
        BA ba8 = this.ba;
        Common common69 = this.__c;
        String valueOf6 = String.valueOf(true);
        Common common70 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba8, "osc.cfg", "#Vavg_en:", valueOf6, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper12 = this._ch_vavg;
            Common common71 = this.__c;
            checkBoxWrapper12.setChecked(true);
        }
        Common common72 = this.__c;
        int DipToCurrent18 = Common.DipToCurrent(100);
        Common common73 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_vavg.getObject(), DipToCurrent17, DipToCurrent15, DipToCurrent18, Common.DipToCurrent(40));
        Common common74 = this.__c;
        int DipToCurrent19 = DipToCurrent15 + Common.DipToCurrent(42);
        this._ch_vrms.Initialize(this.ba, "ch_Vrms");
        _check_style(this._ch_vrms, "Urms");
        files files7 = this._files;
        BA ba9 = this.ba;
        Common common75 = this.__c;
        String valueOf7 = String.valueOf(true);
        Common common76 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba9, "osc.cfg", "#Vrms_en:", valueOf7, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper13 = this._ch_vrms;
            Common common77 = this.__c;
            checkBoxWrapper13.setChecked(true);
        }
        Common common78 = this.__c;
        int DipToCurrent20 = Common.DipToCurrent(100);
        Common common79 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_vrms.getObject(), 0, DipToCurrent19, DipToCurrent20, Common.DipToCurrent(40));
        Common common80 = this.__c;
        int DipToCurrent21 = DipToCurrent19 + Common.DipToCurrent(50);
        labelWrapper.Initialize(this.ba, "");
        Common common81 = this.__c;
        Gravity gravity7 = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common82 = this.__c;
        Colors colors12 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common83 = this.__c;
        Colors colors13 = Common.Colors;
        labelWrapper.setTextColor(Colors.Green);
        labelWrapper.setTextSize(labelWrapper.getTextSize() * 2.0f);
        labelWrapper.setText("_" + _langstr("Time Info") + "_");
        Common common84 = this.__c;
        int DipToCurrent22 = Common.DipToCurrent(204);
        Common common85 = this.__c;
        this._sv.getPanel().AddView((View) labelWrapper.getObject(), 0, DipToCurrent21, DipToCurrent22, Common.DipToCurrent(40));
        Common common86 = this.__c;
        int DipToCurrent23 = DipToCurrent21 + Common.DipToCurrent(50);
        this._ch_t.Initialize(this.ba, "ch_T");
        _check_style(this._ch_t, "T");
        files files8 = this._files;
        BA ba10 = this.ba;
        Common common87 = this.__c;
        String valueOf8 = String.valueOf(true);
        Common common88 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba10, "osc.cfg", "#T_en:", valueOf8, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper14 = this._ch_t;
            Common common89 = this.__c;
            checkBoxWrapper14.setChecked(true);
        }
        Common common90 = this.__c;
        int DipToCurrent24 = Common.DipToCurrent(100);
        Common common91 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_t.getObject(), 0, DipToCurrent23, DipToCurrent24, Common.DipToCurrent(40));
        Common common92 = this.__c;
        int DipToCurrent25 = 0 + Common.DipToCurrent(104);
        this._ch_f.Initialize(this.ba, "ch_F");
        _check_style(this._ch_f, "F");
        files files9 = this._files;
        BA ba11 = this.ba;
        Common common93 = this.__c;
        String valueOf9 = String.valueOf(true);
        Common common94 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba11, "osc.cfg", "#F_en:", valueOf9, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper15 = this._ch_f;
            Common common95 = this.__c;
            checkBoxWrapper15.setChecked(true);
        }
        Common common96 = this.__c;
        int DipToCurrent26 = Common.DipToCurrent(100);
        Common common97 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_f.getObject(), DipToCurrent25, DipToCurrent23, DipToCurrent26, Common.DipToCurrent(40));
        Common common98 = this.__c;
        int DipToCurrent27 = DipToCurrent23 + Common.DipToCurrent(42);
        this._ch_tp.Initialize(this.ba, "ch_Tp");
        _check_style(this._ch_tp, "Tp");
        files files10 = this._files;
        BA ba12 = this.ba;
        Common common99 = this.__c;
        String valueOf10 = String.valueOf(true);
        Common common100 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba12, "osc.cfg", "#Tp_en:", valueOf10, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper16 = this._ch_tp;
            Common common101 = this.__c;
            checkBoxWrapper16.setChecked(true);
        }
        Common common102 = this.__c;
        int DipToCurrent28 = Common.DipToCurrent(100);
        Common common103 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_tp.getObject(), 0, DipToCurrent27, DipToCurrent28, Common.DipToCurrent(40));
        Common common104 = this.__c;
        int DipToCurrent29 = 0 + Common.DipToCurrent(104);
        this._ch_q.Initialize(this.ba, "ch_Q");
        _check_style(this._ch_q, "Q");
        files files11 = this._files;
        BA ba13 = this.ba;
        Common common105 = this.__c;
        String valueOf11 = String.valueOf(true);
        Common common106 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba13, "osc.cfg", "#Q_en:", valueOf11, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper17 = this._ch_q;
            Common common107 = this.__c;
            checkBoxWrapper17.setChecked(true);
        }
        Common common108 = this.__c;
        int DipToCurrent30 = Common.DipToCurrent(100);
        Common common109 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_q.getObject(), DipToCurrent29, DipToCurrent27, DipToCurrent30, Common.DipToCurrent(40));
        Common common110 = this.__c;
        int DipToCurrent31 = DipToCurrent27 + Common.DipToCurrent(42);
        this._ch_pwm.Initialize(this.ba, "ch_PWM");
        _check_style(this._ch_pwm, "PWM");
        files files12 = this._files;
        BA ba14 = this.ba;
        Common common111 = this.__c;
        String valueOf12 = String.valueOf(true);
        Common common112 = this.__c;
        if (BA.ObjectToBoolean(files._datafile(ba14, "osc.cfg", "#PWM_en:", valueOf12, false))) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper18 = this._ch_pwm;
            Common common113 = this.__c;
            checkBoxWrapper18.setChecked(true);
        }
        Common common114 = this.__c;
        int DipToCurrent32 = Common.DipToCurrent(100);
        Common common115 = this.__c;
        this._sv.getPanel().AddView((View) this._ch_pwm.getObject(), 0, DipToCurrent31, DipToCurrent32, Common.DipToCurrent(40));
        Common common116 = this.__c;
        int DipToCurrent33 = DipToCurrent31 + Common.DipToCurrent(50);
        labelWrapper.Initialize(this.ba, "");
        Common common117 = this.__c;
        Gravity gravity8 = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common118 = this.__c;
        Colors colors14 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common119 = this.__c;
        Colors colors15 = Common.Colors;
        labelWrapper.setTextColor(Colors.Green);
        labelWrapper.setTextSize(labelWrapper.getTextSize() * 2.0f);
        labelWrapper.setText("_" + _langstr("Size Sample") + "_");
        Common common120 = this.__c;
        int DipToCurrent34 = Common.DipToCurrent(204);
        Common common121 = this.__c;
        this._sv.getPanel().AddView((View) labelWrapper.getObject(), 0, DipToCurrent33, DipToCurrent34, Common.DipToCurrent(40));
        Common common122 = this.__c;
        int DipToCurrent35 = DipToCurrent33 + Common.DipToCurrent(50);
        spinnerWrapper.Initialize(this.ba, "Spin");
        spinnerWrapper.setTag("Size");
        spinnerWrapper.Add("332");
        spinnerWrapper.Add("664");
        spinnerWrapper.Add("1328");
        spinnerWrapper.Add("1660");
        files files13 = this._files;
        BA ba15 = this.ba;
        String NumberToString = BA.NumberToString(332);
        Common common123 = this.__c;
        spinnerWrapper.setSelectedIndex(spinnerWrapper.IndexOf(files._datafile(ba15, "osc.cfg", "#Size:", NumberToString, false)));
        Common common124 = this.__c;
        int DipToCurrent36 = Common.DipToCurrent(204);
        Common common125 = this.__c;
        this._sv.getPanel().AddView((View) spinnerWrapper.getObject(), 0, DipToCurrent35, DipToCurrent36, Common.DipToCurrent(40));
        Common common126 = this.__c;
        int DipToCurrent37 = DipToCurrent35 + Common.DipToCurrent(50);
        labelWrapper.Initialize(this.ba, "");
        Common common127 = this.__c;
        Gravity gravity9 = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common128 = this.__c;
        Colors colors16 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common129 = this.__c;
        Colors colors17 = Common.Colors;
        labelWrapper.setTextColor(Colors.Green);
        labelWrapper.setTextSize(labelWrapper.getTextSize() * 2.0f);
        labelWrapper.setText("_" + _langstr("Vol Buttons") + "_");
        Common common130 = this.__c;
        int DipToCurrent38 = Common.DipToCurrent(204);
        Common common131 = this.__c;
        this._sv.getPanel().AddView((View) labelWrapper.getObject(), 0, DipToCurrent37, DipToCurrent38, Common.DipToCurrent(40));
        Common common132 = this.__c;
        int DipToCurrent39 = DipToCurrent37 + Common.DipToCurrent(50);
        spinnerWrapper.Initialize(this.ba, "Spin");
        spinnerWrapper.setTag("Vol");
        spinnerWrapper.Add("Time");
        spinnerWrapper.Add("Voltage");
        spinnerWrapper.Add("Input");
        spinnerWrapper.Add("Mode");
        spinnerWrapper.Add("Trigger");
        files files14 = this._files;
        BA ba16 = this.ba;
        String NumberToString2 = BA.NumberToString(0);
        Common common133 = this.__c;
        spinnerWrapper.setSelectedIndex((int) Double.parseDouble(files._datafile(ba16, "osc.cfg", "#Vol_but:", NumberToString2, false)));
        Common common134 = this.__c;
        int DipToCurrent40 = Common.DipToCurrent(204);
        Common common135 = this.__c;
        this._sv.getPanel().AddView((View) spinnerWrapper.getObject(), 0, DipToCurrent39, DipToCurrent40, Common.DipToCurrent(40));
        Common common136 = this.__c;
        this._sv.getPanel().setHeight(Common.DipToCurrent(50) + DipToCurrent39);
        return "";
    }

    public String _langstr(String str) throws Exception {
        AHLocale aHLocale = new AHLocale();
        aHLocale.Initialize();
        if (aHLocale.getLanguage().equals("ru")) {
            switch (BA.switchObjectToInt(str, "Setting", "Close", "Filters", "Voltage Info", "Time Info", "Size Sample", "Vol Buttons")) {
                case 0:
                    return "Настройки";
                case 1:
                    return "Закрыть";
                case 2:
                    return "Фильтры";
                case 3:
                    return "Напряжение";
                case 4:
                    return "Время";
                case 5:
                    return "Размер";
                case 6:
                    return "Кнопки Vol";
            }
        }
        return str;
    }

    public String _setting_touch(int i, float f, float f2) throws Exception {
        return "";
    }

    public String _spin_itemclick(int i, Object obj) throws Exception {
        SpinnerWrapper spinnerWrapper = new SpinnerWrapper();
        Common common = this.__c;
        spinnerWrapper.setObject((SpinnerWrapper.B4ASpinner) Common.Sender(this.ba));
        switch (BA.switchObjectToInt(spinnerWrapper.getTag(), "Size", "Vol")) {
            case 0:
                files files = this._files;
                BA ba = this.ba;
                String valueOf = String.valueOf(obj);
                Common common2 = this.__c;
                files._datafile(ba, "osc.cfg", "#Size:", valueOf, true);
                return "";
            case 1:
                files files2 = this._files;
                BA ba2 = this.ba;
                String NumberToString = BA.NumberToString(spinnerWrapper.getSelectedIndex());
                Common common3 = this.__c;
                files2._datafile(ba2, "osc.cfg", "#Vol_but:", NumberToString, true);
                return "";
            default:
                return "";
        }
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
