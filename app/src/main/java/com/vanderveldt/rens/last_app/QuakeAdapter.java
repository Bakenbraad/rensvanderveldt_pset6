package com.vanderveldt.rens.last_app;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Rens on 16-12-2016.
 *
 * Tutorial: https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 */

public class QuakeAdapter extends ArrayAdapter<EarthQuake> {

    // List of items
    private ArrayList<EarthQuake> objects;

    // Constructor
    public QuakeAdapter(Context context, int textViewResourceId, ArrayList<EarthQuake> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }


    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_layout, null);
        }

        EarthQuake i = objects.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView loc = (TextView) v.findViewById(R.id.locationTV);
            TextView mag = (TextView) v.findViewById(R.id.magTV);
            TextView time = (TextView) v.findViewById(R.id.timeTV);

            loc.setText(i.getLocation());
            mag.setText(i.getMagnitude());

            // Format date before setting.
            SimpleDateFormat formatCal = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatCal.format(Long.parseLong(i.getTime()));
            time.setText(date);
        }

        // the view must be returned to our activity
        return v;

    }
}
