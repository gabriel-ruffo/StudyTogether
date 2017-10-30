package com.example.gabriel.studytogether2;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.groupActivities.ConnectionShareHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccess {
    private static String url = "jdbc:mysql://studytogether.cxdwnsqecra4.us-west-2.rds.amazonaws.com/StudyTogether";
    private static String userName = "gruffo";
    private static String password = "aubriefeb18";
    private static String driver = "com.mysql.jdbc.Driver";

    private Connection connection;
    private ConnectionShareHolder csh = ConnectionShareHolder.getInstance();

    public DatabaseAccess() {
        try {
            if (csh.hasConnection()) {
                connection = csh.getConnection();
            } else {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, userName, password);
                if (connection != null)
                    csh.setConnection(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSingleEvent(long id) {
        String csvRS = "";
        try{
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            String s = "select * from single_event where event_id=" + id;
            ResultSet rs = stmt.executeQuery(s);

            while (rs.next()) {
                csvRS += (rs.getString(1) + "**" + rs.getString(2) + "**"
                        + rs.getString(3) + "**" + rs.getString(4) + "**"
                        + rs.getString(5) + "**" + rs.getString(6) + "**"
                        + rs.getString(7) + "**" + rs.getString(8))+ "::";
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return csvRS;
    }

    public String getAllSingleEvents() {
        String csvRS = "";
        try {
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from single_event");

            // splitting on '::'
            while (rs.next()) {
                csvRS += (rs.getString(1) + "**" + rs.getString(2) + "**"
                        + rs.getString(3) + "**" + rs.getString(4) + "**"
                        + rs.getString(5) + "**" + rs.getString(6) + "**"
                        + rs.getString(7) + "**" + rs.getString(8))+ "::";
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return csvRS;
    }

    public int updateWeekViewEvent(String name, String date, String day, String time_start, String time_end, String busy, String notes, long id) {
        try {
            Statement stmt = connection.createStatement();
            String update_query = "UPDATE single_event ";
            update_query += "SET name = \"" + name + "\", date = \"" + date + "\", day = \"" + day + "\", time_start = \"" + time_start + "\", time_end = \"" + time_end + "\", busy = \"" + busy + "\", notes = \"" + notes + "\" ";
            update_query += "WHERE event_id=" + id;
            int i = stmt.executeUpdate(update_query);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // TODO: Pass in actual 'day' argument, right now it's always 'M'
    public int insertNewWeekViewEvent(String name, String date, String day, String time_start, String time_end, String busy, String notes) {
        try {
            Statement stmt = connection.createStatement();
            String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes)";
            insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + time_start + "\", \"" + time_end + "\", \"" + busy + "\", \"" + notes + "\")";
            int i = stmt.executeUpdate(insert_query);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // comment out later
    public static void main(String[] args) {
        DatabaseAccess dba = new DatabaseAccess();
        String s = dba.getAllSingleEvents();

        System.out.println(s);
    }
}
