package com.example.try1;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static ArrayList<Location> restaurants;

    @Override
    public void onCreate() {
        super.onCreate();
        restaurants = new ArrayList<>();
        restaurants.add(new Location("Dyonisos", "Greek Tavern", "La dreacu", R.drawable.dionysos, "Description Dyonisos"));
        restaurants.add(new Location("DaVinci", "Cucina Italiana", "Bobalna ceva", R.drawable.davinci, "Description DaVinci"));


    }
}
