package com.example.gabriel.studytogether2;

import android.graphics.Color;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * Created by Charley on 10/10/17.
 */

public class EditEnvelope {
    private static ArrayList<WeekViewEvent> eventList = new ArrayList<>();

    private DatabaseAccess dba;

    //private static WeekViewEvent tempEvent;
    private int returnCount = 0;

    public EditEnvelope() {
    }

    public ArrayList<WeekViewEvent> populateEvents(boolean isSingle, long id) {
        eventList = new ArrayList<>();
        //if (dba == null)
            dba = new DatabaseAccess();
        String allEvents_raw = "";
        if (isSingle) {
            allEvents_raw = dba.getSingleEvent(id);
        } else {
            allEvents_raw = dba.getAllSingleEvents();
        }
        String[] allEvents = allEvents_raw.split("::");
        String[] temp;
        WeekViewEvent eventToAdd;
        Calendar startTime;
        Calendar endTime;

        for (int i = 0; i < allEvents.length; i++) {
            temp = allEvents[i].split("\\*\\*");

            // populate start and end time calendar objects
            startTime = populateStartCalendar(temp[2], temp[4]);
            endTime = populateStartCalendar(temp[2], temp[5]);
            eventToAdd = new WeekViewEvent(Long.parseLong(temp[0]), temp[1], startTime, endTime);
            // logic to create a new event

            if (temp[6].equals("Y"))
                eventToAdd.setColor(Color.rgb(239, 147, 147));

            addEvent(eventToAdd);
        }

        return eventList;
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

    public void addEvent(WeekViewEvent wve) {
        if (wve != null) {
            eventList.add(wve);
        }
    }

    public void resetCount() {
        returnCount = 0;
    }

    public boolean returnable() {
        return returnCount == 0;
    }

    public List<WeekViewEvent> getEvents() {
        returnCount++;
        return eventList;
    }

}
