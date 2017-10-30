package com.example.gabriel.studytogether2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Charley on 10/27/17.
 */

public class DBMediumGet implements LoaderManager.LoaderCallbacks<ArrayList<WeekViewEvent>> {

    MainActivity mainActivity;
    MainActivityContainer mac;
    ArrayList<WeekViewEvent> eventList;
    private static final int DB_LOADER = 22;
    private long queryid;
    int returnCount;

    boolean needsRefresh, editExisting;


    public DBMediumGet() {
        mac = MainActivityContainer.getInstance();
        this.mainActivity = mac.getMainActivity();
        eventList = new ArrayList<>();
        needsRefresh = true;
        editExisting = false;
        returnCount = -1;
    }

    public void resetCount() {

    }

    public void refreshList() {
        eventList = new ArrayList<>();

        LoaderManager loaderManager = mainActivity.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this);
        else
            loaderManager.restartLoader(DB_LOADER, null, this);

        needsRefresh = false;
    }

    public ArrayList<WeekViewEvent> getEvents() {
        returnCount++;
        if (returnCount > 0 && returnCount < 5)
            return new ArrayList<>();

        //returnCount++;

        return eventList;
    }

    public boolean needsRefresh() {
        return needsRefresh;
    }

    public void resetRefresh() {
        needsRefresh = true;
    }

    public void resetEdit(long queryid) {
        this.queryid = queryid;
        editExisting = true;
    }

    @Override
    public Loader<ArrayList<WeekViewEvent>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<WeekViewEvent>>(mainActivity) {
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
            public ArrayList<WeekViewEvent> loadInBackground() {
                EditEnvelope ee = new EditEnvelope();

                if (editExisting) {
                    query = ee.populateEvents(true, queryid);
                } else {
                    query = ee.populateEvents(false, 0);
                }
                return query;
            }

            @Override
            public void deliverResult(ArrayList<WeekViewEvent> data) {
                query = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeekViewEvent>> loader, ArrayList<WeekViewEvent> data) {
        //eventList = data;
        //eventList.add(new WeekViewEvent(100, "name", 2017, 10, 27, 10, 0, 2017, 10, 27, 11, 0));
        if (editExisting) {
            resetCount();
            resetRefresh();
            editExisting = false;
            WeekViewEvent wve = data.get(0);
            Intent newEvent = new Intent(mainActivity, EditEvent.class);

            newEvent.putExtra("EDIT_EXISTING", true);
            newEvent.putExtra("NAME", wve.getName());
            Calendar start = wve.getStartTime();
            Calendar end = wve.getEndTime();
            newEvent.putExtra("START_HOUR", start.get(Calendar.HOUR_OF_DAY));
            newEvent.putExtra("START_MINUTE", start.get(Calendar.MINUTE));
            newEvent.putExtra("END_HOUR", end.get(Calendar.HOUR_OF_DAY));
            newEvent.putExtra("END_MINUTE", end.get(Calendar.MINUTE));
            newEvent.putExtra("YEAR", start.get(Calendar.YEAR));
            newEvent.putExtra("MONTH", start.get(Calendar.MONTH));
            newEvent.putExtra("DAY", start.get(Calendar.DAY_OF_MONTH));
            newEvent.putExtra("ID", wve.getId());

            mainActivity.startActivity(newEvent);
        } else {
            for (WeekViewEvent wve : data) {
                WeekViewEvent temp = new WeekViewEvent(wve.getId(), wve.getName(), wve.getStartTime().get(Calendar.YEAR), wve.getStartTime().get(Calendar.MONTH), wve.getStartTime().get(Calendar.DAY_OF_MONTH), wve.getStartTime().get(Calendar.HOUR_OF_DAY), wve.getStartTime().get(Calendar.MINUTE),
                        wve.getEndTime().get(Calendar.YEAR), wve.getEndTime().get(Calendar.MONTH), wve.getEndTime().get(Calendar.DAY_OF_MONTH), wve.getEndTime().get(Calendar.HOUR_OF_DAY), wve.getEndTime().get(Calendar.MINUTE));

                temp.setColor(wve.getColor());
                eventList.add(temp);
            }

            resetCount();
            //Toast.makeText(mainActivity.getBaseContext(), eventList.get(0).toString(), Toast.LENGTH_LONG);
            mainActivity.refreshCalendar();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeekViewEvent>> loader) {

    }
}
