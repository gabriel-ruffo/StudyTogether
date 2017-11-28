package com.example.gabriel.studytogether2;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Daniel on 11/28/2017.
 */

public class GlobalState extends Application {
    private GoogleApiClient mGoogleApiClient;

    public GlobalState(){

    }

    public void setGoogleApiClient(GoogleApiClient gac){
        mGoogleApiClient = gac;
    }

    public GoogleApiClient getGoogleApiClient(){
        return mGoogleApiClient;
    }
}
