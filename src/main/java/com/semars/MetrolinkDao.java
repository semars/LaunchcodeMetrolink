package com.semars;

import java.util.List;

public interface MetrolinkDao {

    List<Stop> getStopsMatchedStops(String stationName);

    List<Time> getArrivalTimes(int stopID);
}
