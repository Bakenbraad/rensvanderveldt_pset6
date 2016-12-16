package com.vanderveldt.rens.last_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rens on 15-12-2016.
 */

public class EQAsyncTask extends AsyncTask<Void, Void, ArrayList<EarthQuake>> {

    public ArrayList<EarthQuake> earthquakesList;
    private Context context;
    private MainActivity activity;
    String magnitude;
    Integer days;
    String results;

    EQAsyncTask (Context c, MainActivity activity, UserSettings settings){
        context = c;
        this.activity = activity;
        days = Integer.parseInt(settings.getDays());
        results = settings.getResults();
        magnitude = settings.getMagnitude();

    }

    @Override
    protected void onPreExecute() {
        // Let the user know we are doing all we can.
        Toast.makeText(context, "Searching", Toast.LENGTH_SHORT).show();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<EarthQuake> doInBackground(Void... params) {

        try {
            // Parse Json to list of earthquake objects.
            JSONObject searchResult = RequestHandler.readJsonFromUrl(magnitude, days, results);
            if (searchResult != null){
                JSONArray array = searchResult.getJSONArray("features");
                earthquakesList = new ArrayList<>();

                for(int i = 0; i < array.length(); i++) {
                    // Get a quake from the quakes array:
                    JSONObject feature = array.getJSONObject(i);

                    // Get its properties
                    JSONObject properties = feature.getJSONObject("properties");
                    if (properties != null){
                        String place = properties.getString("place");
                        String mag = properties.getString("mag");
                        String time = properties.getString("time");
                        if (place == null){
                            place = "unknown";
                        }
                        if (mag == null){
                            mag = "unknown";
                        }
                        if (time == null){
                            time = "unknown";
                        }

                        // Add Quake to list.
                        earthquakesList.add(new EarthQuake(place, mag, time));
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return earthquakesList;
    }

    @Override
    protected void onPostExecute(ArrayList<EarthQuake> earthquakesList) {
        super.onPostExecute(earthquakesList);
        // Send the list to the main activity for loading into listview
        activity.setList(earthquakesList);

    }
}
