package anywheresoftware.b4a.agraham.dialogs;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.Msgbox;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.DateTime;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.objects.SimpleListAdapter;
import com.hoho.android.usbserial.driver.FtdiSerialDriver;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

@BA.Author("Andrew Graham")
@BA.Version(2.91f)
@BA.ActivityObject
@BA.ShortName("InputDialog")
public class InputDialog {
    public static final int INPUT_TYPE_DECIMAL_NUMBERS = 12290;
    public static final int INPUT_TYPE_NONE = 0;
    public static final int INPUT_TYPE_NUMBERS = 2;
    public static final int INPUT_TYPE_PHONE = 3;
    public static final int INPUT_TYPE_TEXT = 1;
    private static final double version = 2.91d;
    private String hint = "";
    private int hintcolor = Colors.DarkGray;
    private String input = "";
    private int inputtype = 1;
    private boolean password = false;
    private int response;

    public void LIBRARY_DOC() {
    }

    public int Show(String message, String title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) {
        AlertDialog ad = new AlertDialog.Builder(ba.context).create();
        float diptopx = ba.context.getResources().getDisplayMetrics().density;
        int dps10 = (int) (10.0f * diptopx);
        LinearLayout view = new LinearLayout(ba.context);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        view.setOrientation(1);
        TextView textView = new TextView(ba.context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins((int) (20.0f * diptopx), 0, dps10, 0);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(3);
        textView.setText(message);
        view.addView(textView);
        EditText editText = new EditText(ba.context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.setMargins(dps10, 0, dps10, 0);
        editText.setLayoutParams(layoutParams2);
        editText.setGravity(7);
        editText.setHorizontallyScrolling(true);
        editText.setText(this.input);
        editText.setInputType(this.inputtype);
        if (this.password) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            editText.setTransformationMethod((TransformationMethod) null);
        }
        if (this.hint != "") {
            editText.setHint(this.hint);
            editText.setHintTextColor(this.hintcolor);
        }
        view.addView(editText);
        ad.setView(view);
        Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
        ad.setTitle(title);
        if (Positive.length() > 0) {
            ad.setButton(-1, Positive, dialogResponse);
        }
        if (Negative.length() > 0) {
            ad.setButton(-2, Negative, dialogResponse);
        }
        if (Cancel.length() > 0) {
            ad.setButton(-3, Cancel, dialogResponse);
        }
        if (icon != null) {
            ad.setIcon(new BitmapDrawable(icon));
        }
        Msgbox.msgbox(ad, false);
        this.input = editText.getText().toString();
        this.response = dialogResponse.res;
        Msgbox.sendCloseMyLoopMessage();
        Msgbox.waitForMessage(false, true);
        ((InputMethodManager) BA.applicationContext.getSystemService("input_method")).hideSoftInputFromWindow(ba.vg.getWindowToken(), 0);
        return dialogResponse.res;
    }

    public double getVersion() {
        return version;
    }

    public void setInput(String value) {
        this.input = value;
    }

    public String getInput() {
        return this.input;
    }

    public int getInputType() {
        return this.inputtype;
    }

    public void setInputType(int inputtype2) {
        this.inputtype = inputtype2;
    }

    public boolean getPasswordMode() {
        return this.password;
    }

    public void setPasswordMode(boolean value) {
        this.password = value;
    }

    public int getResponse() {
        return this.response;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint2) {
        this.hint = hint2;
    }

    public int getHintColor() {
        return this.hintcolor;
    }

    public void setHintColor(int hintcolor2) {
        this.hintcolor = hintcolor2;
    }

    @BA.ActivityObject
    @BA.ShortName("DateDialog")
    public static class DateDialog {
        private int day;
        private int month;
        private int response;
        private int year;

        public int Show(String Message, String Title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            float diptopx = ba.context.getResources().getDisplayMetrics().density;
            LinearLayout view = new LinearLayout(ba.context);
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            view.setOrientation(1);
            TextView textView = new TextView(ba.context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins((int) (20.0f * diptopx), 0, (int) (10.0f * diptopx), 0);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(3);
            textView.setText(Message);
            view.addView(textView);
            DatePicker datePicker = new DatePicker(ba.context);
            datePicker.updateDate(this.year, this.month, this.day);
            view.addView(datePicker);
            ad.setView(view);
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            ad.setTitle(Title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dialogResponse);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dialogResponse);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dialogResponse);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            datePicker.clearFocus();
            this.year = datePicker.getYear();
            this.month = datePicker.getMonth();
            this.day = datePicker.getDayOfMonth();
            this.response = dialogResponse.res;
            return dialogResponse.res;
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public int getYear() {
            return this.year;
        }

        public void setYear(int year2) {
            this.year = year2;
        }

        public int getMonth() {
            return this.month + 1;
        }

        public void setMonth(int month2) {
            this.month = month2 - 1;
        }

        public int getDayOfMonth() {
            return this.day;
        }

        public void setDayOfMonth(int day2) {
            this.day = day2;
        }

        public void SetDate(int dayofmonth, int month2, int year2) {
            this.day = dayofmonth;
            this.month = month2 - 1;
            this.year = year2;
        }

        public long getDateTicks() {
            return new GregorianCalendar(this.year, this.month, this.day, 0, 0).getTimeInMillis();
        }

        public void setDateTicks(long ticks) {
            this.day = DateTime.GetDayOfMonth(ticks);
            this.month = DateTime.GetMonth(ticks) - 1;
            this.year = DateTime.GetYear(ticks);
        }

        public int getResponse() {
            return this.response;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("TimeDialog")
    public static class TimeDialog {
        private int hour;
        private boolean hours24;
        private int minute;
        private int response;

        public int Show(String Message, String Title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            float diptopx = ba.context.getResources().getDisplayMetrics().density;
            LinearLayout view = new LinearLayout(ba.context);
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            view.setOrientation(1);
            TextView textView = new TextView(ba.context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins((int) (20.0f * diptopx), 0, (int) (10.0f * diptopx), 0);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(3);
            textView.setText(Message);
            view.addView(textView);
            TimePicker timePicker = new TimePicker(ba.context);
            timePicker.setIs24HourView(Boolean.valueOf(this.hours24));
            timePicker.setCurrentHour(Integer.valueOf(this.hour));
            timePicker.setCurrentMinute(Integer.valueOf(this.minute));
            view.addView(timePicker);
            ad.setView(view);
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            ad.setTitle(Title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dialogResponse);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dialogResponse);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dialogResponse);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            timePicker.clearFocus();
            this.hour = timePicker.getCurrentHour().intValue();
            this.minute = timePicker.getCurrentMinute().intValue();
            this.response = dialogResponse.res;
            return dialogResponse.res;
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public int getHour() {
            return this.hour;
        }

        public void setHour(int hour2) {
            this.hour = hour2;
        }

        public int getMinute() {
            return this.minute;
        }

        public void setMinute(int minute2) {
            this.minute = minute2;
        }

        public boolean getIs24Hours() {
            return this.hours24;
        }

        public void setIs24Hours(boolean is24hours) {
            this.hours24 = is24hours;
        }

        public long getTimeTicks() {
            return new GregorianCalendar(0, 0, 0, this.hour, this.minute).getTimeInMillis();
        }

        public void setTimeTicks(long ticks) {
            this.hour = DateTime.GetHour(ticks);
            this.minute = DateTime.GetMinute(ticks);
        }

        public void SetTime(int hour2, int minutes, boolean hours242) {
            this.hour = hour2;
            this.minute = minutes;
            this.hours24 = hours242;
        }

        public int getResponse() {
            return this.response;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("ColorDialog")
    public static class ColorDialog {
        /* access modifiers changed from: private */
        public int blue;
        /* access modifiers changed from: private */
        public int green;
        /* access modifiers changed from: private */
        public int red;
        private int response;

        /* access modifiers changed from: private */
        public GradientDrawable getColor(int color, float radius) {
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{color, color});
            gd.setCornerRadius(radius);
            return gd;
        }

        public int Show(String title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            float diptopx = ba.context.getResources().getDisplayMetrics().density;
            int dps20 = (int) (20.0f * diptopx);
            int dps10 = (int) (10.0f * diptopx);
            int dps05 = (int) (5.0f * diptopx);
            LinearLayout view = new LinearLayout(ba.context);
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            view.setOrientation(1);
            TextView textView = new TextView(ba.context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.setMargins(dps20, dps10, dps20, dps20);
            layoutParams.height = dps20 * 2;
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundDrawable(getColor(Color.argb(255, this.red, this.green, this.blue), (float) dps05));
            view.addView(textView);
            TextView textView2 = new TextView(ba.context);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams2.setMargins(dps20, 0, dps20, 0);
            textView2.setLayoutParams(layoutParams2);
            textView2.setGravity(3);
            textView2.setText(Integer.toString(this.red));
            view.addView(textView2);
            SeekBar seekBar = new SeekBar(ba.context);
            seekBar.setMax(255);
            seekBar.setProgress(this.red);
            seekBar.setBackgroundDrawable(getColor(Colors.Red, (float) dps05));
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
            layoutParams3.setMargins(dps10, dps10, dps10, dps10);
            seekBar.setLayoutParams(layoutParams3);
            view.addView(seekBar);
            final TextView textView3 = textView2;
            final TextView textView4 = textView;
            final int i = dps05;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ColorDialog.this.red = progress;
                    textView3.setText(Integer.toString(progress));
                    textView4.setBackgroundDrawable(ColorDialog.this.getColor(Color.argb(255, ColorDialog.this.red, ColorDialog.this.green, ColorDialog.this.blue), (float) i));
                }
            });
            TextView textView5 = new TextView(ba.context);
            textView5.setLayoutParams(layoutParams2);
            textView5.setGravity(3);
            textView5.setText(Integer.toString(this.green));
            view.addView(textView5);
            SeekBar seekBar2 = new SeekBar(ba.context);
            seekBar2.setMax(255);
            seekBar2.setProgress(this.green);
            seekBar2.setBackgroundDrawable(getColor(Colors.Green, (float) dps05));
            seekBar2.setLayoutParams(layoutParams3);
            view.addView(seekBar2);
            final TextView textView6 = textView5;
            final TextView textView7 = textView;
            final int i2 = dps05;
            seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ColorDialog.this.green = progress;
                    textView6.setText(Integer.toString(progress));
                    textView7.setBackgroundDrawable(ColorDialog.this.getColor(Color.argb(255, ColorDialog.this.red, ColorDialog.this.green, ColorDialog.this.blue), (float) i2));
                }
            });
            TextView textView8 = new TextView(ba.context);
            textView8.setLayoutParams(layoutParams2);
            textView8.setGravity(3);
            textView8.setText(Integer.toString(this.blue));
            view.addView(textView8);
            SeekBar seekBar3 = new SeekBar(ba.context);
            seekBar3.setMax(255);
            seekBar3.setProgress(this.blue);
            seekBar3.setBackgroundDrawable(getColor(Colors.Blue, (float) dps05));
            seekBar3.setLayoutParams(layoutParams3);
            final TextView textView9 = textView8;
            final TextView textView10 = textView;
            final int i3 = dps05;
            seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ColorDialog.this.blue = progress;
                    textView9.setText(Integer.toString(progress));
                    textView10.setBackgroundDrawable(ColorDialog.this.getColor(Color.argb(255, ColorDialog.this.red, ColorDialog.this.green, ColorDialog.this.blue), (float) i3));
                }
            });
            view.addView(seekBar3);
            ad.setView(view);
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            ad.setTitle(title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dialogResponse);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dialogResponse);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dialogResponse);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            this.red = seekBar.getProgress();
            this.green = seekBar2.getProgress();
            this.blue = seekBar3.getProgress();
            this.response = dialogResponse.res;
            return dialogResponse.res;
        }

        public int getRed() {
            return this.red;
        }

        public void setRed(int red2) {
            this.red = red2;
        }

        public int getGreen() {
            return this.green;
        }

        public void setGreen(int green2) {
            this.green = green2;
        }

        public int getBlue() {
            return this.blue;
        }

        public void setBlue(int blue2) {
            this.blue = blue2;
        }

        public int getRGB() {
            return Color.rgb(this.red, this.green, this.blue);
        }

        public void setRGB(int color) {
            this.red = Color.red(color);
            this.green = Color.green(color);
            this.blue = Color.blue(color);
        }

        public int ARGB(int alpha) {
            return Color.argb(alpha, this.red, this.green, this.blue);
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public int getResponse() {
            return this.response;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("ColorDialogHSV")
    public static class ColorDialogHSV {
        /* access modifiers changed from: private */
        public float[] hsv = new float[3];
        private int response;

        /* access modifiers changed from: private */
        public GradientDrawable getColor(int color, float radius) {
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{color, color});
            gd.setCornerRadius(radius);
            return gd;
        }

        private GradientDrawable getGradient(int[] colors, float radius) {
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
            gd.setCornerRadius(radius);
            return gd;
        }

        public int Show(String title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            float diptopx = ba.context.getResources().getDisplayMetrics().density;
            int dps20 = (int) (20.0f * diptopx);
            int dps10 = (int) (10.0f * diptopx);
            int dps05 = (int) (5.0f * diptopx);
            LinearLayout view = new LinearLayout(ba.context);
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            view.setOrientation(1);
            TextView textView = new TextView(ba.context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.setMargins(dps20, dps10, dps20, dps20);
            layoutParams.height = dps20 * 2;
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundDrawable(getColor(Color.HSVToColor(this.hsv), (float) dps05));
            view.addView(textView);
            TextView textView2 = new TextView(ba.context);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams2.setMargins(dps20, 0, dps20, 0);
            textView2.setLayoutParams(layoutParams2);
            textView2.setGravity(3);
            textView2.setText("Hue = " + Float.toString(this.hsv[0]));
            view.addView(textView2);
            SeekBar seekBar = new SeekBar(ba.context);
            seekBar.setMax(3600);
            seekBar.setProgress((int) (this.hsv[0] * 10.0f));
            seekBar.setBackgroundDrawable(getGradient(new int[]{Colors.Red, Colors.Green, Colors.Blue, Colors.Red}, (float) dps05));
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
            layoutParams3.setMargins(dps10, dps10, dps10, dps10);
            seekBar.setLayoutParams(layoutParams3);
            view.addView(seekBar);
            final TextView textView3 = textView2;
            final TextView textView4 = textView;
            final int i = dps05;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ColorDialogHSV.this.hsv[0] = ((float) progress) / 10.0f;
                    textView3.setText("Hue = " + Float.toString(ColorDialogHSV.this.hsv[0]));
                    textView4.setBackgroundDrawable(ColorDialogHSV.this.getColor(Color.HSVToColor(ColorDialogHSV.this.hsv), (float) i));
                }
            });
            TextView textView5 = new TextView(ba.context);
            textView5.setLayoutParams(layoutParams2);
            textView5.setGravity(3);
            textView5.setText("Saturation = " + Float.toString(this.hsv[1]));
            view.addView(textView5);
            SeekBar seekBar2 = new SeekBar(ba.context);
            seekBar2.setMax(1000);
            seekBar2.setProgress((int) (this.hsv[1] * 1000.0f));
            seekBar2.setBackgroundDrawable(getGradient(new int[]{Colors.DarkGray, -1}, (float) dps05));
            seekBar2.setLayoutParams(layoutParams3);
            view.addView(seekBar2);
            final TextView textView6 = textView5;
            final TextView textView7 = textView;
            final int i2 = dps05;
            seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ColorDialogHSV.this.hsv[1] = ((float) progress) / 1000.0f;
                    textView6.setText("Saturation = " + Float.toString(ColorDialogHSV.this.hsv[1]));
                    textView7.setBackgroundDrawable(ColorDialogHSV.this.getColor(Color.HSVToColor(ColorDialogHSV.this.hsv), (float) i2));
                }
            });
            TextView textView8 = new TextView(ba.context);
            textView8.setLayoutParams(layoutParams2);
            textView8.setGravity(3);
            textView8.setText("Value = " + Float.toString(this.hsv[2]));
            view.addView(textView8);
            SeekBar seekBar3 = new SeekBar(ba.context);
            seekBar3.setMax(1000);
            seekBar3.setProgress((int) (this.hsv[2] * 1000.0f));
            seekBar3.setBackgroundDrawable(getGradient(new int[]{Colors.DarkGray, -1}, (float) dps05));
            seekBar3.setLayoutParams(layoutParams3);
            final TextView textView9 = textView8;
            final TextView textView10 = textView;
            final int i3 = dps05;
            seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ColorDialogHSV.this.hsv[2] = ((float) progress) / 1000.0f;
                    textView9.setText("Value = " + Float.toString(ColorDialogHSV.this.hsv[2]));
                    textView10.setBackgroundDrawable(ColorDialogHSV.this.getColor(Color.HSVToColor(ColorDialogHSV.this.hsv), (float) i3));
                }
            });
            view.addView(seekBar3);
            ad.setView(view);
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            ad.setTitle(title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dialogResponse);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dialogResponse);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dialogResponse);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            this.response = dialogResponse.res;
            return dialogResponse.res;
        }

        public float getHue() {
            return this.hsv[0];
        }

        public void setHue(float hue) {
            this.hsv[0] = hue;
        }

        public float getSaturation() {
            return this.hsv[1];
        }

        public void setSaturation(float saturation) {
            this.hsv[1] = saturation;
        }

        public float getValue() {
            return this.hsv[2];
        }

        public void setValue(float value) {
            this.hsv[2] = value;
        }

        public int getRGB() {
            return Color.HSVToColor(this.hsv);
        }

        public void setRGB(int color) {
            Color.colorToHSV(color, this.hsv);
        }

        public int ARGB(int alpha) {
            return Color.HSVToColor(alpha, this.hsv);
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public int getResponse() {
            return this.response;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("ColorPickerDialog")
    public static class ColorPickerDialog {
        /* access modifiers changed from: private */
        public int chosen;
        int green = Colors.RGB(0, FtdiSerialDriver.USB_ENDPOINT_IN, 0);
        int maroon = Colors.RGB(FtdiSerialDriver.USB_ENDPOINT_IN, 0, 0);
        int navy = Colors.RGB(0, 0, FtdiSerialDriver.USB_ENDPOINT_IN);
        int orange = Colors.RGB(255, 140, 0);
        private int[] palette = new int[this.stdpalette.length];
        private int response;
        int royalblue = Colors.RGB(65, 105, 225);
        int springgreen = Colors.RGB(0, 255, 127);
        private int[] stdpalette = {-65536, -65281, this.orange, this.maroon, -16777216, -16711936, -256, this.springgreen, this.green, -3355444, -16776961, -16711681, this.royalblue, this.navy, -1};

        public ColorPickerDialog() {
            ResetPalette();
        }

        /* access modifiers changed from: private */
        public GradientDrawable getColor(int color, float radius) {
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{color, color});
            gd.setCornerRadius(radius);
            return gd;
        }

        public int Show(String title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            float diptopx = ba.context.getResources().getDisplayMetrics().density;
            int dps40 = (int) (40.0f * diptopx);
            int dps20 = (int) (20.0f * diptopx);
            int dps05 = (int) (5.0f * diptopx);
            LinearLayout linearLayout = new LinearLayout(ba.context);
            linearLayout.setOrientation(1);
            TextView textView = new TextView(ba.context);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-1, -1);
            layoutParams.setMargins(dps20, (int) (10.0f * diptopx), dps20, dps20);
            layoutParams.height = dps40;
            layoutParams.width = -1;
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundDrawable(getColor(this.chosen, (float) dps05));
            linearLayout.addView(textView);
            TableLayout view = new TableLayout(ba.context);
            view.setLayoutParams(new TableLayout.LayoutParams(-1, -2));
            view.setOrientation(1);
            view.setStretchAllColumns(true);
            for (int j = 0; j < 3; j++) {
                TableRow tableRow = new TableRow(ba.context);
                for (int i = 0; i < 5; i++) {
                    TextView textView2 = new TextView(ba.context);
                    TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(-1, -1);
                    layoutParams2.setMargins(dps05, dps05, dps05, 5);
                    layoutParams2.height = dps40;
                    layoutParams2.width = -1;
                    textView2.setLayoutParams(layoutParams2);
                    textView2.setTag(Integer.valueOf(this.palette[(j * 5) + i]));
                    textView2.setBackgroundDrawable(getColor(this.palette[(j * 5) + i], (float) dps05));
                    final TextView textView3 = textView;
                    final int i2 = dps05;
                    textView2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            ColorPickerDialog.this.chosen = ((Integer) v.getTag()).intValue();
                            textView3.setBackgroundDrawable(ColorPickerDialog.this.getColor(ColorPickerDialog.this.chosen, (float) i2));
                        }
                    });
                    tableRow.addView(textView2);
                }
                view.addView(tableRow);
            }
            linearLayout.addView(view);
            ad.setView(linearLayout);
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            ad.setTitle(title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dialogResponse);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dialogResponse);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dialogResponse);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            this.response = dialogResponse.res;
            return dialogResponse.res;
        }

        public int[] getPalette() {
            int[] newpalette = new int[this.palette.length];
            for (int i = 0; i < this.palette.length; i++) {
                newpalette[i] = this.palette[i];
            }
            return newpalette;
        }

        public void setPalette(int[] palette2) {
            int numcols = Math.min(15, palette2.length);
            for (int i = 0; i < numcols; i++) {
                this.palette[i] = palette2[i];
            }
        }

        public void ResetPalette() {
            for (int i = 0; i < this.stdpalette.length; i++) {
                this.palette[i] = this.stdpalette[i];
            }
        }

        public int getRGB() {
            return this.chosen;
        }

        public void setRGB(int color) {
            this.chosen = -16777216 | color;
        }

        public int GetPaletteAt(int index) {
            return this.palette[index];
        }

        public void SetPaletteAt(int index, int color) {
            this.palette[index] = -16777216 | color;
        }

        public int ARGB(int alpha) {
            return Color.argb(alpha, (this.chosen >> 16) & 255, (this.chosen >> 8) & 255, this.chosen & 255);
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public int getResponse() {
            return this.response;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("NumberDialog")
    public static class NumberDialog {
        /* access modifiers changed from: private */
        public int decimal = 0;
        /* access modifiers changed from: private */
        public char decimalchar = '.';
        /* access modifiers changed from: private */
        public TextView[] figures = new TextView[9];
        private final int maxdigits = 9;
        private boolean negative = false;
        /* access modifiers changed from: private */
        public int[] numbers = new int[9];
        /* access modifiers changed from: private */
        public int numdigits = 5;
        private int response;
        /* access modifiers changed from: private */
        public boolean showsign = false;

        public int Show(String title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) {
            String str;
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            float diptopx = ba.context.getResources().getDisplayMetrics().density;
            int dps50 = (int) (50.0f * diptopx);
            int dps40 = (int) (40.0f * diptopx);
            int dps05 = (int) (5.0f * diptopx);
            TableLayout view = new TableLayout(ba.context);
            view.setLayoutParams(new TableLayout.LayoutParams(-1, -2));
            view.setOrientation(1);
            view.setStretchAllColumns(true);
            TableRow tableRow = new TableRow(ba.context);
            for (int i = this.numdigits - 1; i >= 0; i--) {
                Button button = new Button(ba.context);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-1, -1);
                layoutParams.setMargins(0, 0, 0, 0);
                layoutParams.height = dps50;
                layoutParams.width = -1;
                button.setLayoutParams(layoutParams);
                button.setPadding(0, -dps05, 0, 0);
                button.setText("+");
                button.setTextSize(32.0f);
                button.setTag(Integer.valueOf(i));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int i = ((Integer) v.getTag()).intValue();
                        if (!NumberDialog.this.showsign || i != NumberDialog.this.numdigits - 1) {
                            int[] access$3 = NumberDialog.this.numbers;
                            access$3[i] = access$3[i] + 1;
                            if (NumberDialog.this.numbers[i] > 9) {
                                NumberDialog.this.numbers[i] = 0;
                            }
                            NumberDialog.this.figures[i].setText(Integer.toString(NumberDialog.this.numbers[i]));
                            if (NumberDialog.this.decimal > 0 && NumberDialog.this.decimal == i) {
                                NumberDialog.this.figures[i].setText(String.valueOf(Integer.toString(NumberDialog.this.numbers[i])) + NumberDialog.this.decimalchar);
                                return;
                            }
                            return;
                        }
                        NumberDialog.this.figures[i].setText("+");
                    }
                });
                tableRow.addView(button);
            }
            view.addView(tableRow);
            TableRow tableRow2 = new TableRow(ba.context);
            for (int i2 = this.numdigits - 1; i2 >= 0; i2--) {
                TextView textView = new TextView(ba.context);
                TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(-1, -1);
                layoutParams2.setMargins(0, 0, 0, 0);
                layoutParams2.height = dps40;
                layoutParams2.width = -1;
                textView.setLayoutParams(layoutParams2);
                textView.setPadding(0, 0, 0, 0);
                textView.setGravity(17);
                textView.setText(Integer.toString(this.numbers[i2]));
                if (this.decimal > 0 && this.decimal == i2) {
                    textView.setText(String.valueOf(Integer.toString(this.numbers[i2])) + this.decimalchar);
                }
                textView.setTextSize(32.0f);
                textView.setTag(Integer.valueOf(i2));
                tableRow2.addView(textView);
                this.figures[i2] = textView;
                if (this.showsign && i2 == this.numdigits - 1) {
                    TextView textView2 = this.figures[i2];
                    if (this.negative) {
                        str = "-";
                    } else {
                        str = "+";
                    }
                    textView2.setText(str);
                }
            }
            view.addView(tableRow2);
            TableRow tableRow3 = new TableRow(ba.context);
            for (int i3 = this.numdigits - 1; i3 >= 0; i3--) {
                Button button2 = new Button(ba.context);
                TableRow.LayoutParams layoutParams3 = new TableRow.LayoutParams(-1, -1);
                layoutParams3.setMargins(0, 0, 0, 0);
                layoutParams3.height = dps50;
                layoutParams3.width = -1;
                button2.setLayoutParams(layoutParams3);
                button2.setPadding(0, -dps05, 0, 0);
                button2.setText("-");
                button2.setTextSize(32.0f);
                button2.setTag(Integer.valueOf(i3));
                button2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int i = ((Integer) v.getTag()).intValue();
                        if (!NumberDialog.this.showsign || i != NumberDialog.this.numdigits - 1) {
                            int[] access$3 = NumberDialog.this.numbers;
                            access$3[i] = access$3[i] - 1;
                            if (NumberDialog.this.numbers[i] < 0) {
                                NumberDialog.this.numbers[i] = 9;
                            }
                            NumberDialog.this.figures[i].setText(Integer.toString(NumberDialog.this.numbers[i]));
                            if (NumberDialog.this.decimal > 0 && NumberDialog.this.decimal == i) {
                                NumberDialog.this.figures[i].setText(String.valueOf(Integer.toString(NumberDialog.this.numbers[i])) + NumberDialog.this.decimalchar);
                                return;
                            }
                            return;
                        }
                        NumberDialog.this.figures[i].setText("-");
                    }
                });
                tableRow3.addView(button2);
            }
            view.addView(tableRow3);
            ad.setView(view);
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            ad.setTitle(title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dialogResponse);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dialogResponse);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dialogResponse);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            this.response = dialogResponse.res;
            return dialogResponse.res;
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public int getNumber() {
            int num = 0;
            for (int i = (this.numdigits - 1) - (this.showsign ? 1 : 0); i >= 0; i--) {
                num = (num * 10) + this.numbers[i];
            }
            if (!this.showsign || this.figures[this.numdigits - 1].getText() != "-") {
                return num;
            }
            return -num;
        }

        public void setNumber(int number) {
            this.negative = number < 0;
            int number2 = Math.abs(number);
            for (int i = 0; i < this.numdigits; i++) {
                this.numbers[i] = number2 % 10;
                number2 /= 10;
            }
        }

        public int getDigits() {
            return this.numdigits;
        }

        public void setDigits(int digits) {
            this.numdigits = Math.min(9, Math.max(digits, 1));
        }

        public int getDecimal() {
            return this.decimal;
        }

        public void setDecimal(int digits) {
            this.decimal = Math.min(8, Math.max(digits, 0));
        }

        public char getDecimalChar() {
            return this.decimalchar;
        }

        public void setDecimalChar(char decimalchar2) {
            this.decimalchar = decimalchar2;
        }

        public boolean getShowSign() {
            return this.showsign;
        }

        public void setShowSign(boolean show) {
            this.showsign = show;
        }

        public int getResponse() {
            return this.response;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("FileDialog")
    public static class FileDialog {
        /* access modifiers changed from: private */
        public String chosenname = "";
        private boolean fastscroll = false;
        private String[] fileList;
        /* access modifiers changed from: private */
        public File filepath = new File("//");
        /* access modifiers changed from: private */
        public String fileseparator = "↓ Files ↓";
        /* access modifiers changed from: private */
        public String ftype = "";
        /* access modifiers changed from: private */
        public String[] ftypes = this.ftype.split(",");
        private boolean keyboardpopup = true;
        private boolean onlyfolders = false;
        private int response;
        private int scrollcol = Colors.ARGB(230, 72, 72, 72);

        private class sorter implements Comparator<String> {
            private sorter() {
            }

            /* synthetic */ sorter(FileDialog fileDialog, sorter sorter) {
                this();
            }

            public int compare(String arg0, String arg1) {
                return arg0.compareToIgnoreCase(arg1);
            }
        }

        /* access modifiers changed from: private */
        public void loadFileList(SimpleListAdapter adapter) throws RuntimeException {
            final ArrayList<String> aldirs = new ArrayList<>();
            final ArrayList<String> alfiles = new ArrayList<>();
            adapter.items.clear();
            if (this.filepath.exists()) {
                this.fileList = this.filepath.list(new FilenameFilter() {
                    public boolean accept(File dir, String filename) {
                        if (new File(dir, filename).isDirectory()) {
                            aldirs.add(filename);
                            return true;
                        }
                        for (String contains : FileDialog.this.ftypes) {
                            if (filename.contains(contains) || FileDialog.this.ftype == "") {
                                alfiles.add(filename);
                                return true;
                            }
                        }
                        return false;
                    }
                });
                String[] dirs = (String[]) aldirs.toArray(new String[0]);
                String[] files = (String[]) alfiles.toArray(new String[0]);
                Arrays.sort(dirs, new sorter(this, (sorter) null));
                Arrays.sort(files, new sorter(this, (sorter) null));
                if (this.onlyfolders) {
                    this.fileList = new String[(dirs.length + 1)];
                    this.fileList[0] = "..";
                    System.arraycopy(dirs, 0, this.fileList, 1, dirs.length);
                } else {
                    this.fileList = new String[(dirs.length + files.length + 2)];
                    this.fileList[0] = "..";
                    this.fileList[dirs.length + 1] = this.fileseparator;
                    System.arraycopy(dirs, 0, this.fileList, 1, dirs.length);
                    System.arraycopy(files, 0, this.fileList, dirs.length + 2, files.length);
                }
                for (String str : this.fileList) {
                    SimpleListAdapter.SingleLineData sl = new SimpleListAdapter.SingleLineData();
                    sl.Text = str;
                    sl.ReturnValue = null;
                    adapter.items.add(sl);
                }
                adapter.notifyDataSetChanged();
                return;
            }
            throw new RuntimeException("Path '" + this.filepath.getPath() + "'does not exist");
        }

        public int Show(String Title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) throws RuntimeException {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            int dps10 = (int) (10.0f * ba.context.getResources().getDisplayMetrics().density);
            LinearLayout view = new LinearLayout(ba.context);
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            view.setOrientation(1);
            EditText editText = new EditText(ba.context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            layoutParams.setMargins(dps10, 0, dps10, 0);
            editText.setLayoutParams(layoutParams);
            editText.setGravity(7);
            editText.setHorizontallyScrolling(true);
            editText.setImeOptions(6);
            editText.setSingleLine();
            editText.setText(this.chosenname);
            view.addView(editText);
            ListView listView = new ListView(ba.context);
            listView.setFastScrollEnabled(this.fastscroll);
            listView.setCacheColorHint(this.scrollcol);
            SimpleListAdapter simpleListAdapter = new SimpleListAdapter(ba.context);
            listView.setAdapter(simpleListAdapter);
            listView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            loadFileList(simpleListAdapter);
            if (!this.keyboardpopup) {
                listView.requestFocus();
            }
            final SimpleListAdapter simpleListAdapter2 = simpleListAdapter;
            final EditText editText2 = editText;
            final ListView listView2 = listView;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String chosenfile = (String) simpleListAdapter2.getItem(position);
                    chosenfile.equals("..");
                    File sel = new File(FileDialog.this.filepath, chosenfile);
                    if (sel.isDirectory()) {
                        editText2.setText("");
                        FileDialog.this.filepath = sel;
                        try {
                            FileDialog.this.loadFileList(simpleListAdapter2);
                            listView2.setSelection(0);
                        } catch (RuntimeException e) {
                            RuntimeException e2 = e;
                            if (e2 instanceof NoSuchElementException) {
                                Common.Log(String.valueOf(e2.toString()) + " " + FileDialog.this.filepath + " " + chosenfile);
                            }
                            throw e2;
                        }
                    } else if (!chosenfile.equals(FileDialog.this.fileseparator)) {
                        editText2.setText(chosenfile);
                        FileDialog.this.chosenname = chosenfile;
                    }
                }
            });
            view.addView(listView);
            ad.setView(view);
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            ad.setTitle(Title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dialogResponse);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dialogResponse);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dialogResponse);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            this.chosenname = editText.getText().toString();
            this.response = dialogResponse.res;
            return dialogResponse.res;
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public String getFileFilter() {
            return this.ftype;
        }

        public void setFileFilter(String filetype) {
            this.ftype = filetype;
            this.ftypes = this.ftype.split(",");
        }

        public String getFilePath() throws Exception {
            return this.filepath.getCanonicalPath();
        }

        public void setFilePath(String path) {
            this.filepath = new File(path, "//");
            this.chosenname = "";
        }

        public boolean getFastScroll() {
            return this.fastscroll;
        }

        public void setFastScroll(boolean fastscroll2) {
            this.fastscroll = fastscroll2;
        }

        public String getChosenName() {
            return this.chosenname;
        }

        public void setChosenName(String filename) {
            this.chosenname = filename;
        }

        public int getResponse() {
            return this.response;
        }

        public void setScrollingBackgroundColor(int scrollcolor) {
            this.scrollcol = scrollcolor;
        }

        public int getScrollingBackgroundColor() {
            return this.scrollcol;
        }

        public void setShowOnlyFolders(boolean onlyfolders2) {
            this.onlyfolders = onlyfolders2;
        }

        public boolean getShowOnlyFolders() {
            return this.onlyfolders;
        }

        public void setKeyboardPopUp(boolean keyboardpopup2) {
            this.keyboardpopup = keyboardpopup2;
        }

        public boolean getKeyboardPopUp() {
            return this.keyboardpopup;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("CustomDialog")
    public static class CustomDialog {
        private View cview;
        private int response;

        public int Show(String Title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) throws RuntimeException {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            AbsoluteLayout view = new AbsoluteLayout(ba.context);
            view.setLayoutParams(new AbsoluteLayout.LayoutParams(0, 0, 0, 0));
            view.addView(this.cview);
            ad.setView(view);
            Msgbox.DialogResponse dr = new Msgbox.DialogResponse(false);
            ad.setTitle(Title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dr);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dr);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dr);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            view.removeView(this.cview);
            this.response = dr.res;
            Msgbox.sendCloseMyLoopMessage();
            Msgbox.waitForMessage(false, true);
            ((InputMethodManager) BA.applicationContext.getSystemService("input_method")).hideSoftInputFromWindow(ba.vg.getWindowToken(), 0);
            return dr.res;
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public void AddView(View view, int left, int top, int width, int height) {
            this.cview = view;
            this.cview.setLayoutParams(new AbsoluteLayout.LayoutParams(width, height, left, top));
        }

        public int getResponse() {
            return this.response;
        }
    }

    @BA.ActivityObject
    @BA.ShortName("CustomDialog2")
    public static class CustomDialog2 {
        private View cview;
        private int response;

        public int Show(String Title, String Positive, String Cancel, String Negative, BA ba, Bitmap icon) throws RuntimeException {
            AlertDialog ad = new AlertDialog.Builder(ba.context).create();
            LinearLayout view = new LinearLayout(ba.context);
            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(-1, -1);
            view.setGravity(17);
            view.setLayoutParams(lparams);
            view.addView(this.cview);
            ad.setView(view);
            Msgbox.DialogResponse dr = new Msgbox.DialogResponse(false);
            ad.setTitle(Title);
            if (Positive.length() > 0) {
                ad.setButton(-1, Positive, dr);
            }
            if (Negative.length() > 0) {
                ad.setButton(-2, Negative, dr);
            }
            if (Cancel.length() > 0) {
                ad.setButton(-3, Cancel, dr);
            }
            if (icon != null) {
                ad.setIcon(new BitmapDrawable(icon));
            }
            Msgbox.msgbox(ad, false);
            view.removeView(this.cview);
            this.response = dr.res;
            Msgbox.sendCloseMyLoopMessage();
            Msgbox.waitForMessage(false, true);
            ((InputMethodManager) BA.applicationContext.getSystemService("input_method")).hideSoftInputFromWindow(ba.vg.getWindowToken(), 0);
            return dr.res;
        }

        public double getVersion() {
            return InputDialog.version;
        }

        public void AddView(View view, int width, int height) {
            this.cview = view;
            this.cview.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }

        public int getResponse() {
            return this.response;
        }
    }
}
