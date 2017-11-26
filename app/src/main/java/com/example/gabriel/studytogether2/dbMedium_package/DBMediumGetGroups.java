package com.example.gabriel.studytogether2.dbMedium_package;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.CalculateCommonTime;
import com.example.gabriel.studytogether2.DatabaseAccess;
import com.example.gabriel.studytogether2.MainActivity;
import com.example.gabriel.studytogether2.MainActivityContainer;

import java.util.ArrayList;

/**
 * Created by Charley on 11/24/17.
 */

public class DBMediumGetGroups implements LoaderManager.LoaderCallbacks<ArrayList<DBMediumGetGroups.GroupCard>> {


    private static final int DB_LOADER = 122;
    MainActivity mainActivity;

    ArrayList<GroupCard> groups;



    public DBMediumGetGroups() {
        mainActivity = MainActivityContainer.getInstance().getMainActivity();
        //groups = new ArrayList<>();
    }

    public ArrayList<GroupCard> getGroups() {
        return groups;
    }

    public int getGid(int index) {
        return groups.get(index).groupid;
    }

    public void refreshList() {
        groups = new ArrayList<>();

        LoaderManager loaderManager = mainActivity.getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this).forceLoad();
        else
            loaderManager.restartLoader(DB_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<GroupCard>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<GroupCard>>(mainActivity) {
            ArrayList<GroupCard> query;

            @Override
            public void onStartLoading() {
                if (query != null) {
                    deliverResult(query);
                } else {
                    forceLoad();
                }
            }

            @Override
            public ArrayList<GroupCard> loadInBackground() {

                ArrayList<GroupCard> gcs = new ArrayList<>();

                DatabaseAccess dba = new DatabaseAccess();

                ArrayList<Integer> gIds = dba.getGroupIds(MainActivityContainer.getInstance().getUsername());

                //ArrayList<String> rawResults = new ArrayList<>();

                for (int i = 0; i < gIds.size(); i++) {

                    String tempRaw = dba.getUsersInGroup(gIds.get(i));
                    String[] tempUsers = tempRaw.split("\\*\\*");
                    ArrayList<String> tmpList = new ArrayList<>();

                    for (int j = 0; j < tempUsers.length; j++)
                        tmpList.add(tempUsers[j]);

                    gcs.add(new GroupCard(gIds.get(i), tmpList));

                    //rawResults.add(t)
                }

                return gcs;

                //String rawResults = dba.getGroupIds(MainActivityContainer.getInstance().getUsername());

                //return null;
            }

            @Override
            public void deliverResult(ArrayList<GroupCard> gs) {
                query = gs;
                super.deliverResult(gs);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<GroupCard>> loader, ArrayList<GroupCard> data) {
        groups = data;

        mainActivity.finishGroups();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<GroupCard>> loader) {

    }

    public class GroupCard {
        public int groupid;
        public ArrayList<String> groupmembers;

        public GroupCard(int groupid, ArrayList<String> gmbs) {
            this.groupid = groupid;
            groupmembers = gmbs;
        }

        public String getName() {
            return "Group";
        }

        public String getDescription() {
            String desc = "";

            for (int i = 0; i < groupmembers.size(); i++) {
                String tmpU = groupmembers.get(i).split("@")[0];
                desc += /*groupmembers.get(i)*/ tmpU + ", ";
            }

            return desc;
        }
    }
}
