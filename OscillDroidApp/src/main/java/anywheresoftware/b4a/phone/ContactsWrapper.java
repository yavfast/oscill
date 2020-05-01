package anywheresoftware.b4a.phone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.io.InputStream;
import java.util.HashMap;

@BA.ShortName("Contacts")
public class ContactsWrapper {
    private static final String[] people_projection = {"times_contacted", "number", "last_time_contacted", "display_name", "name", "notes", "starred", "_id"};

    public List GetAll() {
        return getAllContacts((String) null, (String[]) null);
    }

    public List FindByName(String Name, boolean Exact) {
        if (!Exact) {
            return getAllContacts("name LIKE ?", new String[]{"%" + Name + "%"});
        }
        return getAllContacts("name = ?", new String[]{Name});
    }

    public List FindByMail(String Email, boolean Exact) {
        String sel;
        String args;
        ContentResolver cr = BA.applicationContext.getContentResolver();
        if (!Exact) {
            sel = " LIKE ?";
            args = "%" + Email + "%";
        } else {
            sel = " = ?";
            args = Email;
        }
        Cursor crsr = cr.query(Contacts.ContactMethods.CONTENT_EMAIL_URI, new String[]{"person", "data"}, "data" + sel, new String[]{args}, (String) null);
        StringBuilder sb = new StringBuilder();
        while (crsr.moveToNext()) {
            for (int i = 0; i < crsr.getColumnCount(); i++) {
                sb.append(crsr.getString(0)).append(",");
            }
        }
        int count = crsr.getCount();
        crsr.close();
        if (count == 0) {
            List l = new List();
            l.Initialize();
            return l;
        }
        sb.setLength(sb.length() - 1);
        return getAllContacts("_id IN (" + sb.toString() + ")", (String[]) null);
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    public Contact GetById(int Id) {
        List l = getAllContacts("_id = ?", new String[]{String.valueOf(Id)});
        if (l.getSize() == 0) {
            return null;
        }
        return (Contact) l.Get(0);
    }

    /* Debug info: failed to restart local var, previous not found, register: 15 */
    private List getAllContacts(String selection, String[] args) {
        Cursor crsr = BA.applicationContext.getContentResolver().query(Contacts.People.CONTENT_URI, people_projection, selection, args, (String) null);
        List l = new List();
        l.Initialize();
        HashMap<String, Integer> m = new HashMap<>();
        for (int col = 0; col < crsr.getColumnCount(); col++) {
            m.put(crsr.getColumnName(col), Integer.valueOf(col));
        }
        while (crsr.moveToNext()) {
            l.Add(new Contact(crsr.getString(m.get("display_name").intValue()), crsr.getString(m.get("number").intValue()), crsr.getInt(m.get("starred").intValue()) > 0, crsr.getInt(m.get("_id").intValue()), crsr.getString(m.get("notes").intValue()), crsr.getInt(m.get("times_contacted").intValue()), crsr.getLong(m.get("last_time_contacted").intValue()), crsr.getString(m.get("name").intValue())));
        }
        crsr.close();
        return l;
    }

    @BA.ShortName("Contact")
    public static class Contact {
        public static final int EMAIL_CUSTOM = 0;
        public static final int EMAIL_HOME = 1;
        public static final int EMAIL_MOBILE = 4;
        public static final int EMAIL_OTHER = 3;
        public static final int EMAIL_WORK = 2;
        public static final int PHONE_CUSTOM = 0;
        public static final int PHONE_FAX_HOME = 5;
        public static final int PHONE_FAX_WORK = 4;
        public static final int PHONE_HOME = 1;
        public static final int PHONE_MOBILE = 2;
        public static final int PHONE_OTHER = 7;
        public static final int PHONE_PAGER = 6;
        public static final int PHONE_WORK = 3;
        public String DisplayName;
        public int Id = -1;
        public long LastTimeContacted;
        public String Name;
        public String Notes;
        public String PhoneNumber = "";
        public boolean Starred;
        public int TimesContacted;

        public Contact() {
        }

        Contact(String displayName, String phoneNumber, boolean starred, int id, String notes, int timesContacted, long lastTimeContacted, String name) {
            String str;
            String str2;
            String str3;
            String str4;
            if (displayName == null) {
                str = "";
            } else {
                str = displayName;
            }
            this.DisplayName = str;
            if (phoneNumber == null) {
                str2 = "";
            } else {
                str2 = phoneNumber;
            }
            this.PhoneNumber = str2;
            this.Starred = starred;
            this.Id = id;
            if (notes == null) {
                str3 = "";
            } else {
                str3 = notes;
            }
            this.Notes = str3;
            this.TimesContacted = timesContacted;
            this.LastTimeContacted = lastTimeContacted;
            if (name == null) {
                str4 = "";
            } else {
                str4 = name;
            }
            this.Name = str4;
        }

        public CanvasWrapper.BitmapWrapper GetPhoto() {
            byte[] b;
            if (this.Id == -1) {
                throw new RuntimeException("Contact object should be set by calling one of the Contacts methods.");
            }
            Uri u = Uri.withAppendedPath(ContentUris.withAppendedId(Contacts.People.CONTENT_URI, (long) this.Id), "photo");
            Cursor crsr = BA.applicationContext.getContentResolver().query(u, new String[]{"data"}, (String) null, (String[]) null, (String) null);
            CanvasWrapper.BitmapWrapper bw = null;
            if (crsr.moveToNext() && (b = crsr.getBlob(0)) != null) {
                File.InputStreamWrapper isw = new File.InputStreamWrapper();
                isw.InitializeFromBytesArray(b, 0, b.length);
                bw = new CanvasWrapper.BitmapWrapper();
                bw.Initialize2((InputStream) isw.getObject());
            }
            crsr.close();
            return bw;
        }

        public Map GetEmails() {
            if (this.Id == -1) {
                throw new RuntimeException("Contact object should be set by calling one of the Contacts methods.");
            }
            Uri u = Uri.withAppendedPath(ContentUris.withAppendedId(Contacts.People.CONTENT_URI, (long) this.Id), "contact_methods");
            Cursor crsr = BA.applicationContext.getContentResolver().query(u, new String[]{"data", "type", "kind"}, "kind = 1", (String[]) null, (String) null);
            Map m = new Map();
            m.Initialize();
            while (crsr.moveToNext()) {
                m.Put(crsr.getString(0), Integer.valueOf(crsr.getInt(1)));
            }
            crsr.close();
            return m;
        }

        public Map GetPhones() {
            if (this.Id == -1) {
                throw new RuntimeException("Contact object should be set by calling one of the Contacts methods.");
            }
            Uri u = Uri.withAppendedPath(ContentUris.withAppendedId(Contacts.People.CONTENT_URI, (long) this.Id), "phones");
            Cursor crsr = BA.applicationContext.getContentResolver().query(u, new String[]{"number", "type"}, (String) null, (String[]) null, (String) null);
            Map m = new Map();
            m.Initialize();
            while (crsr.moveToNext()) {
                m.Put(crsr.getString(0), Integer.valueOf(crsr.getInt(1)));
            }
            crsr.close();
            return m;
        }

        @BA.Hide
        public String toString() {
            return "DisplayName=" + this.DisplayName + ", PhoneNumber=" + this.PhoneNumber + ", Starred=" + this.Starred + ", Id=" + this.Id + ", Notes=" + this.Notes + ", TimesContacted=" + this.TimesContacted + ", LastTimeContacted=" + this.LastTimeContacted + ", Name=" + this.Name;
        }
    }
}
