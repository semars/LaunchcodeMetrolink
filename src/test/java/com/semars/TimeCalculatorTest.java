package com.semars;

import com.semars.models.Time;
import com.semars.util.TimeCalculator;
import com.semars.dao.SqliteJDBCDao;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TimeCalculatorTest {

    @Test
    public void testCalcEightAndTrainAtEightShouldReturnZero() {
        TimeCalculator timeCalculator = new TimeCalculator();
        List<Time> arrivalTimes = new ArrayList<Time>();
        Time time = new Time();
        long currentMillis = (8)*60*60*1000;

        time.setArrivalTime("8:00:00");
        arrivalTimes.add(time);
        long nextTrainMinutes = timeCalculator.calculate(arrivalTimes, currentMillis);

        assertEquals(0, nextTrainMinutes);
    }

    @Test
    public void testCalcTwelveAndTrainAtTwelveFiftyNineShouldReturnFiftyNine() {
        TimeCalculator timeCalculator = new TimeCalculator();
        List<Time> arrivalTimes = new ArrayList<Time>();
        Time time = new Time();
        long currentMillis = (12)*60*60*1000;

        time.setArrivalTime("12:59:59");
        arrivalTimes.add(time);
        long nextTrainMinutes = timeCalculator.calculate(arrivalTimes, currentMillis);

        assertEquals(59, nextTrainMinutes);
    }

    @Test
    public void testCalcTwentyFiveAndUMSLShouldReturnEight() {
        //UMSL South Metrolink Station
        TimeCalculator timeCalculator = new TimeCalculator();
        SqliteJDBCDao sqliteJDBCDao = new SqliteJDBCDao();
        int stopID = 10630;
        long currentMillis = (25)*60*60*1000;

        List<Time> arrivalTimes = sqliteJDBCDao.getArrivalTimes(stopID);
        long nextTrainMinutes = timeCalculator.calculate(arrivalTimes, currentMillis);
        assertEquals(nextTrainMinutes, 8);
    }

    @Test
    public void testCalcTwelveAndShrewsburyShouldReturnFour() {
        //Shrewsbury Metrolink Station
        TimeCalculator timeCalculator = new TimeCalculator();
        SqliteJDBCDao sqliteJDBCDao = new SqliteJDBCDao();
        int stopID = 14753;
        long currentMillis = (12)*60*60*1000;

        List<Time> arrivalTimes = sqliteJDBCDao.getArrivalTimes(stopID);
        long nextTrainMinutes = timeCalculator.calculate(arrivalTimes, currentMillis);
        assertEquals(nextTrainMinutes, 4);
    }

    @Test
    public void testParsingNoonShouldReturn43200000Millis() {
        TimeCalculator timeCalculator = new TimeCalculator();
        List<Time> arrivalTimes = new ArrayList<Time>();
        Time time = new Time();
        long parsedMillis = 0;

        time.setArrivalTime("12:00:00");
        arrivalTimes.add(time);
        List<Long> parsedTime = timeCalculator.parseTimes(arrivalTimes);
        for (Long parsed : parsedTime) {
            parsedMillis = parsed;
        }

        assertEquals(43200000, parsedMillis);
    }

}
