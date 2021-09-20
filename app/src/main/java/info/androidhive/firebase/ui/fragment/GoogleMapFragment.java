package info.androidhive.firebase.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.androidhive.firebase.BaseApplication;
import info.androidhive.firebase.R;
import info.androidhive.firebase.common.IPreferencesKeys;
import info.androidhive.firebase.dto.EmergencyLocation;
import info.androidhive.firebase.ui.helper.GoogleMapHelper.PathDataObject;
import info.androidhive.firebase.ui.helper.GoogleMapHelper.ReadMapData;
import info.androidhive.firebase.ui.util.VideoCallAnimator;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import lombok.Getter;

public class GoogleMapFragment extends BaseFragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private View rootView;
    private static final long DEFAULT_LAT_LON_VALUE = -1000;

    @BindView(R.id.view_route_detail)
    RelativeLayout routeDetailView;
    @BindView(R.id.txt_distance)
    TextView txtDistance;
    @BindView(R.id.txt_time)
    TextView txtTime;

    private SupportMapFragment mapView;
    @Getter
    private GoogleMap map;
    private Marker mLocationMarker;
    private Polyline polyline;

    private List<Marker> markerList;
    private boolean shouldRouteDetailViewShow;
    private boolean isMapLoaded;
    private int mLocationUpdateCount;
    private Circle circle;
    static double currlat;
    static double currLon;

    public double destinationLat;
    public double destinationLon;

    @Getter
    private List<EmergencyLocation> locationList;

    public GoogleMapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_google_map, container, false);
            ButterKnife.bind(GoogleMapFragment.this, rootView);
        } catch (InflateException e) {
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
         mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        super.onViewCreated(view, savedInstanceState);
        if (mainActivity != null && isAdded()) {
            mainActivity.googleMapFragment = GoogleMapFragment.this;

        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        this.isViewVisible = menuVisible;
        if (menuVisible) {
            setUpUI();
        }
    }

    @Override
    public void setUpUI() {
        if (mapView != null) {
        //    setUpGoogleAnalytics();
            destinationLat = -1000;
            destinationLon = -1000;

            locationList = new ArrayList<>();

            isMapLoaded = false;
            routeDetailView.setVisibility(View.INVISIBLE);
            if (destinationLat != DEFAULT_LAT_LON_VALUE &&
                     destinationLon != DEFAULT_LAT_LON_VALUE) {
//                destinationLat = parentFragment.destinationLat;
//                destinationLon = parentFragment.destinationLon;
                shouldRouteDetailViewShow = true;
            } else {
                destinationLat = DEFAULT_LAT_LON_VALUE;
                destinationLon = DEFAULT_LAT_LON_VALUE;
            }
            updateUI();
        }
    }

    @Override
    public void updateUI() {
        if (map != null && isMapLoaded) {
            mapWork();

        } else {
            setUpMap();
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private void setUpGoogleAnalytics() {
        if (mainActivity != null && isAdded()) {
//            Tracker mTracker = ((BaseApplication) mainActivity.getApplication()).getDefaultTracker();
//            mTracker.setScreenName(IGoogleTrackingKeys.MAP_ID);
//            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    @Override
    protected void setUpToolBar() {
    }

    @OnClick(R.id.btn_control)
    public void onHidden() {
        if (routeDetailView.getVisibility() == View.VISIBLE) {
            shouldRouteDetailViewShow = false;
            VideoCallAnimator.slideToTop(routeDetailView);
        }
    }

    //region Google map listeners
    @Override
    public boolean onMyLocationButtonClick() {
        findMyLocation();
        if (mainActivity != null) {
//            moveCamera(mainActivity.getMLocation().getLatitude(),
//                    mainActivity.getMLocation().getLongitude());
        }
        return true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        destinationLat = marker.getPosition().latitude;
        destinationLon = marker.getPosition().longitude;
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            try {
                mapView.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + currlat + "," + currLon +
                                "&daddr=" + destinationLat + "," + destinationLon + "&travelmode=driving")));
            } catch (Exception e) {
            }
        }
        return false;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
//        if (mainActivity != null && isAdded() && selectedLocation != null) {
//            ChargingLocationFragment chargingLocationFragment = new ChargingLocationFragment();
//            chargingLocationFragment.containerFragment = parentFragment;
//            chargingLocationFragment.locationData = selectedLocation;
//            mainActivity.addFragment(chargingLocationFragment, "more");
//        }
    }
    //endregion

    //region Polyline
    public void drawPolylineForDestination(final double endLat, final double endLng) {
        shouldRouteDetailViewShow = true;
        if (mainActivity != null && isAdded()) {
//            if (mainActivity.getMLocation() == null) {
//                mainActivity.requestDeviceLocationService(() -> getLocationList(mainActivity.getMLocation().getLatitude(),
//                        mainActivity.getMLocation().getLongitude(), endLat, endLng));
//            } else {
//                getLocationList(mainActivity.getMLocation().getLatitude(),
//                        mainActivity.getMLocation().getLongitude(), endLat, endLng);
//                moveCamera(mainActivity.getMLocation().getLatitude(),
//                        mainActivity.getMLocation().getLongitude());
//            }
        }
    }

    private void drawPolylineForLocationList(List<LatLng> positions) {
        removePolyline();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(positions);
        polylineOptions.clickable(true);
        polylineOptions.color(Color.GREEN);
        polylineOptions.width(20);
        polyline = map.addPolyline(polylineOptions);
    }



    private String getMapsApiDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service

        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

    }
    //endregion

    //region Custom methods

    private void setUpMap() {
        mapView.getMapAsync(googleMap -> {
            map = googleMap;
            map.getUiSettings().setMapToolbarEnabled(false);
            map.setOnMarkerClickListener(GoogleMapFragment.this);
            map.setOnInfoWindowClickListener(GoogleMapFragment.this);
            isMapLoaded = true;
            findMyLocation();
            mapWork();
        });
    } 

    private void mapWork() {
        clearMap();
        drawRangeCircle();
        if (mainActivity.getMLocation() != null) {
            moveCamera(mainActivity.getMLocation().getLatitude(),
                    mainActivity.getMLocation().getLongitude());
        }

//        if (parentFragment.getFilteredList() != null) {
//            showLocationList(parentFragment.getFilteredList());
//        }
    }






    public void showLocationList(List<EmergencyLocation> locationList) {
        markerList = new ArrayList<>();
        int radius = getRadius();
        if (locationList != null) {
            // show markers
            for (EmergencyLocation location : locationList) {
                String latitude = location.getLatitude();
                String longitude = location.getLongitude();
                if (!latitude.isEmpty() && !longitude.isEmpty()) {
                    Location mLocation = mainActivity.getMLocation();
                    if (mLocation != null) {
                        if (radius > 0) {
                            double dis = SphericalUtil.computeDistanceBetween(
                                    new LatLng(
                                            Double.parseDouble(location.getLatitude()),
                                            Double.parseDouble(location.getLongitude())),
                                    new LatLng(
                                            mLocation.getLatitude(),
                                            mLocation.getLongitude()));
                            if (dis <= (radius * 1000)) {
//                                markerList.add(addMarker(Double.parseDouble(location.getLatitude()),
//                                        Double.parseDouble(location.getLongitude()),
//                                        location.getLocation(),
//                                        location .getAddress(), location.getType(),
//                                        location.getAvailability_status()));
                            }
                        } else {
//                            markerList.add(addMarker(Double.parseDouble(location.getLatitude()),
//                                    Double.parseDouble(location.getLongitude()),
//                                    location.getLocation(),
//                                    location.getAddress(), location.getType(),
//                                    location.getAvailability_status()));
                        }
                    } else {
//                        markerList.add(addMarker(Double.parseDouble(location.getLatitude()),
//                                Double.parseDouble(location.getLongitude()),
//                                location.getLocation(),
//                                location.getAddress(), location.getType(),
//                                location.getAvailability_status()));
                    }
                }
            }
        }
    }

    private void clearMap() {
        if (map != null) {
            removePolyline();
            removeMarkers();
        }
    }

    private void removePolyline() {
        if (polyline != null) polyline.remove();
    }

    private void removeMarkers() {
        if (markerList != null) {
            for (Marker marker : markerList) {
                marker.remove();
            }
            markerList.clear();
        }
    }

    private void findMyLocation() {
        if (mainActivity != null && isAdded()) {
            if (mainActivity.getMLocation() == null) {
                mainActivity.requestDeviceLocationService(() -> updateMap());
            } else {
                updateMap();
            }
        }
    }

    private void updateMap() {
        if (mainActivity != null && isAdded()) {
            if (ActivityCompat.checkSelfPermission(mainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mainActivity,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
         //   map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(GoogleMapFragment.this);
            updateMyLocation();
        }
    }

    private void updateMyLocation() {
        if (this.mLocationMarker != null) {
            this.mLocationMarker.remove();
            this.mLocationMarker = null;
        }

        if (mainActivity != null && mLocationUpdateCount == 0) {
            mLocationUpdateCount++;
            moveCamera(mainActivity.getMLocation().getLatitude(),
                    mainActivity.getMLocation().getLongitude());
        }

        drawRangeCircle();

        if (mainActivity != null && destinationLat != DEFAULT_LAT_LON_VALUE &&
                destinationLon != DEFAULT_LAT_LON_VALUE) {
            getLocationList(mainActivity.getMLocation().getLatitude(),
                    mainActivity.getMLocation().getLongitude(),
                    destinationLat, destinationLon);
        }
    }

    private void getLocationList(final double startLat, final double startLng,
                                 final double endLat, final double endLng) {
        Observable<PathDataObject> locationListObservable =
                Observable.create(e -> {
                    String url = getMapsApiDirectionsUrl(new LatLng(startLat, startLng),
                            new LatLng(endLat, endLng));
                    ReadMapData mapData = new ReadMapData();
                    mapData.emitter = e;
                    mapData.execute(url);
                });

        Observer locationListObserver = new Observer<PathDataObject>() {

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(PathDataObject pathData) {
                drawPolylineForLocationList(pathData.getRoutes());
                if (shouldRouteDetailViewShow) {
                    txtDistance.setText(pathData.getDistance() != null ? pathData.getDistance() :
                            getString(R.string.default_distance));
                    txtTime.setText(pathData.getTime() != null ? pathData.getTime() :
                            getString(R.string.default_time));
                    routeDetailView.setVisibility(View.VISIBLE);
                }
            }
        };

        locationListObservable.subscribe(locationListObserver);
    }


    private void moveCamera(double latitude, double longitude) {
        if (isMapLoaded) {
            int radius = getRadius();
            long zoomLevel = 15;
            if (radius > 0) {
                zoomLevel = radiusToZoomLevel(radius * 1000);
            }
            LatLng sydney = new LatLng(6.9271, 79.8612);
            map.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney"));
            map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoomLevel));
            currlat = latitude;
            currLon = longitude;

        }
    }


    private Marker addMarker(double latitude, double longitude, String title, String snippet,
                             String type, String isAvailable) {
        int markerId = 0;
//        if (type != null && type.equalsIgnoreCase(getString(R.string.l2_type))) {
//            if (isAvailable.equalsIgnoreCase(getString(R.string.available))) {
//                markerId = R.mipmap.ic_l2_free;
//            } else {
//                markerId = R.mipmap.ic_l2_busy;
//            }
//        } else {
//            if (isAvailable.equalsIgnoreCase(getString(R.string.available))) {
//                markerId = R.mipmap.ic_sc_free;
//            } else {
//                markerId = R.mipmap.ic_sc_busy;
//            }
//        }

        LatLng position = new LatLng(latitude, longitude);
        return map.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(markerId))
                .snippet(snippet)
                .flat(false));
    }

    private void drawRangeCircle() {
        if (circle != null) {
            circle.remove();
            circle = null;
        }

        if (mainActivity.getMLocation() != null) {
            int radius = getRadius();
            radius = 10;
            if (radius > 0) {
                circle = map.addCircle(new CircleOptions()
                        .center(new LatLng(mainActivity.getMLocation().getLatitude(),
                                mainActivity.getMLocation().getLongitude()))
                        .radius(radius * 1000)
                        .strokeWidth(1)
                        .strokeColor(ResourcesCompat.getColor(getResources(),
                                R.color.colorLightBlue, null))
                        .fillColor(ResourcesCompat.getColor(getResources(),
                                R.color.colorLightBlue95Opacity, null)));
            }
        }
    }

    public void enableView() {
        if (map != null) {
            map.getUiSettings().setAllGesturesEnabled(true);
        }
    }

    public void disableView() {
        onHidden();
        if (map != null) {
            map.getUiSettings().setAllGesturesEnabled(false);
        }
    }

    private int getRadius() {
        SharedPreferences sharedPreferences = BaseApplication.getBaseApplication().getPreferences();
        return sharedPreferences.getInt(IPreferencesKeys.RANGE, 0);

    }

    private long radiusToZoomLevel(int radius) {
        double scaledRadius = (radius + radius / 2) / 500;
        return Math.round(16 - Math.log(scaledRadius) / Math.log(2));
    }

    //endregion

}
