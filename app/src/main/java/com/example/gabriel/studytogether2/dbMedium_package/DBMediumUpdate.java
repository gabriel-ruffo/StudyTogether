package com.example.gabriel.studytogether2.dbMedium_package;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.MainActivityContainer;

/**
 * Created by Charley on 10/28/17.
 */

public class DBMediumUpdate implements LoaderManager.LoaderCallbacks<Integer> {

    MainActivityContainer mac;
    private static final int DB_LOADER = 44;

    private String name, date, day, time_start, time_end, busy, notes;
    private long myid;


    public DBMediumUpdate() {
        mac = MainActivityContainer.getInstance();
    }

    public void update(String name, String date, String day, String time_start, String time_end, String busy, String notes, long myid) {
        this.name = name;
        this.date = date;
        this.day = day;
        this.time_start = time_start;
        this.time_end = time_end;
        this.busy = busy;
        this.notes = notes;
        this.myid = myid;

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
                int i = dba.updateWeekViewEvent(name, date, day, time_start, time_end, busy, notes, myid);
                return i;
            }
        };
/*
        return new AsyncTaskLoader<Integer>(mac.getMainActivity()) {
            private ArrayList<WeekViewEvent> query;

            @Override
            public void onStartLoading() {
                if (query != null) {
                    deliverResult(query);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Integer loadInBackground() {
                EditEnvelope ee = new EditEnvelope();

                query = ee.populateEvents();
                return query;
            }

            @Override
            public void deliverResult(I data) {
                query = data;
                super.deliverResult(data);
            }
        };*/
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {
        mac.getMainActivity().refreshCalendar();
    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }
}
