package anywheresoftware.b4a.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.phone.Phone;
import java.util.HashMap;
import java.util.Map;

@BA.ShortName("PhoneEvents")
public class PhoneEvents {
    /* access modifiers changed from: private */
    public BA ba;
    private BroadcastReceiver br;
    /* access modifiers changed from: private */
    public String ev;
    /* access modifiers changed from: private */
    public HashMap<String, ActionHandler> map = new HashMap<>();

    public PhoneEvents() {
        this.map.put("android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED", new ActionHandler() {
            {
                this.event = "_texttospeechfinish";
            }

            public void handle(Intent intent) {
                send(intent, (Object[]) null);
            }
        });
        this.map.put("android.net.conn.CONNECTIVITY_CHANGE", new ActionHandler() {
            {
                this.event = "_connectivitychanged";
            }

            public void handle(Intent intent) {
                NetworkInfo ni = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                send(intent, new Object[]{ni.getTypeName(), ni.getState().toString()});
            }
        });
        this.map.put("android.intent.action.USER_PRESENT", new ActionHandler() {
            {
                this.event = "_userpresent";
            }

            public void handle(Intent intent) {
                send(intent, (Object[]) null);
            }
        });
        this.map.put("android.intent.action.ACTION_SHUTDOWN", new ActionHandler() {
            {
                this.event = "_shutdown";
            }

            public void handle(Intent intent) {
                send(intent, (Object[]) null);
            }
        });
        this.map.put("android.intent.action.SCREEN_ON", new ActionHandler() {
            {
                this.event = "_screenon";
            }

            public void handle(Intent intent) {
                send(intent, (Object[]) null);
            }
        });
        this.map.put("android.intent.action.SCREEN_OFF", new ActionHandler() {
            {
                this.event = "_screenoff";
            }

            public void handle(Intent intent) {
                send(intent, (Object[]) null);
            }
        });
        this.map.put("android.intent.action.PACKAGE_REMOVED", new ActionHandler() {
            {
                this.event = "_packageremoved";
            }

            public void handle(Intent intent) {
                send(intent, new Object[]{intent.getDataString()});
            }
        });
        this.map.put("android.intent.action.PACKAGE_ADDED", new ActionHandler() {
            {
                this.event = "_packageadded";
            }

            public void handle(Intent intent) {
                send(intent, new Object[]{intent.getDataString()});
            }
        });
        this.map.put("android.intent.action.DEVICE_STORAGE_LOW", new ActionHandler() {
            {
                this.event = "_devicestoragelow";
            }

            public void handle(Intent intent) {
                send(intent, (Object[]) null);
            }
        });
        this.map.put("b4a.smssent", new ActionHandler() {
            {
                this.event = "_smssentstatus";
            }

            public void handle(Intent intent) {
                boolean z;
                String msg = "";
                switch (this.resultCode) {
                    case -1:
                        msg = "OK";
                        break;
                    case 1:
                        msg = "GENERIC_FAILURE";
                        break;
                    case 2:
                        msg = "RADIO_OFF";
                        break;
                    case 3:
                        msg = "NULL_PDU";
                        break;
                    case 4:
                        msg = "NO_SERVICE";
                        break;
                }
                Object[] objArr = new Object[3];
                if (this.resultCode == -1) {
                    z = true;
                } else {
                    z = false;
                }
                objArr[0] = Boolean.valueOf(z);
                objArr[1] = msg;
                objArr[2] = intent.getStringExtra("phone");
                send(intent, objArr);
            }
        });
        this.map.put("b4a.smsdelivered", new ActionHandler() {
            {
                this.event = "_smsdelivered";
            }

            public void handle(Intent intent) {
                send(intent, new Object[]{intent.getStringExtra("phone")});
            }
        });
        this.map.put("android.intent.action.DEVICE_STORAGE_OK", new ActionHandler() {
            {
                this.event = "_devicestorageok";
            }

            public void handle(Intent intent) {
                send(intent, (Object[]) null);
            }
        });
        this.map.put("android.intent.action.BATTERY_CHANGED", new ActionHandler() {
            {
                this.event = "_batterychanged";
            }

            public void handle(Intent intent) {
                boolean plugged;
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 1);
                if (intent.getIntExtra("plugged", 0) > 0) {
                    plugged = true;
                } else {
                    plugged = false;
                }
                send(intent, new Object[]{Integer.valueOf(level), Integer.valueOf(scale), Boolean.valueOf(plugged)});
            }
        });
        this.map.put("android.intent.action.AIRPLANE_MODE", new ActionHandler() {
            {
                this.event = "_airplanemodechanged";
            }

            public void handle(Intent intent) {
                send(intent, new Object[]{Boolean.valueOf(intent.getBooleanExtra("state", false))});
            }
        });
        for (Map.Entry<String, ActionHandler> e : this.map.entrySet()) {
            e.getValue().action = e.getKey();
        }
    }

    public void InitializeWithPhoneState(BA ba2, String EventName, Phone.PhoneId PhoneId) {
        this.map.put("android.intent.action.PHONE_STATE", new ActionHandler() {
            {
                this.event = "_phonestatechanged";
            }

            public void handle(Intent intent) {
                String state = intent.getStringExtra("state");
                String incomingNumber = intent.getStringExtra("incoming_number");
                if (incomingNumber == null) {
                    incomingNumber = "";
                }
                send(intent, new Object[]{state, incomingNumber});
            }
        });
        this.map.get("android.intent.action.PHONE_STATE").action = "android.intent.action.PHONE_STATE";
        Initialize(ba2, EventName);
    }

    public void Initialize(BA ba2, String EventName) {
        this.ba = ba2;
        this.ev = EventName.toLowerCase(BA.cul);
        StopListening();
        this.br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                ActionHandler ah;
                if (intent.getAction() != null && (ah = (ActionHandler) PhoneEvents.this.map.get(intent.getAction())) != null) {
                    ah.resultCode = getResultCode();
                    ah.handle(intent);
                }
            }
        };
        IntentFilter f1 = new IntentFilter();
        IntentFilter f2 = null;
        for (ActionHandler ah : this.map.values()) {
            if (ba2.subExists(String.valueOf(this.ev) + ah.event)) {
                if (ah.action == "android.intent.action.PACKAGE_ADDED" || ah.action == "android.intent.action.PACKAGE_REMOVED") {
                    if (f2 == null) {
                        f2 = new IntentFilter();
                        f2.addDataScheme("package");
                    }
                    f2.addAction(ah.action);
                }
                f1.addAction(ah.action);
            }
        }
        BA.applicationContext.registerReceiver(this.br, f1);
        if (f2 != null) {
            BA.applicationContext.registerReceiver(this.br, f2);
        }
    }

    public void StopListening() {
        if (this.br != null) {
            BA.applicationContext.unregisterReceiver(this.br);
        }
        this.br = null;
    }

    private abstract class ActionHandler {
        public String action;
        public String event;
        public int resultCode;

        public abstract void handle(Intent intent);

        private ActionHandler() {
        }

        /* synthetic */ ActionHandler(PhoneEvents phoneEvents, ActionHandler actionHandler) {
            this();
        }

        /* access modifiers changed from: protected */
        public void send(Intent intent, Object[] args) {
            final Object[] o;
            if (args == null) {
                o = new Object[1];
            } else {
                o = new Object[(args.length + 1)];
                System.arraycopy(args, 0, o, 0, args.length);
            }
            o[o.length - 1] = AbsObjectWrapper.ConvertToWrapper(new IntentWrapper(), intent);
            if (BA.debugMode) {
                BA.handler.post(new BA.B4ARunnable() {
                    public void run() {
                        PhoneEvents.this.ba.raiseEvent(this, String.valueOf(PhoneEvents.this.ev) + ActionHandler.this.event, o);
                    }
                });
            } else {
                PhoneEvents.this.ba.raiseEvent(this, String.valueOf(PhoneEvents.this.ev) + this.event, o);
            }
        }
    }

    @BA.ShortName("SmsInterceptor")
    public static class SMSInterceptor {
        /* access modifiers changed from: private */
        public BA ba;
        private BroadcastReceiver br;
        /* access modifiers changed from: private */
        public String eventName;

        public void Initialize(String EventName, BA ba2) {
            Initialize2(EventName, ba2, 0);
        }

        public void ListenToOutgoingMessages() {
            final Uri content = Uri.parse("content://sms");
            BA.applicationContext.getContentResolver().registerContentObserver(content, true, new ContentObserver(new Handler()) {
                public void onChange(boolean selfChange) {
                    super.onChange(selfChange);
                    Cursor cursor = BA.applicationContext.getContentResolver().query(content, (String[]) null, (String) null, (String[]) null, (String) null);
                    if (cursor.moveToNext()) {
                        String protocol = cursor.getString(cursor.getColumnIndex("protocol"));
                        int type = cursor.getInt(cursor.getColumnIndex("type"));
                        if (protocol == null && type == 2) {
                            SMSInterceptor.this.ba.raiseEvent((Object) null, String.valueOf(SMSInterceptor.this.eventName) + "_messagesent", Integer.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
                            cursor.close();
                        }
                    }
                }
            });
        }

        public void Initialize2(String EventName, final BA ba2, int Priority) {
            this.ba = ba2;
            this.eventName = EventName.toLowerCase(BA.cul);
            this.br = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    Bundle bundle;
                    if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") && (bundle = intent.getExtras()) != null) {
                        Object[] pduObj = (Object[]) bundle.get("pdus");
                        for (Object obj : pduObj) {
                            SmsMessage sm = SmsMessage.createFromPdu((byte[]) obj);
                            Boolean res = (Boolean) ba2.raiseEvent(SMSInterceptor.this, String.valueOf(SMSInterceptor.this.eventName) + "_messagereceived", sm.getOriginatingAddress(), sm.getMessageBody());
                            if (res != null && res.booleanValue()) {
                                abortBroadcast();
                            }
                        }
                    }
                }
            };
            IntentFilter fil = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
            fil.setPriority(Priority);
            BA.applicationContext.registerReceiver(this.br, fil);
        }

        public void StopListening() {
            if (this.br != null) {
                BA.applicationContext.unregisterReceiver(this.br);
            }
            this.br = null;
        }
    }
}
