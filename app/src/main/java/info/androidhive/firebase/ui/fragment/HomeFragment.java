package info.androidhive.firebase.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.firebase.Adapter.LocationListRecyclerAdapter;
import info.androidhive.firebase.R;
import info.androidhive.firebase.dto.EmergencyLocation;
import info.androidhive.firebase.ui.util.CallClickListener;

public class HomeFragment extends BaseFragment implements CallClickListener {

    public boolean shouldShowBalance;
    private LocationListRecyclerAdapter locationListRecyclerAdapter;
    private ArrayList<EmergencyLocation> emergencyLocations;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;

    @BindView(R.id.location_list)
    RecyclerView locationListView;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(HomeFragment.this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void setUpUI() {
        if (mainActivity != null && isAdded()) {
            showProgressBar();
            emergencyLocations = new ArrayList<>();
            locationListView.setHasFixedSize(true);
            locationListView.setLayoutManager(new LinearLayoutManager(mainActivity));
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabase = firebaseDatabase.getReference("locations");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    emergencyLocations.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        EmergencyLocation tempLo = dataSnapshot1.getValue(EmergencyLocation.class);
                        emergencyLocations.add(tempLo);
                        if (locationListRecyclerAdapter != null) {
                            locationListRecyclerAdapter.notifyDataSetChanged();
                        }
                    }

                    hideProgressBar();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    hideProgressBar();
                }
            });
            updateUI();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void updateUI() {
        if (mainActivity != null && isAdded()) {
            // getLocations();
            if (this.locationListRecyclerAdapter == null) {
                this.locationListRecyclerAdapter = new LocationListRecyclerAdapter(emergencyLocations,
                        mainActivity, HomeFragment.this);
                locationListView.setAdapter(this.locationListRecyclerAdapter);
            } else {
                locationListRecyclerAdapter.setRowList(emergencyLocations);
                locationListRecyclerAdapter.notifyDataSetChanged();
                if (locationListView.getAdapter() == null) {
                    locationListView.setAdapter(this.locationListRecyclerAdapter);
                }
            }
        }

    }

    private void getLocations() {
        if (mainActivity != null && isAdded()) {
            emergencyLocations.clear();
            EmergencyLocation emergencyLocation = new EmergencyLocation();
            emergencyLocation.setLocation("Tangalle");
            emergencyLocation.setLatitude("12");
            emergencyLocation.setLongitude("85");

            emergencyLocations.add(emergencyLocation);
//            if (myPlantList.size() > 0) {
//                noPlants.setVisibility(View.GONE);
//                noPlantImage.setVisibility(View.GONE);
//                plantListRecyclerView.setVisibility(View.VISIBLE);
//            } else {
//                noPlants.setVisibility(View.VISIBLE);
//                noPlantImage.setVisibility(View.VISIBLE);
//                plantListRecyclerView.setVisibility(View.GONE);
//            }
        }
    }

    @Override
    protected void setUpToolBar() {

    }

    @Override
    public void enableView() {

    }

    @Override
    public void disableView() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onItemClick(String mobile) {
        if(mainActivity != null && isAdded()){
            mainActivity.call(mobile);
        }
    }
}
