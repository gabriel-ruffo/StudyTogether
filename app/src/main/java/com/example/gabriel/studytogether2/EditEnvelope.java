package com.example.gabriel.studytogether2;

import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by Charley on 10/10/17.
 */

public class EditEnvelope {

    private static EditEnvelope myObj;
    private WeekViewEvent wve;

    private EditEnvelope() {

    }

    public static EditEnvelope getInstance() {
        if (myObj == null) {
            myObj = new EditEnvelope();
        }

        return myObj;
    }

    public void setEvent(WeekViewEvent wve) {
        if (myObj != null) {
            myObj.wve = wve;
        }
    }

    public WeekViewEvent getEvent() {
        if (myObj != null) {
            return myObj.wve;
        }

        return null;
    }

}
