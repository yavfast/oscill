package anywheresoftware.b4a.objects;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import anywheresoftware.b4a.BA;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@BA.ActivityObject
public class ServiceHelper {
    private static Method mStartForeground;
    private static final Class<?>[] mStartForegroundSignature = {Integer.TYPE, Notification.class};
    private static Method mStopForeground;
    private static final Class<?>[] mStopForegroundSignature = {Boolean.TYPE};
    NotificationManager mNM = ((NotificationManager) BA.applicationContext.getSystemService("notification"));
    private Service service;

    @BA.Hide
    public static void init() {
        Class<Service> cls = Service.class;
        try {
            mStartForeground = Service.class.getMethod("startForeground", mStartForegroundSignature);
            mStopForeground = Service.class.getMethod("stopForeground", mStopForegroundSignature);
        } catch (NoSuchMethodException e) {
            NoSuchMethodException noSuchMethodException = e;
            mStopForeground = null;
            mStartForeground = null;
        }
    }

    public ServiceHelper(Service service2) {
        this.service = service2;
    }

    public void StartForeground(int Id, Notification Notification) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (mStartForeground != null) {
            mStartForeground.invoke(this.service, new Object[]{Integer.valueOf(Id), Notification});
            return;
        }
        this.service.startForeground(Id, Notification);
        this.mNM.notify(Id, Notification);
    }

    public void StopForeground(int Id) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (mStopForeground != null) {
            mStopForeground.invoke(this.service, new Object[]{Boolean.TRUE});
            return;
        }
        this.mNM.cancel(Id);
        this.service.stopForeground(true);
    }
}
