package anywheresoftware.b4a.objects;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import anywheresoftware.b4a.BA;
import java.util.HashMap;

@BA.ShortName("SeekBar")
@BA.ActivityObject
public class SeekBarWrapper extends ViewWrapper<SeekBar> {
    @BA.Hide
    public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
        if (!keepOldObject) {
            setObject(new SeekBar(ba.context));
        }
        super.innerInitialize(ba, eventName, true);
        if (ba.subExists(eventName + "_valuechanged")) {
            getObject().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ba.raiseEventFromUI(SeekBarWrapper.this.getObject(), eventName + "_valuechanged", progress, fromUser);
                }

                public void onStartTrackingTouch(SeekBar arg0) {
                }

                public void onStopTrackingTouch(SeekBar arg0) {
                }
            });
        }
    }

    public int getMax() {
        return getObject().getMax();
    }

    public void setMax(int value) {
        getObject().setMax(value);
    }

    public int getValue() {
        return getObject().getProgress();
    }

    public void setValue(int value) {
        getObject().setProgress(value);
    }

    @BA.Hide
    public static View build(Object prev, HashMap<String, Object> props, boolean designer, Object tag) throws Exception {
        if (prev == null) {
            prev = ViewWrapper.buildNativeView((Context) tag, SeekBar.class, props, designer);
        }
        SeekBar v = (SeekBar) ViewWrapper.build(prev, props, designer);
        int oldMax = v.getMax();
        v.setMax((Integer) props.get("max"));
        if (v.getMax() != oldMax) {
            v.setProgress(-1);
        }
        v.setProgress((Integer) props.get("value"));
        return v;
    }
}
