package oscilldroid.inel;

import android.graphics.Bitmap;
import android.view.View;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ScrollViewWrapper;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.lang.reflect.Method;
import java.util.HashMap;

public class aboutdlg extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public PanelWrapper _about_pan = null;
    public LabelWrapper _b_close = null;
    public String _close_event = "";
    public converter _converter = null;
    public files _files = null;
    public main _main = null;
    public Object _sbmodule = null;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.aboutdlg");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _about_touch(int i, float f, float f2) throws Exception {
        return "";
    }

    public String _but_close_click() throws Exception {
        Common common = this.__c;
        Common.CallSubNew(this.ba, this._sbmodule, this._close_event);
        return "";
    }

    public String _class_globals() throws Exception {
        this._about_pan = new PanelWrapper();
        this._sbmodule = new Object();
        this._close_event = "";
        this._b_close = new LabelWrapper();
        return "";
    }

    public String _initialize(BA ba, ActivityWrapper activityWrapper, Object obj, String str) throws Exception {
        innerInitialize(ba);
        LabelWrapper labelWrapper = new LabelWrapper();
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        ScrollViewWrapper scrollViewWrapper = new ScrollViewWrapper();
        StringBuilder append = new StringBuilder().append("OscillDroid V2a15");
        Common common = this.__c;
        StringBuilder append2 = append.append(Common.CRLF);
        Common common2 = this.__c;
        String sb = append2.append(Common.CRLF).append("@2014 by Inel").toString();
        StringBuilder append3 = new StringBuilder().append("www.oscill.com и конкретно Роману за помощь и приобретение лицензионного П.О.");
        Common common3 = this.__c;
        StringBuilder append4 = append3.append(Common.CRLF).append(". . .");
        Common common4 = this.__c;
        StringBuilder append5 = new StringBuilder().append(append4.append(Common.CRLF).toString()).append("За поддержку проекта:");
        Common common5 = this.__c;
        StringBuilder append6 = append5.append(Common.CRLF);
        Common common6 = this.__c;
        StringBuilder append7 = new StringBuilder().append(append6.append(Common.CRLF).toString()).append("Marker39");
        Common common7 = this.__c;
        StringBuilder append8 = new StringBuilder().append(append7.append(Common.CRLF).toString()).append("Robston");
        Common common8 = this.__c;
        String str2 = append8.append(Common.CRLF).toString() + "kistinin";
        this._sbmodule = obj;
        this._close_event = str;
        this._about_pan.Initialize(this.ba, "About");
        PanelWrapper panelWrapper = this._about_pan;
        Common common9 = this.__c;
        Colors colors = Common.Colors;
        panelWrapper.setColor(Colors.Black);
        int width = activityWrapper.getWidth();
        Common common10 = this.__c;
        Common common11 = this.__c;
        activityWrapper.AddView((View) this._about_pan.getObject(), (int) (((double) (width - Common.DipToCurrent(300))) / 2.0d), 0, Common.DipToCurrent(300), activityWrapper.getHeight());
        Common common12 = this.__c;
        File file = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "dlg.png");
        this._about_pan.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        Common common13 = this.__c;
        int DipToCurrent = Common.DipToCurrent(20);
        Common common14 = this.__c;
        int DipToCurrent2 = Common.DipToCurrent(20);
        labelWrapper.Initialize(this.ba, "");
        Common common15 = this.__c;
        File file2 = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "icon.png");
        labelWrapper.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        Common common16 = this.__c;
        int DipToCurrent3 = Common.DipToCurrent(100);
        Common common17 = this.__c;
        this._about_pan.AddView((View) labelWrapper.getObject(), DipToCurrent, DipToCurrent2, DipToCurrent3, Common.DipToCurrent(100));
        int width2 = DipToCurrent + labelWrapper.getWidth();
        Common common18 = this.__c;
        int DipToCurrent4 = Common.DipToCurrent(20);
        labelWrapper.Initialize(this.ba, "");
        Common common19 = this.__c;
        Gravity gravity = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common20 = this.__c;
        Colors colors2 = Common.Colors;
        labelWrapper.setTextColor(Colors.Yellow);
        labelWrapper.setText(sb);
        Common common21 = this.__c;
        int width3 = (this._about_pan.getWidth() - width2) - Common.DipToCurrent(20);
        Common common22 = this.__c;
        this._about_pan.AddView((View) labelWrapper.getObject(), width2, DipToCurrent4, width3, Common.DipToCurrent(100));
        Common common23 = this.__c;
        int DipToCurrent5 = Common.DipToCurrent(20);
        Common common24 = this.__c;
        int height = labelWrapper.getHeight() + DipToCurrent4 + Common.DipToCurrent(10);
        labelWrapper.Initialize(this.ba, "");
        Common common25 = this.__c;
        Gravity gravity2 = Common.Gravity;
        labelWrapper.setGravity(17);
        Common common26 = this.__c;
        Colors colors3 = Common.Colors;
        labelWrapper.setTextColor(Colors.Green);
        labelWrapper.setText("Выражаю особую благодарность:");
        int width4 = this._about_pan.getWidth();
        Common common27 = this.__c;
        int DipToCurrent6 = width4 - Common.DipToCurrent(40);
        Common common28 = this.__c;
        this._about_pan.AddView((View) labelWrapper.getObject(), DipToCurrent5, height, DipToCurrent6, Common.DipToCurrent(40));
        Common common29 = this.__c;
        int DipToCurrent7 = Common.DipToCurrent(20);
        Common common30 = this.__c;
        int height2 = labelWrapper.getHeight() + height + Common.DipToCurrent(10);
        BA ba2 = this.ba;
        Common common31 = this.__c;
        scrollViewWrapper.Initialize(ba2, Common.DipToCurrent(200));
        Common common32 = this.__c;
        Colors colors4 = Common.Colors;
        scrollViewWrapper.setColor(Colors.Black);
        int width5 = this._about_pan.getWidth();
        Common common33 = this.__c;
        Common common34 = this.__c;
        this._about_pan.AddView((View) scrollViewWrapper.getObject(), DipToCurrent7, height2, width5 - Common.DipToCurrent(40), (this._about_pan.getHeight() - height2) - Common.DipToCurrent(60));
        labelWrapper.Initialize(this.ba, "");
        Common common35 = this.__c;
        Gravity gravity3 = Common.Gravity;
        labelWrapper.setGravity(1);
        Common common36 = this.__c;
        Colors colors5 = Common.Colors;
        labelWrapper.setColor(Colors.Black);
        Common common37 = this.__c;
        Colors colors6 = Common.Colors;
        labelWrapper.setTextColor(-1);
        labelWrapper.setText(str2);
        scrollViewWrapper.getPanel().AddView((View) labelWrapper.getObject(), 0, 0, scrollViewWrapper.getPanel().getWidth(), scrollViewWrapper.getPanel().getHeight());
        int width6 = this._about_pan.getWidth();
        Common common38 = this.__c;
        int DipToCurrent8 = (int) (((double) (width6 - Common.DipToCurrent(100))) / 2.0d);
        Common common39 = this.__c;
        int height3 = scrollViewWrapper.getHeight() + height2 + Common.DipToCurrent(10);
        this._b_close.Initialize(this.ba, "But_Close");
        this._b_close.setText("Close");
        LabelWrapper labelWrapper2 = this._b_close;
        Common common40 = this.__c;
        Gravity gravity4 = Common.Gravity;
        labelWrapper2.setGravity(17);
        Common common41 = this.__c;
        File file3 = Common.File;
        bitmapWrapper.Initialize(File.getDirAssets(), "recton.png");
        this._b_close.SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        LabelWrapper labelWrapper3 = this._b_close;
        Common common42 = this.__c;
        Colors colors7 = Common.Colors;
        labelWrapper3.setTextColor(Colors.Yellow);
        LabelWrapper labelWrapper4 = this._b_close;
        Common common43 = this.__c;
        labelWrapper4.setEnabled(true);
        Common common44 = this.__c;
        int DipToCurrent9 = Common.DipToCurrent(100);
        Common common45 = this.__c;
        this._about_pan.AddView((View) this._b_close.getObject(), DipToCurrent8, height3, DipToCurrent9, Common.DipToCurrent(40));
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
