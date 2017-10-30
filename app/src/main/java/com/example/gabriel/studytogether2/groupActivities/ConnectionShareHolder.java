package com.example.gabriel.studytogether2.groupActivities;

import java.sql.Connection;

/**
 * Created by Charley on 10/27/17.
 */

public class ConnectionShareHolder {

    private static ConnectionShareHolder csh;
    private Connection connection;
    private boolean hasConnection;

    public ConnectionShareHolder() {
        hasConnection = false;
    }

    public static ConnectionShareHolder getInstance() {
        if (csh == null)
            csh = new ConnectionShareHolder();

        return csh;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        hasConnection = true;
    }

    public boolean hasConnection() {
        return hasConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
