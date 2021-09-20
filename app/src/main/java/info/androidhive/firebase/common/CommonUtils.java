package info.androidhive.firebase.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import info.androidhive.firebase.BaseApplication;


public class CommonUtils {

    private static CommonUtils instance;

    private CommonUtils() {
    }

    public static CommonUtils getInstance() {
        if (instance == null) instance = new CommonUtils();
        return instance;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getBaseApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return  activeNetworkInfo!= null  && activeNetworkInfo.isConnected();
    }

    public void hideKeyboard(Context ctx) {
        try {
            InputMethodManager inputManager =
                    (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            View v = ((Activity) ctx).getCurrentFocus();
            if (v == null) return;
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
