package info.androidhive.firebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import info.androidhive.firebase.R;

import java.util.ArrayList;
import java.util.List;
import info.androidhive.firebase.Model.VideoData;

public class VideoDisplayAdapter extends RecyclerView.Adapter<VideoDisplayAdapter.MyHolder>
{

    List<VideoData> videoDataList;
    private Context context;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    VideoData videoData;

    public VideoDisplayAdapter(List<VideoData> videoDataList, Context context)
    {
        this.context=context;
        this.videoDataList=videoDataList;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,viewGroup,false);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                List<String> videoList = new ArrayList<>();
//                    for (int i=0; i< videoDataList.size();i++){
//                        videoList.add(videoDataList.get(i).getVideoUrl());
//                    }
                videoList.add("W4hTJybfU7s");
                // videoList.add("bSMZknDI6bg");
                youTubePlayer.loadVideo("W4hTJybfU7s");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        videoData=videoDataList.get(position);
       myHolder.title.setText(videoData.getEmail());
       myHolder.desc.setText(videoData.getVideoUrl());
    }

    @Override
    public int getItemCount() {
        return videoDataList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,desc;
        YouTubePlayerView youtubePlayer;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            youtubePlayer=itemView.findViewById(R.id.youtube_player);
            if(onInitializedListener != null) {
                youtubePlayer.initialize("AIzaSyAzfebj4GxlS_sHqbQjT_qk97kUNGwMQrg", onInitializedListener);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoData listdata=videoDataList.get(getAdapterPosition());
//                    Intent i=new Intent(context, EditActivity.class);
//                    i.putExtra("id",listdata.id);
//                    i.putExtra("title",listdata.title);
//                    i.putExtra("desc",listdata.desc);
//                    context.startActivity(i);
                    }
            });

        }


    }
}
