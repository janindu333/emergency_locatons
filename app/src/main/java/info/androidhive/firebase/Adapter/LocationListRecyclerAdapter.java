package info.androidhive.firebase.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.firebase.MainActivity;
import info.androidhive.firebase.R;
import info.androidhive.firebase.dto.EmergencyLocation;
import info.androidhive.firebase.ui.fragment.HomeFragment;
import lombok.Setter;

public class LocationListRecyclerAdapter
        extends BaseRecyclerAdapter<LocationListRecyclerAdapter.LocationViewHolder> {

    private HomeFragment parentFragment;

    @Setter
    private List<EmergencyLocation> rowList;

    class LocationViewHolder  extends RecyclerView.ViewHolder {
        @BindView(R.id.location_name_txt)
        TextView locationNameTxt;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.plant_detail_containers)
        CardView plantDetailContainer;
        @BindView(R.id.location_desc)
        TextView locationDesc;
        @BindView(R.id.call)
                ImageView call;

        LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(LocationViewHolder.this,itemView);
        }
    }

    public LocationListRecyclerAdapter(List<EmergencyLocation> rowList, Context context,
                                       HomeFragment homeFragment) {
        this.rowList = rowList;
        this.mContext = context;
        this.parentFragment = homeFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_recycler_row, parent, false);
        return new LocationViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final EmergencyLocation rowData = this.rowList.get(position);

        LocationViewHolder tmpHolder = (LocationViewHolder)holder;
        tmpHolder.locationNameTxt.setText(rowData.getLocation());
        tmpHolder.locationDesc.setText(rowData.getDescription());

//        Picasso.get().load(rowData.getPlantUrl()).into(tmpHolder.image);
//        tmpHolder.plantDetailContainer.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putLong(mContext.getString(R.string.plant_id),rowData.getMyPlantId());
//            bundle.putString(mContext.getString(R.string.plant_name),rowData.getPlantName());
//            bundle.putString(mContext.getString(R.string.txt_seed_date),rowData.getSeedDate());
//            bundle.putString(mContext.getString(R.string.txt_harvest_date),rowData.getHarvestDate());
//            plantDetailFragment.setArguments(bundle);
//            ((MainActivity)mContext).addFragment(plantDetailFragment,mContext
//                    .getString(R.string.plant_detail));
//        });

        tmpHolder.call.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:"+ rowData.getMobile()));
            ((MainActivity)mContext).startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return rowList != null ? rowList.size() : 0;
    }
}
