package com.example.gabriel.studytogether2.dbMedium_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.schedule_package.EditEnvelope;
import com.example.gabriel.studytogether2.schedule_package.EditEvent;
import com.example.gabriel.studytogether2.MainActivity;
import com.example.gabriel.studytogether2.MainActivityContainer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Charley on 10/27/17.
 */

public class DBMediumGet implements LoaderManager.LoaderCallbacks<ArrayList<WeekViewEvent>> {

    MainActivity mainActivity;
    MainActivityContainer mac;
    ArrayList<WeekViewEvent> eventList;
    HashMap<Integer, ArrayList<WeekViewEvent>> eventMap;
    private static final int DB_LOADER = 22;
    private long queryid;
    int returnCount;

    boolean needsRefresh, editExisting;


    public DBMediumGet() {
        mac = MainActivityContainer.getInstance();
        this.mainActivity = mac.getMainActivity();
        eventList = new ArrayList<>();
        eventMap = new HashMap<>();
        needsRefresh = true;
        editExisting = false;
        returnCount = -1;
    }

    public void resetCount() {

    }

    public void refreshList() {
        eventList = new ArrayList<>();
        eventMap = new HashMap<>();

        LoaderManager loaderManager = mainActivity.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this);
        else
            loaderManager.restartLoader(DB_LOADER, null, this);

        needsRefresh = false;
    }

    public ArrayList<WeekViewEvent> getEvents(int month) {
        /*returnCount++;
        if (returnCount > 0 && returnCount < 5)
            return new ArrayList<>();*/

        /*ArrayList<WeekViewEvent> oct = new ArrayList<>();
        ArrayList<WeekViewEvent> sep = new ArrayList<>();
        ArrayList<WeekViewEvent> nov = new ArrayList<>();

        sep.add(new WeekViewEvent(1, "september", 2017, 8, 31, 1, 0, 2017, 8, 31, 4, 0));
        oct.add(new WeekViewEvent(2, "october", 2017, 9, 31, 1, 0, 2017, 9, 31, 4, 0));
        nov.add(new WeekViewEvent(3, "november", 2017, 10, 31, 1, 0, 2017, 10, 31, 4, 0));

        if (month == 9)
            return sep;
        if (month == 10)
            return oct;
        if (month == 11)
            return nov;*/

        //returnCount++;
        ArrayList<WeekViewEvent> tempList = eventMap.get(month);

        if (tempList == null)
            return new ArrayList<>();

        return tempList;

        //return eventList;
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
                    query = ee.populateEvents(false, mac.getSID());
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
                long id = wve.getId();
                String name = wve.getName();
                int year = wve.getStartTime().get(Calendar.YEAR);
                int month = wve.getStartTime().get(Calendar.MONTH) + 1;
                int startday = wve.getStartTime().get(Calendar.DAY_OF_MONTH);
                int starthour = wve.getStartTime().get(Calendar.HOUR_OF_DAY);
                int startminute = wve.getStartTime().get(Calendar.MINUTE);
                int endday = wve.getEndTime().get(Calendar.DAY_OF_MONTH);
                int endhour = wve.getEndTime().get(Calendar.HOUR_OF_DAY);
                int endminute = wve.getEndTime().get(Calendar.MINUTE);


                //int monthValue = wve.getStartTime().get(Calendar.MONTH);
                ArrayList<WeekViewEvent> tempList = eventMap.get(month);

                if (tempList == null)
                    tempList = new ArrayList<>();

                WeekViewEvent temp = new WeekViewEvent(id, name, year, month, startday, starthour, startminute, year, month, endday, endhour, endminute);

                //WeekViewEvent temp = new WeekViewEvent(id, name, wve.getStartTime().get(Calendar.YEAR), monthValue, wve.getStartTime().get(Calendar.DAY_OF_MONTH), wve.getStartTime().get(Calendar.HOUR_OF_DAY), wve.getStartTime().get(Calendar.MINUTE),
                       // wve.getEndTime().get(Calendar.YEAR), monthValue, wve.getEndTime().get(Calendar.DAY_OF_MONTH), wve.getEndTime().get(Calendar.HOUR_OF_DAY), wve.getEndTime().get(Calendar.MINUTE));

                temp.setColor(wve.getColor());
                tempList.add(temp);

                eventMap.put(month, tempList);
            }

            //resetCount();
            //Toast.makeText(mainActivity.getBaseContext(), eventList.get(0).toString(), Toast.LENGTH_LONG);
            mainActivity.refreshCalendarEvents();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeekViewEvent>> loader) {

    }
}
