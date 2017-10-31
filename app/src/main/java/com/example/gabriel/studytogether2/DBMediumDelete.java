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

public class DBMediumDelete implements LoaderManager.LoaderCallbacks<Integer> {

    MainActivityContainer mac;
    private static final int DB_LOADER = 55;

    private long myid;


    public DBMediumDelete() {
        mac = MainActivityContainer.getInstance();
    }

    public void delete(long myid) {
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
                int i = dba.deleteWeekViewEvent(myid);
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
