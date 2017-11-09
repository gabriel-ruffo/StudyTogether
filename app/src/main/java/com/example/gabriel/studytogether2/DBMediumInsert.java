package com.example.gabriel.studytogether2;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

/**
 * Created by Charley on 10/28/17.
 */

public class DBMediumInsert implements LoaderManager.LoaderCallbacks<Integer> {

    MainActivityContainer mac;
    private static final int DB_LOADER = 33;

    private String name, date, day, time_start, time_end, busy, notes;


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

        LoaderManager loaderManager = mac.getMainActivity().getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<Integer> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Integer>(mac.getMainActivity()) {
            @Override
            public Integer loadInBackground() {
                DatabaseAccess dba = new DatabaseAccess();
                int i = dba.insertNewWeekViewEvent(name, date, day, time_start, time_end, busy, notes);
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
