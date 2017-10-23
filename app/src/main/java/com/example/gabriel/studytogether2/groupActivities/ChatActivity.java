package com.example.gabriel.studytogether2.groupActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.gabriel.studytogether2.R;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar chatActionBar = getSupportActionBar();
        TextView contentTV = (TextView) findViewById(R.id.tv_content);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();

        String id = "id not set";
        String content = "content not set";
        String details = "details not set";

        if(intent.hasExtra("ID")) {
            id = intent.getStringExtra("ID");
        }
        if(intent.hasExtra("Content")) {
            content = intent.getStringExtra("Content");
        }
        if(intent.hasExtra("Details")) {
            details = intent.getStringExtra("Details");
        }

        //toolbar.setTitle(id);
        //setSupportActionBar(toolbar);
        contentTV.setText(content + "\n" + details);
        chatActionBar.setTitle(id);


    }

}
