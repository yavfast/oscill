package anywheresoftware.b4a.objects;

import android.view.View;
import anywheresoftware.b4a.BA;

@BA.ShortName("View")
@BA.ActivityObject
public class ConcreteViewWrapper extends ViewWrapper<View> {
    @BA.Hide
    public void Initialize(BA ba, String eventName) {
        throw new RuntimeException("Cannot initialize object.");
    }
}
