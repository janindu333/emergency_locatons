package info.androidhive.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import info.androidhive.firebase.Adapter.VideoListRecyclerAdapter;
import info.androidhive.firebase.Model.FormData;
import info.androidhive.firebase.Model.PointData;
import info.androidhive.firebase.Model.VideoData;
import info.androidhive.firebase.listners.VideoStatelistner;

public class VideoDisplay extends  YouTubeBaseActivity     {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<VideoData> list =new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceForPoints;
    TextView points;
    LinearLayout noVideos;
    VideoListRecyclerAdapter videoListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        noVideos.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
    }

}
