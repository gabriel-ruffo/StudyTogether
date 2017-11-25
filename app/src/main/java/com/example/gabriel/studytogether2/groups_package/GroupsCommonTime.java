package com.example.gabriel.studytogether2.groups_package;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.CalculateCommonTime;
import com.example.gabriel.studytogether2.R;

import java.util.ArrayList;

public class GroupsCommonTime extends AppCompatActivity
implements GroupsRVAdapter.ListItemClickListener{

    ArrayList<CalculateCommonTime.Event> commonTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_common_time);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_common_times);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        /* adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);*/
        initializeData();

        // Set the adapter
        if (rv instanceof RecyclerView) {
            Context context = this;
            //RecyclerView recyclerView = (RecyclerView) view;
            /*if (mColumnCount <= 1) {
                rv.setLayoutManager(new LinearLayoutManager(context));
            } else {
                rv.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }*/
            GroupsCommonRVAdapter gcrva = new GroupsCommonRVAdapter(timeCards, this);
            //GroupsRVAdapter grva = new GroupsRVAdapter(groups, this);
            rv.setAdapter(gcrva);

        }

        /*FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_group_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newEvent = new Intent(getActivity(), CreateGroup.class);
                //newEvent.putExtra("EDIT_EXISTING", false);
                startActivity(newEvent);
            }
        });
        return view;*/
    }

    ArrayList<CommonTimeCard> timeCards = new ArrayList<>();

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent newIntent = new Intent(this, GroupScreen.class);

        /*int gid = dbmgg.getGid(clickedItemIndex);

        newIntent.putExtra("GROUP_ID", gid);

        ArrayList<String> uNames = dbmgg.getGroups().get(clickedItemIndex).groupmembers;
        newIntent.putExtra("GROUP_SIZE", uNames.size());

        for (int i = 0; i < uNames.size(); i++) {
            newIntent.putExtra("MEMBER" + i, uNames.get(i));
        }

        startActivity(newIntent);*/
    }

    public class CommonTimeCard {
        public CalculateCommonTime.Event ev;
        public ArrayList<Integer> sids;
        public ArrayList<String> usNames;

        public CommonTimeCard(CalculateCommonTime.Event ev) {
            this.ev = ev;
            sids = new ArrayList<>();

            sids = ev.users;
            usNames = ev.usersNames;
        }

        public String getName() {
            return "Time";
        }

        public String getDescription() {
            String desc = "";

            for (int i = 0; i < usNames.size(); i++) {
                desc += usNames.get(i) + ", ";
            }

            return desc;
        }
    }

    private void initializeData() {
        CalculateCommonTime cct = CalculateCommonTime.getInstance();

        commonTimes = cct.getEvents();

        for (int i = 0; i < commonTimes.size(); i++) {
            timeCards.add(new CommonTimeCard(commonTimes.get(i)));
        }
    }


}
