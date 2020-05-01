package oscilldroid.inel;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Bit;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.collections.List;

public class converter {
    public Common __c = null;
    public files _files = null;
    public main _main = null;

    public static Object getObject() {
        throw new RuntimeException("Code module does not support this method.");
    }

    public static String _bytescut(BA ba, byte[] bArr, byte[] bArr2, int i, int i2) {
        for (int i3 = 0; i3 <= i2; i3++) {
            bArr[i + i3] = bArr2[i3];
        }
        return "";
    }

    public static String _bytestohexstr(byte[] bArr, int i, int i2) {
        String str = "";
        for (int i3 = 0; i3 <= i2; i3++) {
            String upperCase = Bit.ToHexString(_bytetounsignedbyte(bArr[i + i3])).toUpperCase();
            if (upperCase.length() == 1) {
                str = str + "0" + upperCase;
            } else {
                str = str + upperCase;
            }
        }
        return str;
    }

    public static long _bytestolong(BA ba, byte[] bArr, int i, int i2) {
        String _bytestohexstr = _bytestohexstr(bArr, i, i2);
        return (long) Bit.ParseInt(_bytestohexstr, 16);
    }

    public static String _bytestostr(BA ba, byte[] bArr, int i, int i2) {
        String str = "";
        for (int i3 = 0; i3 <= i2; i3++) {
            str = str + Common.Chr(bArr[i + i3]);
        }
        return str;
    }

    public static int _bytetoint(byte[] bArr, int i) {
        int _bytetounsignedbyte = _bytetounsignedbyte(bArr[i]);
        int ShiftLeft = Bit.ShiftLeft(_bytetounsignedbyte, 8);
        return Bit.Or(ShiftLeft, _bytetounsignedbyte(bArr[i + 1]));
    }

    public static int _bytetounsignedbyte(byte b) {
        if (b < 0) {
            return b + 256;
        }
        return b;
    }

    public static String _hextoascii(String str) {
        int length = (str.length() - 1);
        String str2 = "";
        for (int i = 0; i <= length; i = i + 2) {
            String substring = str.substring(i, i + 2);
            StringBuilder append = new StringBuilder().append(str2);
            str2 = append.append(Common.Chr(Bit.ParseInt(substring, 16))).toString();
        }
        return str2;
    }

    public static String _inttovolt(float f) {
        String NumberToString;
        String str;
        String NumberToString2 = BA.NumberToString((double) f);
        if (f >= 1000.0f || f <= -1000.0f) {
            NumberToString = BA.NumberToString(Double.parseDouble(NumberToString2) / 1000.0d);
            str = "V";
        } else {
            NumberToString = NumberToString2;
            str = "mV";
        }
        if (NumberToString.length() > 4) {
            if (NumberToString.charAt(3) == BA.ObjectToChar(".")) {
                NumberToString = NumberToString.substring(0, 3);
            } else {
                NumberToString = NumberToString.substring(0, 4);
            }
        }
        return NumberToString + str;
    }

    public static String _process_globals() {
        return "";
    }

    public static int _strhextobyte(String str, byte[] bArr) {
        int i = 0;
        int length = (str.length() - 1);
        for (int i2 = 0; i2 <= length; i2 = i2 + 2) {
            String substring = str.substring(i2, i2 + 2);
            bArr[i] = (byte) Bit.ParseInt(substring, 16);
            i++;
        }
        return i;
    }

    public static long _strtotime(String str) {
        int indexOf = str.indexOf("pS");
        if (indexOf > 0) {
            return (long) Double.parseDouble(str.substring(0, indexOf));
        }
        int indexOf2 = str.indexOf("nS");
        if (indexOf2 > 0) {
            return (long) (Double.parseDouble(str.substring(0, indexOf2)) * 1000.0d);
        }
        int indexOf3 = str.indexOf("uS");
        if (indexOf3 > 0) {
            return (long) (Double.parseDouble(str.substring(0, indexOf3)) * 1000000.0d);
        }
        int indexOf4 = str.indexOf("mS");
        if (indexOf4 > 0) {
            return (long) (Double.parseDouble(str.substring(0, indexOf4)) * 1.0E9d);
        }
        return -1;
    }

    private static List timeSuffixList = new List();
    static {
        timeSuffixList.Initialize();
        timeSuffixList.Add("pS");
        timeSuffixList.Add("nS");
        timeSuffixList.Add("uS");
        timeSuffixList.Add("mS");
        timeSuffixList.Add("S");
    }


    public static String _timetostr(long j) {
        String NumberToString;
        int i;
        if (j == 0) {
            return "0.00S";
        }
        if (j < 1000) {
            NumberToString = BA.NumberToString(j);
            i = 0;
        } else if (j < 1000000) {
            NumberToString = BA.NumberToString(((double) j) / 1000.0d);
            i = 1;
        } else if (j < 1000000000) {
            i = 2;
            NumberToString = BA.NumberToString(((double) j) / 1000000.0d);
        } else if (j < 1000000000000L) {
            NumberToString = BA.NumberToString(((double) j) / 1.0E9d);
            i = 3;
        } else {
            NumberToString = BA.NumberToString(((double) j) / 1.0E12d);
            i = 4;
        }
        if (NumberToString.length() > 4) {
            if (NumberToString.charAt(3) == '.') {
                NumberToString = NumberToString.substring(0, 3);
            } else {
                NumberToString = NumberToString.substring(0, 4);
            }
        }
        switch (BA.switchObjectToInt(NumberToString.substring(1), "99", "9.9", ".99")) {
            case 0:
                NumberToString = BA.NumberToString(Double.parseDouble(NumberToString) + 1.0d);
                break;
            case 1:
                NumberToString = BA.NumberToString(Double.parseDouble(NumberToString) + 0.1d);
                break;
            case 2:
                NumberToString = BA.NumberToString(Double.parseDouble(NumberToString) + 0.01d);
                break;
        }
        if (Double.parseDouble(NumberToString) > 999.0d) {
            NumberToString = BA.NumberToString(Double.parseDouble(NumberToString) / 1000.0d);
            i++;
        }
        return NumberToString + timeSuffixList.Get(i);
    }

    public static int _volttoint(String str) {
        int indexOf = str.indexOf("mV");
        if (indexOf > 0) {
            return (int) Double.parseDouble(str.substring(0, indexOf));
        }
        int indexOf2 = str.indexOf("V");
        if (indexOf2 > 0) {
            return (int) (Double.parseDouble(str.substring(0, indexOf2)) * 1000.0d);
        }
        return -1;
    }
}
