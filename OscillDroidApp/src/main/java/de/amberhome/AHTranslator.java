package de.amberhome;

import android.util.Log;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.util.Locale;

@BA.ShortName("AHTranslator")
public class AHTranslator {
    private String currentFile;
    private String currentLanguage;
    private Map missingTranslation = new Map();
    private Map translation = new Map();

    public void Initialize(String dir, String basename) throws IOException {
        this.translation.Initialize();
        this.missingTranslation.Initialize();
        this.currentLanguage = Locale.getDefault().getLanguage();
        LoadTranslation(dir, basename, this.currentLanguage);
    }

    public void Initialize2(String dir, String basename, String language) throws IOException {
        this.translation.Initialize();
        this.missingTranslation.Initialize();
        LoadTranslation(dir, basename, language);
    }

    private void LoadTranslation(String dir, String file, String language) throws IOException {
        String filename = String.valueOf(file) + "_" + language + ".lng";
        this.currentFile = filename;
        this.currentLanguage = language;
        this.translation.Clear();
        this.missingTranslation.Clear();
        Log.d("LoadTranslation", "FileName:" + filename);
        if (File.Exists(dir, filename)) {
            this.translation = File.ReadMap(dir, filename);
        } else if (File.Exists(File.getDirAssets(), filename)) {
            this.translation = File.ReadMap(dir, filename);
        }
    }

    public void WriteTranslation(String dir, String filename) throws IOException {
        if (this.translation.getSize() > 0) {
            File.WriteMap(dir, String.valueOf(filename) + "_" + this.currentLanguage + ".lng", this.translation);
        }
        if (this.missingTranslation.getSize() > 0) {
            File.WriteMap(dir, String.valueOf(filename) + "_miss_" + this.currentLanguage + ".lng", this.missingTranslation);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 3 */
    public String GetText(String text) {
        if (this.translation.ContainsKey(text)) {
            Log.d("GetText", "Translation: " + this.translation.Get(text));
            return (String) this.translation.Get(text);
        }
        if (!this.missingTranslation.ContainsKey(text)) {
            this.missingTranslation.Put(text, text);
        }
        return text;
    }

    public String GetText2(String text, List params) {
        String myText = GetText(text);
        Log.d("GetText2", "Original: " + myText);
        if (params.getSize() <= 0) {
            return myText;
        }
        String retString = myText;
        for (int i = 0; i < params.getSize(); i++) {
            retString = retString.replace("{" + (i + 1) + "}", params.Get(i).toString());
        }
        String myText2 = retString;
        Log.d("GetText2", "Replaced: " + myText2);
        return myText2;
    }

    public Map getTranslationMap() {
        return this.translation;
    }

    public void setTranslationMap(Map transmap) {
        this.translation = transmap;
    }

    public Map getMissingTranslationMap() {
        return this.missingTranslation;
    }

    public String getCurrentLanguage() {
        return this.currentLanguage;
    }

    public String getCurrentFile() {
        return this.currentFile;
    }
}
