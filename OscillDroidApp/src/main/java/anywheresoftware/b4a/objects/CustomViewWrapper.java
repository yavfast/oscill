package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.objects.collections.Map;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@BA.Hide
public class CustomViewWrapper extends ViewWrapper<BALayout> implements LayoutBuilder.DesignerTextSizeMethod {
    public Object customObject;
    private String eventName;
    public HashMap<String, Object> props;

    @BA.Hide
    public void innerInitialize(BA ba, String eventName2, boolean keepOldObject) {
        this.ba = ba;
        this.eventName = eventName2;
    }

    /* Debug info: failed to restart local var, previous not found, register: 13 */
    public void AfterDesignerScript() throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.customObject.getClass().getMethod("_initialize", new Class[]{BA.class, Object.class, String.class}).invoke(this.customObject, new Object[]{this.ba, this.ba.activity.getClass(), this.eventName});
        Map m = new Map();
        m.Initialize();
        m.Put("defaultcolor", Integer.valueOf(ViewWrapper.defaultColor));
        PanelWrapper pw = new PanelWrapper();
        pw.setObject((ViewGroup) getObject());
        LabelWrapper lw = new LabelWrapper();
        lw.setObject((TextView) getTag());
        lw.setTextSize(((Float) this.props.get("fontsize")).floatValue());
        pw.setTag(this.props.get("tag"));
        if (this.customObject instanceof B4AClass) {
            B4AClass bc = (B4AClass) this.customObject;
            m.Put("activity", bc.getActivityBA().vg);
            bc.getBA().raiseEvent2((Object) null, true, "designercreateview", true, pw, lw, m);
            return;
        }
        ((Common.DesignerCustomView) this.customObject).DesignerCreateView(pw, lw, m);
    }

    public float getTextSize() {
        return ((Float) this.props.get("fontsize")).floatValue();
    }

    public void setTextSize(float TextSize) {
        this.props.put("fontsize", Float.valueOf(TextSize));
    }

    @BA.Hide
    public static View build(Object prev, HashMap<String, Object> props2, boolean designer, Object tag) throws Exception {
        if (prev == null) {
            prev = ViewWrapper.buildNativeView((Context) tag, BALayout.class, props2, designer);
        }
        ViewGroup v = (ViewGroup) ViewWrapper.build(prev, props2, designer);
        Drawable d = (Drawable) DynamicBuilder.build(prev, (HashMap) props2.get("drawable"), designer, (Object) null);
        if (d != null) {
            v.setBackgroundDrawable(d);
        }
        TextView label = (TextView) TextViewWrapper.build(ViewWrapper.buildNativeView((Context) tag, TextView.class, props2, designer), props2, designer);
        v.setTag(label);
        if (designer) {
            v.removeAllViews();
            v.addView(label, new BALayout.LayoutParams(0, 0, -1, -1));
        }
        return v;
    }
}
