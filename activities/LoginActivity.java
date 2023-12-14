package com.example.unlimited_store.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    public static String username;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Khai báo và ánh xạ
        TextInputEditText edtUsername = findViewById(R.id.edtUsernameLogin);
        TextInputEditText edtPassword = findViewById(R.id.edtPasswordLogin);

        userDAO = new UserDAO(LoginActivity.this);

        /**
         * Sự kiện tvForgotPassword
         */
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyển đến màn hình ForgotPasswordActivity
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle();
                    startActivity(intent, bundle);
                }
            }
        });

        /**
         * Sự kiện tvSignUp
         */
        TextView tvSignUp = findViewById(R.id.tvSignUp);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyển đến màn hình RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle();
                    startActivity(intent, bundle);
                }
            }
        });


        /**
         * Sự kiện btnLogin
         */
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy dữ liệu nhập từ người dùng
                username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                //Kiểm tra username, password trong database
                boolean check = userDAO.login(username,password);
                if(check){
                    //Thông báo và chuyển sang MainActivity
                    Toast.makeText(LoginActivity.this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    //Set trạng thái login và lưu username vào sharedPreferences
                    AppDatabase.setLoginState(true);
                    AppDatabase.saveUsername(username);

                    //Transition
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle();
                        startActivity(intent, bundle);
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}