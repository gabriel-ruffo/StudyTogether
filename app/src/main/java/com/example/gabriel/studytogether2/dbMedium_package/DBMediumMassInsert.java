package com.example.gabriel.studytogether2.dbMedium_package;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.widget.EditText;

import com.example.gabriel.studytogether2.CalculateCommonTime;
import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.R;
import com.example.gabriel.studytogether2.groups_package.GroupsCommonDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Charley on 11/25/17.
 */

public class DBMediumMassInsert implements LoaderManager.LoaderCallbacks<Integer> {

    private static final int DB_LOADER = 155;

    private String name, date, day, time_start, time_end, busy, notes;
    private ArrayList<Integer> sids;

    private GroupsCommonDetail gcd;

    public DBMediumMassInsert (GroupsCommonDetail gcd) {
        this.gcd = gcd;
    }



    public void insert(CalculateCommonTime.Event ev) {

        String dateFormat = "yyyy/MM/dd";
        SimpleDateFormat sfd = new SimpleDateFormat(dateFormat, Locale.US);
        //EditText date = (EditText) findViewById(R.id.et_date);

        sids = ev.users;

        this.name = "Group Event";
        String tempDate = sfd.format(ev.start.getTime());
        //String[] broken = tempDate.split("/");
        //date = broken[0] + "-" + broken[1] + "-" + broken[2];
        tempDate.replaceAll("/", "-");
        date = tempDate;
        this.day = "S";

        String timeFormat = "hh:mm:ss";
        SimpleDateFormat sfdTime = new SimpleDateFormat(timeFormat, Locale.US);

        time_start = String.format("%02d", ev.start.get(Calendar.HOUR_OF_DAY)) + ":" +
                String.format("%02d", ev.start.get(Calendar.MINUTE)) + ":00";
        time_end = String.format("%02d", ev.end.get(Calendar.HOUR_OF_DAY)) + ":" +
                String.format("%02d", ev.end.get(Calendar.MINUTE)) + ":00";
        /*time_start = sfdTime.format(ev.start.getTime());
        time_end = sfdTime.format(ev.end.getTime());*/
        busy = "Y";
        notes = "auto-created group event";

        LoaderManager loaderManager = gcd.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<Integer> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Integer>(gcd) {
            @Override
            public Integer loadInBackground() {
                DatabaseAccess dba = new DatabaseAccess();
                int j = 0;
                for (int i = 0; i < sids.size(); i++) {
                    j = dba.insertNewWeekViewEvent(name, date, day, time_start, time_end, busy, notes, sids.get(i));
                }
                return j;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {
        //mac.getMainActivity().refreshCalendar();
        gcd.finishedMassInsert();
    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }
}
