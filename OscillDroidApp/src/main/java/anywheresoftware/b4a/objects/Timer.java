package anywheresoftware.b4a.objects;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.Msgbox;

@BA.ShortName("Timer")
public class Timer implements BA.CheckForReinitialize {
    private BA ba;
    private boolean enabled = false;
    /* access modifiers changed from: private */
    public String eventName;
    /* access modifiers changed from: private */
    public long interval;
    private ParentReference myRef = new ParentReference();
    /* access modifiers changed from: private */
    public int relevantTimer = 0;

    public void Initialize(BA ba2, String EventName, long Interval) {
        this.interval = Interval;
        this.ba = ba2;
        this.eventName = EventName.toLowerCase(BA.cul) + "_tick";
    }

    public boolean IsInitialized() {
        return this.ba != null;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setInterval(long Interval) {
        if (this.interval != Interval) {
            this.interval = Interval;
            if (this.enabled) {
                stopTicking();
                startTicking();
            }
        }
    }

    public long getInterval() {
        return this.interval;
    }

    private void startTicking() {
        BA.handler.postDelayed(new TickTack(this.relevantTimer, this.myRef, this.ba), this.interval);
    }

    public void setEnabled(boolean Enabled) {
        if (Enabled != this.enabled) {
            if (Enabled) {
                this.myRef.timer = this;
                if (this.interval <= 0) {
                    throw new IllegalStateException("Interval must be larger than 0.");
                }
                startTicking();
            } else {
                this.myRef.timer = null;
                stopTicking();
            }
            this.enabled = Enabled;
        }
    }

    static class TickTack implements Runnable {
        private final BA ba;
        private final int currentTimer;
        private final ParentReference parent;

        public TickTack(int currentTimer2, ParentReference parent2, BA ba2) {
            this.currentTimer = currentTimer2;
            this.parent = parent2;
            this.ba = ba2;
        }

        public void run() {
            Timer parentTimer = this.parent.timer;
            if (parentTimer != null && this.currentTimer == parentTimer.relevantTimer) {
                BA.handler.postDelayed(this, parentTimer.interval);
                if (!this.ba.isActivityPaused() && !Msgbox.msgboxIsVisible()) {
                    this.ba.raiseEvent2(parentTimer, false, parentTimer.eventName, true, new Object[0]);
                }
            }
        }
    }

    static class ParentReference {
        public Timer timer;

        ParentReference() {
        }
    }

    private void stopTicking() {
        this.relevantTimer++;
    }
}
