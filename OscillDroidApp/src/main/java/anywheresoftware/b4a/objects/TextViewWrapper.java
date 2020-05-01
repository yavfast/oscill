package anywheresoftware.b4a.objects;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.keywords.constants.Colors;
import java.util.Map;

@BA.Hide
public class TextViewWrapper<T extends TextView> extends ViewWrapper<T> implements LayoutBuilder.DesignerTextSizeMethod {
    public String getText() {
        return ((TextView) getObject()).getText().toString();
    }

    public void setText(Object Text) {
        CharSequence cs;
        if (Text instanceof CharSequence) {
            cs = (CharSequence) Text;
        } else {
            cs = Text.toString();
        }
        ((TextView) getObject()).setText(cs);
    }

    public void setTextColor(int Color) {
        ((TextView) getObject()).setTextColor(Color);
    }

    public int getTextColor() {
        return ((TextView) getObject()).getTextColors().getDefaultColor();
    }

    public void setTextSize(float TextSize) {
        ((TextView) getObject()).setTextSize(TextSize);
    }

    public float getTextSize() {
        return ((TextView) getObject()).getTextSize() / ((TextView) getObject()).getContext().getResources().getDisplayMetrics().scaledDensity;
    }

    public void setGravity(int Gravity) {
        ((TextView) getObject()).setGravity(Gravity);
    }

    public int getGravity() {
        return ((TextView) getObject()).getGravity();
    }

    public void setTypeface(Typeface Typeface) {
        ((TextView) getObject()).setTypeface(Typeface);
    }

    public Typeface getTypeface() {
        return ((TextView) getObject()).getTypeface();
    }

    @BA.Hide
    public String toString() {
        String s = super.toString();
        if (IsInitialized()) {
            return String.valueOf(s) + ", Text=" + getText();
        }
        return s;
    }

    @BA.Hide
    public static View build(Object prev, Map<String, Object> props, boolean designer) throws Exception {
        TextView v = (TextView) ViewWrapper.build(prev, props, designer);
        v.setText((CharSequence) props.get("text"));
        ColorStateList defaultTextColor = null;
        if (designer) {
            defaultTextColor = (ColorStateList) ViewWrapper.getDefault(v, "textColor", v.getTextColors());
        }
        int style = ((Integer) Typeface.class.getField((String) props.get("style")).get((Object) null)).intValue();
        v.setTextSize(((Float) props.get("fontsize")).floatValue());
        v.setTypeface((Typeface) Typeface.class.getField((String) props.get("typeface")).get((Object) null), style);
        v.setGravity(((Integer) Gravity.class.getField((String) props.get("hAlignment")).get((Object) null)).intValue() | ((Integer) Gravity.class.getField((String) props.get("vAlignment")).get((Object) null)).intValue());
        int textColor = ((Integer) props.get("textColor")).intValue();
        if (textColor != -984833) {
            v.setTextColor(textColor);
        }
        if (designer && textColor == -984833) {
            v.setTextColor(defaultTextColor);
        }
        if (designer) {
            setHint(v, (String) props.get("name"));
        }
        return v;
    }

    @BA.Hide
    public static void setHint(TextView v, String name) {
        if (v.getText().length() == 0 && !(v instanceof EditText)) {
            v.setText(name);
            v.setTextColor(Colors.Gray);
        }
    }
}
