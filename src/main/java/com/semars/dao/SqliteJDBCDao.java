package com.semars.dao;

import com.semars.AppOutput;
import com.semars.MetrolinkDao;
import com.semars.Stop;
import com.semars.Time;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteJDBCDao implements MetrolinkDao {

    public static final String JDBC_SQLITE_METROLINK_DB = "jdbc:sqlite:metrolink.db";
    public static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";

    private AppOutput appOutput;

    public List<Stop> getStopsMatchedStops(String stationName) {
        appOutput.print("Fetching stations...");
        stationName = "%" +stationName + "%";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stops WHERE stop_name LIKE ?1");
            preparedStatement.setString(1, stationName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Stop> matchedStops = new ArrayList<Stop>();
            while (resultSet.next()) {
                Stop stop = new Stop();
                stop.setStopID(resultSet.getInt("stop_id"));
                stop.setStopName(resultSet.getString("stop_name"));
                stop.setStopDescription(resultSet.getString("stop_desc"));
                matchedStops.add(stop);
            }
            return matchedStops;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stops");
        }
    }

    public List<Time> getArrivalTimes(int stopID) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT arrival_time FROM stop_times WHERE stop_id=?1 ORDER BY arrival_time");
            preparedStatement.setInt(1, stopID);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Time> arrivalTimes = new ArrayList<Time>();
            while (resultSet.next()) {
                Time time = new Time();
                time.setArrivalTime(resultSet.getString("arrival_time"));
                arrivalTimes.add(time);
            }
            return arrivalTimes;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving arrival times");
        }
    }

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(ORG_SQLITE_JDBC);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find class for loading the database", e);
        }
        return DriverManager.getConnection(JDBC_SQLITE_METROLINK_DB);
    }

    public void setAppOutput(AppOutput appOutput) {
        this.appOutput = appOutput;
    }
}
