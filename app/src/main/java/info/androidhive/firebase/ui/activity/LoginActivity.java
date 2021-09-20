package info.androidhive.firebase.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import info.androidhive.firebase.MainActivity;
import info.androidhive.firebase.R;
import info.androidhive.firebase.ResetPasswordActivity;
import info.androidhive.firebase.SignupActivity;
import info.androidhive.firebase.VideoDisplay;
import info.androidhive.firebase.ViewUsersActivity;
import info.androidhive.firebase.common.Constants;
import info.androidhive.firebase.common.IPreferencesKeys;
import info.androidhive.firebase.ui.util.VideoCallAlert;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private DatabaseReference mDatabase;
    public static boolean checkToFillForm;
    public static String loggedUserEmail;
    public static boolean checkAdminRole;
    protected SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.preferences = getApplicationContext().getSharedPreferences(getApplicationContext()
                .getPackageName(), Context.MODE_PRIVATE);

        if (auth.getCurrentUser() != null) {
            if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
                checkAdminRole = true;
            }
            loggedUserEmail = auth.getCurrentUser().getEmail();
            Query query = mDatabase.child("FormDetails").orderByChild("email")
                    .equalTo(auth.getCurrentUser().getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(checkAdminRole){
                        startActivity(new Intent(LoginActivity.this, ViewUsersActivity.class));
                        finish();
                    }else {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            checkToFillForm = false;
                            startActivity(new Intent(LoginActivity.this, VideoDisplay.class));
                            finish();
                        } else {
                            checkToFillForm = true;
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            finish();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(v -> startActivity(
                new Intent(LoginActivity.this, SignupActivity.class)));

        btnReset.setOnClickListener(v -> startActivity(
                new Intent(LoginActivity.this, ResetPasswordActivity.class)));

        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            final String password = inputPassword.getText().toString();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            //authenticate user
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            preferences.edit().putString(IPreferencesKeys.USER_ACTIVE_STATUS,
                                    "Active").apply();
                            preferences.edit().putString(IPreferencesKeys.USER_EMAIL,
                                    inputEmail.getText().toString()).apply();
                            showMessage("Success");

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
        });
    }

    public void showMessage(String message) {
        VideoCallAlert.show(LoginActivity.this, Constants.ERROR, message);
    }
}

