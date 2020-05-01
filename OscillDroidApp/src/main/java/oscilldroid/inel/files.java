package oscilldroid.inel;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.streams.File;

public class files {
    private static files mostCurrent = new files();
    public Common __c = null;
    public converter _converter = null;
    public main _main = null;

    public static Object getObject() {
        throw new RuntimeException("Code module does not support this method.");
    }

    public static String _datafile(BA ba, String str, String str2, String str3, boolean z) throws Exception {
        List list = new List();
        new File.OutputStreamWrapper();
        list.Initialize();
        if (!File.Exists(File.getDirDefaultExternal(), str)) {
            File.OpenOutput(File.getDirDefaultExternal(), str, true).Close();
        }
        List ReadList = File.ReadList(File.getDirDefaultExternal(), str);
        double size = (double) (ReadList.getSize() - 1);
        for (int i = 0; ((double) i) <= size; i = (int) (((double) i) + 1.0d)) {
            String valueOf = String.valueOf(ReadList.Get(i));
            if (valueOf.startsWith(str2)) {
                ReadList.RemoveAt(i);
                ReadList.Add(str2 + str3);
                if (!z) {
                    return valueOf.substring(str2.length());
                }
                File.WriteList(File.getDirDefaultExternal(), str, ReadList);
                return "";
            }
        }
        if (str3.equals("")) {
            return "";
        }
        ReadList.Add(str2 + str3);
        File.WriteList(File.getDirDefaultExternal(), str, ReadList);
        return str3;
    }

    public static String _getalldatafile(BA ba, String str, List list, List list2) throws Exception {
        List list3 = new List();
        new File.OutputStreamWrapper();
        list.Clear();
        list2.Clear();
        list3.Initialize();
        if (!File.Exists(File.getDirDefaultExternal(), str)) {
            File.OpenOutput(File.getDirDefaultExternal(), str, true).Close();
        }
        List ReadList = File.ReadList(File.getDirDefaultExternal(), str);
        double size = (double) (ReadList.getSize() - 1);
        for (int i = 0; ((double) i) <= size; i = (int) (((double) i) + 1.0d)) {
            String valueOf = String.valueOf(ReadList.Get(i));
            int indexOf = valueOf.indexOf(":") + 1;
            list.Add(valueOf.substring(0, indexOf));
            list2.Add(valueOf.substring(indexOf));
        }
        return "";
    }

    public static String _process_globals() throws Exception {
        return "";
    }
}
