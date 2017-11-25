package com.example.gabriel.studytogether2;

/**
 * Created by Charley on 10/28/17.
 */

public class MainActivityContainer {

    private static MainActivityContainer mac;
    private MainActivity mainActivity;
    private int sid;
    private String username;

    public static MainActivityContainer getInstance() {
        if (mac == null)
            mac = new MainActivityContainer();

        return mac;
    }

    public void setMain(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        //this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setSID(int sid) {
        this.sid = sid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSID() {
        return sid;
    }
}
