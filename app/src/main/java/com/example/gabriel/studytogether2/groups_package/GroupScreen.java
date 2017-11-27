package com.example.gabriel.studytogether2.groups_package;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.CalculateCommonTime;
import com.example.gabriel.studytogether2.MainActivityContainer;
import com.example.gabriel.studytogether2.R;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumDeleteGroup;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumGetFree;

import java.util.ArrayList;

public class GroupScreen extends AppCompatActivity {

    TextView members;
    int gid;
    ArrayList<String> gMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_screen);

        getSupportActionBar().setTitle("Group Details");

        members = (TextView) findViewById(R.id.tv_gs_added_members);

        gid = getIntent().getExtras().getInt("GROUP_ID");
        int size = getIntent().getExtras().getInt("GROUP_SIZE");

        gMembers = new ArrayList<>();

        String tempString = "";

        for (int i = 0; i < size; i++) {
            String mem = getIntent().getExtras().getString("MEMBER" + i);
            gMembers.add(mem);
            tempString += mem + "\n";
        }


        members.setText(tempString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_group_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_group) {
            //submitEvent(findViewById(R.id.ac_ed));
            deleteGroup();
            return true;
        } else if (id == R.id.action_calculate_group) {
            beginCalc();
            return true;

            /* else if (id == R.id.action_delete) {
            deleteEvent();
            return true;
        }*/
        }
        return false;
    }

    private void beginCalc() {
        DBMediumGetFree dbmgf = new DBMediumGetFree(this);
        dbmgf.getFreeEvents(gMembers);
    }

    public void finishedCalc() {
        Intent newActivity = new Intent(this, GroupsCommonTime.class);
        startActivity(newActivity);
    }

    public void finishedEvents(ArrayList<WeekViewEvent> wves) {
        CalculateCommonTime cct = CalculateCommonTime.getInstance();
        cct.startCalc(wves, this);
    }

    private void deleteGroup() {
        DBMediumDeleteGroup dbmdg = new DBMediumDeleteGroup();
        dbmdg.delete(gid);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MainActivityContainer.getInstance().getMainActivity().returnFromCT)
            finish();
    }
}
