package info.androidhive.firebase;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


import info.androidhive.firebase.common.IPreferencesKeys;
import lombok.Getter;

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;

    @Getter
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = (BaseApplication) getApplicationContext();
        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        preferences.edit().putBoolean(IPreferencesKeys.SHOULD_SHOW_BALANCE, false).apply();
        preferences.edit().putBoolean(IPreferencesKeys.SHOULD_SHOW_CHARGE_HISTORY, false).apply();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

}
