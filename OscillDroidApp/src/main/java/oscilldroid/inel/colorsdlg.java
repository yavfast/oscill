package oscilldroid.inel;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Bit;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.SeekBarWrapper;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.lang.reflect.Method;
import java.util.HashMap;

public class colorsdlg extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public String _close_event = "";
    public PanelWrapper _colors_pan = null;
    public converter _converter = null;
    public int[] _fargb = null;
    public files _files = null;
    public LabelWrapper _lbl = null;
    public LabelWrapper[] _lbltxt = null;
    public List _lst = null;
    public List _lstclr = null;
    public int _lstindex = 0;
    public main _main = null;
    public mylistview _mlv = null;
    public SeekBarWrapper[] _sb = null;
    public Object _sbmodule = null;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.colorsdlg");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _btnclose_click() throws Exception {
        Common common = this.__c;
        Common.CallSubNew(this.ba, this._sbmodule, this._close_event);
        return "";
    }

    public String _btnset_click() throws Exception {
        files files = this._files;
        BA ba = this.ba;
        String valueOf = String.valueOf(this._lst.Get(this._lstindex));
        Common common = this.__c;
        Colors colors = Common.Colors;
        String NumberToString = BA.NumberToString(Colors.ARGB(this._fargb[0], this._fargb[1], this._fargb[2], this._fargb[3]));
        Common common2 = this.__c;
        files._datafile(ba, "colors.cfg", valueOf, NumberToString, true);
        _reload();
        return "";
    }

    public String _class_globals() throws Exception {
        this._colors_pan = new PanelWrapper();
        this._mlv = new mylistview();
        this._sb = new SeekBarWrapper[4];
        int length = this._sb.length;
        for (int i = 0; i < length; i++) {
            this._sb[i] = new SeekBarWrapper();
        }
        this._lbl = new LabelWrapper();
        this._fargb = new int[4];
        this._lst = new List();
        this._lstclr = new List();
        this._lstindex = 0;
        this._sbmodule = new Object();
        this._close_event = "";
        this._lbltxt = new LabelWrapper[4];
        int length2 = this._lbltxt.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this._lbltxt[i2] = new LabelWrapper();
        }
        return "";
    }

    public int[] _getargb(int i) throws Exception {
        Common common = this.__c;
        Bit bit = Common.Bit;
        Common common2 = this.__c;
        Bit bit2 = Common.Bit;
        Common common3 = this.__c;
        Bit bit3 = Common.Bit;
        Common common4 = this.__c;
        Bit bit4 = Common.Bit;
        Common common5 = this.__c;
        Bit bit5 = Common.Bit;
        Common common6 = this.__c;
        Bit bit6 = Common.Bit;
        Common common7 = this.__c;
        Bit bit7 = Common.Bit;
        return new int[]{Bit.UnsignedShiftRight(Bit.And(i, Colors.Black), 24), Bit.UnsignedShiftRight(Bit.And(i, 16711680), 16), Bit.UnsignedShiftRight(Bit.And(i, 65280), 8), Bit.And(i, 255)};
    }

    public String _initialize(BA ba, ActivityWrapper activityWrapper, Object obj, String str) throws Exception {
        innerInitialize(ba);
        PanelWrapper panelWrapper = new PanelWrapper();
        LabelWrapper labelWrapper = new LabelWrapper();
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        this._sbmodule = obj;
        this._close_event = str;
        this._colors_pan.Initialize(this.ba, "");
        activityWrapper.AddView((View) this._colors_pan.getObject(), 0, 0, activityWrapper.getWidth(), activityWrapper.getHeight());
        PanelWrapper panelWrapper2 = this._colors_pan;
        Common common = this.__c;
        Colors colors = Common.Colors;
        panelWrapper2.setColor(Colors.Black);
        panelWrapper.Initialize(this.ba, "");
        Common common2 = this.__c;
        int DipToCurrent = Common.DipToCurrent(5);
        Common common3 = this.__c;
        int DipToCurrent2 = Common.DipToCurrent(5);
        Common common4 = this.__c;
        int DipToCurrent3 = Common.DipToCurrent(170);
        int height = this._colors_pan.getHeight();
        Common common5 = this.__c;
        this._colors_pan.AddView((View) panelWrapper.getObject(), DipToCurrent, DipToCurrent2, DipToCurrent3, height - Common.DipToCurrent(10));
        labelWrapper.Initialize(this.ba, "");
        Common common6 = this.__c;
        Colors colors2 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common7 = this.__c;
        Colors colors3 = Common.Colors;
        labelWrapper.setTextColor(-1);
        Common common8 = this.__c;
        labelWrapper.setTextSize((float) Common.DipToCurrent(14));
        Common common9 = this.__c;
        Gravity gravity = Common.Gravity;
        labelWrapper.setGravity(17);
        labelWrapper.setText("Alpha");
        Common common10 = this.__c;
        int DipToCurrent4 = Common.DipToCurrent(200);
        Common common11 = this.__c;
        int DipToCurrent5 = Common.DipToCurrent(10);
        int width = this._colors_pan.getWidth();
        Common common12 = this.__c;
        int DipToCurrent6 = width - Common.DipToCurrent(270);
        Common common13 = this.__c;
        this._colors_pan.AddView((View) labelWrapper.getObject(), DipToCurrent4, DipToCurrent5, DipToCurrent6, Common.DipToCurrent(50));
        this._lbl.Initialize(this.ba, "");
        Common common14 = this.__c;
        int DipToCurrent7 = Common.DipToCurrent(200);
        Common common15 = this.__c;
        int DipToCurrent8 = Common.DipToCurrent(10);
        int width2 = this._colors_pan.getWidth();
        Common common16 = this.__c;
        int DipToCurrent9 = width2 - Common.DipToCurrent(270);
        Common common17 = this.__c;
        this._colors_pan.AddView((View) this._lbl.getObject(), DipToCurrent7, DipToCurrent8, DipToCurrent9, Common.DipToCurrent(50));
        this._sb[0].Initialize(this.ba, "SeekRed");
        SeekBarWrapper seekBarWrapper = this._sb[0];
        Common common18 = this.__c;
        Colors colors4 = Common.Colors;
        seekBarWrapper.setColor(Colors.Red);
        this._sb[0].setMax(255);
        this._sb[0].setValue(127);
        Common common19 = this.__c;
        int DipToCurrent10 = Common.DipToCurrent(200);
        Common common20 = this.__c;
        int DipToCurrent11 = Common.DipToCurrent(70);
        int width3 = this._colors_pan.getWidth();
        Common common21 = this.__c;
        int DipToCurrent12 = width3 - Common.DipToCurrent(270);
        Common common22 = this.__c;
        this._colors_pan.AddView((View) this._sb[0].getObject(), DipToCurrent10, DipToCurrent11, DipToCurrent12, Common.DipToCurrent(40));
        this._lbltxt[0].Initialize(this.ba, "");
        LabelWrapper labelWrapper2 = this._lbltxt[0];
        Common common23 = this.__c;
        Gravity gravity2 = Common.Gravity;
        labelWrapper2.setGravity(17);
        LabelWrapper labelWrapper3 = this._lbltxt[0];
        Common common24 = this.__c;
        Colors colors5 = Common.Colors;
        labelWrapper3.setTextColor(Colors.Red);
        Common common25 = this.__c;
        int DipToCurrent13 = Common.DipToCurrent(200) + this._sb[0].getWidth();
        Common common26 = this.__c;
        int DipToCurrent14 = DipToCurrent13 + Common.DipToCurrent(10);
        Common common27 = this.__c;
        int DipToCurrent15 = Common.DipToCurrent(70);
        Common common28 = this.__c;
        int DipToCurrent16 = Common.DipToCurrent(50);
        Common common29 = this.__c;
        this._colors_pan.AddView((View) this._lbltxt[0].getObject(), DipToCurrent14, DipToCurrent15, DipToCurrent16, Common.DipToCurrent(40));
        this._sb[1].Initialize(this.ba, "SeekGreen");
        SeekBarWrapper seekBarWrapper2 = this._sb[1];
        Common common30 = this.__c;
        Colors colors6 = Common.Colors;
        seekBarWrapper2.setColor(Colors.Green);
        this._sb[1].setMax(255);
        this._sb[1].setValue(127);
        Common common31 = this.__c;
        int DipToCurrent17 = Common.DipToCurrent(200);
        Common common32 = this.__c;
        int DipToCurrent18 = Common.DipToCurrent(120);
        int width4 = this._colors_pan.getWidth();
        Common common33 = this.__c;
        int DipToCurrent19 = width4 - Common.DipToCurrent(270);
        Common common34 = this.__c;
        this._colors_pan.AddView((View) this._sb[1].getObject(), DipToCurrent17, DipToCurrent18, DipToCurrent19, Common.DipToCurrent(40));
        this._lbltxt[1].Initialize(this.ba, "");
        LabelWrapper labelWrapper4 = this._lbltxt[1];
        Common common35 = this.__c;
        Gravity gravity3 = Common.Gravity;
        labelWrapper4.setGravity(17);
        LabelWrapper labelWrapper5 = this._lbltxt[1];
        Common common36 = this.__c;
        Colors colors7 = Common.Colors;
        labelWrapper5.setTextColor(Colors.Green);
        Common common37 = this.__c;
        int DipToCurrent20 = Common.DipToCurrent(200) + this._sb[1].getWidth();
        Common common38 = this.__c;
        int DipToCurrent21 = DipToCurrent20 + Common.DipToCurrent(10);
        Common common39 = this.__c;
        int DipToCurrent22 = Common.DipToCurrent(120);
        Common common40 = this.__c;
        int DipToCurrent23 = Common.DipToCurrent(50);
        Common common41 = this.__c;
        this._colors_pan.AddView((View) this._lbltxt[1].getObject(), DipToCurrent21, DipToCurrent22, DipToCurrent23, Common.DipToCurrent(40));
        this._sb[2].Initialize(this.ba, "SeekBlue");
        SeekBarWrapper seekBarWrapper3 = this._sb[2];
        Common common42 = this.__c;
        Colors colors8 = Common.Colors;
        seekBarWrapper3.setColor(Colors.Blue);
        this._sb[2].setMax(255);
        this._sb[2].setValue(127);
        Common common43 = this.__c;
        int DipToCurrent24 = Common.DipToCurrent(200);
        Common common44 = this.__c;
        int DipToCurrent25 = Common.DipToCurrent(170);
        int width5 = this._colors_pan.getWidth();
        Common common45 = this.__c;
        int DipToCurrent26 = width5 - Common.DipToCurrent(270);
        Common common46 = this.__c;
        this._colors_pan.AddView((View) this._sb[2].getObject(), DipToCurrent24, DipToCurrent25, DipToCurrent26, Common.DipToCurrent(40));
        this._lbltxt[2].Initialize(this.ba, "");
        LabelWrapper labelWrapper6 = this._lbltxt[2];
        Common common47 = this.__c;
        Gravity gravity4 = Common.Gravity;
        labelWrapper6.setGravity(17);
        LabelWrapper labelWrapper7 = this._lbltxt[2];
        Common common48 = this.__c;
        Colors colors9 = Common.Colors;
        labelWrapper7.setTextColor(Colors.Blue);
        Common common49 = this.__c;
        int DipToCurrent27 = Common.DipToCurrent(200) + this._sb[2].getWidth();
        Common common50 = this.__c;
        int DipToCurrent28 = DipToCurrent27 + Common.DipToCurrent(10);
        Common common51 = this.__c;
        int DipToCurrent29 = Common.DipToCurrent(170);
        Common common52 = this.__c;
        int DipToCurrent30 = Common.DipToCurrent(50);
        Common common53 = this.__c;
        this._colors_pan.AddView((View) this._lbltxt[2].getObject(), DipToCurrent28, DipToCurrent29, DipToCurrent30, Common.DipToCurrent(40));
        this._sb[3].Initialize(this.ba, "SeekAlpha");
        SeekBarWrapper seekBarWrapper4 = this._sb[3];
        Common common54 = this.__c;
        Colors colors10 = Common.Colors;
        seekBarWrapper4.setColor(Colors.Gray);
        this._sb[3].setMax(255);
        this._sb[3].setValue(255);
        Common common55 = this.__c;
        int DipToCurrent31 = Common.DipToCurrent(200);
        Common common56 = this.__c;
        int DipToCurrent32 = Common.DipToCurrent(220);
        int width6 = this._colors_pan.getWidth();
        Common common57 = this.__c;
        int DipToCurrent33 = width6 - Common.DipToCurrent(270);
        Common common58 = this.__c;
        this._colors_pan.AddView((View) this._sb[3].getObject(), DipToCurrent31, DipToCurrent32, DipToCurrent33, Common.DipToCurrent(40));
        this._lbltxt[3].Initialize(this.ba, "");
        LabelWrapper labelWrapper8 = this._lbltxt[3];
        Common common59 = this.__c;
        Gravity gravity5 = Common.Gravity;
        labelWrapper8.setGravity(17);
        LabelWrapper labelWrapper9 = this._lbltxt[3];
        Common common60 = this.__c;
        Colors colors11 = Common.Colors;
        labelWrapper9.setTextColor(-1);
        Common common61 = this.__c;
        int DipToCurrent34 = Common.DipToCurrent(200) + this._sb[3].getWidth();
        Common common62 = this.__c;
        int DipToCurrent35 = DipToCurrent34 + Common.DipToCurrent(10);
        Common common63 = this.__c;
        int DipToCurrent36 = Common.DipToCurrent(220);
        Common common64 = this.__c;
        int DipToCurrent37 = Common.DipToCurrent(50);
        Common common65 = this.__c;
        this._colors_pan.AddView((View) this._lbltxt[3].getObject(), DipToCurrent35, DipToCurrent36, DipToCurrent37, Common.DipToCurrent(40));
        labelWrapper.Initialize(this.ba, "BtnSet");
        Common common66 = this.__c;
        int DipToCurrent38 = Common.DipToCurrent(200);
        int height2 = this._colors_pan.getHeight();
        Common common67 = this.__c;
        int DipToCurrent39 = height2 - Common.DipToCurrent(50);
        int width7 = this._colors_pan.getWidth();
        Common common68 = this.__c;
        Common common69 = this.__c;
        this._colors_pan.AddView((View) labelWrapper.getObject(), DipToCurrent38, DipToCurrent39, (int) (((double) (width7 - Common.DipToCurrent(220))) / 2.0d), Common.DipToCurrent(40));
        Common common70 = this.__c;
        File file = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "recton.png");
        labelWrapper.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        Common common71 = this.__c;
        Colors colors12 = Common.Colors;
        labelWrapper.setTextColor(Colors.Yellow);
        Common common72 = this.__c;
        Gravity gravity6 = Common.Gravity;
        labelWrapper.setGravity(17);
        labelWrapper.setText("Set");
        labelWrapper.Initialize(this.ba, "BtnClose");
        Common common73 = this.__c;
        int width8 = this._colors_pan.getWidth();
        Common common74 = this.__c;
        double DipToCurrent40 = ((double) Common.DipToCurrent(200)) + (((double) (width8 - Common.DipToCurrent(220))) / 2.0d);
        Common common75 = this.__c;
        int height3 = this._colors_pan.getHeight();
        Common common76 = this.__c;
        int DipToCurrent41 = height3 - Common.DipToCurrent(50);
        int width9 = this._colors_pan.getWidth();
        Common common77 = this.__c;
        Common common78 = this.__c;
        this._colors_pan.AddView((View) labelWrapper.getObject(), (int) (DipToCurrent40 + ((double) Common.DipToCurrent(10))), DipToCurrent41, (int) (((double) (width9 - Common.DipToCurrent(220))) / 2.0d), Common.DipToCurrent(40));
        Common common79 = this.__c;
        File file2 = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "recton.png");
        labelWrapper.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        Common common80 = this.__c;
        Colors colors13 = Common.Colors;
        labelWrapper.setTextColor(Colors.Yellow);
        Common common81 = this.__c;
        Gravity gravity7 = Common.Gravity;
        labelWrapper.setGravity(17);
        labelWrapper.setText("Close");
        this._mlv._initialize(this.ba, panelWrapper, this, "LvSelect");
        this._lst.Initialize();
        this._lstclr.Initialize();
        this._lstindex = 0;
        _reload();
        return "";
    }

    public String _lvselect(int i) throws Exception {
        this._lstindex = i;
        _reload();
        return "";
    }

    public String _reload() throws Exception {
        files files = this._files;
        files._getalldatafile(this.ba, "colors.cfg", this._lst, this._lstclr);
        this._mlv._clearitems();
        LabelWrapper labelWrapper = new LabelWrapper();
        double size = (double) (this._lst.getSize() - 1);
        for (int i = 0; ((double) i) <= size; i = (int) (((double) i) + 1.0d)) {
            mylistview mylistview = this._mlv;
            Common common = this.__c;
            mylistview._additem("it", BA.NumberToString(Common.DipToCurrent(60)));
            labelWrapper.setObject((TextView) this._mlv._getitem2(i, "lb1").getObject());
            Common common2 = this.__c;
            Colors colors = Common.Colors;
            labelWrapper.setTextColor(-1);
            labelWrapper.setText(this._lst.Get(i));
            labelWrapper.setObject((TextView) this._mlv._getitem2(i, "lb2").getObject());
            labelWrapper.setColor((int) BA.ObjectToNumber(this._lstclr.Get(i)));
            labelWrapper.setText("");
        }
        labelWrapper.setObject((TextView) this._mlv._getitem2(this._lstindex, "lb1").getObject());
        Common common3 = this.__c;
        Colors colors2 = Common.Colors;
        labelWrapper.setColor(Colors.Gray);
        this._fargb = _getargb((int) BA.ObjectToNumber(this._lstclr.Get(this._lstindex)));
        this._sb[3].setValue(this._fargb[0]);
        this._sb[0].setValue(this._fargb[1]);
        this._sb[1].setValue(this._fargb[2]);
        this._sb[2].setValue(this._fargb[3]);
        this._lbltxt[3].setText(Integer.valueOf(this._fargb[0]));
        this._lbltxt[0].setText(Integer.valueOf(this._fargb[1]));
        this._lbltxt[1].setText(Integer.valueOf(this._fargb[2]));
        this._lbltxt[2].setText(Integer.valueOf(this._fargb[3]));
        this._lbl.setColor((int) BA.ObjectToNumber(this._lstclr.Get(this._lstindex)));
        return "";
    }

    public String _seekalpha_valuechanged(int i, boolean z) throws Exception {
        this._fargb[0] = i;
        LabelWrapper labelWrapper = this._lbl;
        Common common = this.__c;
        Colors colors = Common.Colors;
        labelWrapper.setColor(Colors.ARGB(this._fargb[0], this._fargb[1], this._fargb[2], this._fargb[3]));
        this._lbltxt[3].setText(Integer.valueOf(this._fargb[0]));
        return "";
    }

    public String _seekblue_valuechanged(int i, boolean z) throws Exception {
        this._fargb[3] = i;
        LabelWrapper labelWrapper = this._lbl;
        Common common = this.__c;
        Colors colors = Common.Colors;
        labelWrapper.setColor(Colors.ARGB(this._fargb[0], this._fargb[1], this._fargb[2], this._fargb[3]));
        this._lbltxt[2].setText(Integer.valueOf(this._fargb[3]));
        return "";
    }

    public String _seekgreen_valuechanged(int i, boolean z) throws Exception {
        this._fargb[2] = i;
        LabelWrapper labelWrapper = this._lbl;
        Common common = this.__c;
        Colors colors = Common.Colors;
        labelWrapper.setColor(Colors.ARGB(this._fargb[0], this._fargb[1], this._fargb[2], this._fargb[3]));
        this._lbltxt[1].setText(Integer.valueOf(this._fargb[2]));
        return "";
    }

    public String _seekred_valuechanged(int i, boolean z) throws Exception {
        this._fargb[1] = i;
        LabelWrapper labelWrapper = this._lbl;
        Common common = this.__c;
        Colors colors = Common.Colors;
        labelWrapper.setColor(Colors.ARGB(this._fargb[0], this._fargb[1], this._fargb[2], this._fargb[3]));
        this._lbltxt[0].setText(Integer.valueOf(this._fargb[1]));
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
