package anywheresoftware.b4a.objects.collections;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

@BA.ShortName("List")
public class List extends AbsObjectWrapper<java.util.List<?>> implements BA.IterableList {
    public void Initialize() {
        setObject(new ArrayList());
    }

    public void Initialize2(List Array) {
        setObject((java.util.List) Array.getObject());
    }

    public void Clear() {
        ((java.util.List) getObject()).clear();
    }

    public void Add(Object item) {
        Object prev;
        if (BA.debugMode && ((java.util.List) getObject()).size() > 0 && (prev = Get(getSize() - 1)) != null && prev == item && !(prev instanceof String) && !(prev instanceof Number) && !(prev instanceof Boolean)) {
            BA.WarningEngine.warn(BA.WarningEngine.SAME_OBJECT_ADDED_TO_LIST);
        }
        ((java.util.List) getObject()).add(item);
    }

    public void AddAll(List List) {
        ((java.util.List) getObject()).addAll((Collection) List.getObject());
    }

    public void AddAllAt(int Index, List List) {
        ((java.util.List) getObject()).addAll(Index, (Collection) List.getObject());
    }

    public void RemoveAt(int Index) {
        ((java.util.List) getObject()).remove(Index);
    }

    public void InsertAt(int Index, Object Item) {
        ((java.util.List) getObject()).add(Index, Item);
    }

    public Object Get(int Index) {
        return ((java.util.List) getObject()).get(Index);
    }

    public void Set(int Index, Object Item) {
        ((java.util.List) getObject()).set(Index, Item);
    }

    public int getSize() {
        return ((java.util.List) getObject()).size();
    }

    public int IndexOf(Object Item) {
        return ((java.util.List) getObject()).indexOf(Item);
    }

    /* Debug info: failed to restart local var, previous not found, register: 2 */
    public void Sort(boolean Ascending) {
        if (Ascending) {
            Collections.sort((java.util.List) getObject());
        } else {
            Collections.sort((java.util.List) getObject(), new Comparator<Comparable>() {
                public int compare(Comparable o1, Comparable o2) {
                    return o2.compareTo(o1);
                }
            });
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 4 */
    public void SortType(String FieldName, final boolean Ascending) throws SecurityException, NoSuchFieldException {
        if (getSize() != 0) {
            final Field f = Get(0).getClass().getDeclaredField(FieldName);
            f.setAccessible(true);
            Collections.sort((java.util.List) getObject(), new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    try {
                        return ((Comparable) f.get(o1)).compareTo(f.get(o2)) * (Ascending ? 1 : -1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    public void SortCaseInsensitive(boolean Ascending) {
        if (Ascending) {
            Collections.sort((java.util.List) getObject(), new Comparator<Comparable>() {
                public int compare(Comparable o1, Comparable o2) {
                    return o1.toString().compareToIgnoreCase(o2.toString());
                }
            });
        } else {
            Collections.sort((java.util.List) getObject(), new Comparator<Comparable>() {
                public int compare(Comparable o1, Comparable o2) {
                    return o2.toString().compareToIgnoreCase(o1.toString());
                }
            });
        }
    }
}
