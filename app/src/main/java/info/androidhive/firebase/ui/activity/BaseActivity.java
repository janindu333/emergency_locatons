package info.androidhive.firebase.ui.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import info.androidhive.firebase.ui.util.VideoCallAlert;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolBar;

    protected void showLongToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void showProgressBar() {
        VideoCallAlert.showProgressBar(this);
    }

    public void hideProgressBar() {
        if (!this.isFinishing()) {
            VideoCallAlert.hideProgressBar(this);
        }
    }
}
