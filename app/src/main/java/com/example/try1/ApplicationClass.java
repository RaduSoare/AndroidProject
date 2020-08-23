package com.example.try1;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.try1.ui.leaderboard.LeaderboardEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/*
 Clasa Globala ce poate fi vazuta din tot proiectul pentru chestiile FOARTE generale
 */
public class ApplicationClass extends Application {

    // Lista restaurantelor
    public static ArrayList<Location> restaurants;

    private DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate() {
        super.onCreate();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        restaurants = new ArrayList<>();
        restaurants = getDataFromFirebase(restaurants, getString(R.string.restaurants_location));


    }

    /*
     * Fiecare snapshot e un obiect din colectia data ca parametru
     * Obtine fiecare locatie din colectia specificata
     */
    private ArrayList<Location> getDataFromFirebase(final ArrayList<Location> locations, String collection) {

        Query query = databaseReference.child(collection);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Curata lista inainte sa adauge noile Locatii updatate din Database
                if(locations != null) {
                    locations.clear();
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child(getString(R.string.name_location_field)).getValue().toString();
                    String specific = snapshot.child(getString(R.string.specific_location_field)).getValue().toString();
                    String adress = snapshot.child(getString(R.string.adress_location_field)).getValue().toString();
                    String description = snapshot.child(getString(R.string.description_location_field)).getValue().toString();
                    String thumbnailLink = snapshot.child(getString(R.string.image_location_field)).getValue().toString();
                    Location location = new Location(name, specific, adress,  description, thumbnailLink);
                    locations.add(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return locations;
    }







}
