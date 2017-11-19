package com.example.gabriel.studytogether2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.alamkanak.weekview.WeekViewEvent;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Charley on 11/9/17.
 */

public class DBMediumGetUser implements LoaderManager.LoaderCallbacks<String> {

    private SignInActivity signIn;
    private String email;
    private static final int DB_LOADER = 77;


    public DBMediumGetUser(SignInActivity signIn) {
        this.signIn = signIn;
    }

    public void getScheduleId(String email) {
        this.email = email;

        LoaderManager loaderManager = signIn.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(signIn) {
            private String schedule_id;

            @Override
            public void onStartLoading() {
                if (schedule_id != null) {
                    deliverResult(schedule_id);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                DatabaseAccess dba = new DatabaseAccess();
                schedule_id = dba.getScheduleId(email);
                return schedule_id;
            }

            @Override
            public void deliverResult(String data) {
                schedule_id = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        signIn.loadFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
