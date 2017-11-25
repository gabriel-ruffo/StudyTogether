package com.example.gabriel.studytogether2.groups_package;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabriel.studytogether2.R;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumGetGroups;

import java.util.ArrayList;

/**
 * Created by Charley on 11/25/17.
 */

public class GroupsCommonRVAdapter extends RecyclerView.Adapter<GroupsCommonRVAdapter.TimeCardViewHolder> {

    public ArrayList<GroupsCommonTime.CommonTimeCard> timeCards;

    final private GroupsRVAdapter.ListItemClickListener mOnClickListener;


    public  GroupsCommonRVAdapter(ArrayList<GroupsCommonTime.CommonTimeCard> timeCards,
                                  GroupsRVAdapter.ListItemClickListener listener) {
        this.timeCards = timeCards;
        mOnClickListener = listener;
    }

    @Override
    public TimeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_common_time, parent, false);
        GroupsCommonRVAdapter.TimeCardViewHolder pvh = new GroupsCommonRVAdapter.TimeCardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TimeCardViewHolder holder, int position) {

        holder.title.setText(timeCards.get(position).getName());
        holder.desc.setText(timeCards.get(position).getDescription());
        holder.image.setImageResource(R.drawable.ic_add);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class TimeCardViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        CardView cv;
        TextView title;
        TextView desc;
        ImageView image;

        TimeCardViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv_common_time);
            title = (TextView) itemView.findViewById(R.id.tv_common_title);
            desc = (TextView) itemView.findViewById(R.id.tv_common_description);
            image = (ImageView) itemView.findViewById(R.id.iv_common_image);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
