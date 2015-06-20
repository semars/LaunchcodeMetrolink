package com.semars.util;

import com.semars.models.Time;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TimeCalculator {

    public int calculate(List<Time> arrivalTimes, long currentMillis) {
        List<Long> timesFormatted = parseTimes(arrivalTimes);
        return getNextTrainTime(timesFormatted, currentMillis);
    }

    public List<Long> parseTimes(List<Time> arrivalTimes) {
        List<Long> parsedTimes = new ArrayList<Long>();
        for (Time time : arrivalTimes) {
            String[] timeSplit = time.getArrivalTime().split(":");
            Integer timeHour = Integer.parseInt(timeSplit[0]);
            Integer timeMinute = Integer.parseInt(timeSplit[1]);
            Integer timeSecond = Integer.parseInt(timeSplit[2]);
            Long timeMilli = (timeHour.longValue()*60*60*1000 + timeMinute.longValue()*60*1000 + timeSecond.longValue()*1000);
            parsedTimes.add(timeMilli);
        }
        return parsedTimes;
    }

    private int getNextTrainTime(List<Long> timesFormatted, long currentMillis) {
        Long nextTrainTime = timesFormatted.get(timesFormatted.size()-1);
        for (Long arrivalTime : timesFormatted) {
            if (arrivalTime > currentMillis) {
                if (arrivalTime < nextTrainTime) {
                    nextTrainTime = arrivalTime;
                }
            }
        }
        int nextTrainMinutes = (int) (nextTrainTime - currentMillis)/60/1000;
        return nextTrainMinutes;
    }
}
