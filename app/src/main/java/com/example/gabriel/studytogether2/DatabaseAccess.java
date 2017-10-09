package com.example.gabriel.studytogether2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccess {
    static String url = "jdbc:mysql://studytogether.cxdwnsqecra4.us-west-2.rds.amazonaws.com/StudyTogether";
    static String userName = "gruffo";
    static String password = "aubriefeb18";
    static String driver = "com.mysql.jdbc.Driver";

    private Connection connection;

    public DatabaseAccess() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getAllSingleEvents() {
        String csvRS = "";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from single_event");

            while (rs.next()) {
                csvRS = (rs.getString(1) + " " + rs.getString(2) + " "
                        + rs.getString(3) + " " + rs.getString(4) + " "
                        + rs.getString(5) + " " + rs.getString(6) + " "
                        + rs.getString(7) + " " + rs.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return csvRS;
    }

    // comment out later
    public static void main(String[] args) {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from single_event");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " "
                        + rs.getString(3) + " " + rs.getString(4) + " "
                        + rs.getString(5) + " " + rs.getString(6) + " "
                        + rs.getString(7) + " " + rs.getString(8));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
