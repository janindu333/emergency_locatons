package info.androidhive.firebase;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.androidhive.firebase.Model.FormData;
import info.androidhive.firebase.Model.VideoData;
import info.androidhive.firebase.common.CommonUtils;
import info.androidhive.firebase.common.IPreferencesKeys;
import info.androidhive.firebase.dto.EmergencyLocation;
import info.androidhive.firebase.ui.activity.BaseActivity;
import info.androidhive.firebase.ui.activity.LoginActivity;
import info.androidhive.firebase.ui.fragment.GoogleMapFragment;
import info.androidhive.firebase.ui.fragment.HomeFragment;
import info.androidhive.firebase.ui.util.IOnCompletionListener;
import info.androidhive.firebase.ui.util.VideoCallAlert;
import info.androidhive.firebase.ui.util.VideoCallAnimator;
import lombok.Getter;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener, LocationListener , FloatingActionMenu.OnMenuToggleListener{

    private FirebaseAuth auth;
    WebView browser;
    EditText name;
    EditText mobileNumber;
    EditText email;
    EditText bankName;
    EditText branchName;
    EditText accountNumber;
    Button submitBtn;
    private DatabaseReference mDatabase;
    AdView adView;
    AdRequest adRequest;
    private SharedPreferences preferences;
    private HomeFragment homeFragment;
    public GoogleMapFragment googleMapFragment;
    protected LocationManager locationManager;
    private IOnCompletionListener listener;

    private boolean isLocationAlertShown;
    private boolean didRequestGoogleLocationPermission;
    private static final int SETTINGS_ACTIVITY = 001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 002;
    private static final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 003;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000; //

    public boolean isMenuOpen;

    @Getter
    private Location mLocation;

    @Getter
    private List<EmergencyLocation> locationList;

    private int mLocationUpdateCount;

    @BindView(R.id.progress_view)
    RelativeLayout progressView;
    @BindView(R.id.navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.fab_menu)
    FloatingActionMenu floatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        didRequestGoogleLocationPermission = false;
        locationList = new ArrayList<>();
        performLocationRequest(null);
        initFragment(getHomeFragment());
    }

    public void showProgressBar() {
        hideProgressBar();
        VideoCallAnimator.fadeIn(progressView, 250);
    }

    public void hideProgressBar() {
        if (progressView.getVisibility() == View.VISIBLE) {
            VideoCallAnimator.fadeOut(progressView);
        }
    }

    public void onExit() {
        finish();
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                fragment = getHomeFragment();
                break;
            case R.id.action_map:
                didRequestGoogleLocationPermission = false;
                fragment = getLocationFragment();
                break;
        }

        initFragment(fragment);

        return true;
    }

    private Fragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            homeFragment.setArguments(getIntent().getExtras());
        }

        return homeFragment;
    }

    private Fragment getLocationFragment() {
        if (googleMapFragment == null) {
            googleMapFragment = new GoogleMapFragment();
            googleMapFragment.setArguments(getIntent().getExtras());
        }
        return googleMapFragment;
    }

    private void initFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (fragment != null && supportFragmentManager != null
                && !supportFragmentManager.isDestroyed()) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.basic_fragment, fragment).commitAllowingStateLoss();
        }
    }

    public void requestDeviceLocationService(IOnCompletionListener completionListener) {
        final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> task =
                LocationServices.getSettingsClient(this)
                        .checkLocationSettings(builder.build());

        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);
                findMyLocationWithLocationPermission();
            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            if (!didRequestGoogleLocationPermission) {
                                didRequestGoogleLocationPermission = true;
                                ResolvableApiException resolvable =
                                        (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(MainActivity.this,
                                        PERMISSIONS_REQUEST_ACCESS_LOCATION);
                            }
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        } catch (ClassCastException e) {
                            // Ignore, should be an impossible error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    public void findMyLocationWithLocationPermission() {
        if (!this.isFinishing()) {
            if ((ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)) {
                if (!isLocationAlertShown) {
                    isLocationAlertShown = true;
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            } else if ((ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                getLocation();
            }
        }
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) BaseApplication.getBaseApplication()
                    .getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;

            try {

                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled =
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
                System.out.println(ex);
            }

            String provider = isNetworkEnabled ?
                    LocationManager.NETWORK_PROVIDER :
                    isGPSEnabled ?
                            LocationManager.GPS_PROVIDER :
                            null;

            if (provider == null) {
                requestDeviceLocationService(null);
            } else {
                if (ActivityCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        mLocation = locationManager.getLastKnownLocation(provider);
                    }

                    if (mLocation == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            mLocation = locationManager.getLastKnownLocation(provider);

                        }
                    }

                    if (mLocation != null && listener != null) {
                        listener.onComplete();
                        this.listener = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopUsingGPS();
        mLocationUpdateCount = 0;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocationUpdateCount++;
        if (location != null) {
            mLocation = location;
            if (listener != null) {
                listener.onComplete();
                this.listener = null;
            }
        }
    }

    public void showLocationResponse(List<EmergencyLocation> locationList) {

        if (locationList.size() > 0) {
            this.locationList.clear();
            this.locationList.addAll(locationList);
            if (listener != null) {
                listener.onComplete();
            }
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationUpdateCount == 0) {
            requestDeviceLocationService(listener);
        }
    }

    public void performLocationRequest(final IOnCompletionListener completionListener) {
        if (!MainActivity.this.isFinishing()) {
            if (!CommonUtils.getInstance().isNetworkConnected()) {
                VideoCallAlert.show(MainActivity.this, getString(R.string.warning),
                        getString(R.string.internet_connection_lost), getString(R.string.try_again),
                        getString(R.string.cancel), (dialog, which) -> {
                            dialog.dismiss();
                            performLocationRequest(completionListener);
                        }, (dialog, which) -> dialog.dismiss());
            } else {
                this.listener = completionListener;
                EmergencyLocation emergencyLocation = new EmergencyLocation();
                emergencyLocation.setLongitude("69.265");
                emergencyLocation.setLatitude("69.254");
                emergencyLocation.setLocation("Moratuwa");

                List<EmergencyLocation> locationList = new ArrayList<>();
                locationList.add(emergencyLocation);
               showLocationResponse(locationList);
              //  ((LocationPresenter) locationPresenter).getActiveChargingPointLocations();
            }
        }
    }

    @OnClick(R.id.fab_logout)
    public void logout() {
        clearUserData();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("TOKEN_EX_MSG", "TOKEN_EX_MSG");
        startActivity(intent);
        finish();
    }

    public void clearUserData() {
        preferences.edit().clear().apply();
    }

    @Override
    public void onMenuToggle(boolean b) {

    }
}