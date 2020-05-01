package oscilldroid.inel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.ButtonWrapper;
import anywheresoftware.b4a.objects.ConcreteViewWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ScrollViewWrapper;
import java.lang.reflect.Method;
import java.util.HashMap;

public class mylistview extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public converter _converter = null;
    public files _files = null;
    public main _main = null;
    public PanelWrapper _mparent = null;
    public int _ncount = 0;
    public int _nheight = 0;
    public Object _sbmodule = null;
    public String _sel_event = "";
    public ScrollViewWrapper _sv = null;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.mylistview");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _additem(String str, String str2) throws Exception {
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.Initialize(this.ba, "p");
        panelWrapper.setTag(Integer.valueOf(this._ncount));
        this._sv.getPanel().AddView((View) panelWrapper.getObject(), 0, this._nheight, -1, (int) Double.parseDouble(str2));
        panelWrapper.LoadLayout(str, this.ba);
        this._sv.getPanel().setHeight(this._nheight + panelWrapper.getHeight());
        this._nheight += panelWrapper.getHeight();
        this._ncount++;
        return "";
    }

    public String _btn_click() throws Exception {
        ButtonWrapper buttonWrapper = new ButtonWrapper();
        Common common = this.__c;
        buttonWrapper.setObject((Button) Common.Sender(this.ba));
        Common common2 = this.__c;
        Common.Log("btn:" + buttonWrapper.getText() + " click");
        return "";
    }

    public String _class_globals() throws Exception {
        this._sv = new ScrollViewWrapper();
        this._nheight = 0;
        this._mparent = new PanelWrapper();
        this._ncount = 0;
        this._sbmodule = new Object();
        this._sel_event = "";
        return "";
    }

    public String _clearitems() throws Exception {
        this._sv.getPanel().RemoveAllViews();
        this._nheight = 0;
        this._ncount = 0;
        return "";
    }

    public int _getcounts() throws Exception {
        return this._ncount;
    }

    public ConcreteViewWrapper _getitem(int i, int i2) throws Exception {
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.setObject((ViewGroup) this._sv.getPanel().GetView(i).getObject());
        return panelWrapper.GetView(i2);
    }

    public ConcreteViewWrapper _getitem2(int i, String str) throws Exception {
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.setObject((ViewGroup) this._sv.getPanel().GetView(i).getObject());
        double numberOfViews = (double) (panelWrapper.getNumberOfViews() - 1);
        for (int i2 = 0; ((double) i2) <= numberOfViews; i2 = (int) (((double) i2) + 1.0d)) {
            if (panelWrapper.GetView(i2).getTag().equals(str)) {
                return panelWrapper.GetView(i2);
            }
        }
        ConcreteViewWrapper concreteViewWrapper = new ConcreteViewWrapper();
        Common common = this.__c;
        return (ConcreteViewWrapper) AbsObjectWrapper.ConvertToWrapper(concreteViewWrapper, (View) Common.Null);
    }

    public String _initialize(BA ba, PanelWrapper panelWrapper, Object obj, String str) throws Exception {
        innerInitialize(ba);
        this._sbmodule = obj;
        this._sel_event = str;
        this._sv.Initialize(this.ba, this._nheight);
        this._mparent = panelWrapper;
        this._ncount = 0;
        this._mparent.AddView((View) this._sv.getObject(), 0, 0, -1, -1);
        return "";
    }

    public String _p_click() throws Exception {
        PanelWrapper panelWrapper = new PanelWrapper();
        Common common = this.__c;
        panelWrapper.setObject((ViewGroup) Common.Sender(this.ba));
        Common common2 = this.__c;
        Common.CallSubNew2(this.ba, this._sbmodule, this._sel_event, panelWrapper.getTag());
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
