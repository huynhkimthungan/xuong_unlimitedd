package com.example.unlimited_store.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.dao.UserDAO;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Khai báo và ánh xạ
        TextInputEditText edtUsername = findViewById(R.id.edtUsernameFP);
        Button btnReset = findViewById(R.id.btnResetFP);

        userDAO = new UserDAO(ForgotPasswordActivity.this);

        //Sự kiện btnReset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtUsername.getText().toString();
                //Validate thông tin
                if(tenDangNhap.equals("")){
                    Toast.makeText(ForgotPasswordActivity.this, "Please complete all information!", Toast.LENGTH_SHORT).show();
                    //Kiểm tra username có tồn tại không?
                }else if(!userDAO.checkExistingUsername(tenDangNhap)){
                    Toast.makeText(ForgotPasswordActivity.this,"Your username does not exist",Toast.LENGTH_SHORT).show();
                }else{
                    //Chuyển activity và transition
                    Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("tenDangNhap",tenDangNhap);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(ForgotPasswordActivity.this).toBundle();
                        startActivity(intent, bundle);
                    }
                }
            }
        });
    }
}