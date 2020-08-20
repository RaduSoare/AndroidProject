package com.example.try1.loginSystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.try1.Location;
import com.example.try1.MainActivity;
import com.example.try1.R;
import com.example.try1.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText etRegisterFullName, etRegisterEmail, etRegisterPassword, etPhone;
    Button btnRegister;
    Button btnToLoginPage;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterFullName = findViewById(R.id.etRegisterFullName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);
        btnToLoginPage = findViewById(R.id.btnToLoginPage);

        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etRegisterEmail.getText().toString().trim();
                String password = etRegisterPassword.getText().toString().trim();
                final String fullName = etRegisterFullName.getText().toString();
                final String phone = etPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    etRegisterEmail.setError(getString(R.string.email_empty));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etRegisterPassword.setError(getString(R.string.password_empty));
                    return;
                }

                if (password.length() < 6) {
                    etRegisterPassword.setError(getString(R.string.password_constraint));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();

                            // Retine ID-ul userului curent
                            userID = fAuth.getCurrentUser().getUid();

                            // Retine documentul corespunzator ID-ului
                            DocumentReference documentReference = fStore.collection(getString(R.string.users_collection)).document(userID);

                            // Creeaza o intrare pentru userul curent cu datele din Hash

                            HashMap<String, Boolean> visited = new HashMap<>();
                            User user = new User(fullName, phone, email, visited);
                            documentReference.set(user);


                            progressBar.setVisibility(View.INVISIBLE);

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        btnToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}