package com.example.gabriel.studytogether2.dbMedium_package;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.MainActivityContainer;

import java.util.ArrayList;

/**
 * Created by Charley on 11/23/17.
 */

public class DBMediumCreateGroup implements LoaderManager.LoaderCallbacks<Integer> {

    private ArrayList<String> usernames;
    private MainActivityContainer mac;

    private static final int DB_LOADER = 111;


    public DBMediumCreateGroup() {
        mac = MainActivityContainer.getInstance();
    }

    public void createGroup(ArrayList<String> users) {
        usernames = users;
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
                int i = dba.createNewGroup(usernames);
                return i;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {
        mac.getMainActivity().refreshGroups();

    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }
}
