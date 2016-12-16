package com.vanderveldt.rens.last_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<EarthQuake> earthquakesList = new ArrayList<>();
    private ListView lv;
    private FirebaseAuth mAuth;
    public FirebaseUser user;
    private DatabaseReference mSettingsReference;
    UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Earthquake view");

        // Get the listview
        lv = (ListView) findViewById(R.id.listView);

        // Get instance of user.
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Get User-settings
        mSettingsReference = FirebaseDatabase.getInstance().getReference().child("userSettings").child(user.getUid());
        if (user != null) {
            // User is signed in
            ValueEventListener settingsListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get UserSettings object.
                    settings = dataSnapshot.getValue(UserSettings.class);
                    if (settings != null) {
                        loadData();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mSettingsReference.addValueEventListener(settingsListener);
        }
    }

    public void goToLogin() {
        Intent goToMain = new Intent(this, LoginActivity.class);
        startActivity(goToMain);
        finish();
    }

    public void logOut(View view){
        mAuth.signOut();
        goToLogin();
    }


    public void goToSettings(View view) {
        Intent goToSettings = new Intent(this, SettingsActivity.class);
        startActivity(goToSettings);
    }

    public void loadData() {
        new EQAsyncTask(this, this, settings).execute();
        QuakeAdapter adapter = new QuakeAdapter(this,R.layout.row_layout, earthquakesList);
        lv.setAdapter(adapter);
    }

    public void setList(ArrayList<EarthQuake> EarthQuakesList){
        this.earthquakesList = EarthQuakesList;
    }

    public void reloadData(View view) {
        loadData();
    }
}
