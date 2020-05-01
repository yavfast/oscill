package oscilldroid.inel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.B4AMenuItem;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.Msgbox;
import anywheresoftware.b4a.keywords.Bit;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.keywords.constants.KeyCodes;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.CompoundButtonWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.ListViewWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import anywheresoftware.b4a.phone.Phone;
import com.hoho.android.usbserial.driver.FtdiSerialDriver;
import com.hoho.android.usbserial.driver.UsbId;
import de.amberhome.AHLocale;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class main extends Activity implements B4AActivity {
    public static int _barpos = 0;
    public static bluetooth _bt = null;
    public static int[] _t_cur_pos = null;
    public static usb _usb1 = null;
    public static int _vol_but = 0;
    static boolean afterFirstLayout = false;
    public static final boolean fullScreen = true;
    public static final boolean includeTitle = false;
    static boolean isFirst = true;
    public static main mostCurrent;
    public static WeakReference<Activity> previousOne;
    public static BA processBA;
    private static boolean processGlobalsRun = false;
    public Common __c = null;
    public aboutdlg _ab_dlg = null;
    ActivityWrapper _activity;
    public colorsdlg _clr_dlg = null;
    public converter _converter = null;
    public files _files = null;
    public graphpan _gp = null;
    public LabelWrapper _lab_auto = null;
    public LabelWrapper _lab_menu = null;
    public LabelWrapper _lab_mes = null;
    public LabelWrapper _lab_start = null;
    public LabelWrapper _lab_t_info = null;
    public LabelWrapper _lab_vol_info = null;
    public PanelWrapper _menupan = null;
    public oscill _osc = null;
    public CompoundButtonWrapper.RadioButtonWrapper _r_osc = null;
    public CompoundButtonWrapper.RadioButtonWrapper _r_time = null;
    public dawnsidebar _sbar = null;
    public PanelWrapper _startpan = null;
    public settingdlg _stg_dlg = null;
    public CanvasWrapper.BitmapWrapper _tbmp = null;
    BA activityBA;
    BALayout layout;
    ArrayList<B4AMenuItem> menuItems;
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;

    public void onCreate(Bundle bundle) {
        Activity activity;
        super.onCreate(bundle);
        if (isFirst) {
            processBA = new BA(getApplicationContext(), (BALayout) null, (BA) null, "oscilldroid.inel", "oscilldroid.inel.main");
            processBA.loadHtSubs(getClass());
            BALayout.setDeviceScale(getApplicationContext().getResources().getDisplayMetrics().density);
        } else if (!(previousOne == null || (activity = (Activity) previousOne.get()) == null || activity == this)) {
            BA.LogInfo("Killing previous instance (main).");
            activity.finish();
        }
        getWindow().requestFeature(1);
        getWindow().setFlags(1024, 1024);
        mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
        this.layout = new BALayout(this);
        setContentView(this.layout);
        afterFirstLayout = false;
        BA.handler.postDelayed(new WaitForLayout(), 5);
    }

    private static class WaitForLayout implements Runnable {
        private WaitForLayout() {
        }

        public void run() {
            if (main.afterFirstLayout || main.mostCurrent == null) {
                return;
            }
            if (main.mostCurrent.layout.getWidth() == 0) {
                BA.handler.postDelayed(this, 5);
                return;
            }
            main.mostCurrent.layout.getLayoutParams().height = main.mostCurrent.layout.getHeight();
            main.mostCurrent.layout.getLayoutParams().width = main.mostCurrent.layout.getWidth();
            main.afterFirstLayout = true;
            main.mostCurrent.afterFirstLayout();
        }
    }

    /* access modifiers changed from: private */
    public void afterFirstLayout() {
        if (this == mostCurrent) {
            this.activityBA = new BA(this, this.layout, processBA, "oscilldroid.inel", "oscilldroid.inel.main");
            processBA.sharedProcessBA.activityBA = new WeakReference<>(this.activityBA);
            ViewWrapper.lastId = 0;
            this._activity = new ActivityWrapper(this.activityBA, "activity");
            Msgbox.isDismissing = false;
            initializeProcessGlobals();
            initializeGlobals();
            BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
            processBA.raiseEvent2((Object) null, true, "activity_create", false, Boolean.valueOf(isFirst));
            isFirst = false;
            if (this == mostCurrent) {
                processBA.setActivityPaused(false);
                BA.LogInfo("** Activity (main) Resume **");
                processBA.raiseEvent((Object) null, "activity_resume", new Object[0]);
                if (Build.VERSION.SDK_INT >= 11) {
                    try {
                        Activity.class.getMethod("invalidateOptionsMenu", new Class[0]).invoke(this, (Object[]) null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void addMenuItem(B4AMenuItem b4AMenuItem) {
        if (this.menuItems == null) {
            this.menuItems = new ArrayList<>();
        }
        this.menuItems.add(b4AMenuItem);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (this.menuItems == null) {
            return false;
        }
        Iterator<B4AMenuItem> it = this.menuItems.iterator();
        while (it.hasNext()) {
            B4AMenuItem next = it.next();
            MenuItem add = menu.add(next.title);
            if (next.drawable != null) {
                add.setIcon(next.drawable);
            }
            if (Build.VERSION.SDK_INT >= 11) {
                try {
                    if (next.addToBar) {
                        MenuItem.class.getMethod("setShowAsAction", new Class[]{Integer.TYPE}).invoke(add, new Object[]{1});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            add.setOnMenuItemClickListener(new B4AMenuItemsClickListener(next.eventName.toLowerCase(BA.cul)));
        }
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        processBA.raiseEvent2((Object) null, true, "activity_windowfocuschanged", false, z);
    }

    private class B4AMenuItemsClickListener implements MenuItem.OnMenuItemClickListener {
        private final String eventName;

        public B4AMenuItemsClickListener(String str) {
            this.eventName = str;
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            main.processBA.raiseEvent(menuItem.getTitle(), this.eventName + "_click", new Object[0]);
            return true;
        }
    }

    public static Class<?> getObject() {
        return main.class;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.onKeySubExist == null) {
            this.onKeySubExist = processBA.subExists("activity_keypress");
        }
        if (this.onKeySubExist) {
            Boolean bool = (Boolean) processBA.raiseEvent2(this._activity, false, "activity_keypress", false, i);
            if (bool == null || bool) {
                return true;
            }
            if (i == 4) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.onKeyUpSubExist == null) {
            this.onKeyUpSubExist = processBA.subExists("activity_keyup");
        }
        if (this.onKeyUpSubExist) {
            Boolean bool = (Boolean) processBA.raiseEvent2(this._activity, false, "activity_keyup", false, i);
            if (bool == null || bool) {
                return true;
            }
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    public void onPause() {
        super.onPause();
        if (this._activity != null) {
            Msgbox.dismiss(true);
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + this.activityBA.activity.isFinishing() + " **");
            processBA.raiseEvent2(this._activity, true, "activity_pause", false, this.activityBA.activity.isFinishing());
            processBA.setActivityPaused(true);
            mostCurrent = null;
            if (!this.activityBA.activity.isFinishing()) {
                previousOne = new WeakReference<>((Activity) this);
            }
            Msgbox.isDismissing = false;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        previousOne = null;
    }

    public void onResume() {
        super.onResume();
        mostCurrent = this;
        Msgbox.isDismissing = false;
        if (this.activityBA != null) {
            BA.handler.post(new ResumeMessage(mostCurrent));
        }
    }

    private static class ResumeMessage implements Runnable {
        private final WeakReference<Activity> activity;

        public ResumeMessage(Activity activity2) {
            this.activity = new WeakReference<>(activity2);
        }

        public void run() {
            if (main.mostCurrent != null && main.mostCurrent == this.activity.get()) {
                main.processBA.setActivityPaused(false);
                BA.LogInfo("** Activity (main) Resume **");
                main.processBA.raiseEvent(main.mostCurrent._activity, "activity_resume", (Object[]) null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        processBA.onActivityResult(i, i2, intent);
    }

    private static void initializeGlobals() {
        processBA.raiseEvent2((Object) null, true, "globals", false, (Object[]) null);
    }

    public static String _aboutclose() throws Exception {
        if (!mostCurrent._ab_dlg.IsInitialized() || !mostCurrent._ab_dlg._about_pan.IsInitialized()) {
            return "";
        }
        mostCurrent._ab_dlg._about_pan.RemoveView();
        return "";
    }

    public static String _activity_create(boolean z) throws Exception {
        int PerYToCurrent = Common.PerYToCurrent(10.0f, mostCurrent.activityBA);
        int PerYToCurrent2 = Common.PerYToCurrent(20.0f, mostCurrent.activityBA);
        PanelWrapper panelWrapper = new PanelWrapper();
        new Phone.PhoneWakeState();
        Phone.PhoneWakeState.KeepAlive(processBA, true);
        Phone.PhoneWakeState.PartialLock(processBA);
        _bt._initialize(processBA, getObject(), "onBT_Connect", "on_RX");
        _usb1._initialize(processBA, getObject(), "on_RX");
        mostCurrent._gp._initialize(mostCurrent.activityBA, (PanelWrapper) AbsObjectWrapper.ConvertToWrapper(new PanelWrapper(), (ViewGroup) mostCurrent._activity.getObject()), 0, PerYToCurrent, Common.PerXToCurrent(100.0f, mostCurrent.activityBA), (Common.PerYToCurrent(100.0f, mostCurrent.activityBA) - PerYToCurrent) - PerYToCurrent2);
        mostCurrent._gp._regevents(getObject(), "onCur", "onCenter");
        mostCurrent._gp._resize(332, 255, BA.NumberToString(32), BA.NumberToString(32));
        mostCurrent._gp._cursorused[0] = true;
        mostCurrent._gp._cursorused[1] = true;
        mostCurrent._gp._cursorused[2] = true;
        mostCurrent._gp._net = 1;
        mostCurrent._lab_menu.Initialize(mostCurrent.activityBA, "onStartPan");
        mostCurrent._lab_menu.setTag(0);
        mostCurrent._activity.AddView((View) mostCurrent._lab_menu.getObject(), 0, 0, Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.PerYToCurrent(10.0f, mostCurrent.activityBA));
        CanvasWrapper.BitmapWrapper bitmapWrapper = mostCurrent._tbmp;
        bitmapWrapper.Initialize(File.getDirAssets(), "menu.png");
        mostCurrent._lab_menu.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        int width = mostCurrent._lab_menu.getWidth();
        mostCurrent._sbar._initialize(mostCurrent.activityBA, (PanelWrapper) AbsObjectWrapper.ConvertToWrapper(new PanelWrapper(), (ViewGroup) mostCurrent._activity.getObject()), width, 0, Common.PerXToCurrent(100.0f, mostCurrent.activityBA) - width, Common.PerYToCurrent(10.0f, mostCurrent.activityBA));
        mostCurrent._sbar._barinit(getObject(), "onSBar", 6, Common.DipToCurrent(200), 1.5f);
        CanvasWrapper.BitmapWrapper bitmapWrapper2 = mostCurrent._tbmp;
        bitmapWrapper2.Initialize(File.getDirAssets(), "panmenu.png");
        mostCurrent._sbar._barpan.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        mostCurrent._startpan.Initialize(mostCurrent.activityBA, "");
        mostCurrent._activity.AddView((View) mostCurrent._startpan.getObject(), 0, mostCurrent._gp._underpan.getHeight() + mostCurrent._gp._underpan.getTop(), Common.PerXToCurrent(100.0f, mostCurrent.activityBA), Common.PerYToCurrent(100.0f, mostCurrent.activityBA) - (mostCurrent._gp._underpan.getTop() + mostCurrent._gp._underpan.getHeight()));
        CanvasWrapper.BitmapWrapper bitmapWrapper3 = mostCurrent._tbmp;
        bitmapWrapper3.Initialize(File.getDirAssets(), "panmenu.png");
        mostCurrent._startpan.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        mostCurrent._lab_mes.Initialize(mostCurrent.activityBA, "onStartPan");
        mostCurrent._lab_mes.setTag(1);
        mostCurrent._startpan.AddView((View) mostCurrent._lab_mes.getObject(), 0, 0, Common.PerXToCurrent(35.0f, mostCurrent.activityBA), (int) (((double) mostCurrent._startpan.getHeight()) / 2.0d));
        CanvasWrapper.BitmapWrapper bitmapWrapper4 = mostCurrent._tbmp;
        bitmapWrapper4.Initialize(File.getDirAssets(), "panmenu.png");
        mostCurrent._lab_mes.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        mostCurrent._lab_mes.setText(_messages(0));
        LabelWrapper labelWrapper = mostCurrent._lab_mes;
        labelWrapper.setGravity(17);
        int height = mostCurrent._lab_mes.getHeight();
        panelWrapper.Initialize(mostCurrent.activityBA, "");
        mostCurrent._startpan.AddView((View) panelWrapper.getObject(), 0, height, Common.PerXToCurrent(35.0f, mostCurrent.activityBA), (int) (((double) mostCurrent._startpan.getHeight()) / 2.0d));
        CanvasWrapper.BitmapWrapper bitmapWrapper5 = mostCurrent._tbmp;
        bitmapWrapper5.Initialize(File.getDirAssets(), "panmenu.png");
        panelWrapper.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        LabelWrapper[] labelWrapperArr = new LabelWrapper[4];
        int length = labelWrapperArr.length;
        for (int i = 0; i < length; i++) {
            labelWrapperArr[i] = new LabelWrapper();
        }
        int width2 = (int) (((double) (panelWrapper.getWidth() - Common.DipToCurrent(34))) / 4.0d);
        int DipToCurrent = Common.DipToCurrent(2);
        labelWrapperArr[0].Initialize(mostCurrent.activityBA, "lb_step");
        labelWrapperArr[0].setTag(0);
        CanvasWrapper.BitmapWrapper bitmapWrapper6 = mostCurrent._tbmp;
        bitmapWrapper6.Initialize(File.getDirAssets(), "minus.png");
        labelWrapperArr[0].SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        panelWrapper.AddView((View) labelWrapperArr[0].getObject(), DipToCurrent, Common.DipToCurrent(2), width2, panelWrapper.getHeight() - Common.DipToCurrent(4));
        int width3 = DipToCurrent + labelWrapperArr[0].getWidth();
        labelWrapperArr[1].Initialize(mostCurrent.activityBA, "");
        LabelWrapper labelWrapper2 = labelWrapperArr[1];
        labelWrapper2.setGravity(17);
        LabelWrapper labelWrapper3 = labelWrapperArr[1];
        labelWrapper3.setColor(Colors.Black);
        LabelWrapper labelWrapper4 = labelWrapperArr[1];
        labelWrapper4.setTextColor(Colors.Green);
        labelWrapperArr[1].setText("T");
        panelWrapper.AddView((View) labelWrapperArr[1].getObject(), width3, Common.DipToCurrent(2), Common.DipToCurrent(10), panelWrapper.getHeight() - Common.DipToCurrent(4));
        int width4 = width3 + labelWrapperArr[1].getWidth();
        labelWrapperArr[1].Initialize(mostCurrent.activityBA, "lb_step");
        labelWrapperArr[1].setTag(1);
        CanvasWrapper.BitmapWrapper bitmapWrapper7 = mostCurrent._tbmp;
        bitmapWrapper7.Initialize(File.getDirAssets(), "plus.png");
        labelWrapperArr[1].SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        panelWrapper.AddView((View) labelWrapperArr[1].getObject(), width4, Common.DipToCurrent(2), width2, panelWrapper.getHeight() - Common.DipToCurrent(4));
        int width5 = labelWrapperArr[1].getWidth() + width4 + Common.DipToCurrent(10);
        labelWrapperArr[2].Initialize(mostCurrent.activityBA, "lb_step");
        labelWrapperArr[2].setTag(2);
        CanvasWrapper.BitmapWrapper bitmapWrapper8 = mostCurrent._tbmp;
        bitmapWrapper8.Initialize(File.getDirAssets(), "minus.png");
        labelWrapperArr[2].SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        panelWrapper.AddView((View) labelWrapperArr[2].getObject(), width5, Common.DipToCurrent(2), width2, panelWrapper.getHeight() - Common.DipToCurrent(4));
        int width6 = width5 + labelWrapperArr[2].getWidth();
        labelWrapperArr[3].Initialize(mostCurrent.activityBA, "");
        LabelWrapper labelWrapper5 = labelWrapperArr[3];
        labelWrapper5.setGravity(17);
        LabelWrapper labelWrapper6 = labelWrapperArr[3];
        labelWrapper6.setColor(Colors.Black);
        LabelWrapper labelWrapper7 = labelWrapperArr[3];
        labelWrapper7.setTextColor(Colors.Green);
        labelWrapperArr[3].setText("V");
        panelWrapper.AddView((View) labelWrapperArr[3].getObject(), width6, Common.DipToCurrent(2), Common.DipToCurrent(10), panelWrapper.getHeight() - Common.DipToCurrent(4));
        int width7 = width6 + labelWrapperArr[3].getWidth();
        labelWrapperArr[3].Initialize(mostCurrent.activityBA, "lb_step");
        labelWrapperArr[3].setTag(3);
        CanvasWrapper.BitmapWrapper bitmapWrapper9 = mostCurrent._tbmp;
        bitmapWrapper9.Initialize(File.getDirAssets(), "plus.png");
        labelWrapperArr[3].SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        panelWrapper.AddView((View) labelWrapperArr[3].getObject(), width7, Common.DipToCurrent(2), width2, panelWrapper.getHeight() - Common.DipToCurrent(4));
        int width8 = 0 + mostCurrent._lab_mes.getWidth();
        panelWrapper.Initialize(mostCurrent.activityBA, "");
        mostCurrent._startpan.AddView((View) panelWrapper.getObject(), width8, 0, Common.DipToCurrent(50), mostCurrent._startpan.getHeight());
        CanvasWrapper.BitmapWrapper bitmapWrapper10 = mostCurrent._tbmp;
        bitmapWrapper10.Initialize(File.getDirAssets(), "panmenu.png");
        panelWrapper.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        mostCurrent._r_osc.Initialize(mostCurrent.activityBA, "RadBut");
        CompoundButtonWrapper.RadioButtonWrapper radioButtonWrapper = mostCurrent._r_osc;
        radioButtonWrapper.setGravity(17);
        CompoundButtonWrapper.RadioButtonWrapper radioButtonWrapper2 = mostCurrent._r_osc;
        radioButtonWrapper2.setTextColor(Colors.Green);
        mostCurrent._r_osc.setText("O");
        mostCurrent._r_osc.setTag(0);
        mostCurrent._r_osc.setChecked(true);
        panelWrapper.AddView((View) mostCurrent._r_osc.getObject(), 0, 0, panelWrapper.getWidth(), (int) (((double) panelWrapper.getHeight()) / 3.0d));
        mostCurrent._r_time.Initialize(mostCurrent.activityBA, "RadBut");
        CompoundButtonWrapper.RadioButtonWrapper radioButtonWrapper3 = mostCurrent._r_time;
        radioButtonWrapper3.setGravity(17);
        CompoundButtonWrapper.RadioButtonWrapper radioButtonWrapper4 = mostCurrent._r_time;
        radioButtonWrapper4.setTextColor(Colors.Green);
        mostCurrent._r_time.setText("T");
        mostCurrent._r_time.setTag(1);
        panelWrapper.AddView((View) mostCurrent._r_time.getObject(), 0, mostCurrent._r_osc.getHeight(), panelWrapper.getWidth(), (int) (((double) panelWrapper.getHeight()) / 3.0d));
        int width9 = width8 + panelWrapper.getWidth();
        mostCurrent._lab_vol_info.Initialize(mostCurrent.activityBA, "onStartPan");
        mostCurrent._lab_vol_info.setTag(10);
        mostCurrent._startpan.AddView((View) mostCurrent._lab_vol_info.getObject(), width9, 0, Common.PerXToCurrent(20.0f, mostCurrent.activityBA), mostCurrent._startpan.getHeight());
        CanvasWrapper.BitmapWrapper bitmapWrapper11 = mostCurrent._tbmp;
        bitmapWrapper11.Initialize(File.getDirAssets(), "panmenu.png");
        mostCurrent._lab_vol_info.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        LabelWrapper labelWrapper8 = mostCurrent._lab_vol_info;
        labelWrapper8.setGravity(17);
        int width10 = width9 + mostCurrent._lab_vol_info.getWidth();
        mostCurrent._lab_t_info.Initialize(mostCurrent.activityBA, "onStartPan");
        mostCurrent._lab_t_info.setTag(11);
        mostCurrent._startpan.AddView((View) mostCurrent._lab_t_info.getObject(), width10, 0, Common.PerXToCurrent(25.0f, mostCurrent.activityBA), mostCurrent._startpan.getHeight());
        CanvasWrapper.BitmapWrapper bitmapWrapper12 = mostCurrent._tbmp;
        bitmapWrapper12.Initialize(File.getDirAssets(), "panmenu.png");
        mostCurrent._lab_t_info.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        LabelWrapper labelWrapper9 = mostCurrent._lab_t_info;
        labelWrapper9.setGravity(17);
        mostCurrent._lab_t_info.setText("");
        int width11 = width10 + mostCurrent._lab_t_info.getWidth();
        mostCurrent._lab_auto.Initialize(mostCurrent.activityBA, "onStartPan");
        mostCurrent._lab_auto.setTag(2);
        mostCurrent._startpan.AddView((View) mostCurrent._lab_auto.getObject(), width11, 0, mostCurrent._startpan.getWidth() - width11, (int) (((double) mostCurrent._startpan.getHeight()) / 2.0d));
        CanvasWrapper.BitmapWrapper bitmapWrapper13 = mostCurrent._tbmp;
        bitmapWrapper13.Initialize(File.getDirAssets(), "recton.png");
        mostCurrent._lab_auto.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        LabelWrapper labelWrapper10 = mostCurrent._lab_auto;
        labelWrapper10.setGravity(17);
        mostCurrent._lab_auto.setText("Auto");
        int height2 = mostCurrent._lab_auto.getHeight();
        mostCurrent._lab_start.Initialize(mostCurrent.activityBA, "onStartPan");
        mostCurrent._lab_start.setTag(3);
        mostCurrent._startpan.AddView((View) mostCurrent._lab_start.getObject(), width11, height2, mostCurrent._startpan.getWidth() - width11, (int) (((double) mostCurrent._startpan.getHeight()) / 2.0d));
        CanvasWrapper.BitmapWrapper bitmapWrapper14 = mostCurrent._tbmp;
        bitmapWrapper14.Initialize(File.getDirAssets(), "recton.png");
        mostCurrent._lab_start.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        LabelWrapper labelWrapper11 = mostCurrent._lab_start;
        labelWrapper11.setGravity(17);
        mostCurrent._lab_start.setText("Start");
        mostCurrent._osc._initialize(mostCurrent.activityBA, getObject(), "lvMenu", "onOsc_RX", 1.0f);
        mostCurrent._osc._lst_str[4].getTwoLinesAndBitmap().ImageView.setWidth(mostCurrent._sbar._barlab[4].getWidth() - Common.DipToCurrent(10));
        try {
            _recolors();
        } catch (Throwable e) {}
        try {
            _reconfig();
        } catch (Throwable e) {}
        mostCurrent._osc._on(false);
        mostCurrent._lab_start.setText("Start");
        if (_bt._isconnected) {
            _bt._disconnect();
        }
        mostCurrent._osc._speed = 9600;
        if (_usb1._isconnected) {
            _usb1._disconnect();
        }
        if (_usb1._connect(mostCurrent._osc._speed)) {
            _setmessages(_usb1._message, 0);
            _onbt_connect(true);
        }
        if (_usb1._isconnected) {
            return "";
        }
        bluetooth bluetooth = _bt;
        if (!bluetooth._connect(files._datafile(mostCurrent.activityBA, "osc.cfg", "#bt_dev:", "", false))) {
            return "";
        }
        _setmessages(_bt._message, 0);
        return "";
    }

    public static String _activity_destroy() throws Exception {
        new Phone.PhoneWakeState();
        Phone.PhoneWakeState.ReleaseKeepAlive();
        Phone.PhoneWakeState.ReleasePartialLock();
        _bt._disconnect();
        _usb1._disconnect();
        mostCurrent._activity.Finish();
        Common.ExitApplication();
        return "";
    }

    public static boolean _activity_keypress(int i) throws Exception {
        Integer valueOf = Integer.valueOf(i);
        switch (BA.switchObjectToInt(valueOf, 4, 24, 25)) {
            case 0:
                _barsclose();
                _colorclose();
                _settingclose();
                _aboutclose();
                return true;
            case 1:
                if (mostCurrent._osc._lst_pos[_vol_but] < mostCurrent._osc._lst_str[_vol_but].getSize() - 1) {
                    mostCurrent._osc._lst_pos[_vol_but] = mostCurrent._osc._lst_pos[_vol_but] + 1;
                    _lvmenu(_vol_but);
                }
                return true;
            case 2:
                if (mostCurrent._osc._lst_pos[_vol_but] > 0) {
                    mostCurrent._osc._lst_pos[_vol_but] = mostCurrent._osc._lst_pos[_vol_but] - 1;
                    _lvmenu(_vol_but);
                }
                return true;
            default:
                return true;
        }
    }

    public static String _activity_pause(boolean z) throws Exception {
        _activity_destroy();
        return "";
    }

    public static String _activity_resume() throws Exception {
        return "";
    }

    public static String _barsclose() throws Exception {
        if (mostCurrent._menupan.IsInitialized()) {
            mostCurrent._menupan.setVisible(false);
            mostCurrent._menupan.RemoveView();
        }
        mostCurrent._sbar._close();
        return "";
    }

    public static String _colorclose() throws Exception {
        if (!mostCurrent._clr_dlg.IsInitialized() || !mostCurrent._clr_dlg._colors_pan.IsInitialized()) {
            return "";
        }
        mostCurrent._clr_dlg._colors_pan.RemoveView();
        _recolors();
        return "";
    }

    public static String _createmenu() throws Exception {
        ListViewWrapper listViewWrapper = new ListViewWrapper();
        if (!mostCurrent._menupan.IsInitialized() || !mostCurrent._menupan.getVisible()) {
            _barsclose();
            mostCurrent._menupan.Initialize(mostCurrent.activityBA, "");
            CanvasWrapper.BitmapWrapper bitmapWrapper = mostCurrent._tbmp;
            bitmapWrapper.Initialize(File.getDirAssets(), "left_menu.png");
            mostCurrent._menupan.SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
            mostCurrent._activity.AddView((View) mostCurrent._menupan.getObject(), 0, mostCurrent._gp._underpan.getTop(), Common.DipToCurrent(200), mostCurrent._gp._underpan.getHeight());
            listViewWrapper.Initialize(mostCurrent.activityBA, "onLv_menu");
            CanvasWrapper.BitmapWrapper bitmapWrapper2 = mostCurrent._tbmp;
            bitmapWrapper2.Initialize(File.getDirAssets(), "bluetooth.png");
            listViewWrapper.AddTwoLinesAndBitmap("BT Connect", "", (Bitmap) mostCurrent._tbmp.getObject());
            CanvasWrapper.BitmapWrapper bitmapWrapper3 = mostCurrent._tbmp;
            bitmapWrapper3.Initialize(File.getDirAssets(), "usb.png");
            listViewWrapper.AddTwoLinesAndBitmap("USB Connect", "", (Bitmap) mostCurrent._tbmp.getObject());
            CanvasWrapper.BitmapWrapper bitmapWrapper4 = mostCurrent._tbmp;
            bitmapWrapper4.Initialize(File.getDirAssets(), "color.png");
            listViewWrapper.AddTwoLinesAndBitmap("Colors", "", (Bitmap) mostCurrent._tbmp.getObject());
            CanvasWrapper.BitmapWrapper bitmapWrapper5 = mostCurrent._tbmp;
            bitmapWrapper5.Initialize(File.getDirAssets(), "setting.png");
            listViewWrapper.AddTwoLinesAndBitmap("Setting", "", (Bitmap) mostCurrent._tbmp.getObject());
            CanvasWrapper.BitmapWrapper bitmapWrapper6 = mostCurrent._tbmp;
            bitmapWrapper6.Initialize(File.getDirAssets(), "exit.png");
            listViewWrapper.AddTwoLinesAndBitmap("Exit", "", (Bitmap) mostCurrent._tbmp.getObject());
            CanvasWrapper.BitmapWrapper bitmapWrapper7 = mostCurrent._tbmp;
            bitmapWrapper7.Initialize(File.getDirAssets(), "about.png");
            listViewWrapper.AddTwoLinesAndBitmap("About", "", (Bitmap) mostCurrent._tbmp.getObject());
            mostCurrent._menupan.AddView((View) listViewWrapper.getObject(), 0, Common.DipToCurrent(5), Common.DipToCurrent(200), mostCurrent._menupan.getHeight() - Common.DipToCurrent(10));
            return "";
        }
        _barsclose();
        return "";
    }

    public static String _getpretrigstr() throws Exception {
        return converter._timetostr((long) ((((double) converter._strtotime(mostCurrent._sbar._barlab[0].getText())) / ((double) mostCurrent._osc._divpix)) * ((double) mostCurrent._gp._cursor_pos[2])));
    }

    public static String _gettrigvolstr() throws Exception {
        return converter._inttovolt(mostCurrent._osc._vstep * ((float) (mostCurrent._gp._cursor_pos[0] - mostCurrent._gp._cursor_pos[1])));
    }

    public static void initializeProcessGlobals() {
        if (!processGlobalsRun) {
            processGlobalsRun = true;
            try {
                _process_globals();
                converter._process_globals();
                files._process_globals();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isAnyActivityVisible() {
        boolean z;
        if (mostCurrent != null) {
            z = true;
        } else {
            z = false;
        }
        return z | false;
    }

    public static String _globals() throws Exception {
        mostCurrent._osc = new oscill();
        mostCurrent._gp = new graphpan();
        mostCurrent._clr_dlg = new colorsdlg();
        mostCurrent._stg_dlg = new settingdlg();
        mostCurrent._ab_dlg = new aboutdlg();
        mostCurrent._sbar = new dawnsidebar();
        _barpos = 0;
        _barpos = 0;
        mostCurrent._startpan = new PanelWrapper();
        mostCurrent._lab_menu = new LabelWrapper();
        mostCurrent._lab_mes = new LabelWrapper();
        mostCurrent._lab_vol_info = new LabelWrapper();
        mostCurrent._lab_t_info = new LabelWrapper();
        mostCurrent._lab_auto = new LabelWrapper();
        mostCurrent._lab_start = new LabelWrapper();
        mostCurrent._menupan = new PanelWrapper();
        mostCurrent._tbmp = new CanvasWrapper.BitmapWrapper();
        _vol_but = 0;
        mostCurrent._r_osc = new CompoundButtonWrapper.RadioButtonWrapper();
        mostCurrent._r_time = new CompoundButtonWrapper.RadioButtonWrapper();
        _t_cur_pos = new int[3];
        return "";
    }

    public static String _lb_step_click() throws Exception {
        LabelWrapper labelWrapper = new LabelWrapper();
        labelWrapper.setObject((TextView) Common.Sender(mostCurrent.activityBA));
        switch (BA.switchObjectToInt(labelWrapper.getTag(), 0, 1, 2, 3)) {
            case 0:
                if (mostCurrent._osc._lst_pos[0] <= 0) {
                    return "";
                }
                mostCurrent._osc._lst_pos[0] = mostCurrent._osc._lst_pos[0] - 1;
                _lvmenu(0);
                return "";
            case 1:
                if (mostCurrent._osc._lst_pos[0] >= mostCurrent._osc._lst_str[0].getSize() - 1) {
                    return "";
                }
                mostCurrent._osc._lst_pos[0] = mostCurrent._osc._lst_pos[0] + 1;
                _lvmenu(0);
                return "";
            case 2:
                if (mostCurrent._osc._lst_pos[1] <= 0) {
                    return "";
                }
                mostCurrent._osc._lst_pos[1] = mostCurrent._osc._lst_pos[1] - 1;
                _lvmenu(1);
                return "";
            case 3:
                if (mostCurrent._osc._lst_pos[1] >= mostCurrent._osc._lst_str[1].getSize() - 1) {
                    return "";
                }
                mostCurrent._osc._lst_pos[1] = mostCurrent._osc._lst_pos[1] + 1;
                _lvmenu(1);
                return "";
            default:
                return "";
        }
    }

    public static String _lvmenu(int i) throws Exception {
        switch (i) {
            case 0:
                mostCurrent._sbar._barlab[0].setText(mostCurrent._osc._lst_str[0].GetItem(mostCurrent._osc._lst_pos[0]));
                files files = mostCurrent._files;
                files._datafile(mostCurrent.activityBA, "osc.cfg", "#Time:", BA.NumberToString(mostCurrent._osc._lst_pos[0]), true);
                if (mostCurrent._osc._connect_flg) {
                    mostCurrent._osc._txtimediv();
                    break;
                }
                break;
            case 1:
                mostCurrent._sbar._barlab[1].setText(mostCurrent._osc._lst_str[1].GetItem(mostCurrent._osc._lst_pos[1]));
                files files2 = mostCurrent._files;
                files2._datafile(mostCurrent.activityBA, "osc.cfg", "#Voltage:", BA.NumberToString(mostCurrent._osc._lst_pos[1]), true);
                if (mostCurrent._osc._connect_flg) {
                    mostCurrent._osc._txsens();
                    break;
                }
                break;
            case 2:
                mostCurrent._sbar._barlab[2].setText(mostCurrent._osc._lst_str[2].GetItem(mostCurrent._osc._lst_pos[2]));
                files files3 = mostCurrent._files;
                files3._datafile(mostCurrent.activityBA, "osc.cfg", "#input:", BA.NumberToString(mostCurrent._osc._lst_pos[2]), true);
                if (mostCurrent._osc._connect_flg) {
                    mostCurrent._osc._txinput();
                    break;
                }
                break;
            case 3:
                mostCurrent._sbar._barlab[3].setText(mostCurrent._osc._lst_str[3].GetItem(mostCurrent._osc._lst_pos[3]));
                files files4 = mostCurrent._files;
                files4._datafile(mostCurrent.activityBA, "osc.cfg", "#SampleMod:", BA.NumberToString(mostCurrent._osc._lst_pos[3]), true);
                if (mostCurrent._osc._connect_flg) {
                    mostCurrent._osc._txsamplemode();
                    break;
                }
                break;
            case 4:
                CanvasWrapper.BitmapWrapper bitmapWrapper = mostCurrent._tbmp;
                File file = Common.File;
                bitmapWrapper.Initialize(File.getDirAssets(), String.valueOf(mostCurrent._osc._lst_str[4].GetItem(mostCurrent._osc._lst_pos[4])));
                mostCurrent._sbar._barlab[4].SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
                files files5 = mostCurrent._files;
                files5._datafile(mostCurrent.activityBA, "osc.cfg", "#Sync:", BA.NumberToString(mostCurrent._osc._lst_pos[4]), true);
                if (mostCurrent._osc._connect_flg) {
                    mostCurrent._osc._txsync();
                    break;
                }
                break;
            case 5:
                mostCurrent._sbar._barlab[5].setText(mostCurrent._osc._lst_str[5].GetItem(mostCurrent._osc._lst_pos[5]));
                files files6 = mostCurrent._files;
                files6._datafile(mostCurrent.activityBA, "osc.cfg", "#TypeSync:", BA.NumberToString(mostCurrent._osc._lst_pos[5]), true);
                if (mostCurrent._osc._connect_flg) {
                    mostCurrent._osc._txtypesync();
                    break;
                }
                break;
        }
        mostCurrent._sbar._close();
        return "";
    }

    public static String _messages(int i) throws Exception {
        AHLocale aHLocale = new AHLocale();
        aHLocale.Initialize();
        if (aHLocale.getLanguage().equals("ru")) {
            switch (i) {
                case 0:
                    return "Добро пожаловать!";
            }
        } else {
            switch (i) {
                case 0:
                    return "Welcome!";
            }
        }
        return "";
    }

    public static String _on_rx(byte[] bArr) throws Exception {
        mostCurrent._osc._rx(bArr);
        return "";
    }

    public static String _onbt_connect(boolean z) throws Exception {
        if (z) {
            mostCurrent._osc._on(true);
            mostCurrent._osc.setSenden(true);
            mostCurrent._osc._txconnect();
            mostCurrent._osc._txdevinfo();
            mostCurrent._osc._txdelayedsweep();
            mostCurrent._osc._txalignsweep();
            mostCurrent._osc._txsamplmethod();
            mostCurrent._osc._txminnumpas_strob();
            mostCurrent._osc._txnumpas_avgpick();
            mostCurrent._osc._txtimediv();
            mostCurrent._osc._txtosinca();
            mostCurrent._osc._txtosincw();
            mostCurrent._osc._txsync();
            mostCurrent._osc._txinput();
            mostCurrent._osc._txsamplemode();
            mostCurrent._osc._txsens();
            mostCurrent._osc._txshift();
            mostCurrent._osc._txlevelsync();
            mostCurrent._osc._txtypesync();
            if (_usb1._isconnected) {
                oscill oscill = mostCurrent._osc;
                files files = mostCurrent._files;
                oscill._speed = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#usb_speed:", "", false));
                mostCurrent._osc._txchangespeed();
            }
        } else {
            _setmessages(_bt._message, 1);
        }
        return "";
    }

    public static String _oncenter(int i) throws Exception {
        switch (i) {
            case 0:
                _barsclose();
                return "";
            default:
                return "";
        }
    }

    public static String _oncur(int i) throws Exception {
        switch (i) {
            case 1:
                switch (BA.switchObjectToInt(mostCurrent._gp._curevt, 0, 1, 2, 3, 4, 5, 6, 7, 8)) {
                    case 0:
                        mostCurrent._gp._setcurtext(0, _gettrigvolstr());
                        if (!mostCurrent._r_osc.getChecked()) {
                            if (mostCurrent._r_time.getChecked()) {
                                _t_cur_pos[0] = mostCurrent._gp._cursor_pos[0];
                                break;
                            }
                        } else {
                            mostCurrent._osc._levelsync = mostCurrent._gp._cursor_pos[0];
                            files._datafile(mostCurrent.activityBA, "osc.cfg", "#levelSync:", BA.NumberToString(mostCurrent._osc._levelsync), true);
                            if (mostCurrent._osc._connect_flg) {
                                mostCurrent._osc._txlevelsync();
                                break;
                            }
                        }
                        break;
                    case 1:
                        mostCurrent._gp._setcurtext(0, _gettrigvolstr());
                        mostCurrent._gp._setcurtext(1, BA.NumberToString(mostCurrent._gp._cursor_pos[1]));
                        if (!mostCurrent._r_osc.getChecked()) {
                            if (mostCurrent._r_time.getChecked()) {
                                _t_cur_pos[1] = mostCurrent._gp._cursor_pos[1];
                                break;
                            }
                        } else {
                            mostCurrent._osc._shift = FtdiSerialDriver.USB_ENDPOINT_IN - mostCurrent._gp._cursor_pos[1];
                            files._datafile(mostCurrent.activityBA, "osc.cfg", "#Shift:", BA.NumberToString(mostCurrent._osc._shift), true);
                            if (mostCurrent._osc._connect_flg) {
                                mostCurrent._osc._txshift();
                                break;
                            }
                        }
                        break;
                    case 2:
                        mostCurrent._gp._setcurtext(2, _getpretrigstr());
                        if (!mostCurrent._r_osc.getChecked()) {
                            if (mostCurrent._r_time.getChecked()) {
                                _t_cur_pos[2] = mostCurrent._gp._cursor_pos[2];
                                break;
                            }
                        } else {
                            mostCurrent._osc._alignsweep = mostCurrent._gp._cursor_pos[2];
                            files._datafile(mostCurrent.activityBA, "osc.cfg", "#AlingSweep:", BA.NumberToString(mostCurrent._osc._alignsweep), true);
                            if (mostCurrent._osc._connect_flg) {
                                mostCurrent._osc._txalignsweep();
                                break;
                            }
                        }
                        break;
                    case 3:
                        if (mostCurrent._osc._lst_pos[1] > 0) {
                            mostCurrent._osc._lst_pos[1] = mostCurrent._osc._lst_pos[1] - 1;
                            _lvmenu(1);
                            break;
                        }
                        break;
                    case 4:
                        if (mostCurrent._osc._lst_pos[1] < mostCurrent._osc._lst_str[1].getSize() - 1) {
                            mostCurrent._osc._lst_pos[1] = mostCurrent._osc._lst_pos[1] + 1;
                            _lvmenu(1);
                            break;
                        }
                        break;
                    case 7:
                        if (mostCurrent._osc._lst_pos[0] < mostCurrent._osc._lst_str[0].getSize() - 1) {
                            mostCurrent._osc._lst_pos[0] = mostCurrent._osc._lst_pos[0] + 1;
                            _lvmenu(0);
                            break;
                        }
                        break;
                    case 8:
                        if (mostCurrent._osc._lst_pos[0] > 0) {
                            mostCurrent._osc._lst_pos[0] = mostCurrent._osc._lst_pos[0] - 1;
                            _lvmenu(0);
                            break;
                        }
                        break;
                }
            case 2:
                switch (BA.switchObjectToInt(mostCurrent._gp._curevt, 0, 1, 2)) {
                    case 0:
                        mostCurrent._gp._setcurtext(0, _gettrigvolstr());
                        break;
                    case 1:
                        mostCurrent._gp._setcurtext(0, _gettrigvolstr());
                        mostCurrent._gp._setcurtext(1, BA.NumberToString(mostCurrent._gp._cursor_pos[1]));
                        break;
                    case 2:
                        mostCurrent._gp._setcurtext(2, _getpretrigstr());
                        break;
                }
        }
        if (!mostCurrent._r_time.getChecked()) {
            mostCurrent._osc._getfreq(mostCurrent._gp._buff_data, mostCurrent._osc._alignsweep, mostCurrent._osc._sync_z, mostCurrent._osc._sync_rels);
        } else if (mostCurrent._osc._lst_pos[3] == 2) {
            oscill oscill = mostCurrent._osc;
            int[] iArr = mostCurrent._gp._buff_data;
            int i2 = mostCurrent._gp._cursor_pos[2];
            int ShiftLeft = Bit.ShiftLeft(mostCurrent._gp._cursor_pos[1], 8);
            oscill._getfreq(iArr, i2, ShiftLeft, Bit.ShiftLeft(mostCurrent._gp._cursor_pos[0] - mostCurrent._gp._cursor_pos[1], 8));
        } else {
            mostCurrent._osc._getfreq(mostCurrent._gp._buff_data, mostCurrent._gp._cursor_pos[2], mostCurrent._gp._cursor_pos[1], mostCurrent._gp._cursor_pos[0] - mostCurrent._gp._cursor_pos[1]);
        }
        mostCurrent._gp._data_size = mostCurrent._osc._sizesample;
        mostCurrent._lab_vol_info.setText(mostCurrent._osc._vinfo);
        mostCurrent._lab_t_info.setText(mostCurrent._osc._tinfo);
        return "";
    }

    public static String _onlv_menu_itemclick(int i, Object obj) throws Exception {
        switch (i) {
            case 0:
                mostCurrent._osc._on(false);
                mostCurrent._lab_start.setText("Start");
                if (_usb1._isconnected) {
                    _usb1._disconnect();
                }
                if (_bt._isconnected) {
                    _bt._disconnect();
                }
                String _dev_select = _bt._dev_select();
                if (!_dev_select.equals("")) {
                    files._datafile(mostCurrent.activityBA, "osc.cfg", "#bt_dev:", _dev_select, true);
                    if (!_bt._connect(_dev_select)) {
                        _setmessages(_bt._message, 1);
                        break;
                    } else {
                        _setmessages(_bt._message, 0);
                        break;
                    }
                } else {
                    _setmessages(_bt._message, 1);
                    return "";
                }
            case 1:
                mostCurrent._osc._on(false);
                mostCurrent._lab_start.setText("Start");
                if (_bt._isconnected) {
                    _bt._disconnect();
                }
                if (_usb1._isconnected) {
                    _usb1._disconnect();
                }
                int _getspeed = _usb1._getspeed(mostCurrent._osc._speed);
                if (_getspeed >= 0) {
                    files files2 = mostCurrent._files;
                    files._datafile(mostCurrent.activityBA, "osc.cfg", "#usb_speed:", BA.NumberToString(_getspeed), true);
                    if (!_usb1._connect(mostCurrent._osc._speed)) {
                        _setmessages(_usb1._message, 1);
                        break;
                    } else {
                        _setmessages(_usb1._message, 0);
                        _onbt_connect(true);
                        break;
                    }
                } else {
                    _setmessages(_usb1._message, 1);
                    return "";
                }
            case 2:
                mostCurrent._clr_dlg._initialize(mostCurrent.activityBA, mostCurrent._activity, getObject(), "ColorClose");
                break;
            case 3:
                mostCurrent._stg_dlg._initialize(mostCurrent.activityBA, mostCurrent._activity, getObject(), "SettingClose");
                break;
            case 4:
                _activity_destroy();
                break;
            case 5:
                mostCurrent._ab_dlg._initialize(mostCurrent.activityBA, mostCurrent._activity, getObject(), "AboutClose");
                break;
        }
        _barsclose();
        return "";
    }

    public static String _onosc_rx(int i) throws Exception {
        int i2;
        int i3;
        switch (i) {
            case 0:
                _setmessages(mostCurrent._osc._message, 0);
                break;
            case 1:
                _setmessages(mostCurrent._osc._message, 0);
                break;
            case 2:
                mostCurrent._sbar._barlab[2].setText(mostCurrent._osc._lst_str[2].GetItem(mostCurrent._osc._lst_pos[2]));
                break;
            case 3:
                int i4 = FtdiSerialDriver.USB_ENDPOINT_IN - mostCurrent._osc._shift;
                int i5 = mostCurrent._osc._levelsync;
                if (mostCurrent._osc._sizesample < 332) {
                    i3 = 166;
                } else {
                    i3 = 332;
                }
                mostCurrent._gp._resize(i3, 255, BA.NumberToString(mostCurrent._osc._divpix), BA.NumberToString(32));
                mostCurrent._gp._setcur(1, i4);
                mostCurrent._gp._setcur(0, i5);
                mostCurrent._osc._vstep = (float) (BA.ObjectToNumber(mostCurrent._osc._lst_data[1].Get(mostCurrent._osc._lst_pos[1])) / ((double) 32));
                mostCurrent._sbar._barlab[3].setText(mostCurrent._osc._lst_str[3].GetItem(mostCurrent._osc._lst_pos[3]));
                break;
            case 4:
                mostCurrent._gp._setcur(0, mostCurrent._osc._levelsync);
                break;
            case 5:
                mostCurrent._sbar._barlab[5].setText(mostCurrent._osc._lst_str[5].GetItem(mostCurrent._osc._lst_pos[5]));
                break;
            case 6:
                CanvasWrapper.BitmapWrapper bitmapWrapper = mostCurrent._tbmp;
                bitmapWrapper.Initialize(File.getDirAssets(), String.valueOf(mostCurrent._osc._lst_str[4].GetItem(mostCurrent._osc._lst_pos[4])));
                mostCurrent._sbar._barlab[4].SetBackgroundImage(mostCurrent._tbmp.getObject());
                break;
            case 7:
                mostCurrent._sbar._barlab[1].setText(mostCurrent._osc._lst_str[1].GetItem(mostCurrent._osc._lst_pos[1]));
                break;
            case 8:
                mostCurrent._gp._setcur(1, FtdiSerialDriver.USB_ENDPOINT_IN - mostCurrent._osc._shift);
                break;
            case 9:
                if (mostCurrent._osc._sizesample < 332) {
                    i2 = 166;
                } else {
                    i2 = 332;
                }
                mostCurrent._gp._resize(i2, 255, BA.NumberToString(mostCurrent._osc._divpix), BA.NumberToString(32));
                LabelWrapper labelWrapper = mostCurrent._sbar._barlab[0];
                labelWrapper.setText(converter._timetostr(mostCurrent._osc._time));
                _setmessages(mostCurrent._osc._message, 0);
                break;
            case 10:
                mostCurrent._gp._setcur(2, mostCurrent._osc._alignsweep);
                break;
            case 12:
                switch (BA.switchObjectToInt(mostCurrent._osc._lst_pos[3], 2, 3)) {
                    case 0:
                        if (mostCurrent._osc._lst_pos[0] > 3) {
                            mostCurrent._osc._fordraw_hires(mostCurrent._gp._buff_data);
                            if (mostCurrent._r_time.getChecked()) {
                                oscill oscill = mostCurrent._osc;
                                int[] iArr = mostCurrent._gp._buff_data;
                                int i6 = mostCurrent._gp._cursor_pos[2];
                                int ShiftLeft = Bit.ShiftLeft(mostCurrent._gp._cursor_pos[1], 8);
                                oscill._getfreq(iArr, i6, ShiftLeft, Bit.ShiftLeft(mostCurrent._gp._cursor_pos[0] - mostCurrent._gp._cursor_pos[1], 8));
                            } else {
                                mostCurrent._osc._getfreq(mostCurrent._gp._buff_data, mostCurrent._osc._alignsweep, mostCurrent._osc._sync_z, mostCurrent._osc._sync_rels);
                            }
                            mostCurrent._gp._data_size = mostCurrent._osc._sizesample;
                            mostCurrent._gp._draw_del = 256;
                            mostCurrent._gp._draw();
                            mostCurrent._lab_vol_info.setText(mostCurrent._osc._vinfo);
                            mostCurrent._lab_t_info.setText(mostCurrent._osc._tinfo);
                            return "";
                        }
                        break;
                    case 1:
                        if (mostCurrent._osc._lst_pos[0] > 5) {
                            mostCurrent._osc._fordraw_peak(mostCurrent._gp._buff_data);
                            if (mostCurrent._r_time.getChecked()) {
                                mostCurrent._osc._getfreq(mostCurrent._gp._buff_data, mostCurrent._gp._cursor_pos[2], mostCurrent._gp._cursor_pos[1], mostCurrent._gp._cursor_pos[0] - mostCurrent._gp._cursor_pos[1]);
                            } else {
                                mostCurrent._osc._getfreq(mostCurrent._gp._buff_data, mostCurrent._osc._alignsweep, mostCurrent._osc._sync_z, mostCurrent._osc._sync_rels);
                            }
                            mostCurrent._gp._data_size = mostCurrent._osc._sizesample;
                            mostCurrent._gp._drawpeak();
                            mostCurrent._lab_vol_info.setText(mostCurrent._osc._vinfo);
                            mostCurrent._lab_t_info.setText(mostCurrent._osc._tinfo);
                            return "";
                        }
                        break;
                }
                mostCurrent._osc._fordraw_sample(mostCurrent._gp._buff_data);
                if (mostCurrent._r_time.getChecked()) {
                    mostCurrent._osc._getfreq(mostCurrent._gp._buff_data, mostCurrent._gp._cursor_pos[2], mostCurrent._gp._cursor_pos[1], mostCurrent._gp._cursor_pos[0] - mostCurrent._gp._cursor_pos[1]);
                } else {
                    mostCurrent._osc._getfreq(mostCurrent._gp._buff_data, mostCurrent._osc._alignsweep, mostCurrent._osc._sync_z, mostCurrent._osc._sync_rels);
                }
                mostCurrent._gp._data_size = mostCurrent._osc._sizesample;
                mostCurrent._gp._draw_del = 1;
                mostCurrent._gp._draw();
                mostCurrent._lab_vol_info.setText(mostCurrent._osc._vinfo);
                mostCurrent._lab_t_info.setText(mostCurrent._osc._tinfo);
                break;
            case 20:
                mostCurrent._gp._frm.setText(mostCurrent._osc._fps);
                break;
            case 21:
                _setmessages(mostCurrent._osc._message, 1);
                if (_bt._isconnected) {
                    _bt._disconnect();
                }
                if (_usb1._isconnected) {
                    _usb1._disconnect();
                }
                mostCurrent._osc._speed = 9600;
                break;
            case 22:
                _usb1._disconnect();
                SystemClock.sleep(300L); // TODO:
                if (!_usb1._connect(mostCurrent._osc._speed)) {
                    _setmessages(_usb1._message, 1);
                    break;
                } else {
                    _setmessages(_usb1._message, 0);
                    break;
                }
        }
        if (!mostCurrent._osc._flgstart) {
            mostCurrent._lab_start.setText("Start");
        }
        return "";
    }

    public static String _onsbar(LabelWrapper labelWrapper) throws Exception {
        if (!mostCurrent._sbar._isopen || ((double) _barpos) != BA.ObjectToNumber(labelWrapper.getTag())) {
            _barsclose();
            mostCurrent._osc._lst_str[(int) BA.ObjectToNumber(labelWrapper.getTag())].SetSelection(mostCurrent._osc._lst_pos[(int) BA.ObjectToNumber(labelWrapper.getTag())]);
            mostCurrent._sbar._open(labelWrapper, mostCurrent._osc._lst_str[(int) BA.ObjectToNumber(labelWrapper.getTag())]);
            _barpos = (int) BA.ObjectToNumber(labelWrapper.getTag());
            return "";
        }
        _barsclose();
        return "";
    }

    public static String _onstartpan_click() throws Exception {
        LabelWrapper labelWrapper = new LabelWrapper();
        labelWrapper.setObject((TextView) Common.Sender(mostCurrent.activityBA));
        int ObjectToNumber = (int) BA.ObjectToNumber(labelWrapper.getTag());
        if (ObjectToNumber > 0) {
            _barsclose();
        }
        switch (ObjectToNumber) {
            case 0:
                _createmenu();
                return "";
            case 1:
                Common.ToastMessageShow(mostCurrent._lab_mes.getText(), true);
                return "";
            case 2:
                if (labelWrapper.getText().equals("Auto")) {
                    labelWrapper.setText("Single");
                    mostCurrent._osc._start_auto = false;
                    return "";
                }
                labelWrapper.setText("Auto");
                mostCurrent._osc._start_auto = true;
                return "";
            case 3:
                if (labelWrapper.getText().equals("Start")) {
                    mostCurrent._osc._flgstart = true;
                    mostCurrent._osc._txstart();
                    labelWrapper.setText("Stop");
                    return "";
                }
                mostCurrent._osc._flgstart = false;
                labelWrapper.setText("Start");
                return "";
            default:
                return "";
        }
    }

    public static String _process_globals() throws Exception {
        _bt = new bluetooth();
        _usb1 = new usb();
        return "";
    }

    public static String _radbut_checkedchange(boolean z) throws Exception {
        CompoundButtonWrapper.RadioButtonWrapper radioButtonWrapper = new CompoundButtonWrapper.RadioButtonWrapper();
        radioButtonWrapper.setObject((RadioButton) Common.Sender(mostCurrent.activityBA));
        switch (BA.switchObjectToInt(radioButtonWrapper.getTag(), 0, 1)) {
            case 0:
                mostCurrent._gp._setcur(1, FtdiSerialDriver.USB_ENDPOINT_IN - mostCurrent._osc._shift);
                mostCurrent._gp._setcur(0, mostCurrent._osc._levelsync);
                mostCurrent._gp._setcur(2, mostCurrent._osc._alignsweep);
                mostCurrent._gp._setcurtext(0, _gettrigvolstr());
                mostCurrent._gp._setcurtext(1, BA.NumberToString(mostCurrent._gp._cursor_pos[1]));
                mostCurrent._gp._setcurtext(2, _getpretrigstr());
                break;
            case 1:
                mostCurrent._gp._setcur(1, _t_cur_pos[1]);
                mostCurrent._gp._setcur(0, _t_cur_pos[0]);
                mostCurrent._gp._setcur(2, _t_cur_pos[2]);
                mostCurrent._gp._setcurtext(0, _gettrigvolstr());
                mostCurrent._gp._setcurtext(1, BA.NumberToString(mostCurrent._gp._cursor_pos[1]));
                mostCurrent._gp._setcurtext(2, _getpretrigstr());
                break;
        }
        mostCurrent._gp._cursors_visible(true);
        mostCurrent._gp._cursors_visible(false);
        return "";
    }

    public static String _recolors() throws Exception {
        BA ba = mostCurrent.activityBA;
        String _datafile = files._datafile(ba, "colors.cfg", "#net:", BA.NumberToString(Colors.ARGB(122, 50, 200, 50)), false);
        mostCurrent._gp._netcolor = (int) Double.parseDouble(_datafile);
        mostCurrent._gp._cursor_line[0].setColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#curtrigline:", BA.NumberToString((int) Colors.Red), false)));
        mostCurrent._gp._cursor_line[2].setColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#curpretrigline:", BA.NumberToString((int) Colors.Red), false)));
        mostCurrent._gp._cursor_line[1].setColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#curzeroline:", BA.NumberToString((int) Colors.Blue), false)));
        mostCurrent._gp._cursor_info[0].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtcurtrig:", BA.NumberToString((int) Colors.Red), false)));
        mostCurrent._gp._cursor_info[2].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtcurtpretrig:", BA.NumberToString((int) Colors.Red), false)));
        mostCurrent._gp._cursor_info[1].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtcurzero:", BA.NumberToString((int) Colors.Blue), false)));
        String _datafile2 = files._datafile(ba, "colors.cfg", "#pen:", BA.NumberToString(-1), false);
        mostCurrent._gp._pen_color = (int) Double.parseDouble(_datafile2);
        String _datafile3 = files._datafile(ba, "colors.cfg", "#back:", BA.NumberToString((int) Colors.Black), false);
        mostCurrent._gp._underpan.setColor((int) Double.parseDouble(_datafile3));
        mostCurrent._gp._back_color = (int) Double.parseDouble(_datafile3);
        mostCurrent._sbar._barlab[0].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtheadtime:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._sbar._barlab[1].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtheadvolt:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._sbar._barlab[2].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtheadin:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._sbar._barlab[3].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtheadmode:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._sbar._barlab[5].setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtheadtrig:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._osc._lst_str[0].getSingleLineLayout().Label.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txttimelist:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._osc._lst_str[1].getSingleLineLayout().Label.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtvoltlist:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._osc._lst_str[2].getSingleLineLayout().Label.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtinlist:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._osc._lst_str[3].getSingleLineLayout().Label.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtmodelist:", BA.NumberToString((int) Colors.Yellow), false)));
        mostCurrent._osc._lst_str[5].getSingleLineLayout().Label.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txttriglist:", BA.NumberToString((int) Colors.Yellow), false)));
        LabelWrapper labelWrapper = mostCurrent._gp._frm;
        labelWrapper.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtfps:", BA.NumberToString((int) Colors.Green), false)));
        LabelWrapper labelWrapper2 = mostCurrent._lab_mes;
        labelWrapper2.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtinfo:", BA.NumberToString((int) Colors.Green), false)));
        LabelWrapper labelWrapper3 = mostCurrent._lab_vol_info;
        labelWrapper3.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtvinfo:", BA.NumberToString(-1), false)));
        LabelWrapper labelWrapper4 = mostCurrent._lab_t_info;
        labelWrapper4.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txttinfo:", BA.NumberToString(-1), false)));
        LabelWrapper labelWrapper5 = mostCurrent._lab_auto;
        labelWrapper5.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtauto:", BA.NumberToString((int) Colors.Yellow), false)));
        LabelWrapper labelWrapper6 = mostCurrent._lab_start;
        labelWrapper6.setTextColor((int) Double.parseDouble(files._datafile(ba, "colors.cfg", "#txtstart:", BA.NumberToString((int) Colors.Yellow), false)));
        files._datafile(ba, "colors.cfg", "#txtinfoerr:", BA.NumberToString((int) Colors.Magenta), false);
        mostCurrent._gp._resize(332, 255, BA.NumberToString(32), BA.NumberToString(32));
        return "";
    }

    public static String _reconfig() throws Exception {
        files._datafile(mostCurrent.activityBA, "osc.cfg", "#usb_speed:", BA.NumberToString(115200), false);
        files._datafile(mostCurrent.activityBA, "osc.cfg", "#bt_dev:", "", false);
        int[] iArr = mostCurrent._osc._lst_pos;
        iArr[0] = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Time:", BA.NumberToString(10), false));
        mostCurrent._sbar._barlab[0].setText(mostCurrent._osc._lst_str[0].GetItem(mostCurrent._osc._lst_pos[0]));
        int[] iArr2 = mostCurrent._osc._lst_pos;
        iArr2[1] = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Voltage:", BA.NumberToString(5), false));
        mostCurrent._sbar._barlab[1].setText(mostCurrent._osc._lst_str[1].GetItem(mostCurrent._osc._lst_pos[1]));
        int[] iArr3 = mostCurrent._osc._lst_pos;
        iArr3[2] = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#input:", BA.NumberToString(1), false));
        mostCurrent._sbar._barlab[2].setText(mostCurrent._osc._lst_str[2].GetItem(mostCurrent._osc._lst_pos[2]));
        int[] iArr4 = mostCurrent._osc._lst_pos;
        iArr4[3] = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#SampleMod:", BA.NumberToString(0), false));
        mostCurrent._sbar._barlab[3].setText(mostCurrent._osc._lst_str[3].GetItem(mostCurrent._osc._lst_pos[3]));
        int[] iArr5 = mostCurrent._osc._lst_pos;
        iArr5[4] = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Sync:", BA.NumberToString(0), false));
        CanvasWrapper.BitmapWrapper bitmapWrapper = mostCurrent._tbmp;
        bitmapWrapper.Initialize(File.getDirAssets(), String.valueOf(mostCurrent._osc._lst_str[4].GetItem(mostCurrent._osc._lst_pos[4])));
        mostCurrent._sbar._barlab[4].SetBackgroundImage((Bitmap) mostCurrent._tbmp.getObject());
        int[] iArr6 = mostCurrent._osc._lst_pos;
        iArr6[5] = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#TypeSync:", BA.NumberToString(0), false));
        mostCurrent._sbar._barlab[5].setText(mostCurrent._osc._lst_str[5].GetItem(mostCurrent._osc._lst_pos[5]));
        oscill oscill = mostCurrent._osc;
        oscill._sizesampler = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Size:", BA.NumberToString(332), false));
        oscill._vmax_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Vmax_en:", String.valueOf(true), false));
        oscill._vmin_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Vmin_en:", String.valueOf(true), false));
        oscill._vamp_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Vamp_en:", String.valueOf(true), false));
        oscill._vavg_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Vavg_en:", String.valueOf(true), false));
        oscill._vrms_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Vrms_en:", String.valueOf(true), false));
        oscill._t_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#T_en:", String.valueOf(true), false));
        oscill._f_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#F_en:", String.valueOf(true), false));
        oscill._tp_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Tp_en:", String.valueOf(true), false));
        oscill._q_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Q_en:", String.valueOf(true), false));
        oscill._pwm_en = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#PWM_en:", String.valueOf(true), false));
        if (mostCurrent._osc._t_en || mostCurrent._osc._f_en || mostCurrent._osc._tp_en || mostCurrent._osc._q_en || !mostCurrent._osc._pwm_en) {
            mostCurrent._osc._t_all_dis = false;
        } else {
            mostCurrent._osc._t_all_dis = true;
        }
        oscill._to_time = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#To_time:", BA.NumberToString(10), false));
        oscill._te_time = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Te_time:", BA.NumberToString(500), false));
        _vol_but = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Vol_but:", BA.NumberToString(0), false));
        oscill._levelsync = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#levelSync:", BA.NumberToString((int) FtdiSerialDriver.USB_ENDPOINT_IN), false));
        oscill._shift = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Shift:", BA.NumberToString(0), false));
        oscill._alignsweep = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#AlingSweep:", BA.NumberToString(100), false));
        oscill._lfilter = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Fl_3k:", String.valueOf(false), false));
        oscill._hfilter = BA.ObjectToBoolean(files._datafile(mostCurrent.activityBA, "osc.cfg", "#Fl_3m:", String.valueOf(false), false));
        usb usb = _usb1;
        usb._vid = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#USB_VID:", BA.NumberToString((int) UsbId.VENDOR_SILAB), false));
        usb._pid = (int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "osc.cfg", "#USB_PID:", BA.NumberToString(33806), false));
        mostCurrent._gp._setcurtext(0, _gettrigvolstr());
        mostCurrent._gp._setcurtext(1, BA.NumberToString(mostCurrent._gp._cursor_pos[1]));
        mostCurrent._gp._setcurtext(2, _getpretrigstr());
        mostCurrent._gp._resize(mostCurrent._osc._sizesample, 255, BA.NumberToString(mostCurrent._osc._divpix), BA.NumberToString(32));
        mostCurrent._gp._setcur(1, FtdiSerialDriver.USB_ENDPOINT_IN - mostCurrent._osc._shift);
        mostCurrent._gp._setcur(0, mostCurrent._osc._levelsync);
        mostCurrent._osc._vstep = (float) (BA.ObjectToNumber(mostCurrent._osc._lst_data[1].Get(mostCurrent._osc._lst_pos[1])) / 32.0d);
        mostCurrent._gp._setcur(2, mostCurrent._osc._alignsweep);
        _t_cur_pos[0] = mostCurrent._osc._levelsync;
        _t_cur_pos[1] = FtdiSerialDriver.USB_ENDPOINT_IN - mostCurrent._osc._shift;
        _t_cur_pos[2] = mostCurrent._osc._alignsweep;
        return "";
    }

    public static String _setmessages(String str, int i) throws Exception {
        if (i == 0) {
            LabelWrapper labelWrapper = mostCurrent._lab_mes;
            labelWrapper.setTextColor((int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "colors.cfg", "#txtinfo:", "", false)));
        }
        if (i == 1) {
            LabelWrapper labelWrapper2 = mostCurrent._lab_mes;
            labelWrapper2.setTextColor((int) Double.parseDouble(files._datafile(mostCurrent.activityBA, "colors.cfg", "#txtinfoerr:", "", false)));
        }
        mostCurrent._lab_mes.setText(str);
        return "";
    }

    public static String _settingclose() throws Exception {
        if (!mostCurrent._stg_dlg.IsInitialized() || !mostCurrent._stg_dlg._setting_pan.IsInitialized()) {
            return "";
        }
        _reconfig();
        mostCurrent._osc._txtimediv();
        mostCurrent._osc._txinput();
        mostCurrent._stg_dlg._setting_pan.RemoveView();
        return "";
    }
}
