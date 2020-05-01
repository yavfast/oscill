package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.objects.SimpleListAdapter;
import java.util.HashMap;

@BA.ActivityObject
@BA.ShortName("ListView")
public class ListViewWrapper extends ViewWrapper<ListViewWrapper.SimpleListView> {
    @BA.Hide
    public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
        if (!keepOldObject) {
            setObject(new SimpleListView(ba.context));
        }
        super.innerInitialize(ba, eventName, true);
        if (ba.subExists(String.valueOf(eventName) + "_itemclick")) {
            ((SimpleListView) getObject()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    ba.raiseEventFromUI(ListViewWrapper.this.getObject(), String.valueOf(eventName) + "_itemclick", Integer.valueOf(position), ((SimpleListView) ListViewWrapper.this.getObject()).adapter.getItem(position));
                }
            });
        }
        if (ba.subExists(String.valueOf(eventName) + "_itemlongclick")) {
            ((SimpleListView) getObject()).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                    ba.raiseEventFromUI(ListViewWrapper.this.getObject(), String.valueOf(eventName) + "_itemlongclick", Integer.valueOf(position), ((SimpleListView) ListViewWrapper.this.getObject()).adapter.getItem(position));
                    return true;
                }
            });
        }
    }

    public int getSize() {
        return ((SimpleListView) getObject()).adapter.getCount();
    }

    public SimpleListAdapter.SingleLineLayout getSingleLineLayout() {
        return ((SimpleListView) getObject()).adapter.SingleLine;
    }

    public SimpleListAdapter.TwoLinesLayout getTwoLinesLayout() {
        return ((SimpleListView) getObject()).adapter.TwoLines;
    }

    public SimpleListAdapter.TwoLinesAndBitmapLayout getTwoLinesAndBitmap() {
        return ((SimpleListView) getObject()).adapter.TwoLinesAndBitmap;
    }

    public void AddSingleLine(String Text) {
        AddSingleLine2(Text, (Object) null);
    }

    public void AddSingleLine2(String Text, Object ReturnValue) {
        SimpleListAdapter.SingleLineData sl = new SimpleListAdapter.SingleLineData();
        sl.Text = Text;
        sl.ReturnValue = ReturnValue;
        add(sl);
    }

    public void AddTwoLines(String Text1, String Text2) {
        AddTwoLines2(Text1, Text2, (Object) null);
    }

    public void AddTwoLines2(String Text1, String Text2, Object ReturnValue) {
        SimpleListAdapter.TwoLinesData t = new SimpleListAdapter.TwoLinesData();
        t.Text = Text1;
        t.ReturnValue = ReturnValue;
        t.SecondLineText = Text2;
        add(t);
    }

    public void AddTwoLinesAndBitmap(String Text1, String Text2, Bitmap Bitmap) {
        AddTwoLinesAndBitmap2(Text1, Text2, Bitmap, (Object) null);
    }

    public void AddTwoLinesAndBitmap2(String Text1, String Text2, Bitmap Bitmap, Object ReturnValue) {
        SimpleListAdapter.TwoLinesAndBitmapData t = new SimpleListAdapter.TwoLinesAndBitmapData();
        t.Text = Text1;
        t.ReturnValue = ReturnValue;
        t.SecondLineText = Text2;
        t.Bitmap = Bitmap;
        add(t);
    }

    @BA.Hide
    public void add(SimpleListAdapter.SimpleItem si) {
        ((SimpleListView) getObject()).adapter.items.add(si);
        ((SimpleListView) getObject()).adapter.notifyDataSetChanged();
    }

    public Object GetItem(int Index) {
        return ((SimpleListView) getObject()).adapter.getItem(Index);
    }

    public void RemoveAt(int Index) {
        ((SimpleListView) getObject()).adapter.items.remove(Index);
        ((SimpleListView) getObject()).adapter.notifyDataSetChanged();
    }

    public void Clear() {
        ((SimpleListView) getObject()).adapter.items.clear();
        ((SimpleListView) getObject()).adapter.notifyDataSetChanged();
    }

    public void setFastScrollEnabled(boolean Enabled) {
        ((SimpleListView) getObject()).setFastScrollEnabled(Enabled);
    }

    public boolean getFastScrollEnabled() {
        return ((SimpleListView) getObject()).isFastScrollEnabled();
    }

    public void setScrollingBackgroundColor(int Color) {
        ((SimpleListView) getObject()).setCacheColorHint(Color);
    }

    public void SetSelection(int Position) {
        ((SimpleListView) getObject()).setSelection(Position);
    }

    @BA.Hide
    public static class SimpleListView extends ListView {
        public SimpleListAdapter adapter;

        public SimpleListView(Context context) {
            super(context);
            this.adapter = new SimpleListAdapter(context);
            setAdapter(this.adapter);
        }
    }

    @BA.Hide
    public static View build(Object prev, HashMap<String, Object> props, boolean designer, Object tag) throws Exception {
        if (prev == null) {
            prev = ViewWrapper.buildNativeView((Context) tag, SimpleListView.class, props, designer);
        }
        ListView list = (ListView) ViewWrapper.build(prev, props, designer);
        Drawable d = (Drawable) DynamicBuilder.build(list, (HashMap) props.get("drawable"), designer, (Object) null);
        if (d != null) {
            list.setBackgroundDrawable(d);
        }
        list.setFastScrollEnabled(((Boolean) props.get("fastScrollEnabled")).booleanValue());
        if (designer) {
            SimpleListView slv = (SimpleListView) list;
            if (slv.adapter.items.size() == 0) {
                for (int i = 1; i <= 10; i++) {
                    SimpleListAdapter.SingleLineData s = new SimpleListAdapter.SingleLineData();
                    s.Text = "Item #" + i;
                    slv.adapter.items.add(s);
                }
                slv.adapter.notifyDataSetChanged();
            }
        }
        return list;
    }
}
