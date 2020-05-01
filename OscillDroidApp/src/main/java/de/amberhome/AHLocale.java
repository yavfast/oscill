package de.amberhome;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.Locale;

@BA.Author("Markus Stipp")
@BA.ShortName("AHLocale")
public class AHLocale {
    private boolean isInitialized = false;
    private Calendar myCalendar;
    public Locale myLocale;

    private void init(Locale loc) {
        this.myLocale = loc;
        this.myCalendar = new GregorianCalendar(loc);
        this.isInitialized = true;
    }

    public void Initialize() {
        init(Locale.getDefault());
    }

    public void Initialize2(String language) {
        init(new Locale(language, Locale.getDefault().getCountry()));
    }

    public void Initialize3(String language, String country) {
        init(new Locale(language, country));
    }

    public void InitializeUS() {
        init(Locale.US);
    }

    public static List getAvailableLocales() {
        List ret = new List();
        ret.Initialize();
        for (Locale loc : Locale.getAvailableLocales()) {
            if (!(loc.getCountry() == "" || loc.getLanguage() == "")) {
                AHLocale ahloc = new AHLocale();
                ahloc.init(loc);
                ret.Add(ahloc);
            }
        }
        return ret;
    }

    public Boolean getInitialized() {
        return Boolean.valueOf(this.isInitialized);
    }

    public String getISOCode() {
        return this.myLocale.toString();
    }

    public String getCountry() {
        return this.myLocale.getCountry();
    }

    public String getDisplayCountry() {
        return this.myLocale.getDisplayCountry(this.myLocale);
    }

    public String getEnglishCountry() {
        return this.myLocale.getDisplayCountry(Locale.US);
    }

    public String getDisplayName() {
        return this.myLocale.getDisplayName(this.myLocale);
    }

    public String getEnglishName() {
        return this.myLocale.getDisplayName(Locale.US);
    }

    public String getLanguage() {
        return this.myLocale.getLanguage();
    }

    public String getDisplayLanguage() {
        return this.myLocale.getDisplayLanguage(this.myLocale);
    }

    public String getEnglishLanguage() {
        return this.myLocale.getDisplayLanguage(Locale.US);
    }

    public String getISO3Country() {
        return this.myLocale.getISO3Country();
    }

    public String getISO3Language() {
        return this.myLocale.getISO3Language();
    }

    public String[] getISOCountries() {
        return Locale.getISOCountries();
    }

    public String[] getISOLanguages() {
        return Locale.getISOLanguages();
    }

    public String getCurrencySymbol() {
        return Currency.getInstance(this.myLocale).getSymbol();
    }

    public String getCurrencyCode() {
        return Currency.getInstance(this.myLocale).getCurrencyCode();
    }

    public int getCurrencyFractionDigits() {
        return Currency.getInstance(this.myLocale).getDefaultFractionDigits();
    }

    public String[] getAmPmStrings() {
        return new DateFormatSymbols(this.myLocale).getAmPmStrings();
    }

    public String[] getMonths() {
        return new DateFormatSymbols(this.myLocale).getMonths();
    }

    public String[] getShortMonths() {
        return new DateFormatSymbols(this.myLocale).getShortMonths();
    }

    public String[] getWeekDays() {
        return new DateFormatSymbols(this.myLocale).getWeekdays();
    }

    public String[] getShortWeekDays() {
        return new DateFormatSymbols(this.myLocale).getShortWeekdays();
    }

    public int getFirstDayOfWeek() {
        return this.myCalendar.getFirstDayOfWeek();
    }
}
