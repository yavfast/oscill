package anywheresoftware.b4a.keywords;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;

@BA.ShortName("StringBuilder")
public class StringBuilderWrapper extends AbsObjectWrapper<StringBuilder> {
    public void Initialize() {
        setObject(new StringBuilder());
    }

    public StringBuilderWrapper Append(String Text) {
        ((StringBuilder) getObject()).append(Text);
        return this;
    }

    public String ToString() {
        return ((StringBuilder) getObject()).toString();
    }

    public StringBuilderWrapper Remove(int StartOffset, int EndOffset) {
        ((StringBuilder) getObject()).delete(StartOffset, EndOffset);
        return this;
    }

    public StringBuilderWrapper Insert(int Offset, String Text) {
        ((StringBuilder) getObject()).insert(Offset, Text);
        return this;
    }

    public int getLength() {
        return ((StringBuilder) getObject()).length();
    }

    /* Debug info: failed to restart local var, previous not found, register: 1 */
    @BA.Hide
    public String toString() {
        return getObjectOrNull() == null ? "null" : ((StringBuilder) getObject()).toString();
    }
}
