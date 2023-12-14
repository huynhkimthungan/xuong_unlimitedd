package com.example.unlimited_store.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.unlimited_store.R;
import com.example.unlimited_store.database.AppDatabase;

public class WelcomeActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome1);

        Button btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.setWelcomeState(true);
                startActivity(new Intent(WelcomeActivity1.this, WelcomeActivity2.class));
            }
        });
    }
}