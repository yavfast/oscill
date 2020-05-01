package anywheresoftware.b4a.objects.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.objects.ViewWrapper;
import java.util.HashMap;

@BA.ActivityObject
@BA.ShortName("ColorDrawable")
public class ColorDrawable extends AbsObjectWrapper<Drawable> {
    public void Initialize(int Color, int CornerRadius) {
        if (CornerRadius == 0) {
            setObject(new android.graphics.drawable.ColorDrawable(Color));
            return;
        }
        GradientDrawableWithCorners gd = new GradientDrawableWithCorners(GradientDrawable.Orientation.BL_TR, new int[]{Color, Color});
        gd.setCornerRadius((float) CornerRadius);
        setObject(gd);
    }

    @BA.Hide
    public static class GradientDrawableWithCorners extends GradientDrawable {
        public float cornerRadius;

        public GradientDrawableWithCorners(GradientDrawable.Orientation o, int[] colors) {
            super(o, colors);
        }

        public void setCornerRadius(float radius) {
            super.setCornerRadius(radius);
            this.cornerRadius = radius;
        }
    }

    @BA.Hide
    public static Drawable build(Object prev, HashMap<String, Object> d, boolean designer, Object tag) {
        int alpha = ((Integer) d.get("alpha")).intValue();
        int solidColor = ((Integer) d.get("color")).intValue();
        if (solidColor == -984833) {
            if (!designer) {
                return null;
            }
            if (((Drawable) ViewWrapper.getDefault((View) prev, "background", (Object) null)) == null) {
                return new android.graphics.drawable.ColorDrawable(0);
            }
        }
        int color = (alpha << 24) | ((solidColor << 8) >>> 8);
        Integer corners = (Integer) d.get("cornerRadius");
        if (corners == null) {
            corners = 0;
        }
        ColorDrawable cd = new ColorDrawable();
        cd.Initialize(color, (int) (BALayout.getDeviceScale() * ((float) corners.intValue())));
        return (Drawable) cd.getObject();
    }
}
