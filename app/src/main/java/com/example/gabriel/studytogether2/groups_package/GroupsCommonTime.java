package com.example.gabriel.studytogether2.groups_package;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.CalculateCommonTime;
import com.example.gabriel.studytogether2.MainActivityContainer;
import com.example.gabriel.studytogether2.R;

import java.util.ArrayList;
import java.util.Calendar;

public class GroupsCommonTime extends AppCompatActivity
implements GroupsRVAdapter.ListItemClickListener{

    ArrayList<CalculateCommonTime.Event> commonTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_common_time);

        getSupportActionBar().setTitle("Common Events");
        TextView nct = (TextView) findViewById(R.id.tv_no_common);
        nct.setVisibility(View.GONE);

        initializeData();

        if (commonTimes.size() == 0) {
            nct.setVisibility(View.VISIBLE);
        } else {

            RecyclerView rv = (RecyclerView) findViewById(R.id.rv_common_times);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            rv.setLayoutManager(llm);

        /* adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);*/
            //initializeData();

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
        Intent newIntent = new Intent(this, GroupsCommonDetail.class);

        GroupsCTContainer gctc = GroupsCTContainer.getInstance();
        gctc.setTimeCard(timeCards.get(clickedItemIndex));

        startActivity(newIntent);

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

        public String getSize() {
            return sids.size() + "";
        }

        public String getName() {
            String ttl = "";

            ttl += ev.start.get(Calendar.MONTH) + 1 + "/";
            ttl += ev.start.get(Calendar.DAY_OF_MONTH) + "/";
            ttl += ev.start.get(Calendar.YEAR) % 1000 + " ";

            int h = ev.start.get(Calendar.HOUR_OF_DAY);
            boolean isNoon = false;
            if (h > 11)
                isNoon = true;
            if (h > 12)
                h -= 12;
            if (h < 10)
                ttl += "0";
            ttl += h + ":";

            int m = ev.start.get(Calendar.MINUTE);

            if (m < 10)
                ttl += "0";

            //ttl += ev.start.get(Calendar.HOUR_OF_DAY) + ":";
            ttl += m + " ";
            if (isNoon)
                ttl += "pm";
            else
                ttl += "am";

            ttl += " - ";


            ttl += ev.end.get(Calendar.MONTH) + 1 + "/";
            ttl += ev.end.get(Calendar.DAY_OF_MONTH) + "/";
            ttl += ev.end.get(Calendar.YEAR) % 1000 + " ";

            h = ev.end.get(Calendar.HOUR_OF_DAY);
            isNoon = false;
            if (h > 11)
                isNoon = true;
            if (h > 12)
                h -= 12;
            if (h < 10)
                ttl += "0";
            ttl += h + ":";

            //ttl += ev.start.get(Calendar.HOUR_OF_DAY) + ":";
            m = ev.end.get(Calendar.MINUTE);

            if (m < 10)
                ttl += "0";

            ttl += m + " ";
            if (isNoon)
                ttl += "pm";
            else
                ttl += "am";
            //ttl += ev.end.get(Calendar.HOUR_OF_DAY) + ":";
            //ttl += ev.end.get(Calendar.MINUTE) + "";


            return ttl;
            //return "Time";
        }

        public String getDescription() {
            String desc = "";

            for (int i = 0; i < usNames.size(); i++) {
                String tmpU = usNames.get(i).split("@")[0];
                desc += /*usNames.get(i)*/ tmpU;

                if (i < usNames.size() - 1)
                    desc += ", ";
            }

            return desc;
        }

        public String getDescriptionTwo() {

            String desc = "";

            for (int i = 0; i < usNames.size(); i++) {
                String tmpU = usNames.get(i);
                desc += /*usNames.get(i)*/ tmpU + "\n";
            }

            return desc;
        }

        public boolean hasUsername(String tempName) {
            for (int i = 0; i < usNames.size(); i++) {
                if (usNames.get(i).equals(tempName))
                    return true;
            }

            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MainActivityContainer.getInstance().getMainActivity().returnFromCT)
            finish();
        //finish();
    }

    private void initializeData() {
        CalculateCommonTime cct = CalculateCommonTime.getInstance();

        commonTimes = cct.getEvents();

        for (int i = 0; i < commonTimes.size(); i++) {
            timeCards.add(new CommonTimeCard(commonTimes.get(i)));
        }
    }


}
