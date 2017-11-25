package com.example.gabriel.studytogether2.groups_package;

import android.content.Intent;
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
 * Created by Charley on 11/24/17.
 */

public class GroupsRVAdapter extends RecyclerView.Adapter<GroupsRVAdapter.GroupViewHolder> {

    public ArrayList<DBMediumGetGroups.GroupCard> groups;
    public DBMediumGetGroups dbmgg;

    final private ListItemClickListener mOnClickListener;


    public GroupsRVAdapter(ArrayList<DBMediumGetGroups.GroupCard> groups, ListItemClickListener listener) {
        this.groups = groups;
        mOnClickListener = listener;
        //this.dbmgg = dbmgg;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_group, parent, false);
        GroupViewHolder pvh = new GroupViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {

        holder.personName.setText(groups.get(position).getName());
        holder.personAge.setText(groups.get(position).getDescription());
        holder.personPhoto.setImageResource(R.drawable.ic_add);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        GroupViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv_group);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }


        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}