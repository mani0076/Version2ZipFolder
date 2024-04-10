package com.movieinformation.versionnumber2;

import android.content.Context;
import android.content.SharedPreferences;
/*
   Utility class for managing shared preferences.
*/
public class SharedPrefs {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /*
      Initializes the shared preferences object.
      @param context The application context.
   */
    public void initialisePrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_name),
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /*
      Saves a string value to shared preferences.
      @param key The key under which to save the value.
      @param value The string value to save.
   */
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    /*
      Retrieves a string value from shared preferences.
      @param key The key of the value to retrieve.
      @param en The default value to return if the key is not found.
      @return The string value associated with the key, or the default value if the key is not found.
   */
    public String getString(String key, String en) {
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, true);
    }
}
