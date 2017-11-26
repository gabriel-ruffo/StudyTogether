package com.example.gabriel.studytogether2;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.groups_package.GroupScreen;
import com.example.gabriel.studytogether2.groups_package.GroupsCommonTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Charley on 11/24/17.
 */

public class CalculateCommonTime {

    ArrayList<WeekViewEvent> events;
    ArrayList<Event> fevents, cevents;
    ArrayList<HalfEvent> hevents;
    private static CalculateCommonTime cct;

    private CalculateCommonTime() {

    }

    public static CalculateCommonTime getInstance() {
        if (cct == null) {
            cct = new CalculateCommonTime();
        }

        return cct;
    }

    public void startCalc(ArrayList<WeekViewEvent> events, GroupScreen gs) {
        this.events = events;
        fevents = new ArrayList<>();
        cevents = new ArrayList<>();
        hevents = new ArrayList<>();

        runCalculation();

        gs.finishedCalc();
    }

    /*public CalculateCommonTime(ArrayList<WeekViewEvent> events, GroupScreen gs) {

        this.events = events;
        fevents = new ArrayList<>();
        cevents = new ArrayList<>();
        hevents = new ArrayList<>();

        runCalculation();

        gs.finishedCalc();
    }*/

    public void runCalculation() {
        initializeVars();

        Collections.sort(hevents);

        createCevents();

        findFevents();

    }

    public ArrayList<Event> getEvents() {
        return fevents;
    }

    private void createCevents() {
        for (int i = 0; i < hevents.size(); i++) {

            HalfEvent he = hevents.get(i);

            if (!he.isStart) { // end of event
                HashMap<Integer, Boolean> blacklist = new HashMap<>();

                for (int j = i - 1; j > -1; j--) {
                    HalfEvent temp = hevents.get(j);

                    boolean b = true;

                    if (blacklist.get(temp.userId) != null) {
                        b = false;
                    }
                    // boolean b = !blacklist.get(temp.userId);

                    if (temp.isStart && b/* !blacklist.get(temp.userId) */) { // overlapping start of event
                        if (temp.compareTo(he) != 0) {
                            cevents.add(
                                    new Event(he.userId, he.username, temp.time, he.time));

                            blacklist.put(temp.userId, true);
                        }

                        if (temp.userId == he.userId) { // reached beginning of he
                            j = -1;
                        }

                    } else if (!temp.isStart) {

                        blacklist.put(temp.userId, true);
                    }
                }
            }
        }
    }

    private void findFevents() {
        for (int i = 0; i < cevents.size(); i++) {
            for (int j = 0; j < events.size(); j++) {
                WeekViewEvent ev = events.get(j);
                Event cev = cevents.get(i);

                if (ev.getStartTime().compareTo(cev.start) <= 0 &&
                        ev.getEndTime().compareTo(cev.end) >= 0) {
                    cevents.get(i).addUser((int) ev.getId(), ev.getName());
                }
            }
        }

        for (int i = 0; i < cevents.size(); i++) {
            Event temp = cevents.get(i);
            if (temp.users.size() > 1) {
                fevents.add(temp);
            }
        }
    }

    private void initializeVars() {
        for (int i = 0; i < events.size(); i++) {
            WeekViewEvent wve = events.get(i);
            HalfEvent heS = new HalfEvent((int) wve.getId(), wve.getName(), true, wve.getStartTime());
            HalfEvent heE = new HalfEvent((int) wve.getId(), wve.getName(), false, wve.getEndTime());

            hevents.add(heS);
            hevents.add(heE);
        }


    }

    public void addEventsBulk(ArrayList<WeekViewEvent> eBulk) {
        for (int i = 0; i < eBulk.size(); i++) {
            events.add(eBulk.get(i));
        }
    }

    public void addSingleEvent(WeekViewEvent wve) {
        events.add(wve);
    }

    public class Event {
        public int userId;
        public String username;
        public ArrayList<Integer> users;
        public ArrayList<String> usersNames;
        public Calendar start;
        public Calendar end;

        public Event(int userId, String username, Calendar start, Calendar end) {
            this.userId = userId;
            users = new ArrayList<>();
            usersNames = new ArrayList<>();
            //users.add(userId);
            this.username = username;
            this.start = start;
            this.end = end;
        }

        public void addUser(int uid, String tempU) {
            users.add(uid);
            usersNames.add(tempU);
        }
    }

    private class HalfEvent implements Comparable<HalfEvent> {

        int userId;
        public String username;
        boolean isStart;
        Calendar time;


        public HalfEvent(int uid, String username, boolean isS, Calendar time) {
            userId = uid;
            this.username = username;
            isStart = isS;
            this.time = time;
        }

        @Override
        public int compareTo(@NonNull HalfEvent o) {
            return time.compareTo(o.time);
        }
    }
}
