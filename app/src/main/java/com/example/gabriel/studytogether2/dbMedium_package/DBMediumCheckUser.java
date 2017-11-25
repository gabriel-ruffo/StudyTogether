package com.example.gabriel.studytogether2.dbMedium_package;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.gabriel.studytogether2.groups_package.CreateGroup;
import com.example.gabriel.studytogether2.DatabaseAccess;

/**
 * Created by Charley on 11/21/17.
 */

public class DBMediumCheckUser implements LoaderManager.LoaderCallbacks<Boolean> {

    private String username;
    private CreateGroup cg;

    private static final int DB_LOADER = 99;


    public DBMediumCheckUser(CreateGroup cg) {
        this.username = username;
        this.cg = cg;
    }

    public void checkUser(String username) {
        this.username = username;
        LoaderManager loaderManager = cg.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Boolean>(cg) {
            @Override
            public Boolean loadInBackground() {
                DatabaseAccess dba = new DatabaseAccess();
                boolean b = dba.checkUsername(username);
                return b;
                /*int i = dba.deleteWeekViewEvent(myid);
                return i;*/
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        cg.doneChecking(data, username);
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }
}
