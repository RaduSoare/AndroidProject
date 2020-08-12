package com.example.try1.ui.general;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.try1.R;

public class DetailActivity extends AppCompatActivity {

    TextView tvLocationDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvLocationDescription = findViewById(R.id.tvLocationDescription);
        tvLocationDescription.setText(getIntent().getStringExtra("description"));
    }

}