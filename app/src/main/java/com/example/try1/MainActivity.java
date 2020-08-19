package com.example.try1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.try1.loginSystem.Login;
import com.example.try1.ui.leaderboard.LeaderboardEntry;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_restaurants, R.id.nav_leaderboard)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

    private void getUsersForLeaderboard() {
        firebaseFirestore.collection(getString(R.string.users_collection)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                LeaderboardEntry leaderboardEntry = new LeaderboardEntry((String) document.get("fullName"), (int) ((HashMap<String, Object>) document.get("visited")).size());
                                leaderboardUsers.add(leaderboardEntry);

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}