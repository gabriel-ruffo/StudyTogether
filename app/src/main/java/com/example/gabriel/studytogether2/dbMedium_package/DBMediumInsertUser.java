package com.example.gabriel.studytogether2.dbMedium_package;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.SignInActivity;

/**
 * Created by Charley on 11/9/17.
 */

public class DBMediumInsertUser implements LoaderManager.LoaderCallbacks<String>  {

    private SignInActivity signIn;
    private String email;
    private static final int DB_LOADER = 88;


    public DBMediumInsertUser(SignInActivity signIn) {
        this.signIn = signIn;
    }

    public void insertNewEmail(String email) {
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
            @Override
            public String loadInBackground() {
                DatabaseAccess dba = new DatabaseAccess();
                dba.insertNewUser(email);
                return "";
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        signIn.doneInsertingUser();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
