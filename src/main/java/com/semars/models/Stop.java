package com.semars.models;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "stops")
public class Stop {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stop_id", unique = true, nullable = false)
    private Integer stopID;
    @Column (name = "stop_name")
    private String stopName;
    @Column (name = "stop_desc")
    private String stopDescription;

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopDescription() {
        return stopDescription;
    }

    public void setStopDescription(String stopDescription) {
        this.stopDescription = stopDescription;
    }

    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }
}
