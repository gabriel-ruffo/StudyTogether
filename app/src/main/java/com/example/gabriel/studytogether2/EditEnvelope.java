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
    int returnCount = 0;

    private EditEnvelope() {

    }

    public static EditEnvelope getInstance() {
        if (myObj == null) {
            myObj = new EditEnvelope();
        }

        return myObj;
    }

    public void setEvent(WeekViewEvent wve) {

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
