package com.example.try1.loginSystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.try1.MainActivity;
import com.example.try1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    ProgressBar progressBarLogin;
    FirebaseAuth fAuth;
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        progressBarLogin = findViewById(R.id.progressBarLogin);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);


        // Verificare daca userul e dega logat si se trimite catre activitatea principala
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Extragere input de la user
                String email = etLoginEmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();

                // Constrangeri pentru input
                if (TextUtils.isEmpty(email)) {
                    etLoginEmail.setError(getString(R.string.email_empty));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etLoginPassword.setError(getString(R.string.password_empty));
                    return;
                }

                if (password.length() < 6) {
                    etLoginPassword.setError(getString(R.string.password_constraint));
                    return;
                }

                // ProgressBar-ul devine vizibil pana se introduc datele in BD
                progressBarLogin.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "User signed in", Toast.LENGTH_SHORT).show();
                            progressBarLogin.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarLogin.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        // Butonul care trimite catre Register daca userul nu are deja cont
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}