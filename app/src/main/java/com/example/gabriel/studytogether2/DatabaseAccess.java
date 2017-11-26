package com.example.gabriel.studytogether2;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.groupActivities.ConnectionShareHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.transform.Result;

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

    public String getScheduleId(String email) {
        String csvRS = "";
        try {
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            String s = "select user.schedule_id from user where user.email=\"" + email + "\"";
            ResultSet rs = stmt.executeQuery(s);

            while(rs.next()) {
                csvRS += rs.getString(1);
            }
            // splitting on '::'
            /*while (rs.next()) {
                csvRS += (rs.getString(1) + "**" + rs.getString(2) + "**"
                        + rs.getString(3) + "**" + rs.getString(4) + "**"
                        + rs.getString(5) + "**" + rs.getString(6) + "**"
                        + rs.getString(7) + "**" + rs.getString(8))+ "::";
            }*/
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return csvRS;
    }

    public int insertNewUser(String email) {
        try {
            Statement stmt = connection.createStatement();
            String insert_query = "INSERT INTO user(email)";
            insert_query += " VALUES(\"" + email + "\")";
            int i = stmt.executeUpdate(insert_query);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getAllSingleEvents(int sid) {
        String csvRS = "";
        try {
            /*if (sid != 937)
                sid = 10;*/
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from single_event where schedule_id=" + sid);

            // splitting on '::'
            while (rs.next()) {
                csvRS += (rs.getString(1) + "**" + rs.getString(2) + "**"
                        + rs.getString(3) + "**" + rs.getString(4) + "**"
                        + rs.getString(5) + "**" + rs.getString(6) + "**"
                        + rs.getString(7) + "**" + rs.getString(8))+ "::";
            }
        } catch (Exception/* | SQLException | ClassNotFoundException*/ e) {
            e.printStackTrace();
        }

        return csvRS;
    }

    public int deleteGroup(int groupid) {
        /*try {
            Statement stmt = connection.createStatement();
            String delete_query = "DELETE from single_event ";
            delete_query += "WHERE event_id=" + id;
            int i = stmt.executeUpdate(delete_query);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;*/
        String delGStmt = "DELETE from StudyTogether.group where group_id=" + groupid;

        try {
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            //String delete_query = "DELETE from single_event ";
            //delete_query += "WHERE event_id=" + id;
            int i = stmt.executeUpdate(delGStmt);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }

    public ArrayList<String> getFreeEvents(ArrayList<String> uNames) {

        //String csvRS = "";
        ArrayList<Integer> sids = new ArrayList<>();

        String getSidQuery = "select user.schedule_id from user where user.email=\"";

        for (int i = 0; i < uNames.size(); i++) {
            try {
                Class.forName(driver);
                Statement stmt = connection.createStatement();

                String tempQ = getSidQuery + uNames.get(i) + "\"";

                ResultSet rs = stmt.executeQuery(tempQ);

                while (rs.next()) {
                    sids.add(rs.getInt(1));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<String> allRaw = new ArrayList<>();

        String csvRS = "";
        for (int i = 0; i < sids.size(); i++) {
            csvRS = "";
            try {
                Class.forName(driver);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from single_event where schedule_id=" + sids.get(i) + " and busy=\"N\"");

                // splitting on '::'
                //int index = 0;
                while (rs.next()) {
                    csvRS += (rs.getString(1) + "**" + /*rs.getString(2)*/ uNames.get(i) + "**"
                            + rs.getString(3) + "**" + rs.getString(4) + "**"
                            + rs.getString(5) + "**" + rs.getString(6) + "**"
                            + rs.getString(7) + "**" + rs.getString(8)) + "::";

                    //index++;
                }
            } catch (Exception/* | SQLException | ClassNotFoundException*/ e) {
                e.printStackTrace();
            }
            allRaw.add(csvRS);
        }

        return allRaw;
    }

    public ArrayList<Integer> getGroupIds(String uName) {
        String getGroupIDS = "SELECT group_id from StudyTogether.group where email=\"" + uName + "\"";

        ArrayList<Integer> groupIds = new ArrayList<>();

        try {
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getGroupIDS);

            while (rs.next()) {
                groupIds.add(Integer.parseInt(rs.getString(1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return groupIds;

        //return "";
    }

    public String getUsersInGroup(int groupid) {
        String getUIG = "SELECT email from StudyTogether.group where group_id=" + groupid;
        String csvRS = "";

        try {
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getUIG);

            while (rs.next()) {
                csvRS += rs.getString(1).toString() + "**";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return csvRS;
    }

    public int createNewGroup(ArrayList<String> usernames) {

        String getMaxGIDQuery = "SELECT MAX(group_id) from StudyTogether.group";

        String csvRS = "";
        int maxID = -1;

        try {
            Class.forName(driver);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getMaxGIDQuery);



            if (rs.next()) {
                maxID = rs.getInt(1);
                //maxID = Integer.parseInt(rs.getS(1));
                //maxID++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (maxID == -1)
            maxID = 1;
        else
            maxID++;

        String insQuery = "insert into StudyTogether.group(group_id, email) values(" + maxID + ", \"";

        for (int i = 0; i < usernames.size(); i++) {
            try {
                Statement stmt = connection.createStatement();
                //long tempid = 100;
                //String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes, schedule_id)";
                //insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + time_start + "\", \"" + time_end + "\", \"" + busy + "\", \"" + notes + "\", \"" + sid + "\")";
                String tempQuery = insQuery + usernames.get(i) + "\")";

                int j = stmt.executeUpdate(tempQuery);
                //return j;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return  0;

        /*try {
            Statement stmt = connection.createStatement();
            //long tempid = 100;
            String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes, schedule_id)";
            insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + time_start + "\", \"" + time_end + "\", \"" + busy + "\", \"" + notes + "\", \"" + sid + "\")";
            int i = stmt.executeUpdate(insert_query);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }*/

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

    public boolean checkUsername(String uName) {
        //String csvRS = "";
        try {
            boolean userExists = false;

            Class.forName(driver);
            Statement stmt = connection.createStatement();
            String s = "select user.schedule_id from user where user.email=\"" + uName + "\"";
            ResultSet rs = stmt.executeQuery(s);

            while(rs.next()) {
                userExists = true;
                //csvRS += rs.getString(1);
            }

            return userExists;
            // splitting on '::'
            /*while (rs.next()) {
                csvRS += (rs.getString(1) + "**" + rs.getString(2) + "**"
                        + rs.getString(3) + "**" + rs.getString(4) + "**"
                        + rs.getString(5) + "**" + rs.getString(6) + "**"
                        + rs.getString(7) + "**" + rs.getString(8))+ "::";
            }*/
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int deleteWeekViewEvent(long id) {
        try {
            Statement stmt = connection.createStatement();
            String delete_query = "DELETE from single_event ";
            delete_query += "WHERE event_id=" + id;
            int i = stmt.executeUpdate(delete_query);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return 0;
    }

    // on insert:
    //      check to see if there exist events on the day and time of the candidate event
    //          if candidate.busy && exists.busy
    //              don't insert candidate & reply with toast
    //          if candidate.busy && exists.free
    //              insert candidate as usual, but update exists.time_start ot exists.time_end accordingly
    //          if candidate.free && exists.free
    //              don't insert candidate, but update exists with new time_start or time_end accordingly
    //          if candidate.free && exists.busy
    //              don't insert candidate & reply with toast
    //
    // TODO: Pass in actual 'day' argument, right now it's always 'M'
    public int insertNewWeekViewEvent(String name, String date, String day, String time_start, String time_end, String busy, String notes, int sid) {
        int upper_event_id = upperOverlappingEvent(date, time_start, time_end);
        int lower_event_id = lowerOverlappingEvent(date, time_start, time_end);

        if (upper_event_id != -1) {
            String other_busy = checkIfBusy(upper_event_id);

            // Case 1: busy && busy
            if (busy.equals("Y") && other_busy.equals("Y")) {
                // TODO: Charlesy -- Respond with Toast("There's already a busy event here!");
                return 0;
            } else if(busy.equals("Y") && other_busy.equals("N")) {
                // Case 2: busy && free
                int ret_val = updateEventUpperEnd(upper_event_id, time_end);
                try {
                    Statement stmt = connection.createStatement();
                    String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes, schedule_id)";
                    insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + time_start + "\", \"" + time_end + "\", \"" + busy + "\", \"" + notes + "\", \"" + sid + "\")";
                    return stmt.executeUpdate(insert_query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return ret_val;
            } else if (busy.equals("N") && other_busy.equals("N")) {
                // Case 3: free && free
                String other_time_end = getOtherTimeEnd(upper_event_id);
                try {
                    Statement stmt = connection.createStatement();
                    String delete_query = "DELETE FROM single_event WHERE event_id = " + upper_event_id;
                    stmt.executeUpdate(delete_query);
                    String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes, schedule_id)";
                    insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + time_start + "\", \"" + other_time_end + "\", \"" + busy + "\", \"" + notes + "\", \"" + sid + "\")";
                    return stmt.executeUpdate(insert_query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return 0;
            } else if (busy.equals("N") && other_busy.equals("Y")) {
                // Case 4: free && busy
                // TODO: Charlesy -- Respond with Toast("There's already a busy event here!");
                return 0;
            }

        } else if (lower_event_id != -1) {
            String other_busy = checkIfBusy(lower_event_id);

            if (busy.equals("Y") && other_busy.equals("Y")) {
                // Case 1: busy && busy
                // TODO: Toast("There's already a busy event here!");
                return 0;
            } else if (busy.equals("Y") && other_busy.equals("N")) {
                // Case 2: busy && free
                int ret_val = updateEventLowerEnd(lower_event_id, time_start);
                try {
                    Statement stmt = connection.createStatement();
                    String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes, schedule_id)";
                    insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + time_start + "\", \"" + time_end + "\", \"" + busy + "\", \"" + notes + "\", \"" + sid + "\")";
                    return stmt.executeUpdate(insert_query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return ret_val;
            } else if (busy.equals("N") && other_busy.equals("N")) {
                // Case 3: free && free
                String other_time_start = getOtherTimeStart(lower_event_id);
                try {
                    Statement stmt = connection.createStatement();
                    String delete_query = "DELETE FROM single_event WHERE event_id = " + lower_event_id;
                    stmt.executeUpdate(delete_query);
                    String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes, schedule_id)";
                    insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + other_time_start + "\", \"" + time_end + "\", \"" + busy + "\", \"" + notes + "\", \"" + sid + "\")";
                    return stmt.executeUpdate(insert_query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return 0;
            } else if (busy.equals("N") && other_busy.equals("Y")) {
                // Case 4: free && busy
                // TODO: Toast("There's already a busy event here!");
                return 0;
            }
        } else {
            try {
                Statement stmt = connection.createStatement();
                //long tempid = 100;
                String insert_query = "INSERT INTO single_event(name, date, day, time_start, time_end, busy, notes, schedule_id)";
                insert_query += " VALUES(\"" + name + "\", \"" + date + "\", \"" + day + "\", \"" + time_start + "\", \"" + time_end + "\", \"" + busy + "\", \"" + notes + "\", \"" + sid + "\")";
                int i = stmt.executeUpdate(insert_query);
                return i;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private String checkIfBusy(int event_id) {
        String busy = "N";
        String query = "SELECT busy FROM single_event WHERE event_id = " + event_id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                busy = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return busy;
    }

    private int upperOverlappingEvent(String date, String time_start, String time_end) {
        int sid = MainActivityContainer.getInstance().getSID();
        String query = "SELECT event_id FROM single_event WHERE (schedule_id = " + sid +
                " AND date=\"" + date +"\") AND (time_start > \'" + time_start + "\') AND" +
                " (time_start < \'" + time_end + "\' AND time_end > \'" + time_end + "\')";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int lowerOverlappingEvent(String date, String time_start, String time_end) {
        int sid = MainActivityContainer.getInstance().getSID();
        String query = "SELECT event_id FROM single_event WHERE (schedule_id = " + sid +
                " AND date=\"" + date +"\") AND (time_end < \'" + time_end + "\') AND" +
                " (time_start < \'" + time_start + "\' AND time_end > \'" + time_start + "\')";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int updateEventUpperEnd(int event_id, String old_time_end) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE single_event " +
                            " SET time_start = \"" + old_time_end + "\" " +
                            " WHERE event_id = " + event_id;
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int updateEventLowerEnd(int event_id, String old_time_start) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE single_event " +
                            " SET time_end = \"" + old_time_start + "\" " +
                            " WHERE event_id = " + event_id;
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private String getOtherTimeEnd(int event_id) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT time_end FROM single_event WHERE event_id=" + event_id;
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getOtherTimeStart(int event_id) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT time_start FROM single_event WHERE event_id=" + event_id;
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // comment out later
    public static void main(String[] args) {
        DatabaseAccess dba = new DatabaseAccess();
        //String s = dba.getAllSingleEvents();

        //System.out.println(s);
    }
}
