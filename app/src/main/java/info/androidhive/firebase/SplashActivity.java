package info.androidhive.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import info.androidhive.firebase.common.CommonUtils;
import info.androidhive.firebase.common.Constants;
import info.androidhive.firebase.common.IPreferencesKeys;
import info.androidhive.firebase.ui.activity.LoginActivity;
import info.androidhive.firebase.ui.util.VideoCallAlert;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
//            final SharedPreferences preferences =
//                    getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(i);
//            finish();
            new Handler().postDelayed(() -> {
                navigateToApp();
            }, SPLASH_TIME_OUT);
        }, SPLASH_TIME_OUT);

    }

    private void navigateToApp() {
        if (CommonUtils.getInstance().isNetworkConnected()) {
            final SharedPreferences preferences =
                    getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            Class classObj = (preferences.contains(IPreferencesKeys.USER_EMAIL) &&
                    preferences.getString(IPreferencesKeys.USER_ACTIVE_STATUS,
                            Constants.EMPTY_STRING).equals("Active"))
                    ? MainActivity.class : LoginActivity.class;

            Intent i = new Intent(SplashActivity.this, classObj);
            startActivity(i);
            finish();
        } else
            showDialog();
    }

    private void showDialog() {
        VideoCallAlert.tryAgainDialog(SplashActivity.this, getString(R.string.warning),
                getString(R.string.internet_connection_lost), getString(R.string.try_again),
                getString(R.string.cancel), (dialog, which) -> {
                    navigateToApp();
                    dialog.dismiss();
                }, (dialog, which) -> {
                    System.exit(0);
                    dialog.dismiss();
                });


    }
}
