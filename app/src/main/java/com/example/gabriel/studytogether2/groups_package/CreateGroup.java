package com.example.gabriel.studytogether2.groups_package;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.studytogether2.MainActivityContainer;
import com.example.gabriel.studytogether2.R;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumCheckUser;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumCreateGroup;

import java.util.ArrayList;

public class CreateGroup extends AppCompatActivity {

    TextView userList;
    EditText newMember;
    TextView exMembers;
    DBMediumCheckUser dbmcu;// = new DBMediumCheckUser(this, newMember.getText().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        getSupportActionBar().setTitle("Create Group");

        userList = (TextView) findViewById(R.id.tv_added_members);
        newMember = (EditText) findViewById(R.id.et_new_member);
        exMembers = (TextView) findViewById(R.id.tv_added_members);
        dbmcu = new DBMediumCheckUser(this);

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
            doCreate();
            return true;
        }/* else if (id == R.id.action_delete) {
            deleteEvent();
            return true;
        }*/
        return false;
    }

    public void doCreate() {
        String[] groupMembers = exMembers.getText().toString().split("\n");
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < groupMembers.length; i++) {
            if (groupMembers[i].length() > 0)
                temp.add(groupMembers[i]);
        }

        temp.add(MainActivityContainer.getInstance().getUsername());

        DBMediumCreateGroup dbmcg = new DBMediumCreateGroup();
        dbmcg.createGroup(temp);
        finish();
    }

    public void addMember(View v) {
        String[] existingMembers = exMembers.getText().toString().split("\n");

        String tempName = newMember.getText().toString();

        boolean alreadyAdded = false;

        for (int i = 0; i < existingMembers.length; i++) {
            if (tempName.equals(existingMembers[i]))
                alreadyAdded = true;
        }

        if (!alreadyAdded)
            dbmcu.checkUser(tempName);
        else
            Toast.makeText(this, "user already added", Toast.LENGTH_SHORT).show();
    }

    public void doneChecking(boolean userExists, String username) {
        if (userExists) {
            userList.setText((userList.getText() + "\n" + username).trim());
        } else {
            Toast.makeText(this, "invalid username", Toast.LENGTH_SHORT).show();
        }
    }
}
