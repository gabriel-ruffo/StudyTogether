package com.example.gabriel.studytogether2.groups_package;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.gabriel.studytogether2.MainActivity;
import com.example.gabriel.studytogether2.MainActivityContainer;
import com.example.gabriel.studytogether2.R;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumMassInsert;

public class GroupsCommonDetail extends AppCompatActivity {

    TextView time;
    TextView members;
    GroupsCommonTime.CommonTimeCard ctc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_common_detail);

        getSupportActionBar().setTitle("Common Event Details");

        time = (TextView) findViewById(R.id.tv_gcd_time);
        members = (TextView) findViewById(R.id.tv_gcd_members);

        ctc = GroupsCTContainer.getInstance().getTimeCard();

        time.setText(ctc.getName() + "\n" + ctc.getSize() + " Users");
        members.setText(ctc.getDescriptionTwo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_create_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_saveGroup) {
            //submitEvent(findViewById(R.id.ac_ed));
            doMassInsert();
            return true;
        }/* else if (id == R.id.action_delete) {
            deleteEvent();
            return true;
        }*/
        return false;
    }

    private void doMassInsert() {
        DBMediumMassInsert dbmmi = new DBMediumMassInsert(this);
        dbmmi.insert(ctc.ev);
    }

    public void finishedMassInsert() {
        MainActivity mainActivity = MainActivityContainer.getInstance().getMainActivity();
        mainActivity.refreshCalendar();
        mainActivity.returnFromCT = true;
        finish();
    }
}
