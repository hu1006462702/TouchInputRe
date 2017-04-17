package com.ti;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;


/**
 * @author 超级小志
 * 
 */
public class Preference {


	public static void putString(SharedPreferences pref,String key, String value) {
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void putDouble( SharedPreferences pref,String key, double value) {
		Editor editor = pref.edit();
		editor.putString(key, String.valueOf(value));
		editor.commit();
	}

	public static void putInt( SharedPreferences pref,String key, int value) {
		Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void putBoolean( SharedPreferences pref,String key, boolean value) {
		Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static int getInt( SharedPreferences pref,String key) {
		return pref.getInt(key, 0);
	}

	public static int getInt( SharedPreferences pref,String key, int default_value) {
		return pref.getInt(key, default_value);
	}

	public static String getString( SharedPreferences pref,String key) {
		return pref.getString(key, "");
	}

	public static String getString( SharedPreferences pref,String key, String default_value) {
		return pref.getString(key, default_value);
	}

	public static double getDouble( SharedPreferences pref,String key) {
		if (TextUtils.isEmpty(pref.getString(key, ""))) {
			return 0;
		} else {
			return Double.parseDouble(pref.getString(key, ""));
		}
	}

	public static boolean getBoolean( SharedPreferences pref,String key, boolean defValue) {
		return pref.getBoolean(key, defValue);
	}

	public static void onChanged() {

	}
}
