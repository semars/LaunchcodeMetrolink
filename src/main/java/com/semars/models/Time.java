package com.semars.models;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "stop_times")
public class Time {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column (name = "trip_id", unique = true, nullable = false)
    private Integer tripID;
    @Column (name = "stop_id", unique = false, nullable = false)
    private Integer stopID;
    @Column (name = "arrival_time")
    private String arrivalTime;

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public int getStopID() {
        return stopID;
    }
    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}