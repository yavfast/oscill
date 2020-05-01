package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.drawable.BitmapDrawable;
import anywheresoftware.b4a.objects.drawable.ColorDrawable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@BA.Hide
public class ViewWrapper<T extends View> extends AbsObjectWrapper<T> {
    @BA.Hide
    public static final int defaultColor = -984833;
    @BA.Hide
    public static int lastId = 0;
    protected BA ba;

    public void Initialize(BA ba2, String EventName) {
        innerInitialize(ba2, EventName.toLowerCase(BA.cul), false);
    }

    @BA.Hide
    public void innerInitialize(final BA ba2, final String eventName, boolean keepOldObject) {
        this.ba = ba2;
        int i = lastId + 1;
        lastId = i;
        getObject().setId(i);
        if (ba2.subExists(eventName + "_click")) {
            getObject().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ba2.raiseEvent(ViewWrapper.this.getObject(), eventName + "_click", new Object[0]);
                }
            });
        }
        if (ba2.subExists(eventName + "_longclick")) {
            getObject().setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ba2.raiseEvent(ViewWrapper.this.getObject(), eventName + "_longclick", new Object[0]);
                    return true;
                }
            });
        }
    }

    public Drawable getBackground() {
        return ((View) getObject()).getBackground();
    }

    public void setBackground(Drawable drawable) {
        ((View) getObject()).setBackgroundDrawable(drawable);
    }

    public void SetBackgroundImage(Bitmap Bitmap) {
        BitmapDrawable bd = new BitmapDrawable();
        bd.Initialize(Bitmap);
        ((View) getObject()).setBackgroundDrawable((Drawable) bd.getObject());
    }

    public void Invalidate() {
        ((View) getObject()).invalidate();
    }

    public void Invalidate2(Rect Rect) {
        ((View) getObject()).invalidate(Rect);
    }

    public void Invalidate3(int Left, int Top, int Right, int Bottom) {
        ((View) getObject()).invalidate(Left, Top, Right, Bottom);
    }

    public void setWidth(@BA.Pixel int width) {
        ((View) getObject()).getLayoutParams().width = width;
        ((View) getObject()).getParent().requestLayout();
    }

    public int getWidth() {
        return ((View) getObject()).getLayoutParams().width;
    }

    public int getHeight() {
        return ((View) getObject()).getLayoutParams().height;
    }

    public int getLeft() {
        return ((BALayout.LayoutParams) ((View) getObject()).getLayoutParams()).left;
    }

    public int getTop() {
        return ((BALayout.LayoutParams) ((View) getObject()).getLayoutParams()).top;
    }

    public void setHeight(@BA.Pixel int height) {
        ((View) getObject()).getLayoutParams().height = height;
        ((View) getObject()).getParent().requestLayout();
    }

    public void setLeft(@BA.Pixel int left) {
        ((BALayout.LayoutParams) ((View) getObject()).getLayoutParams()).left = left;
        ((View) getObject()).getParent().requestLayout();
    }

    public void setTop(@BA.Pixel int top) {
        ((BALayout.LayoutParams) ((View) getObject()).getLayoutParams()).top = top;
        ((View) getObject()).getParent().requestLayout();
    }

    public void setColor(int color) {
        Drawable d = ((View) getObject()).getBackground();
        if (d == null || !(d instanceof GradientDrawable)) {
            ((View) getObject()).setBackgroundColor(color);
            return;
        }
        float radius = Common.Density;
        if (d instanceof ColorDrawable.GradientDrawableWithCorners) {
            radius = ((ColorDrawable.GradientDrawableWithCorners) d).cornerRadius;
        } else {
            GradientDrawable g = (GradientDrawable) ((View) getObject()).getBackground();
            try {
                Field state = g.getClass().getDeclaredField("mGradientState");
                state.setAccessible(true);
                Object gstate = state.get(g);
                radius = ((Float) gstate.getClass().getDeclaredField("mRadius").get(gstate)).floatValue();
            } catch (Exception e) {
                Common.Log(e.toString());
            }
        }
        ColorDrawable cd = new ColorDrawable();
        cd.Initialize(color, (int) radius);
        ((View) getObject()).setBackgroundDrawable((Drawable) cd.getObject());
    }

    public void setTag(Object tag) {
        ((View) getObject()).setTag(tag);
    }

    public Object getTag() {
        return ((View) getObject()).getTag();
    }

    public void setVisible(boolean Visible) {
        ((View) getObject()).setVisibility(Visible ? 0 : 8);
    }

    public boolean getVisible() {
        return ((View) getObject()).getVisibility() == 0;
    }

    public void setEnabled(boolean Enabled) {
        ((View) getObject()).setEnabled(Enabled);
    }

    public boolean getEnabled() {
        return ((View) getObject()).isEnabled();
    }

    public void BringToFront() {
        if (((View) getObject()).getParent() instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) ((View) getObject()).getParent();
            vg.removeView((View) getObject());
            vg.addView((View) getObject());
        }
    }

    public void SendToBack() {
        if (((View) getObject()).getParent() instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) ((View) getObject()).getParent();
            vg.removeView((View) getObject());
            vg.addView((View) getObject(), 0);
        }
    }

    public void RemoveView() {
        if (((View) getObject()).getParent() instanceof ViewGroup) {
            ((ViewGroup) ((View) getObject()).getParent()).removeView((View) getObject());
        }
    }

    public void SetLayout(@BA.Pixel int Left, @BA.Pixel int Top, @BA.Pixel int Width, @BA.Pixel int Height) {
        BALayout.LayoutParams lp = (BALayout.LayoutParams) ((View) getObject()).getLayoutParams();
        lp.left = Left;
        lp.top = Top;
        lp.width = Width;
        lp.height = Height;
        ((View) getObject()).getParent().requestLayout();
    }

    public boolean RequestFocus() {
        return ((View) getObject()).requestFocus();
    }

    @BA.Hide
    public String toString() {
        String s;
        String s2 = baseToString();
        if (!IsInitialized()) {
            return s2;
        }
        String s3 = String.valueOf(s2) + ": ";
        if (!getEnabled()) {
            s3 = String.valueOf(s3) + "Enabled=false, ";
        }
        if (!getVisible()) {
            s3 = String.valueOf(s3) + "Visible=false, ";
        }
        if (((View) getObject()).getLayoutParams() == null || !(((View) getObject()).getLayoutParams() instanceof BALayout.LayoutParams)) {
            s = String.valueOf(s3) + "Layout not available";
        } else {
            s = String.valueOf(s3) + "Left=" + getLeft() + ", Top=" + getTop() + ", Width=" + getWidth() + ", Height=" + getHeight();
        }
        if (getTag() != null) {
            return String.valueOf(s) + ", Tag=" + getTag().toString();
        }
        return s;
    }

    @BA.Hide
    public static View build(Object prev, Map<String, Object> props, boolean designer) throws Exception {
        View v = (View) prev;
        if (v.getTag() == null && designer) {
            HashMap<String, Object> defaults = new HashMap<>();
            defaults.put("background", v.getBackground());
            v.setTag(defaults);
        }
        BALayout.LayoutParams lp = (BALayout.LayoutParams) v.getLayoutParams();
        if (lp == null) {
            lp = new BALayout.LayoutParams();
            v.setLayoutParams(lp);
        }
        lp.setFromUserPlane(((Integer) props.get("left")).intValue(), ((Integer) props.get("top")).intValue(), ((Integer) props.get("width")).intValue(), ((Integer) props.get("height")).intValue());
        v.setEnabled(((Boolean) props.get("enabled")).booleanValue());
        if (!designer) {
            int visible = 0;
            if (!((Boolean) props.get("visible")).booleanValue()) {
                visible = 8;
            }
            v.setVisibility(visible);
            v.setTag(props.get("tag"));
        }
        return v;
    }

    @BA.Hide
    public static <T> T buildNativeView(Context context, Class<T> cls, HashMap<String, Object> props, boolean designer) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        Class<?> c;
        String overideClass = (String) props.get("nativeClass");
        if (overideClass != null && overideClass.startsWith(".")) {
            overideClass = BA.applicationContext.getPackageName() + overideClass;
        }
        if (!designer && overideClass != null) {
            try {
                if (overideClass.length() != 0) {
                    c = Class.forName(overideClass);
                    return (T)c.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
                }
            } catch (ClassNotFoundException e) {
                ClassNotFoundException classNotFoundException = e;
                int i = overideClass.lastIndexOf(".");
                c = Class.forName(overideClass.substring(0, i) + "$" + overideClass.substring(i + 1));
            }
        }
        c = cls;
        return (T)c.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
    }

    @BA.Hide
    public static Object getDefault(View v, String key, Object defaultValue) {
        HashMap<String, Object> map = (HashMap) v.getTag();
        if (map.containsKey(key)) {
            return map.get(key);
        }
        map.put(key, defaultValue);
        return defaultValue;
    }
}
