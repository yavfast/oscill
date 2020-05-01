package anywheresoftware.b4a.objects.collections;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

@BA.ShortName("Map")
public class Map extends AbsObjectWrapper<anywheresoftware.b4a.objects.collections.Map.MyMap> {
    public void Initialize() {
        setObject(new MyMap());
    }

    public Object Put(Object Key, Object Value) {
        return ((MyMap) getObject()).put(Key, Value);
    }

    public Object Remove(Object Key) {
        return ((MyMap) getObject()).remove(Key);
    }

    public Object Get(Object Key) {
        return ((MyMap) getObject()).get(Key);
    }

    public Object GetDefault(Object Key, Object Default) {
        Object res = ((MyMap) getObject()).get(Key);
        if (res == null) {
            return Default;
        }
        return res;
    }

    public void Clear() {
        ((MyMap) getObject()).clear();
    }

    public Object GetKeyAt(int Index) {
        return ((MyMap) getObject()).getKey(Index);
    }

    public Object GetValueAt(int Index) {
        return ((MyMap) getObject()).getValue(Index);
    }

    public int getSize() {
        return ((MyMap) getObject()).size();
    }

    public boolean ContainsKey(Object Key) {
        return ((MyMap) getObject()).containsKey(Key);
    }

    public BA.IterableList Keys() {
        return new BA.IterableList() {
            public Object Get(int index) {
                return Map.this.GetKeyAt(index);
            }

            public int getSize() {
                return Map.this.getSize();
            }
        };
    }

    public BA.IterableList Values() {
        return new BA.IterableList() {
            public Object Get(int index) {
                return Map.this.GetValueAt(index);
            }

            public int getSize() {
                return Map.this.getSize();
            }
        };
    }

    @BA.Hide
    public static class MyMap<K,V> implements java.util.Map<K, V> {
        private java.util.Map.Entry<K, V> currentEntry;
        private LinkedHashMap<K, V> innerMap = new LinkedHashMap<>();
        private Iterator<Entry<K, V>> iterator;
        private int iteratorPosition;

        public Object getKey(int index) {
            return getEntry(index).getKey();
        }

        public Object getValue(int index) {
            return getEntry(index).getValue();
        }

        private java.util.Map.Entry<?, ?> getEntry(int index) {
            if (!(this.iterator == null || this.iteratorPosition == index)) {
                if (this.iteratorPosition == index - 1) {
                    this.currentEntry = this.iterator.next();
                    this.iteratorPosition++;
                } else {
                    this.iterator = null;
                }
            }
            if (this.iterator == null) {
                this.iterator = this.innerMap.entrySet().iterator();
                for (int i = 0; i <= index; i++) {
                    this.currentEntry = this.iterator.next();
                }
                this.iteratorPosition = index;
            }
            return this.currentEntry;
        }

        public void clear() {
            this.iterator = null;
            this.innerMap.clear();
        }

        public boolean containsKey(Object key) {
            return this.innerMap.containsKey(key);
        }

        public boolean containsValue(Object value) {
            return this.innerMap.containsValue(value);
        }

        public Set<java.util.Map.Entry<K, V>> entrySet() {
            return this.innerMap.entrySet();
        }

        public V get(Object key) {
            return this.innerMap.get(key);
        }

        public boolean isEmpty() {
            return this.innerMap.isEmpty();
        }

        public Set<K> keySet() {
            return this.innerMap.keySet();
        }

        public V put(K key, V value) {
            this.iterator = null;
            return this.innerMap.put(key, value);
        }

        public void putAll(java.util.Map m) {
            this.iterator = null;
            this.innerMap.putAll(m);
        }

        public V remove(Object key) {
            this.iterator = null;
            return this.innerMap.remove(key);
        }

        public int size() {
            return this.innerMap.size();
        }

        public Collection values() {
            return this.innerMap.values();
        }

        public String toString() {
            return this.innerMap.toString();
        }
    }
}
