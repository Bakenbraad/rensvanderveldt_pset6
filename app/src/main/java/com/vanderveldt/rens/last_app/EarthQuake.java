package com.vanderveldt.rens.last_app;

/**
 * Created by Rens on 15-12-2016.
 */

public class EarthQuake {
    private String location;
    private String magnitude;
    private String time;

    public EarthQuake(String location, String magnitude, String time){
        super();
        this.location = location;
        this.magnitude = magnitude;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getTime() {
        return time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
