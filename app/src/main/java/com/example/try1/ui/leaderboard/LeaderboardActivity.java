package com.example.try1.ui.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.try1.MainActivity;
import com.example.try1.R;

import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recyclerView = findViewById(R.id.users_leaderboard_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Sorteaza lista din clasament de fiecare data cand urmeaza sa se afiseze
        // Poate merge gasita o varianta mai optima in care sa nu se faca sortarea decat daca se modifica ceva
        Collections.sort(MainActivity.leaderboardUsers);

        myAdapter = new LeaderboardAdapter(this, MainActivity.leaderboardUsers);
        recyclerView.setAdapter(myAdapter);
        Toast.makeText(this, "Aici", Toast.LENGTH_SHORT).show();
        myAdapter.notifyDataSetChanged();
    }
}