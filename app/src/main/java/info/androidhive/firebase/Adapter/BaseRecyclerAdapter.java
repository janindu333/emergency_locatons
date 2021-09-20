package info.androidhive.firebase.Adapter;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import info.androidhive.firebase.R;

/**
 * Created by cg on 8/29/2017.
 */

abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int lastPosition = -1;
    Context mContext;
    boolean isHorizontal;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position >lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(mContext,
//                    isHorizontal ? R.anim.left_from_right :  R.anim.up_from_bottom);
//            holder.itemView.startAnimation(animation);
//            lastPosition = position;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
