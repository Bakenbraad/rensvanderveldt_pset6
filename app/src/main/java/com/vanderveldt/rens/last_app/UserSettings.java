package com.vanderveldt.rens.last_app;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Rens on 15-12-2016.
 */
@IgnoreExtraProperties
public class UserSettings {
    private String days;
    private String magnitude;
    private String results;

    public UserSettings() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserSettings(String days, String magnitude, String results) {
        this.days = days;
        this.magnitude = magnitude;
        this.results = results;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getResults() {
        return results;
    }

    public String getDays() {
        return days;
    }
}
