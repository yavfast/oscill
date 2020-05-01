package anywheresoftware.b4a.phone;

import android.database.Cursor;
import android.provider.CallLog;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import java.util.HashMap;

@BA.ShortName("CallLog")
public class CallLogWrapper {
    private static final String[] calls_projection = {"date", "type", "duration", "number", "_id", "name"};

    public List GetAll(int Limit) {
        return getAllCalls((String) null, (String[]) null, Limit);
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    public CallItem GetById(int Id) {
        List l = getAllCalls("_id = ?", new String[]{String.valueOf(Id)}, 0);
        if (l.getSize() == 0) {
            return null;
        }
        return (CallItem) l.Get(0);
    }

    public List GetSince(long Date, int Limit) {
        return getAllCalls("date >= ?", new String[]{Long.toString(Date)}, Limit);
    }

    /* Debug info: failed to restart local var, previous not found, register: 18 */
    private List getAllCalls(String selection, String[] args, int limit) {
        Cursor crsr = BA.applicationContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, calls_projection, selection, args, "date DESC");
        List l = new List();
        l.Initialize();
        HashMap<String, Integer> m = new HashMap<>();
        for (int col = 0; col < crsr.getColumnCount(); col++) {
            m.put(crsr.getColumnName(col), Integer.valueOf(col));
        }
        int i = 0;
        while (crsr.moveToNext()) {
            l.Add(new CallItem(crsr.getString(m.get("number").intValue()), crsr.getInt(m.get("_id").intValue()), crsr.getLong(m.get("duration").intValue()), crsr.getInt(m.get("type").intValue()), crsr.getLong(m.get("date").intValue()), crsr.getString(m.get("name").intValue())));
            if (limit > 0) {
                i++;
                if (i >= limit) {
                    break;
                }
            }
        }
        crsr.close();
        return l;
    }

    @BA.ShortName("CallItem")
    public static class CallItem {
        public static final int TYPE_INCOMING = 1;
        public static final int TYPE_MISSED = 3;
        public static final int TYPE_OUTGOING = 2;
        public String CachedName = "";
        public int CallType;
        public long Date;
        public long Duration;
        public int Id = -1;
        public String Number;

        public CallItem() {
        }

        CallItem(String number, int id, long duration, int type, long date, String name) {
            String str;
            String str2;
            if (number == null) {
                str = "";
            } else {
                str = number;
            }
            this.Number = str;
            this.Id = id;
            this.CallType = type;
            this.Duration = duration;
            this.Date = date;
            if (name == null) {
                str2 = "";
            } else {
                str2 = name;
            }
            this.CachedName = str2;
        }

        @BA.Hide
        public String toString() {
            return "Id=" + this.Id + ", Number=" + this.Number + ",CachedName=" + this.CachedName + ", Type=" + this.CallType + ", Date=" + this.Date + ", Duration=" + this.Duration;
        }
    }
}
