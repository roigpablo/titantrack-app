package com.titantrack.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFS_NAME = "titantrack_session";
    private static final String KEY_IS_LOGGED = "isLogged";
    private static final String KEY_USER_EMAIL = "userEmail";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(String email) {
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED, true)
                .putString(KEY_USER_EMAIL, email)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED, false);
    }

    public String getUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, "");
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}