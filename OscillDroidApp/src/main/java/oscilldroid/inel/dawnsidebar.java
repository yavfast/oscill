package oscilldroid.inel;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.ListViewWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.lang.reflect.Method;
import java.util.HashMap;

public class dawnsidebar extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public String _barevent = "";
    public LabelWrapper[] _barlab = null;
    public PanelWrapper _barpan = null;
    public converter _converter = null;
    public files _files = null;
    public boolean _isopen = false;
    public main _main = null;
    public Object _sbmodule = null;
    public PanelWrapper _sidepan = null;
    public int _spanheight = 0;
    public int _spantop = 0;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.dawnsidebar");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _barinit(Object obj, String str, int i, int i2, float f) throws Exception {
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        this._barevent = str;
        this._sbmodule = obj;
        int width = (int) (((double) ((this._barpan.getWidth() - i) + 1)) / ((double) i));
        int i3 = 0;
        double d = (double) (i - 1);
        int i4 = 0;
        while (true) {
            int i5 = i4;
            int i6 = i3;
            if (((double) i5) > d) {
                this._spanheight = i2;
                return "";
            }
            this._barlab[i5].Initialize(this.ba, "BarLab");
            this._barpan.AddView((View) this._barlab[i5].getObject(), i6, 1, width, this._barpan.getHeight());
            Common common = this.__c;
            File file = Common.File;
            bitmapWrapper.Initialize(File.getDirAssets(), "recton.png");
            this._barlab[i5].SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
            LabelWrapper labelWrapper = this._barlab[i5];
            Common common2 = this.__c;
            Gravity gravity = Common.Gravity;
            labelWrapper.setGravity(17);
            LabelWrapper labelWrapper2 = this._barlab[i5];
            Common common3 = this.__c;
            Colors colors = Common.Colors;
            labelWrapper2.setTextColor(Colors.Yellow);
            this._barlab[i5].setTextSize(this._barlab[i5].getTextSize() * f);
            this._barlab[i5].setTag(Integer.valueOf(i5));
            i3 = i6 + width + 1;
            i4 = (int) (((double) i5) + 1.0d);
        }
    }

    public String _barlab_click() throws Exception {
        LabelWrapper labelWrapper = new LabelWrapper();
        Common common = this.__c;
        labelWrapper.setObject((TextView) Common.Sender(this.ba));
        Common common2 = this.__c;
        Common.CallSubNew2(this.ba, this._sbmodule, this._barevent, labelWrapper);
        return "";
    }

    public String _class_globals() throws Exception {
        this._barpan = new PanelWrapper();
        this._sidepan = new PanelWrapper();
        this._barlab = new LabelWrapper[10];
        int length = this._barlab.length;
        for (int i = 0; i < length; i++) {
            this._barlab[i] = new LabelWrapper();
        }
        this._isopen = false;
        this._spanheight = 0;
        this._spantop = 0;
        this._barevent = "";
        this._sbmodule = new Object();
        return "";
    }

    public String _close() throws Exception {
        this._sidepan.setHeight(0);
        this._sidepan.RemoveAllViews();
        Common common = this.__c;
        this._isopen = false;
        return "";
    }

    public String _initialize(BA ba, PanelWrapper panelWrapper, int i, int i2, int i3, int i4) throws Exception {
        innerInitialize(ba);
        Common common = this.__c;
        this._isopen = false;
        this._barpan.Initialize(this.ba, "");
        this._sidepan.Initialize(this.ba, "");
        panelWrapper.AddView((View) this._barpan.getObject(), i, i2, i3, i4);
        this._spantop = this._barpan.getTop() + this._barpan.getHeight();
        panelWrapper.AddView((View) this._sidepan.getObject(), 0, this._spantop, 0, 0);
        return "";
    }

    public String _open(LabelWrapper labelWrapper, ListViewWrapper listViewWrapper) throws Exception {
        _close();
        this._sidepan.setLeft(labelWrapper.getLeft() + this._barpan.getLeft());
        this._sidepan.setWidth(labelWrapper.getWidth());
        this._sidepan.setHeight(this._spanheight);
        this._sidepan.AddView((View) listViewWrapper.getObject(), 0, 0, this._sidepan.getWidth(), this._sidepan.getHeight());
        Common common = this.__c;
        this._isopen = true;
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
