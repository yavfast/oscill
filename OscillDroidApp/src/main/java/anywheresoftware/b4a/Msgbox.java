package anywheresoftware.b4a;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.ViewParent;
import anywheresoftware.b4a.BA;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@BA.Hide
public class Msgbox {
    private static Object closeMyLoop = new Object();
    private static Field flagsF;
    public static boolean isDismissing = false;
    private static Method nextM;
    public static WeakReference<ProgressDialog> pd;
    private static boolean stopCodeAfterDismiss = false;
    private static boolean visible = false;
    /* access modifiers changed from: private */
    public static WeakReference<AlertDialog> visibleAD;
    private static Field whenF;

    static {
        Class<Message> cls = Message.class;
        try {
            nextM = MessageQueue.class.getDeclaredMethod("next", (Class[]) null);
            nextM.setAccessible(true);
            whenF = Message.class.getDeclaredField("when");
            whenF.setAccessible(true);
            flagsF = null;
            try {
                flagsF = Message.class.getDeclaredField("flags");
                flagsF.setAccessible(true);
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean msgboxIsVisible() {
        return visible;
    }

    public static boolean isItReallyAMsgboxAndNotDebug() {
        return visibleAD != null;
    }

    public static void dismiss(boolean stopCodeAfterDismiss2) {
        dismissProgressDialog();
        if (BA.debugMode) {
            try {
                Class.forName("anywheresoftware.b4a.debug.Debug").getMethod("hideProgressDialogToAvoidLeak", (Class[]) null).invoke((Object) null, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isDismissing = true;
        if (visible) {
            if (visibleAD != null) {
                AlertDialog ad = (AlertDialog) visibleAD.get();
                if (ad != null) {
                    ad.dismiss();
                }
            } else {
                sendCloseMyLoopMessage();
            }
            stopCodeAfterDismiss = stopCodeAfterDismiss2;
        }
    }

    public static void sendCloseMyLoopMessage() {
        Message msg = Message.obtain();
        msg.setTarget(BA.handler);
        msg.obj = closeMyLoop;
        msg.sendToTarget();
    }

    public static void dismissProgressDialog() {
        ProgressDialog p;
        if (pd != null && (p = (ProgressDialog) pd.get()) != null) {
            p.dismiss();
        }
    }

    public static class DialogResponse implements DialogInterface.OnClickListener {
        private boolean dismiss;
        public int res = -3;

        public DialogResponse(boolean dismissAfterClick) {
            this.dismiss = dismissAfterClick;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.res = which;
            if (this.dismiss && Msgbox.visibleAD != null) {
                ((AlertDialog) Msgbox.visibleAD.get()).dismiss();
            }
        }
    }

    public static void msgbox(AlertDialog ad, boolean isTopMostInStack) {
        if (!visible) {
            try {
                if (!isDismissing) {
                    stopCodeAfterDismiss = false;
                    Message msg = Message.obtain();
                    msg.setTarget(BA.handler);
                    msg.obj = closeMyLoop;
                    ad.setDismissMessage(msg);
                    visible = true;
                    visibleAD = new WeakReference<>(ad);
                    ad.show();
                    waitForMessage(false, (Handler) null);
                    if (!stopCodeAfterDismiss || isTopMostInStack) {
                        visible = false;
                        visibleAD = null;
                        return;
                    }
                    throw new B4AUncaughtException();
                }
            } finally {
                visible = false;
                visibleAD = null;
            }
        }
    }

    public static void debugWait(Dialog d) {
        if (visible) {
            System.out.println("already visible");
            return;
        }
        try {
            if (!isDismissing) {
                stopCodeAfterDismiss = false;
                visible = true;
                waitForMessage(true, findHandler(d));
                if (stopCodeAfterDismiss) {
                    Log.w("", "throwing b4a uncaught exception");
                    throw new B4AUncaughtException();
                } else {
                    visible = false;
                }
            }
        } finally {
            visible = false;
        }
    }

    private static Handler findHandler(Dialog d) {
        if (d == null) {
            return null;
        }
        Handler vp2 = null;
        for (ViewParent vp = d.getWindow().getDecorView().getParent(); vp != null; vp = vp.getParent()) {
            vp2 = (Handler)vp;
        }
        if (vp2 instanceof Handler) {
            return vp2;
        }
        return null;
    }

    public static void waitForMessage(boolean allowB4ARunnables, boolean onlyDrawableEvents) {
        waitForMessage(onlyDrawableEvents, (Handler) null);
    }

    private static void waitForMessage(boolean onlyDrawableEvents, Handler allowedHandler) {
        try {
            MessageQueue queue = Looper.myQueue();
            while (true) {
                Message msg = (Message) nextM.invoke(queue, (Object[]) null);
                if (msg != null) {
                    if (msg.obj == closeMyLoop) {
                        msg.recycle();
                        return;
                    } else if (msg.getCallback() == null || !(msg.getCallback() instanceof BA.B4ARunnable)) {
                        if (onlyDrawableEvents) {
                            if ((msg.obj == null || !(msg.obj instanceof Drawable)) && msg.what >= 100 && msg.what <= 150) {
                                skipMessage(msg);
                            }
                        }
                        msg.getTarget().dispatchMessage(msg);
                        msg.recycle();
                    } else {
                        skipMessage(msg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void skipMessage(Message msg) throws IllegalArgumentException, IllegalAccessException {
        whenF.set(msg, 0);
        if (flagsF != null) {
            flagsF.setInt(msg, flagsF.getInt(msg) & -2);
        }
        msg.getTarget().sendMessage(msg);
    }
}
