package com.example.gabriel.studytogether2;

/**
 * Created by Charley on 10/28/17.
 */

public class MainActivityContainer {

    private static MainActivityContainer mac;
    private MainActivity mainActivity;

    public static MainActivityContainer getInstance() {
        if (mac == null)
            mac = new MainActivityContainer();

        return mac;
    }

    public void setMain(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
