package info.androidhive.firebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import info.androidhive.firebase.ui.activity.LoginActivity;
import info.androidhive.firebase.Model.FormData;
import info.androidhive.firebase.Model.VideoData;
import info.androidhive.firebase.R;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import info.androidhive.firebase.listners.VideoStatelistner;

public class VideoListRecyclerAdapter
        extends BaseRecyclerAdapter<VideoListRecyclerAdapter.VideoLisViewHolder> {

    private List<VideoData> rowList;
    YouTubePlayerView youtubePlayer;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener ;
    Button playVideoBtn;
    private DatabaseReference mDatabase;
    VideoStatelistner videoStatelistner;

    class VideoLisViewHolder  extends RecyclerView.ViewHolder {

        VideoLisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(VideoLisViewHolder.this,itemView);
        }
    }

    public VideoListRecyclerAdapter(List<VideoData> rowList, Context context ) {
        this.rowList = rowList;
        this.mContext = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        youtubePlayer = itemView.findViewById(R.id.youtube_player);
        playVideoBtn = itemView.findViewById(R.id.play_video_btn);

        playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onAdStarted() {
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason arg0) {
                Toast.makeText(mContext, "Error occurred Try again later", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoaded(String arg0) {
            }

            @Override
            public void onLoading() {
            }

            @Override
            public void onVideoEnded() {
                addPoint(LoginActivity.loggedUserEmail);
            }

            @Override
            public void onVideoStarted() {
                Toast.makeText(mContext, "Please watch video until end", Toast.LENGTH_LONG).show();
            }
        };

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                List<String> videoList = new ArrayList<>();
//                    for (int i=0; i< videoDataList.size();i++){
//                        videoList.add(videoDataList.get(i).getVideoUrl());
//                    }
                videoList.add("W4hTJybfU7s");
                // videoList.add("bSMZknDI6bg");
                youTubePlayer.loadVideo(rowList.get(0).getVideoUrl());
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        playVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubePlayer.initialize("AIzaSyAzfebj4GxlS_sHqbQjT_qk97kUNGwMQrg", onInitializedListener);
                playVideoBtn.setVisibility(View.GONE);
            }
        });
        return new VideoLisViewHolder(itemView);
    }

    private void addPoint(String email)
    {
        Query query = mDatabase.child("FormDetails").orderByChild("email")
                .equalTo(LoginActivity.loggedUserEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        FormData formData = dataSnapshot1.getValue(FormData.class);
                        formData.setPoint(formData.getPoint()+1);
                        mDatabase.child("FormDetails").child(formData.getId()).setValue(formData).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(mContext, "Point updated", Toast.LENGTH_SHORT).show();
                                            UpdateVideoToWatch();
                                        } else {
                                            Toast.makeText(mContext, "{Point} updating Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
//                else{
//                    String id=mDatabase.push().getKey();
//                    PointData pointData = new PointData(id,email,1);
//                    mDatabase.child("FormDetails").child(id).setValue(pointData).
//                            addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(mContext, "Point Added", Toast.LENGTH_SHORT).show();
//                                        UpdateVideoToWatch();
//                                    }else {
//                                        Toast.makeText(mContext, "Point Adding Failed", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void UpdateVideoToWatch() {
        VideoData videoData = new VideoData(rowList.get(0).getId(),rowList.get(0).getEmail(), rowList.get(0).getVideoUrl(),true);
        mDatabase.child("VideoDetails").child(rowList.get(0).getId()).setValue(videoData).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Video updated", Toast.LENGTH_SHORT).show();
                            videoStatelistner.adapterRefresh();
                        }else {
                            Toast.makeText(mContext, "Video updating Failled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final VideoData rowData = this.rowList.get(position);
        VideoLisViewHolder tmpHolder = (VideoLisViewHolder)holder;

//        tmpHolder.txtName.setText(rowData.getLocation());
//        tmpHolder.txtReference.setText(rowData.getReference());
//        tmpHolder.txtType.setText(rowData.getType());

    }

    @Override
    public int getItemCount() {
        return rowList != null ? rowList.size() : 0;
    }
}
