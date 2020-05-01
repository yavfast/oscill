package anywheresoftware.b4a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.keywords.Common;
import java.util.HashMap;

public class BALayout extends ViewGroup {
    private static float deviceScale = Common.Density;
    /* access modifiers changed from: private */
    public static float scale = Common.Density;

    public BALayout(Context context) {
        super(context);
    }

    public static void setDeviceScale(float scale2) {
        deviceScale = scale2;
    }

    public static void setUserScale(float userScale) {
        if (Float.compare(deviceScale, userScale) == 0) {
            scale = 1.0f;
        } else {
            scale = deviceScale / userScale;
        }
    }

    public static float getDeviceScale() {
        return deviceScale;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                if (child.getLayoutParams() instanceof LayoutParams) {
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    child.layout(lp.left, lp.top, lp.left + child.getMeasuredWidth(), lp.top + child.getMeasuredHeight());
                } else {
                    child.layout(0, 0, getLayoutParams().width, getLayoutParams().height);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(getLayoutParams().width, widthMeasureSpec), resolveSize(getLayoutParams().height, heightMeasureSpec));
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int left;
        public int top;

        public LayoutParams(int left2, int top2, int width, int height) {
            super(width, height);
            this.left = left2;
            this.top = top2;
        }

        public LayoutParams() {
            super(0, 0);
        }

        public HashMap<String, Object> toDesignerMap() {
            HashMap<String, Object> props = new HashMap<>();
            props.put("left", Integer.valueOf((int) (((float) this.left) / BALayout.scale)));
            props.put("top", Integer.valueOf((int) (((float) this.top) / BALayout.scale)));
            props.put("width", Integer.valueOf((int) (((float) this.width) / BALayout.scale)));
            props.put("height", Integer.valueOf((int) (((float) this.height) / BALayout.scale)));
            return props;
        }

        public void setFromUserPlane(int left2, int top2, int width, int height) {
            int i;
            int i2;
            this.left = (int) (((float) left2) * BALayout.scale);
            this.top = (int) (((float) top2) * BALayout.scale);
            if (width > 0) {
                i = (int) (((float) width) * BALayout.scale);
            } else {
                i = width;
            }
            this.width = i;
            if (height > 0) {
                i2 = (int) (((float) height) * BALayout.scale);
            } else {
                i2 = height;
            }
            this.height = i2;
        }
    }
}
