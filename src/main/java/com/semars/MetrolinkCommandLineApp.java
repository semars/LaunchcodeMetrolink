package com.semars;

import com.semars.util.TimeCalculator;
import org.joda.time.LocalTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class MetrolinkCommandLineApp {

    public static void main(String[] varArgs) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        MetrolinkCommandLineApp metrolinkCommandLineApp =
                (MetrolinkCommandLineApp) context.getBean("metrolinkCommandLineApp");
        metrolinkCommandLineApp.run();
    }

    private void run() {
        // Lists all of the Stations matching search string
        String stationName = "METROLINK STATION";
        List<Stop> matchedStops = getMatchedStops(stationName);

        // Asks the user what station they are currently at
        Scanner inputScan = new Scanner(System.in);
        String inputCurrentStation;
        int checkedStopID;
        do {
            appOutput.print("Which station are you at?");
            inputCurrentStation = inputScan.nextLine().toUpperCase();
            checkedStopID = checkStop(inputCurrentStation, matchedStops);
        } while (checkedStopID == 0);

        // Displays the amount time until the next arrives (aka 'The next train is arriving in {x} minutes')
        List<Time> arrivalTimes = getArrivalTimes(checkedStopID);
        int nextTrainMinutes = getNextArrivalTime(arrivalTimes);
        appOutput.print(String.format("The next train is arriving in %d minutes.", nextTrainMinutes));
    }

    private List<Stop> getMatchedStops(String stationName) {
        // Format and output all stops matching query to console
        List<Stop> stopsMatchedStops = metrolinkDao.getStopsMatchedStops(stationName);
        for (Stop stop : stopsMatchedStops) {
            appOutput.print(String.format("--- %s ---", stop.getStopName()));
        }
        return stopsMatchedStops;
    }

    private int checkStop(String stopName, List<Stop> stopsList) {
        // Ensure user-input matches a stop
        for (Stop stop : stopsList) {
            if (stopName.equals(stop.getStopName())) {
                return stop.getStopID();
            }
        }
        // Case: stop not found in list
        return 0;
    }

    private List<Time> getArrivalTimes(int stopID) {
        List<Time> arrivalTimes = metrolinkDao.getArrivalTimes(stopID);
        return arrivalTimes;
    }

    private int getNextArrivalTime(List<Time> arrivalTimes) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        TimeCalculator timeCalculator = (TimeCalculator) context.getBean("timeCalculator");
        long current = LocalTime.now().getMillisOfDay();
        System.out.format("current ms: %d\n", current);
        int nextTrainMinutes = timeCalculator.calculate(arrivalTimes, current);
        return nextTrainMinutes;
    }

    private MetrolinkDao metrolinkDao;
    private AppOutput appOutput;

    public void setMetrolinkDao(MetrolinkDao metrolinkDao) {
        this.metrolinkDao = metrolinkDao;
    }

    public void setAppOutput(AppOutput appOutput) {
        this.appOutput = appOutput;
    }
}
