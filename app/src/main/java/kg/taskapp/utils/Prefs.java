package kg.taskapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveShowStatus(){
        preferences.edit().putBoolean("isShown", true).apply();
    }
    public boolean isShown() {
        return preferences.getBoolean("isShown", false);
    }
    public void clearPrefs(){
        preferences
                .edit()
                .clear()
                .apply();
    }


}
