package de.amberhome;

import anywheresoftware.b4a.BA;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@BA.ShortName("AHDateTime")
public class DateTime {
    private String pattern = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat sdf;

    public void Initialize() {
        this.sdf = new SimpleDateFormat();
        this.sdf.setTimeZone(TimeZone.getDefault());
    }

    public void Initialize2(AHLocale locale) {
        this.sdf = new SimpleDateFormat(this.pattern, locale.myLocale);
        this.sdf.setTimeZone(TimeZone.getDefault());
    }

    public void InitializeUS() {
        this.sdf = new SimpleDateFormat(this.pattern, Locale.US);
        this.sdf.setTimeZone(TimeZone.getDefault());
    }

    public void setPattern(String pattern2) {
        this.sdf.applyPattern(pattern2);
    }

    public String getPattern() {
        return this.sdf.toPattern();
    }

    public void setLenient(boolean value) {
        this.sdf.setLenient(value);
    }

    public boolean getLenient() {
        return this.sdf.isLenient();
    }

    public String Format(long ticks) {
        return this.sdf.format(new Date(ticks));
    }

    public long Parse(String date) {
        try {
            return this.sdf.parse(date).getTime();
        } catch (ParseException e) {
            ParseException parseException = e;
            throw new RuntimeException("Unable to parse date string");
        }
    }
}
