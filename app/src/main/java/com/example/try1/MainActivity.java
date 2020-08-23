package com.example.try1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.try1.loginSystem.Login;
import com.example.try1.ui.leaderboard.LeaderboardActivity;
import com.example.try1.ui.leaderboard.LeaderboardEntry;
import com.example.try1.ui.pubs.PubsActivity;
import com.example.try1.ui.restaurants.RestaurantActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    public static HashMap<String, Boolean> locationsVisited;

    // Lista userilor din leaderboard
    public static ArrayList<LeaderboardEntry> leaderboardUsers;

    TextView tvUserName;
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference firestoreReference;

    private interface FirestoreCallback {
        void onCallBack(HashMap<String, Boolean> locationsVisited);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Cod autogenerat. Construieste drawerul
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Instante din MainActivity
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreReference = firebaseFirestore.collection(getString(R.string.users_collection)).document(firebaseAuth.getCurrentUser().getUid());

        tvUserName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        btnLogout = findViewById(R.id.btnLogout);

        leaderboardUsers = new ArrayList<>();

        // Logica buton de Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        // Afisare nume user in Drawer
        tvUserName.setText(firebaseAuth.getCurrentUser().getEmail().toString());

        // Obtine locatiile vizitate de userul curent
        getVisitedLocations(new FirestoreCallback() {
            @Override
            public void onCallBack(HashMap<String, Boolean> locationsVisited) {

            }
        });

        // Obtine lista userilor pentru clasament
        getUsersForLeaderboard();


    }

      /*
    Hack pentru a putea folosi lista de locatii vizitate si in afara OnCreate
    Aparent onComplete e asincrona si nu apuca sa se introduca datele in lista fara CallBack
     */
    private void getVisitedLocations(final FirestoreCallback firestoreCallback) {
        firestoreReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        locationsVisited = (HashMap<String, Boolean>) document.get("visited");
                        firestoreCallback.onCallBack(locationsVisited);
                    } else {
                        Log.d("TAG", "Error getting firestore document: " + task.getException());
                    }
                }
            }

        });
    }

    /*
    Obtine userii din DB si creeaza o lista de forma (Nume, NrLocatiiVizitate) pentru clasament
     */
    private void getUsersForLeaderboard() {
        firebaseFirestore.collection(getString(R.string.users_collection)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                LeaderboardEntry leaderboardEntry = new LeaderboardEntry((String) document.get(getString(R.string.full_name_user_field)),
                                        (int) ((HashMap<String, Object>) document.get(getString(R.string.visited_user_field))).size());
                                leaderboardUsers.add(leaderboardEntry);

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /*
   Hack ca atunci cand se apasa BACK cu drawerul pornit, sa se inchida drawerul, nu aplicatia
    */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_restaurants:
                Intent intentRestaurants = new Intent(MainActivity.this, RestaurantActivity.class);
                startActivity(intentRestaurants);
                break;
            case R.id.nav_pubs:
                Intent intentPubs = new Intent(MainActivity.this, PubsActivity.class);
                startActivity(intentPubs);
                break;
            case R.id.nav_leaderboard:
                Intent intentLeaderboard = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intentLeaderboard);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




}