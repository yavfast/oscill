package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.DynamicBuilder;
import java.util.HashMap;

@BA.ShortName("Button")
@BA.ActivityObject
public class ButtonWrapper extends TextViewWrapper<Button> {
    @BA.Hide
    public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
        if (!keepOldObject) {
            setObject(new Button(ba.context));
        }
        super.innerInitialize(ba, eventName, true);
        if (ba.subExists(String.valueOf(eventName) + "_down") || ba.subExists(String.valueOf(eventName) + "_up")) {
            ((Button) getObject()).setOnTouchListener(new View.OnTouchListener() {
                private boolean down = false;

                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 0) {
                        this.down = true;
                        ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(eventName) + "_down", new Object[0]);
                    } else if (this.down && (event.getAction() == 1 || event.getAction() == 3)) {
                        this.down = false;
                        ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(eventName) + "_up", new Object[0]);
                    } else if (event.getAction() == 2) {
                        int[] states = v.getDrawableState();
                        if (states == null) {
                            return false;
                        }
                        int i = 0;
                        while (i < states.length) {
                            if (states[i] != 16842919) {
                                i++;
                            } else if (this.down) {
                                return false;
                            } else {
                                ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(eventName) + "_down", new Object[0]);
                                this.down = true;
                                return false;
                            }
                        }
                        if (this.down) {
                            ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(eventName) + "_up", new Object[0]);
                            this.down = false;
                        }
                    }
                    return false;
                }
            });
        }
    }

    @BA.Hide
    public static View build(Object prev, HashMap<String, Object> props, boolean designer, Object tag) throws Exception {
        if (prev == null) {
            prev = ViewWrapper.buildNativeView((Context) tag, Button.class, props, designer);
        }
        TextView v = (TextView) TextViewWrapper.build(prev, props, designer);
        Drawable d = (Drawable) DynamicBuilder.build(prev, (HashMap) props.get("drawable"), designer, (Object) null);
        if (d != null) {
            v.setBackgroundDrawable(d);
        }
        if (designer) {
            v.setPressed(((Boolean) props.get("pressed")).booleanValue());
        }
        return v;
    }
}
