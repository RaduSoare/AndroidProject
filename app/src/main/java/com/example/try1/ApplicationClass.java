package com.example.try1;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static ArrayList<Location> restaurants;

    //Firebase
    private DatabaseReference myRef;


    @Override
    public void onCreate() {
        super.onCreate();
        restaurants = new ArrayList<>();
       /* restaurants.add(new Location("Dyonisos", "Greek Tavern", "La dreacu", R.drawable.dionysos, "Description Dyonisos", ""));
        restaurants.add(new Location("DaVinci", "Cucina Italiana", "Bobalna ceva", R.drawable.davinci, "Description DaVinci", ""));*/

        myRef = FirebaseDatabase.getInstance().getReference();

        restaurants = new ArrayList<>();

        clearAll();

        getDataFromFirebase();

    }


    private void getDataFromFirebase() {
        Query query = myRef.child("restaurants");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue().toString();
                    String specific = snapshot.child("specific").getValue().toString();
                    String adress = snapshot.child("location").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String thumbnailLink = snapshot.child("image").getValue().toString();
                    Location location = new Location(name, specific, adress, 14, description, thumbnailLink);
                    restaurants.add(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clearAll() {
        if(restaurants != null) {
            restaurants.clear();
        }
        restaurants = new ArrayList<>();
    }
}
