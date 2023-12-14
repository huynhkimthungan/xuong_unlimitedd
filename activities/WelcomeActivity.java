package com.example.unlimited_store.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.unlimited_store.R;
import com.example.unlimited_store.database.AppDatabase;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDatabase = new AppDatabase(WelcomeActivity.this);

                if(AppDatabase.getWelcomeState()){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity1.class));
                    finish();
                }
            }
        },1500);
    }
}