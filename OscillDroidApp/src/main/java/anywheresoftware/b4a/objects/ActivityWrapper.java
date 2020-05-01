package anywheresoftware.b4a.objects;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.B4AMenuItem;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.keywords.LayoutValues;
import anywheresoftware.b4a.objects.drawable.BitmapDrawable;
import java.lang.reflect.Field;
import java.util.HashMap;

@BA.ShortName("Activity")
@BA.ActivityObject
public class ActivityWrapper extends ViewWrapper<BALayout> implements BA.IterableList {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_UP = 1;

    public ActivityWrapper() {
    }

    public ActivityWrapper(final BA ba, String name) {
        setObject(ba.vg);
        innerInitialize(ba, name, true);
        if (ba.subExists("activity_touch")) {
            ((BALayout) getObject()).setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    ba.raiseEventFromUI(ActivityWrapper.this, "activity_touch", Integer.valueOf(event.getAction()), Float.valueOf(event.getX()), Float.valueOf(event.getY()));
                    return true;
                }
            });
        }
    }

    public IntentWrapper GetStartingIntent() {
        IntentWrapper iw = new IntentWrapper();
        iw.setObject(this.ba.activity.getIntent());
        return iw;
    }

    public void SetActivityResult(int Result, IntentWrapper Data) {
        this.ba.activity.setResult(Result, (Intent) Data.getObject());
    }

    public void AddView(View View, @BA.Pixel int Left, @BA.Pixel int Top, @BA.Pixel int Width, @BA.Pixel int Height) {
        ((BALayout) getObject()).addView(View, new BALayout.LayoutParams(Left, Top, Width, Height));
    }

    public ConcreteViewWrapper GetView(int Index) {
        ConcreteViewWrapper vw = new ConcreteViewWrapper();
        vw.setObject(((BALayout) getObject()).getChildAt(Index));
        return vw;
    }

    public void RemoveAllViews() {
        ((BALayout) getObject()).removeAllViews();
    }

    public void RemoveViewAt(int Index) {
        ((BALayout) getObject()).removeViewAt(Index);
    }

    public int getNumberOfViews() {
        return ((BALayout) getObject()).getChildCount();
    }

    public void AddMenuItem(String Title, String EventName) {
        AddMenuItem3(Title, EventName, (Bitmap) null, false);
    }

    public void AddMenuItem2(String Title, String EventName, Bitmap Bitmap) {
        AddMenuItem3(Title, EventName, Bitmap, false);
    }

    public void AddMenuItem3(String Title, String EventName, Bitmap Bitmap, boolean AddToActionBar) {
        Drawable d = null;
        if (Bitmap != null) {
            BitmapDrawable bd = new BitmapDrawable();
            bd.Initialize(Bitmap);
            d = (Drawable) bd.getObject();
        }
        ((B4AActivity) this.ba.activity).addMenuItem(new B4AMenuItem(Title, d, EventName, AddToActionBar));
    }

    public LayoutValues LoadLayout(String LayoutFile, BA ba) throws Exception {
        AbsObjectWrapper.Activity_LoadLayout_Was_Called = true;
        return LayoutBuilder.loadLayout(LayoutFile, ba, true, ba.vg, (HashMap<String, ViewWrapper<?>>) null, false).layoutValues;
    }

    public void RerunDesignerScript(String Layout, BA ba, int Width, int Height) throws Exception {
        ViewGroup vg = new BALayout(ba.context);
        vg.setLayoutParams(new ViewGroup.LayoutParams(Width, Height));
        HashMap<String, ViewWrapper<?>> dynamicTable = new LayoutBuilder.LayoutHashMap<>();
        for (Field f : ba.activity.getClass().getFields()) {
            if (f.getName().startsWith("_") && ViewWrapper.class.isAssignableFrom(f.getType())) {
                dynamicTable.put(f.getName().substring(1), (ViewWrapper) f.get(ba.activity));
            }
        }
        LayoutBuilder.loadLayout(Layout, ba, false, vg, dynamicTable, false);
    }

    public void OpenMenu() {
        this.ba.activity.openOptionsMenu();
    }

    public void CloseMenu() {
        this.ba.activity.closeOptionsMenu();
    }

    public void setTitle(Object Title) {
        CharSequence cs;
        if (Title instanceof CharSequence) {
            cs = (CharSequence) Title;
        } else {
            cs = Title.toString();
        }
        this.ba.activity.setTitle(cs);
    }

    public CharSequence getTitle() {
        return this.ba.activity.getTitle();
    }

    public int getTitleColor() {
        return this.ba.activity.getTitleColor();
    }

    public void setTitleColor(int Color) {
        this.ba.activity.setTitleColor(Color);
    }

    public int getWidth() {
        return ((BALayout) getObject()).getWidth();
    }

    public int getHeight() {
        return ((BALayout) getObject()).getHeight();
    }

    public int getLeft() {
        return 0;
    }

    public int getTop() {
        return 0;
    }

    @BA.Hide
    public void setVisible(boolean Visible) {
    }

    @BA.Hide
    public boolean getVisible() {
        return true;
    }

    @BA.Hide
    public void setEnabled(boolean Enabled) {
    }

    @BA.Hide
    public boolean getEnabled() {
        return true;
    }

    @BA.Hide
    public void BringToFront() {
    }

    @BA.Hide
    public void SendToBack() {
    }

    @BA.Hide
    public void RemoveView() {
    }

    public void Finish() {
        this.ba.activity.finish();
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
        Drawable d = (Drawable) DynamicBuilder.build(prev, (HashMap) props.get("drawable"), designer, (Object) null);
        View v = (View) prev;
        int defaultTitleColor = 0;
        if (designer) {
            defaultTitleColor = ((Integer) ViewWrapper.getDefault(v, "titleColor", Integer.valueOf(((Activity) v.getContext()).getTitleColor()))).intValue();
        }
        if (d != null) {
            v.setBackgroundDrawable(d);
        }
        ((Activity) v.getContext()).setTitle((String) props.get("title"));
        int titleColor = ((Integer) props.get("titleColor")).intValue();
        if (titleColor != -984833) {
            ((Activity) v.getContext()).setTitleColor(titleColor);
        } else if (designer) {
            ((Activity) v.getContext()).setTitleColor(defaultTitleColor);
        }
        if (BA.debugMode) {
            BA.warningEngine.checkFullScreenInLayout(((Boolean) props.get("fullScreen")).booleanValue(), ((Boolean) props.get("includeTitle")).booleanValue());
        }
        if (designer) {
            boolean fullScreen = ((Boolean) props.get("fullScreen")).booleanValue();
            boolean includeTitle = ((Boolean) props.get("includeTitle")).booleanValue();
            Class<?> cls = Class.forName("anywheresoftware.b4a.designer.Designer");
            boolean prevFullScreen = cls.getField("fullScreen").getBoolean(v.getContext());
            boolean prevIncludeTitle = cls.getField("includeTitle").getBoolean(v.getContext());
            if (!(prevFullScreen == fullScreen && includeTitle == prevIncludeTitle)) {
                Intent i = new Intent(v.getContext().getApplicationContext(), cls);
                i.putExtra("anywheresoftware.b4a.designer.includeTitle", includeTitle);
                i.putExtra("anywheresoftware.b4a.designer.fullScreen", fullScreen);
                cls.getMethod("restartActivity", new Class[]{Intent.class}).invoke(v.getContext(), new Object[]{i});
            }
        }
        return (View) prev;
    }
}
