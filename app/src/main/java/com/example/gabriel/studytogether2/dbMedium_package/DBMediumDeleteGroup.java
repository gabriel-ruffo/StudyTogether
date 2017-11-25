package com.example.gabriel.studytogether2.dbMedium_package;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.MainActivity;
import com.example.gabriel.studytogether2.MainActivityContainer;

/**
 * Created by Charley on 11/24/17.
 */

public class DBMediumDeleteGroup implements LoaderManager.LoaderCallbacks<Integer> {
    MainActivity mainActivity;
    //MainActivityContainer mac;
    private static final int DB_LOADER = 133;
    int gid;


    public DBMediumDeleteGroup() {
        mainActivity = MainActivityContainer.getInstance().getMainActivity();
    }

    public void delete(int gid) {
        this.gid = gid;
        LoaderManager loaderManager = mainActivity.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<Integer> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Integer>(mainActivity) {
            @Override
            public Integer loadInBackground() {
                DatabaseAccess dba = new DatabaseAccess();
                int i = dba.deleteGroup(gid);
                return i;
            }
        };    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {
        mainActivity.refreshGroups();
    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }
}
