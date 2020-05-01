package anywheresoftware.b4a.phone;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.IOnActivityResult;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.constants.KeyCodes;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@BA.Version(2.1f)
@BA.ShortName("Phone")
public class Phone {
    public static final int RINGER_NORMAL = 2;
    public static final int RINGER_SILENT = 0;
    public static final int RINGER_VIBRATE = 1;
    public static final int VOLUME_ALARM = 4;
    public static final int VOLUME_MUSIC = 3;
    public static final int VOLUME_NOTIFICATION = 5;
    public static final int VOLUME_RING = 2;
    public static final int VOLUME_SYSTEM = 1;
    public static final int VOLUME_VOICE_CALL = 0;

    public static void LIBRARY_DOC() {
    }

    public static int Shell(String Command, String[] Args, StringBuilder StdOut, StringBuilder StdErr) throws InterruptedException, IOException {
        Process process;
        if (Args == null || Args.length == 0) {
            process = Runtime.getRuntime().exec(Command);
        } else {
            String[] a = new String[(Args.length + 1)];
            a[0] = Command;
            System.arraycopy(Args, 0, a, 1, Args.length);
            process = Runtime.getRuntime().exec(a);
        }
        byte[] buffer = new byte[4096];
        if (StdOut != null) {
            InputStream in = process.getInputStream();
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                StdOut.append(new String(buffer, 0, count, "UTF8"));
            }
        }
        if (StdErr != null) {
            InputStream in2 = process.getErrorStream();
            while (true) {
                int count2 = in2.read(buffer);
                if (count2 == -1) {
                    break;
                }
                StdErr.append(new String(buffer, 0, count2, "UTF8"));
            }
        }
        process.waitFor();
        return process.exitValue();
    }

    public Drawable GetResourceDrawable(int ResourceId) {
        return BA.applicationContext.getResources().getDrawable(ResourceId);
    }

    public static boolean IsAirplaneModeOn() {
        return Settings.System.getInt(BA.applicationContext.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public static String GetSettings(String Settings) {
        String s = android.provider.Settings.Secure.getString(BA.applicationContext.getContentResolver(), Settings);
        if (s != null) {
            return s;
        }
        String s2 = android.provider.Settings.System.getString(BA.applicationContext.getContentResolver(), Settings);
        if (s2 == null) {
            return "";
        }
        return s2;
    }

    public static void SendBroadcastIntent(Intent Intent) {
        BA.applicationContext.sendBroadcast(Intent);
    }

    public static void SetScreenBrightness(BA ba, float Value) {
        WindowManager.LayoutParams lp = ((BA) ba.sharedProcessBA.activityBA.get()).activity.getWindow().getAttributes();
        lp.screenBrightness = Value;
        ((BA) ba.sharedProcessBA.activityBA.get()).activity.getWindow().setAttributes(lp);
    }

    public static void SetScreenOrientation(BA ba, int Orientation) {
        ((BA) ba.sharedProcessBA.activityBA.get()).activity.setRequestedOrientation(Orientation);
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static void HideKeyboard(ActivityWrapper Activity) {
        ((InputMethodManager) BA.applicationContext.getSystemService("input_method")).hideSoftInputFromWindow(((BALayout) Activity.getObject()).getWindowToken(), 0);
    }

    public static String GetSimOperator() {
        String s = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getSimOperator();
        return s == null ? "" : s;
    }

    public static String GetNetworkOperatorName() {
        String s = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getNetworkOperatorName();
        return s == null ? "" : s;
    }

    public static boolean IsNetworkRoaming() {
        return ((TelephonyManager) BA.applicationContext.getSystemService("phone")).isNetworkRoaming();
    }

    public static String GetNetworkType() {
        switch (((TelephonyManager) BA.applicationContext.getSystemService("phone")).getNetworkType()) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "EVDO_0";
            case 6:
                return "EVDO_A";
            case 7:
                return "1xRTT";
            case 8:
                return "HSDPA";
            case KeyCodes.KEYCODE_2:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "IDEN";
            case 12:
                return "EVDO_B";
            case 13:
                return "LTE";
            case KeyCodes.KEYCODE_7:
                return "EHRPD";
            case KeyCodes.KEYCODE_8:
                return "HSPAP";
            default:
                return "UNKNOWN";
        }
    }

    public static String GetPhoneType() {
        switch (((TelephonyManager) BA.applicationContext.getSystemService("phone")).getPhoneType()) {
            case 1:
                return "GSM";
            case 2:
                return "CDMA";
            default:
                return "NONE";
        }
    }

    public static String GetDataState() {
        switch (((TelephonyManager) BA.applicationContext.getSystemService("phone")).getDataState()) {
            case 0:
                return "DISCONNECTED";
            case 1:
                return "CONNECTING";
            case 2:
                return "CONNECTED";
            case 3:
                return "SUSPENDED";
            default:
                return "DISCONNECTED";
        }
    }

    public static int GetMaxVolume(int Channel) {
        return getAudioManager().getStreamMaxVolume(Channel);
    }

    public static void SetVolume(int Channel, int VolumeIndex, boolean ShowUI) {
        getAudioManager().setStreamVolume(Channel, VolumeIndex, ShowUI ? 1 : 0);
    }

    public static int GetVolume(int Channel) {
        return getAudioManager().getStreamVolume(Channel);
    }

    public static void SetMute(int Channel, boolean Mute) {
        getAudioManager().setStreamMute(Channel, Mute);
    }

    public static void SetRingerMode(int Mode) {
        getAudioManager().setRingerMode(Mode);
    }

    public static int GetRingerMode() {
        return getAudioManager().getRingerMode();
    }

    private static AudioManager getAudioManager() {
        return (AudioManager) BA.applicationContext.getSystemService("audio");
    }

    @BA.ShortName("PhoneId")
    public static class PhoneId {
        public static String GetDeviceId() {
            String s = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getDeviceId();
            return s == null ? "" : s;
        }

        public static String GetLine1Number() {
            String s = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getLine1Number();
            return s == null ? "" : s;
        }

        public static String GetSubscriberId() {
            String s = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getSubscriberId();
            return s == null ? "" : s;
        }

        public static String GetSimSerialNumber() {
            String s = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getSimSerialNumber();
            return s == null ? "" : s;
        }
    }

    @BA.ShortName("PhoneVibrate")
    public static class PhoneVibrate {
        public static void Vibrate(BA ba, long TimeMs) {
            ((Vibrator) ba.context.getSystemService("vibrator")).vibrate(TimeMs);
        }
    }

    @BA.ShortName("PhoneWakeState")
    public static class PhoneWakeState {
        private static PowerManager.WakeLock partialLock;
        private static PowerManager.WakeLock wakeLock;

        public static void KeepAlive(BA ba, boolean BrightScreen) {
            int i;
            if (wakeLock == null || !wakeLock.isHeld()) {
                PowerManager pm = (PowerManager) ba.context.getSystemService("power");
                if (BrightScreen) {
                    i = 10;
                } else {
                    i = 6;
                }
                wakeLock = pm.newWakeLock(i | 268435456, "B4A");
                wakeLock.acquire();
                return;
            }
            Common.Log("WakeLock already held.");
        }

        public static void PartialLock(BA ba) {
            if (partialLock == null || !partialLock.isHeld()) {
                partialLock = ((PowerManager) ba.context.getSystemService("power")).newWakeLock(1, "B4A-Partial");
                partialLock.acquire();
                return;
            }
            Common.Log("Partial wakeLock already held.");
        }

        public static void ReleaseKeepAlive() {
            if (wakeLock == null || !wakeLock.isHeld()) {
                Common.Log("No wakelock.");
            } else {
                wakeLock.release();
            }
        }

        public static void ReleasePartialLock() {
            if (partialLock == null || !partialLock.isHeld()) {
                Common.Log("No partial wakelock.");
            } else {
                partialLock.release();
            }
        }
    }

    @BA.ShortName("PhoneIntents")
    public static class PhoneIntents {
        public static Intent OpenBrowser(String Uri) {
            return new Intent(IntentWrapper.ACTION_VIEW, android.net.Uri.parse(Uri));
        }

        public static Intent PlayAudio(String Dir, String File) {
            Uri uri;
            if (Dir == anywheresoftware.b4a.objects.streams.File.ContentDir) {
                uri = Uri.parse(File);
            } else {
                uri = Uri.parse("file://" + new java.io.File(Dir, File).toString());
            }
            Intent it = new Intent(IntentWrapper.ACTION_VIEW);
            it.setDataAndType(uri, "audio/*");
            return it;
        }

        public static Intent PlayVideo(String Dir, String File) {
            Uri uri;
            if (Dir == anywheresoftware.b4a.objects.streams.File.ContentDir) {
                uri = Uri.parse(File);
            } else {
                uri = Uri.parse("file://" + new java.io.File(Dir, File).toString());
            }
            Intent it = new Intent(IntentWrapper.ACTION_VIEW);
            it.setDataAndType(uri, "video/*");
            return it;
        }
    }

    @BA.ShortName("PhoneCalls")
    public static class PhoneCalls {
        public static Intent Call(String PhoneNumber) {
            return new Intent(IntentWrapper.ACTION_CALL, Uri.parse("tel:" + PhoneNumber));
        }
    }

    @BA.ShortName("PhoneSms")
    public static class PhoneSms {
        public static void Send(String PhoneNumber, String Text) {
            SmsManager sm = SmsManager.getDefault();
            Intent i1 = new Intent("b4a.smssent");
            i1.putExtra("phone", PhoneNumber);
            PendingIntent pi = PendingIntent.getBroadcast(BA.applicationContext, 0, i1, 134217728);
            Intent i2 = new Intent("b4a.smsdelivered");
            i2.putExtra("phone", PhoneNumber);
            sm.sendTextMessage(PhoneNumber, (String) null, Text, pi, PendingIntent.getBroadcast(BA.applicationContext, 0, i2, 134217728));
        }
    }

    @BA.ShortName("Email")
    public static class Email {
        public List Attachments = new List();
        public List BCC = new List();
        public String Body = "";
        public List CC = new List();
        public String Subject = "";
        public List To = new List();

        public Email() {
            this.To.Initialize();
            this.CC.Initialize();
            this.BCC.Initialize();
            this.Attachments.Initialize();
        }

        public Intent GetHtmlIntent() {
            return getIntent(true);
        }

        /* Debug info: failed to restart local var, previous not found, register: 11 */
        private Intent getIntent(boolean html) {
            Intent emailIntent = new Intent("android.intent.action.SEND_MULTIPLE");
            emailIntent.setType(html ? "text/html" : "text/plain");
            emailIntent.putExtra("android.intent.extra.EMAIL", (String[]) ((java.util.List) this.To.getObject()).toArray(new String[0]));
            emailIntent.putExtra("android.intent.extra.CC", (String[]) ((java.util.List) this.CC.getObject()).toArray(new String[0]));
            emailIntent.putExtra("android.intent.extra.BCC", (String[]) ((java.util.List) this.BCC.getObject()).toArray(new String[0]));
            emailIntent.putExtra("android.intent.extra.SUBJECT", this.Subject);
            emailIntent.putExtra("android.intent.extra.TEXT", html ? Html.fromHtml(this.Body) : this.Body);
            ArrayList<Uri> uris = new ArrayList<>();
            for (Object file : (java.util.List) this.Attachments.getObject()) {
                uris.add(Uri.fromFile(new java.io.File((String) file)));
            }
            if (uris.size() == 1) {
                emailIntent.putExtra("android.intent.extra.STREAM", uris.get(0));
                emailIntent.setAction(IntentWrapper.ACTION_SEND);
            } else if (uris.size() > 1) {
                emailIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", uris);
            }
            return emailIntent;
        }

        public Intent GetIntent() {
            return getIntent(false);
        }
    }

    @BA.ShortName("LogCat")
    public static class LogCat {
        /* access modifiers changed from: private */
        public static Process lc;
        private static Thread logcatReader;
        /* access modifiers changed from: private */
        public static volatile InputStream logcatStream;
        /* access modifiers changed from: private */
        public static volatile boolean logcatWorking;

        public static void LogCatStart(final BA ba, String[] Args, String EventName) throws InterruptedException, IOException {
            LogCatStop();
            if (logcatReader != null) {
                int wait = 10;
                while (logcatReader.isAlive()) {
                    int wait2 = wait - 1;
                    if (wait <= 0) {
                        break;
                    }
                    Thread.sleep(50);
                    logcatReader.interrupt();
                    wait = wait2;
                }
            }
            final String[] a = new String[(Args.length + 1)];
            a[0] = "/system/bin/logcat";
            System.arraycopy(Args, 0, a, 1, Args.length);
            final String ev = String.valueOf(EventName.toLowerCase(BA.cul)) + "_logcatdata";
            logcatReader = new Thread(new Runnable() {
                /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: java.lang.Object[]} */
                /* JADX WARNING: Multi-variable type inference failed */
                public void run() {
                    try {
                        LogCat.lc = Runtime.getRuntime().exec(a);
                        LogCat.logcatStream = LogCat.lc.getInputStream();
                        InputStream in = LogCat.logcatStream;
                        LogCat.logcatWorking = true;
                        byte[] buffer = new byte[4092];
                        while (true) {
                            if (!LogCat.logcatWorking) {
                                break;
                            }
                            int count = in.read(buffer);
                            Thread.sleep(100);
                            while (count > 0 && in.available() > 0 && count < buffer.length) {
                                count += in.read(buffer, count, buffer.length - count);
                                Thread.sleep(100);
                            }
                            if (count == -1) {
                                LogCat.logcatWorking = false;
                                break;
                            }
                            ba.raiseEvent((Object) null, ev, buffer, Integer.valueOf(count));
                        }
                        LogCat.lc.destroy();
                    } catch (Exception e) {
                        Exception exc = e;
                        if (LogCat.lc != null) {
                            LogCat.lc.destroy();
                        }
                    }
                }
            });
            logcatReader.setDaemon(true);
            logcatReader.start();
        }

        public static void LogCatStop() throws IOException {
            logcatWorking = false;
            if (logcatStream != null) {
                logcatStream.close();
            }
            if (lc != null) {
                lc.destroy();
            }
        }
    }

    @BA.ShortName("ContentChooser")
    public static class ContentChooser implements BA.CheckForReinitialize {
        /* access modifiers changed from: private */
        public String eventName;
        /* access modifiers changed from: private */
        public IOnActivityResult ion;

        public void Initialize(String EventName) {
            this.eventName = EventName.toLowerCase(BA.cul);
        }

        public boolean IsInitialized() {
            return this.eventName != null;
        }

        public void Show(final BA ba, String Mime, String Title) {
            if (this.eventName == null) {
                throw new RuntimeException("ContentChooser not initialized.");
            }
            Intent in = new Intent("android.intent.action.GET_CONTENT");
            in.setType(Mime);
            in.addCategory("android.intent.category.OPENABLE");
            Intent in2 = Intent.createChooser(in, Title);
            this.ion = new IOnActivityResult() {
                public void ResultArrived(int resultCode, Intent intent) {
                    String Dir = null;
                    String File = null;
                    if (!(resultCode != -1 || intent == null || intent.getData() == null)) {
                        try {
                            Uri uri = intent.getData();
                            String scheme = uri.getScheme();
                            if ("file".equals(scheme)) {
                                Dir = "";
                                File = uri.getPath();
                            } else if ("content".equals(scheme)) {
                                Dir = anywheresoftware.b4a.objects.streams.File.ContentDir;
                                File = uri.toString();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    ContentChooser.this.ion = null;
                    if (Dir == null || File == null) {
                        ba.raiseEvent(ContentChooser.this, String.valueOf(ContentChooser.this.eventName) + "_result", false, "", "");
                        return;
                    }
                    ba.raiseEvent(ContentChooser.this, String.valueOf(ContentChooser.this.eventName) + "_result", true, Dir, File);
                }
            };
            ba.startActivityForResult(this.ion, in2);
        }
    }

    @BA.ShortName("VoiceRecognition")
    public static class VoiceRecognition {
        /* access modifiers changed from: private */
        public String eventName;
        /* access modifiers changed from: private */
        public IOnActivityResult ion;
        private String language;
        private String prompt;

        public void Initialize(String EventName) {
            this.eventName = EventName.toLowerCase(BA.cul);
        }

        public void setPrompt(String value) {
            this.prompt = value;
        }

        public void setLanguage(String value) {
            this.language = value;
        }

        public boolean IsSupported() {
            return BA.applicationContext.getPackageManager().queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0).size() > 0;
        }

        public void Listen(final BA ba) {
            if (this.eventName == null) {
                throw new RuntimeException("VoiceRecognition was not initialized.");
            }
            Intent i = new Intent("android.speech.action.RECOGNIZE_SPEECH");
            if (this.prompt != null && this.prompt.length() > 0) {
                i.putExtra("android.speech.extra.PROMPT", this.prompt);
            }
            if (this.language != null && this.language.length() > 0) {
                i.putExtra("android.speech.extra.LANGUAGE", this.language);
            }
            this.ion = new IOnActivityResult() {
                public void ResultArrived(int resultCode, Intent intent) {
                    List list = new List();
                    if (resultCode == -1) {
                        ArrayList<String> t = intent.getStringArrayListExtra("android.speech.extra.RESULTS");
                        if (t.size() > 0) {
                            list.setObject(t);
                        }
                    }
                    VoiceRecognition.this.ion = null;
                    ba.raiseEvent(VoiceRecognition.this, String.valueOf(VoiceRecognition.this.eventName) + "_result", Boolean.valueOf(list.IsInitialized()), list);
                }
            };
            ba.startActivityForResult(this.ion, i);
        }
    }

    @BA.ShortName("PhoneOrientation")
    public static class PhoneOrientation {
        private SensorEventListener listener;

        public void StartListening(final BA ba, String EventName) {
            SensorManager sm = (SensorManager) ba.context.getSystemService("sensor");
            final String s = String.valueOf(EventName.toLowerCase(BA.cul)) + "_orientationchanged";
            this.listener = new SensorEventListener() {
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }

                public void onSensorChanged(SensorEvent event) {
                    ba.raiseEvent(this, s, Float.valueOf(event.values[0]), Float.valueOf(event.values[1]), Float.valueOf(event.values[2]));
                }
            };
            sm.registerListener(this.listener, sm.getDefaultSensor(3), 3);
        }

        public void StopListening(BA ba) {
            if (this.listener != null) {
                ((SensorManager) ba.context.getSystemService("sensor")).unregisterListener(this.listener);
            }
        }
    }

    @BA.ShortName("PhoneAccelerometer")
    public static class PhoneAccelerometer {
        private SensorEventListener listener;

        public void StartListening(final BA ba, String EventName) {
            SensorManager sm = (SensorManager) ba.context.getSystemService("sensor");
            final String s = String.valueOf(EventName.toLowerCase(BA.cul)) + "_accelerometerchanged";
            this.listener = new SensorEventListener() {
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }

                public void onSensorChanged(SensorEvent event) {
                    ba.raiseEvent(this, s, Float.valueOf(event.values[0]), Float.valueOf(event.values[1]), Float.valueOf(event.values[2]));
                }
            };
            sm.registerListener(this.listener, sm.getDefaultSensor(1), 3);
        }

        public void StopListening(BA ba) {
            if (this.listener != null) {
                ((SensorManager) ba.context.getSystemService("sensor")).unregisterListener(this.listener);
            }
        }
    }

    @BA.ShortName("PhoneSensors")
    public static class PhoneSensors {
        public static int TYPE_ACCELEROMETER = 1;
        public static int TYPE_GYROSCOPE = 4;
        public static int TYPE_LIGHT = 5;
        public static int TYPE_MAGNETIC_FIELD = 2;
        public static int TYPE_ORIENTATION = 3;
        public static int TYPE_PRESSURE = 6;
        public static int TYPE_PROXIMITY = 8;
        public static int TYPE_TEMPERATURE = 7;
        private int currentType;
        private SensorEventListener listener;
        private int sensorDelay;

        public void Initialize(int SensorType) {
            Initialize2(SensorType, 3);
        }

        public void Initialize2(int SensorType, int SensorDelay) {
            this.currentType = SensorType;
            this.sensorDelay = SensorDelay;
        }

        public boolean StartListening(final BA ba, String EventName) {
            SensorManager sm = (SensorManager) BA.applicationContext.getSystemService("sensor");
            final String s = String.valueOf(EventName.toLowerCase(BA.cul)) + "_sensorchanged";
            this.listener = new SensorEventListener() {
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }

                public void onSensorChanged(SensorEvent event) {
                    ba.raiseEvent(PhoneSensors.this, s, event.values);
                }
            };
            return sm.registerListener(this.listener, sm.getDefaultSensor(this.currentType), this.sensorDelay);
        }

        public void StopListening(BA ba) {
            if (this.listener != null) {
                ((SensorManager) ba.context.getSystemService("sensor")).unregisterListener(this.listener);
            }
        }

        /* Debug info: failed to restart local var, previous not found, register: 3 */
        public float getMaxValue() {
            java.util.List<Sensor> l = ((SensorManager) BA.applicationContext.getSystemService("sensor")).getSensorList(this.currentType);
            if (l == null || l.size() == 0) {
                return -1.0f;
            }
            return l.get(0).getMaximumRange();
        }
    }
}
