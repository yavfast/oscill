package oscilldroid.inel.designerscripts;

import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.objects.ViewWrapper;
import java.util.HashMap;

public class LS_it {
    public static void LS_general(HashMap<String, ViewWrapper<?>> hashMap, int i, int i2, float f) {
        LayoutBuilder.setScaleRate(0.3d);
        LayoutBuilder.scaleAll(hashMap);
    }
}
