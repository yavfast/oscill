package anywheresoftware.b4a.phone;

import android.content.ContentValues;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.IOnActivityResult;
import anywheresoftware.b4a.objects.streams.File;

@BA.ShortName("RingtoneManager")
public class RingtoneManagerWrapper {
    public static final int TYPE_ALARM = 4;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RINGTONE = 1;
    /* access modifiers changed from: private */
    public IOnActivityResult ion;

    public String GetContentDir() {
        return File.ContentDir;
    }

    public String AddToMediaStore(String Dir, String FileName, String Title, boolean IsAlarm, boolean IsNotification, boolean IsRingtone, boolean IsMusic) {
        java.io.File k = new java.io.File(Dir, FileName);
        ContentValues values = new ContentValues();
        values.put("_data", k.getAbsolutePath());
        values.put("title", Title);
        values.put("mime_type", "audio/*");
        values.put("is_ringtone", Boolean.valueOf(IsRingtone));
        values.put("is_notification", Boolean.valueOf(IsNotification));
        values.put("is_alarm", Boolean.valueOf(IsAlarm));
        values.put("is_music", Boolean.valueOf(IsMusic));
        return BA.applicationContext.getContentResolver().insert(MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath()), values).toString();
    }

    public void SetDefault(int Type, String Uri) {
        RingtoneManager.setActualDefaultRingtoneUri(BA.applicationContext, Type, android.net.Uri.parse(Uri));
    }

    public String GetDefault(int Type) {
        Uri u = RingtoneManager.getDefaultUri(Type);
        if (u == null) {
            return "";
        }
        return u.toString();
    }

    public void DeleteRingtone(String Uri) {
        BA.applicationContext.getContentResolver().delete(android.net.Uri.parse(Uri), (String) null, (String[]) null);
    }

    public void ShowRingtonePicker(final BA ba, String EventName, int Type, boolean IncludeSilence, String ChosenRingtone) {
        final String eventName = EventName.toLowerCase(BA.cul);
        Intent i = new Intent("android.intent.action.RINGTONE_PICKER");
        i.putExtra("android.intent.extra.ringtone.TYPE", Type);
        i.putExtra("android.intent.extra.ringtone.SHOW_SILENT", IncludeSilence);
        if (ChosenRingtone.length() > 0) {
            i.putExtra("android.intent.extra.ringtone.EXISTING_URI", Uri.parse(ChosenRingtone));
        }
        this.ion = new IOnActivityResult() {
            public void ResultArrived(int resultCode, Intent intent) {
                String uri = null;
                if (resultCode == -1 && intent != null) {
                    Uri u = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
                    uri = u == null ? "" : u.toString();
                }
                RingtoneManagerWrapper.this.ion = null;
                if (uri != null) {
                    ba.raiseEvent(RingtoneManagerWrapper.this, String.valueOf(eventName) + "_pickerresult", true, uri);
                } else {
                    ba.raiseEvent(RingtoneManagerWrapper.this, String.valueOf(eventName) + "_pickerresult", false, "");
                }
            }
        };
        ba.startActivityForResult(this.ion, i);
    }
}
