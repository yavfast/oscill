package anywheresoftware.b4a.keywords;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.ConnectorUtils;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.CustomViewWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@BA.Hide
public class LayoutBuilder {
    private static double autoscale;
    private static HashMap<String, WeakReference<MapAndCachedStrings>> cachedLayouts = new HashMap<>();
    private static List<CustomViewWrapper> customViewWrappers;
    private static double screenSize = 0.0d;
    private static BA tempBA;

    @BA.Hide
    public interface DesignerTextSizeMethod {
        float getTextSize();

        void setTextSize(float f);
    }

    public static class LayoutValuesAndMap {
        public final LayoutValues layoutValues;
        public final HashMap<String, ViewWrapper<?>> map;

        public LayoutValuesAndMap(LayoutValues layoutValues2, HashMap<String, ViewWrapper<?>> map2) {
            this.layoutValues = layoutValues2;
            this.map = map2;
        }
    }

    private static class MapAndCachedStrings {
        public final String[] cachedStrings;
        public final HashMap<String, Object> map;

        public MapAndCachedStrings(HashMap<String, Object> map2, String[] cachedStrings2) {
            this.map = map2;
            this.cachedStrings = cachedStrings2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0086 A[Catch:{ IOException -> 0x01ae, Exception -> 0x01bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ce A[Catch:{ IOException -> 0x01ae, Exception -> 0x01bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00f6 A[Catch:{ IOException -> 0x01ae, Exception -> 0x01bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0140 A[Catch:{ IOException -> 0x01ae, Exception -> 0x01bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x018f A[SYNTHETIC, Splitter:B:77:0x018f] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:68:0x017a=Splitter:B:68:0x017a, B:83:0x01bc=Splitter:B:83:0x01bc} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static LayoutValuesAndMap loadLayout(String file, BA ba, boolean isActivity, ViewGroup parent, HashMap<String, ViewWrapper<?>> dynamicTable, boolean d4a) throws IOException {
        MapAndCachedStrings mcs;
        String[] cache;
        int numberOfVariants;
        Throwable e;
        int i;
        HashMap<String, Object> props;
        int variantIndex;
        try {
            tempBA = ba;
            if (!d4a) {
                file = file.toLowerCase(BA.cul);
            }
            if (!file.endsWith(".bal")) {
                file = String.valueOf(file) + ".bal";
            }
            WeakReference<MapAndCachedStrings> cl = cachedLayouts.get(file);
            if (cl != null) {
                mcs = (MapAndCachedStrings) cl.get();
            } else {
                mcs = null;
            }
            DataInputStream din = new DataInputStream(BA.applicationContext.getAssets().open(file));
            int version = ConnectorUtils.readInt(din);
            for (int pos = ConnectorUtils.readInt(din); pos > 0; pos = (int) (((long) pos) - din.skip((long) pos))) {
            }
            String[] cache2 = null;
            if (version >= 3) {
                if (mcs != null) {
                    String[] cache3 = mcs.cachedStrings;
                    ConnectorUtils.readInt(din);
                    for (int i2 = 0; i2 < cache3.length; i2++) {
                        din.skipBytes(ConnectorUtils.readInt(din));
                    }
                    cache = cache3;
                    numberOfVariants = ConnectorUtils.readInt(din);
                    LayoutValues chosen = null;
                    LayoutValues device = Common.GetDeviceLayoutValues(ba);
                    float distance = Float.MAX_VALUE;
                    i = 0;
                    int variantIndex2 = 0;
                    while (i < numberOfVariants) {
                        LayoutValues test = LayoutValues.readFromStream(din);
                        if (chosen == null) {
                            chosen = test;
                            distance = test.calcDistance(device);
                            variantIndex = i;
                        } else {
                            float testDistance = test.calcDistance(device);
                            if (testDistance < distance) {
                                chosen = test;
                                distance = testDistance;
                                variantIndex = i;
                            } else {
                                variantIndex = variantIndex2;
                            }
                        }
                        i++;
                        variantIndex2 = variantIndex;
                    }
                    BALayout.setUserScale(chosen.Scale);
                    if (dynamicTable == null) {
                        LayoutHashMap layoutHashMap = new LayoutHashMap();
                        if (mcs != null) {
                            props = mcs.map;
                        } else {
                            props = ConnectorUtils.readMap(din, cache);
                            cachedLayouts.put(file, new WeakReference(new MapAndCachedStrings(props, cache)));
                        }
                        loadLayoutHelper(props, ba, ba.eventsTarget == null ? ba.activity : ba.eventsTarget, parent, isActivity, "variant" + variantIndex2, true, layoutHashMap, d4a);
                        dynamicTable = layoutHashMap;
                    }
                    din.close();
                    if (isActivity || parent.getLayoutParams() == null) {
                        runScripts(file, chosen, dynamicTable, ba.vg.getWidth(), ba.vg.getHeight(), Common.Density, d4a);
                    } else {
                        runScripts(file, chosen, dynamicTable, parent.getLayoutParams().width, parent.getLayoutParams().height, Common.Density, d4a);
                    }
                    BALayout.setUserScale(1.0f);
                    if (customViewWrappers != null) {
                        for (CustomViewWrapper cvw : customViewWrappers) {
                            cvw.AfterDesignerScript();
                        }
                    }
                    LayoutValuesAndMap layoutValuesAndMap = new LayoutValuesAndMap(chosen, dynamicTable);
                    tempBA = null;
                    customViewWrappers = null;
                    return layoutValuesAndMap;
                }
                cache2 = new String[ConnectorUtils.readInt(din)];
                for (int i3 = 0; i3 < cache2.length; i3++) {
                    cache2[i3] = ConnectorUtils.readString(din);
                }
            }
            cache = cache2;
            numberOfVariants = ConnectorUtils.readInt(din);
            LayoutValues chosen2 = null;
            LayoutValues device2 = Common.GetDeviceLayoutValues(ba);
            float distance2 = Float.MAX_VALUE;
            i = 0;
            int variantIndex22 = 0;
            while (i < numberOfVariants) {
            }
            BALayout.setUserScale(chosen2.Scale);
            if (dynamicTable == null) {
            }
            din.close();
            if (isActivity || parent.getLayoutParams() == null) {
            }
            BALayout.setUserScale(1.0f);
            if (customViewWrappers != null) {
            }
            LayoutValuesAndMap layoutValuesAndMap2 = new LayoutValuesAndMap(chosen2, dynamicTable);
            tempBA = null;
            customViewWrappers = null;
            return layoutValuesAndMap2;
        } catch (IOException e3) {
            e = e3;
            try {
                throw e;
            } catch (Throwable th2) {
                e = th2;
            }
        } catch (Exception e4) {
            e = e4;
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00cb, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00cc, code lost:
        r4.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00d0, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00da, code lost:
        throw new java.lang.RuntimeException(r4.getCause());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x00cb A[ExcHandler: SecurityException (r4v5 'e' java.lang.SecurityException A[CUSTOM_DECLARE]), Splitter:B:3:0x0014] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00d0 A[ExcHandler: InvocationTargetException (r4v3 'e' java.lang.reflect.InvocationTargetException A[CUSTOM_DECLARE]), Splitter:B:3:0x0014] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00dd A[ExcHandler: ClassNotFoundException (e java.lang.ClassNotFoundException), Splitter:B:3:0x0014] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void runScripts(String file, LayoutValues lv, HashMap<String, ViewWrapper<?>> views, int w, int h, float s, boolean d4a) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("LS_");
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= file.length() - 4) {
                break;
            } else {
                char c = file.charAt(i2);
                if (Character.isLetterOrDigit(c)) {
                    sb.append(c);
                } else {
                    sb.append("_");
                }
                i = i2 + 1;
            }
        }
        Class<?> c2 = Class.forName(String.valueOf(BA.packageName) + ".designerscripts." + sb.toString());
        c2.getMethod(variantToMethod((LayoutValues) null), new Class[]{HashMap.class, Integer.TYPE, Integer.TYPE, Float.TYPE}).invoke((Object) null, new Object[]{views, Integer.valueOf(w), Integer.valueOf(h), Float.valueOf(s)});
        c2.getMethod(variantToMethod(lv), new Class[]{HashMap.class, Integer.TYPE, Integer.TYPE, Float.TYPE}).invoke((Object) null, new Object[]{views, Integer.valueOf(w), Integer.valueOf(h), Float.valueOf(s)});
    }

    public static void setScaleRate(double rate) {
        autoscale = 1.0d + (rate * ((double) ((((float) (tempBA.vg.getWidth() + tempBA.vg.getHeight())) / (750.0f * Common.Density)) - 1.0f)));
        screenSize = 0.0d;
    }

    public static double getScreenSize() {
        if (screenSize == 0.0d) {
            screenSize = (Math.sqrt(Math.pow((double) tempBA.vg.getWidth(), 2.0d) + Math.pow((double) tempBA.vg.getHeight(), 2.0d)) / 160.0d) / ((double) Common.Density);
        }
        return screenSize;
    }

    public static void scaleAll(HashMap<String, ViewWrapper<?>> views) {
        for (ViewWrapper<?> v : views.values()) {
            if (v.IsInitialized() && !(v instanceof ActivityWrapper)) {
                scaleView(v);
            }
        }
    }

    public static void scaleView(ViewWrapper<?> v) {
        v.setLeft((int) (((double) v.getLeft()) * autoscale));
        v.setTop((int) (((double) v.getTop()) * autoscale));
        v.setWidth((int) (((double) v.getWidth()) * autoscale));
        v.setHeight((int) (((double) v.getHeight()) * autoscale));
        if (v instanceof DesignerTextSizeMethod) {
            DesignerTextSizeMethod t = (DesignerTextSizeMethod) v;
            t.setTextSize((float) (((double) t.getTextSize()) * autoscale));
        }
    }

    private static String variantToMethod(LayoutValues lv) {
        String variant;
        if (lv == null) {
            variant = "general";
        } else {
            variant = String.valueOf(String.valueOf(lv.Width)) + "x" + String.valueOf(lv.Height) + "_" + BA.NumberToString((double) lv.Scale).replace(".", "_");
        }
        return "LS_" + variant;
    }

    private static void loadLayoutHelper(HashMap<String, Object> props, BA ba, Object fieldsTarget, ViewGroup parent, boolean root, String currentVariant, boolean firstCall, HashMap<String, ViewWrapper<?>> dynamicTable, boolean d4a) throws Exception {
        ViewGroup viewGroup;
        HashMap<String, Object> variant = (HashMap) props.get(currentVariant);
        if (root || !firstCall) {
            ViewGroup act = root ? parent : null;
            props.put("left", variant.get("left"));
            props.put("top", variant.get("top"));
            props.put("width", variant.get("width"));
            props.put("height", variant.get("height"));
            View o = (View) DynamicBuilder.build(act, props, false, parent.getContext());
            if (!root) {
                String name = ((String) props.get("name")).toLowerCase(BA.cul);
                String cls = (String) props.get("type");
                if (cls.startsWith(".")) {
                    cls = "anywheresoftware.b4a.objects" + cls;
                }
                ViewWrapper ow = (ViewWrapper) Class.forName(cls).newInstance();
                dynamicTable.put(name, ow);
                Object customObject = ow;
                if (ow instanceof CustomViewWrapper) {
                    if (customViewWrappers == null) {
                        customViewWrappers = new ArrayList();
                    }
                    customViewWrappers.add((CustomViewWrapper) ow);
                    String cclass = (String) props.get("customType");
                    if (cclass == null || cclass.length() == 0) {
                        throw new RuntimeException("CustomView CustomType property was not set.");
                    }
                    Object newInstance = Class.forName(cclass).newInstance();
                    CustomViewWrapper cvw = (CustomViewWrapper) ow;
                    cvw.customObject = newInstance;
                    cvw.props = props;
                    customObject = newInstance;
                }
                if (!d4a) {
                    try {
                        Field field = fieldsTarget.getClass().getField("_" + name);
                        if (field != null) {
                            field.set(fieldsTarget, customObject);
                        }
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Field " + name + " was declared with the wrong type.");
                    } catch (NoSuchFieldException e2) {
                    }
                }
                ow.setObject(o);
                if (!d4a) {
                    ow.innerInitialize(ba, ((String) props.get("eventName")).toLowerCase(BA.cul), true);
                }
                parent.addView(o, o.getLayoutParams());
                viewGroup = (ViewGroup) o;
            } else {
                viewGroup = (ViewGroup) o;
            }
        } else {
            parent.setBackgroundDrawable((Drawable) DynamicBuilder.build(parent, (HashMap) props.get("drawable"), false, (Object) null));
            viewGroup = parent;
        }
        HashMap<String, Object> kids = (HashMap) props.get(":kids");
        if (kids != null) {
            for (int i = 0; i < kids.size(); i++) {
                loadLayoutHelper((HashMap) kids.get(String.valueOf(i)), ba, fieldsTarget, viewGroup, false, currentVariant, false, dynamicTable, d4a);
            }
        }
    }

    @BA.Hide
    public static class LayoutHashMap<K, V> extends HashMap<K, V> {
        public V get(Object key) {
            V v = super.get(key);
            if (v != null) {
                return v;
            }
            throw new RuntimeException("Cannot find view: " + key.toString() + "\nAll views in script should be declared.");
        }
    }
}
