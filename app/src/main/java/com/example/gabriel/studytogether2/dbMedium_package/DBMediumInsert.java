package com.example.gabriel.studytogether2.dbMedium_package;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.MainActivityContainer;
import com.example.gabriel.studytogether2.schedule_package.EditEvent;

import java.util.ArrayList;

/**
 * Created by Charley on 10/28/17.
 */

public class DBMediumInsert implements LoaderManager.LoaderCallbacks<Integer> {

    MainActivityContainer mac;
    private static final int DB_LOADER = 33;

    private String name, date, day, time_start, time_end, busy, notes;

    private boolean repeatInsert = false;
    ArrayList<EditEvent.SmallDate> smallDates;


    public DBMediumInsert() {
        mac = MainActivityContainer.getInstance();
    }

    public void insert(String name, String date, String day, String time_start, String time_end, String busy, String notes) {
        this.name = name;
        this.date = date;
        this.day = day;
        this.time_start = time_start;
        this.time_end = time_end;
        this.busy = busy;
        this.notes = notes;

        repeatInsert = false;

        LoaderManager loaderManager = mac.getMainActivity().getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    public void insertRepeat(String name, String date, String day, String time_start, String time_end, String busy, String notes, ArrayList<EditEvent.SmallDate> sds) {
        smallDates = sds;
        repeatInsert = true;

        this.name = name;
        this.date = date;
        this.day = day;
        this.time_start = time_start;
        this.time_end = time_end;
        this.busy = busy;
        this.notes = notes;

        LoaderManager loaderManager = mac.getMainActivity().getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Integer> onCreateLoader(final int id, Bundle args) {
        return new AsyncTaskLoader<Integer>(mac.getMainActivity()) {
            @Override
            public Integer loadInBackground() {
                DatabaseAccess dba = new DatabaseAccess();
                int i = 0;
                /*if (smallDates != null) {
                    for (int j = 0; i < smallDates.size(); j++) {
                        EditEvent.SmallDate sd = smallDates.get(j);
                        i = dba.insertNewWeekViewEvent(name, "" + sd.year + "-" + sd.month + "-" + sd.day, day, time_start, time_end, busy, notes, mac.getSID());

                        //i = dba.insertNewWeekViewEvent(name, "" + sd.year + "-" + sd.month + "-" + sd.day, "M" , day, time_start, time_end, busy, notes, mac.getSID());
                    }
                } else {*/

                if (repeatInsert) {
                    for (int k = 0; k < smallDates.size(); k++) {
                        EditEvent.SmallDate sd = smallDates.get(k);
                        dba.insertNewWeekViewEvent(name, "" + sd.year + "-" + sd.month + "-" + sd.day, day, time_start, time_end, busy, notes, mac.getSID());
                    }
                } else {
                    i = dba.insertNewWeekViewEvent(name, date, day, time_start, time_end, busy, notes, mac.getSID());
                }
                //}
                return i;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {
        mac.getMainActivity().refreshCalendar();
    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }
}
