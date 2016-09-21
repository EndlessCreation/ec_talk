package kr.re.ec.talk.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import kr.re.ec.talk.App;

/**
 * Created by slhyv on 9/14/2016.
 */
public class PrefUtil {
    /**
     * KEY SHOULD BE READ FROM strings.xml
     * */
    public static final String PREFERENCES_KEY_MY_TOKEN = "PREFERENCES_KEY_MY_TOKEN";
    public static final String PREFERENCES_KEY_MY_NICKNAME = "PREFERENCES_KEY_MY_NICKNAME";

    public static String getString(String key, String defValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(App.context);
        return pref.getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(App.context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(App.context);
        return pref.getBoolean(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(App.context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void remove(String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(App.context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
}
