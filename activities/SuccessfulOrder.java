package com.example.unlimited_store.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.unlimited_store.R;

public class SuccessfulOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_order);

        /**
         * Nút BackHome
         */
        Button btnBackHome = findViewById(R.id.btnBackHome);

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessfulOrder.this, MainActivity.class));
                finish();
            }
        });
    }
}