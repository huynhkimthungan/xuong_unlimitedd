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
import com.google.android.material.textfield.TextInputEditText;

public class ResetPasswordActivity extends AppCompatActivity {

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        /**
         * Khai báo và ánh xạ
         */
        TextView tvUsername = findViewById(R.id.tvUsernameRP);
        TextInputEditText edtPassword = findViewById(R.id.edtPasswordRP);
        TextInputEditText edtConfirmPassword = findViewById(R.id.edtConfirmPasswordRP);


        /**
         * Khởi tạo userDAO
         */
        userDAO = new UserDAO(ResetPasswordActivity.this);


        /**
         * Nhận dữ liệu từ ForgotPassword
         */
        Intent intent = getIntent();
        String tenDangNhap = intent.getStringExtra("tenDangNhap");
        tvUsername.setText(tenDangNhap);


        /**
         * Nút confirm
         */
        Button btnConfirm = findViewById(R.id.btnConfirmRP);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy dự liệu từ người dùng nhập
                String newPassword = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                //Kiểm tra dữ liệu đã nhập đầy đủ chưa
                if (newPassword.length() == 0 || confirmPassword.length() == 0) {
                    Toast.makeText(ResetPasswordActivity.this,"Please complete all information!",Toast.LENGTH_SHORT).show();
                }else{
                    //Kiểm tra mật khẩu có hợp lệ không?
                    if (RegisterActivity.isValid(newPassword)){
                        //Kiểm tra xác nhận mật khẩu có chính xác không?
                        if(newPassword.equals(confirmPassword)){
                            boolean check = userDAO.updatePassword(newPassword,tenDangNhap, userDAO.getRoleByUsername(tvUsername.getText().toString()));
                            if(check){
                                //Thông báo và chuyển sang màn hình LoginActivity khi thành công
                                Toast.makeText(ResetPasswordActivity.this,"Password reset successful!",Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(ResetPasswordActivity.this, LoginActivity.class);

                                //Transition
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(ResetPasswordActivity.this).toBundle();
                                    startActivity(intent1, bundle);
                                }
                                finish();
                            }else{
                                //Thông báo khi thất bại
                                Toast.makeText(ResetPasswordActivity.this,"Password reset failed!",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            //Thông báo khi xác nhận password không chính xác
                            Toast.makeText(ResetPasswordActivity.this,"Password confirmation is incorrect!!!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        //Thông báo khi đặt mật khẩu không hợp lệ
                        Toast.makeText(ResetPasswordActivity.this, "Password must be \n" +
                                "greater than 8 characters\n " +
                                "including numbers\n" +
                                " capital letters, and special characters\n" +
                                "Ex: PuPuChaCha@9389", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}