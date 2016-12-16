package com.vanderveldt.rens.last_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Rens on 15-12-2016.
 *
 * From: // From http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
 */

public class RequestHandler {


    // Reads input.
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String mag, Integer time, String results) throws IOException, JSONException {

        // Extract user data.

        // Convert days setting to usable data
        Calendar cal = GregorianCalendar.getInstance();
        cal.add( Calendar.DAY_OF_YEAR, -time);
        SimpleDateFormat formatCal = new SimpleDateFormat("yyyy-MM-dd");
        Date calDate = cal.getTime();
        String date = formatCal.format(calDate);

        // Get the inputstream from the created url, and return json to the asynctask.
        InputStream is = new URL("http://earthquake.usgs.gov/fdsnws/event/1/query.geojson?endtime=" + date + "&minmagnitude=" + mag + "&orderby=time&limit=" + results).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
