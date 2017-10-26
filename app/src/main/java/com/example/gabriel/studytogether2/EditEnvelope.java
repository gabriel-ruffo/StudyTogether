package com.example.gabriel.studytogether2;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charley on 10/10/17.
 */

public class EditEnvelope {

    private static EditEnvelope myObj;
    private static ArrayList<WeekViewEvent> eventList = new ArrayList<>();
    //private static WeekViewEvent tempEvent;
    int returnCount = 0;
    long id = 100;
    long event;

    private EditEnvelope() {

    }

    public static EditEnvelope getInstance() {
        if (myObj == null) {
            myObj = new EditEnvelope();
        }

        return myObj;
    }

    public void addEvent(WeekViewEvent wve) {
        wve.setId(id);
        id++;

        if (wve != null) {
            eventList.add(wve);
        }

    }

    public void updateEvent(WeekViewEvent wve) {

        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getId() == event) {
                eventList.remove(i);
            }
        }
        wve.setId(event);
        event = 0;

        eventList.add(wve);
    }

    public void setEvent(WeekViewEvent wve) {
        //tempEvent = wve;
        event = wve.getId();

    }

    public WeekViewEvent getEvent() {
        if (event > 99) {
            for (int i = 0; i < eventList.size(); i++) {
                if (eventList.get(i).getId() == event) {
                    return eventList.get(i);
                }
            }
        }

        return null;
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
