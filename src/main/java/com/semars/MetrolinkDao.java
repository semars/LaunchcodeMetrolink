package com.semars;

import com.semars.models.Stop;
import com.semars.models.Time;

import java.util.List;

public interface MetrolinkDao {

    List<Stop> getStopsMatchedStops(String stationName);

    List<Time> getArrivalTimes(int stopID);
}
