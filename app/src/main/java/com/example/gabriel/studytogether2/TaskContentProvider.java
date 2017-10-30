package com.example.gabriel.studytogether2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

/**
 * Created by Charley on 10/27/17.
 */

public class TaskContentProvider implements LoaderManager.LoaderCallbacks<ArrayList<WeekViewEvent>> {

    public boolean onCreate() {
        return false;
    }

    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    public String getType(@NonNull Uri uri) {
        return null;
    }

    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public Loader<ArrayList<WeekViewEvent>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeekViewEvent>> loader, ArrayList<WeekViewEvent> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeekViewEvent>> loader) {

    }
}
