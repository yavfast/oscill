package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.keywords.LayoutValues;
import java.util.HashMap;

@BA.ShortName("Panel")
@BA.ActivityObject
public class PanelWrapper extends ViewWrapper<ViewGroup> implements BA.IterableList {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_UP = 1;

    @BA.Hide
    public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
        if (!keepOldObject) {
            setObject(new BALayout(ba.context));
        }
        super.innerInitialize(ba, eventName, true);
        if (ba.subExists(String.valueOf(eventName) + "_touch")) {
            ((ViewGroup) getObject()).setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    ba.raiseEventFromUI(PanelWrapper.this.getObject(), String.valueOf(eventName) + "_touch", Integer.valueOf(event.getAction()), Float.valueOf(event.getX()), Float.valueOf(event.getY()));
                    return true;
                }
            });
        }
    }

    public void AddView(View View, int Left, int Top, int Width, int Height) {
        ((ViewGroup) getObject()).addView(View, new BALayout.LayoutParams(Left, Top, Width, Height));
    }

    public ConcreteViewWrapper GetView(int Index) {
        ConcreteViewWrapper c = new ConcreteViewWrapper();
        c.setObject(((ViewGroup) getObject()).getChildAt(Index));
        return c;
    }

    public void RemoveAllViews() {
        ((ViewGroup) getObject()).removeAllViews();
    }

    public void RemoveViewAt(int Index) {
        ((ViewGroup) getObject()).removeViewAt(Index);
    }

    public int getNumberOfViews() {
        return ((ViewGroup) getObject()).getChildCount();
    }

    public LayoutValues LoadLayout(String LayoutFile, BA ba) throws Exception {
        ViewGroup.LayoutParams lp = ((ViewGroup) getObject()).getLayoutParams();
        boolean zeroSize = false;
        boolean width_fill_parent = false;
        if (lp == null) {
            zeroSize = true;
        }
        if (!zeroSize && lp.width == -1) {
            if (((ViewGroup) getObject()).getParent() == null || ((View) ((ViewGroup) getObject()).getParent()).getLayoutParams() == null) {
                zeroSize = true;
            } else {
                setWidth(((View) ((ViewGroup) getObject()).getParent()).getLayoutParams().width);
                width_fill_parent = true;
            }
        }
        if (zeroSize) {
            BA.WarningEngine.warn(BA.WarningEngine.ZERO_SIZE_PANEL);
        }
        LayoutValues lv = LayoutBuilder.loadLayout(LayoutFile, ba, false, (ViewGroup) getObject(), (HashMap<String, ViewWrapper<?>>) null, false).layoutValues;
        if (width_fill_parent) {
            setWidth(-1);
        }
        return lv;
    }

    @BA.Hide
    public Object Get(int index) {
        return GetView(index).getObject();
    }

    @BA.Hide
    public int getSize() {
        return getNumberOfViews();
    }

    @BA.Hide
    public static View build(Object prev, HashMap<String, Object> props, boolean designer, Object tag) throws Exception {
        View vg = (View) prev;
        if (vg == null) {
            vg = (View) ViewWrapper.buildNativeView((Context) tag, BALayout.class, props, designer);
        }
        View vg2 = ViewWrapper.build(vg, props, designer);
        Drawable d = (Drawable) DynamicBuilder.build(vg2, (HashMap) props.get("drawable"), designer, (Object) null);
        if (d != null) {
            vg2.setBackgroundDrawable(d);
        }
        return vg2;
    }
}
