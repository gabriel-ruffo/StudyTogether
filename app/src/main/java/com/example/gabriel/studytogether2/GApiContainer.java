package com.example.gabriel.studytogether2;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Charley on 11/28/17.
 */

public class GApiContainer {

    private static GApiContainer gac;
    private GoogleApiClient gap;

    public static GApiContainer getInstance() {
        if (gac == null)
            gac = new GApiContainer();

        return gac;
    }

    public void setGApi(GoogleApiClient temp) {
        gap = temp;
    }

    public GoogleApiClient getGApi() {
        return gap;
    }
}
