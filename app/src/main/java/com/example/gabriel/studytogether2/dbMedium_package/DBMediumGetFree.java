package com.example.gabriel.studytogether2.dbMedium_package;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.groups_package.GroupScreen;
import com.example.gabriel.studytogether2.schedule_package.EditEnvelope;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Charley on 11/24/17.
 */

public class DBMediumGetFree implements LoaderManager.LoaderCallbacks<ArrayList<WeekViewEvent>> {

    GroupScreen groupScreen;

    private static final int DB_LOADER = 144;
    private ArrayList<String> usernames;


    public DBMediumGetFree(GroupScreen groupScreen) {
        this.groupScreen = groupScreen;

    }

    public void getFreeEvents(ArrayList<String> usernames) {
        this.usernames = usernames;

        LoaderManager loaderManager = groupScreen.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<WeekViewEvent>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<WeekViewEvent>>(groupScreen) {
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
                DatabaseAccess dba = new DatabaseAccess();

                ArrayList<String> rawResults = dba.getFreeEvents(usernames);

                ArrayList<WeekViewEvent> events = new ArrayList<>();

                for (int i = 0; i < rawResults.size(); i++) {

                    String[] allEvents = rawResults.get(i).split("::");
                    String[] temp;
                    WeekViewEvent eventToAdd;
                    Calendar startTime;
                    Calendar endTime;

                    if (allEvents[0].length() > 0) {
                        for (int j = 0; j < allEvents.length; j++) {
                            temp = allEvents[j].split("\\*\\*");

                            // populate start and end time calendar objects
                            startTime = populateStartCalendar(temp[2], temp[4]);
                            endTime = populateStartCalendar(temp[2], temp[5]);
                            eventToAdd = new WeekViewEvent(Long.parseLong(temp[0]), temp[1], startTime, endTime);
                            // logic to create a new event

                            if (temp[6].equals("Y"))
                                eventToAdd.setColor(Color.rgb(239, 147, 147));

                            addEvent(events, eventToAdd);
                        }
                    }
                }

                if (events.size() > 0) {

                }

                return events;
            }

            private Calendar populateStartCalendar(String dateYMD, String time) {
                Calendar temp = Calendar.getInstance();
                String[] split_year = dateYMD.split("-");
                String[] split_time = time.split(":");

                temp.set(Calendar.YEAR, Integer.parseInt(split_year[0]));
                temp.set(Calendar.MONTH, Integer.parseInt(split_year[1]) - 1);
                temp.set(Calendar.DAY_OF_MONTH, Integer.parseInt(split_year[2]));
                temp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split_time[0]));
                temp.set(Calendar.MINUTE, Integer.parseInt(split_time[1]));

                return temp;
            }

            public void addEvent(ArrayList<WeekViewEvent> wves, WeekViewEvent wve) {
                if (wve != null) {
                    wves.add(wve);
                }
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
        groupScreen.finishedEvents(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeekViewEvent>> loader) {

    }
}
