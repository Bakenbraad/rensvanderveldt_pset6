package com.vanderveldt.rens.last_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    EditText dateTime;
    TextView magnitude;
    EditText results;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseUser user;

    private DatabaseReference mDatabase;
    private DatabaseReference mSettingsReference;
    UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        // Get instance of user and database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        // Get text fields:
        dateTime = (EditText) findViewById(R.id.dateTime);
        magnitude = (TextView) findViewById(R.id.magnitudeText);
        results = (EditText) findViewById(R.id.editText3);

        user = mAuth.getCurrentUser();
        mSettingsReference = FirebaseDatabase.getInstance().getReference().child("userSettings").child(user.getUid());
        if (user != null) {
            // User is signed in
            ValueEventListener settingsListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get UserSettings object and use the values to update the UI
                    UserSettings settings = dataSnapshot.getValue(UserSettings.class);
                    if (settings != null){
                        dateTime.setText(settings.getDays());
                        magnitude.setText(settings.getMagnitude());
                        results.setText(settings.getResults());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, show a message
                    Toast.makeText(SettingsActivity.this, "Please enter a number between 1 and 365",
                            Toast.LENGTH_SHORT).show();
                }
            };
            mSettingsReference.addValueEventListener(settingsListener);
        } else {
            // User is signed out
            Intent goToLogin = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(goToLogin);
            finish();
        }
    }

    public void saveSettings (View view) {

        // Get the input values from the fields.
        String dateTimeText = dateTime.getText().toString();
        String magnitudeText = magnitude.getText().toString();
        String resultsText = results.getText().toString();

        try {
            Integer intTime = Integer.parseInt(dateTimeText);
            Integer intResults = Integer.parseInt(resultsText);
            if (intTime > 0) {
                if (intTime < 366) {
                    if (intResults > 0) {
                        if (intResults < 100) {
                            // Create a settings object to store user settings
                            settings = new UserSettings(dateTimeText, magnitudeText, resultsText);

                            // Use the UID to store the settings to a unique user.
                            String userid = user.getUid();

                            // Store data in userSettings
                            mDatabase.child("userSettings").child(userid).setValue(settings);

                            // Comfort the user that their settings are safe.
                            Toast.makeText(this, "Saved",
                                    Toast.LENGTH_SHORT).show();

                            forward();
                        } else {
                            Toast.makeText(this, "Please enter a valid number",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please enter a valid number",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please enter a valid number",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a valid number",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(SettingsActivity.this, "Please enter valid numbers",
                    Toast.LENGTH_SHORT).show();
        }
    }


        // Check if time settings are correct.


    private void forward(){
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);
        finish();
    }

    public void decreaseMag(View view) {
        Double mag = Double.parseDouble(magnitude.getText().toString());
        if (mag == 0.5){
            Toast.makeText(this, "Minimum reached",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            mag = mag - 0.5;
            magnitude.setText(mag.toString());
        }
    }

    public void increaseMag(View view) {
        Double mag = Double.parseDouble(magnitude.getText().toString());
        if (mag == 10.5){
            Toast.makeText(this, "Maximum reached",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            mag = mag + 0.5;
            magnitude.setText(mag.toString());
        }
    }
}
