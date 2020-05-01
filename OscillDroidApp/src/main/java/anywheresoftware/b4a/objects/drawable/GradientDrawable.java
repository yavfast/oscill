package anywheresoftware.b4a.objects.drawable;

import android.graphics.drawable.Drawable;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import java.util.HashMap;

@BA.ShortName("GradientDrawable")
@BA.ActivityObject
public class GradientDrawable extends AbsObjectWrapper<android.graphics.drawable.GradientDrawable> {
    public void Initialize(android.graphics.drawable.GradientDrawable.Orientation Orientation, int[] Colors) {
        setObject(new android.graphics.drawable.GradientDrawable(Orientation, Colors));
    }

    public void setCornerRadius(float Radius) {
        ((android.graphics.drawable.GradientDrawable) getObject()).setCornerRadius(Radius);
    }

    @BA.Hide
    public static Drawable build(Object prev, HashMap<String, Object> d, boolean designer, Object tag) {
        android.graphics.drawable.GradientDrawable gd =
                new android.graphics.drawable.GradientDrawable(
                        (android.graphics.drawable.GradientDrawable.Orientation) Enum.valueOf(android.graphics.drawable.GradientDrawable.Orientation.class,
                                (String) d.get("orientation")), new int[]{((Integer) d.get("firstColor")).intValue(), ((Integer) d.get("secondColor")).intValue()});
        gd.setCornerRadius((float) ((int) (BALayout.getDeviceScale() * ((float) ((Integer) d.get("cornerRadius")).intValue()))));
        return gd;
    }
}
