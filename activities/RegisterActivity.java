package com.example.unlimited_store.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.model.User;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         * Khai báo và ánh xạ
         */
        TextInputEditText edtUsername = findViewById(R.id.edtUsernameRegister);
        TextInputEditText edtPassword = findViewById(R.id.edtPasswordRegister);
        TextInputEditText edtEmail = findViewById(R.id.edtEmailRegister);
        TextInputEditText edtPhoneNumber = findViewById(R.id.edtPhoneNumberRegister);
        TextInputEditText edtAddress = findViewById(R.id.edtAddressRegister);
        TextInputEditText edtConfirmPassword = findViewById(R.id.edtConfirmPasswordRegister);


        /**
         * Khởi tạo userDAO
         */
        userDAO = new UserDAO(RegisterActivity.this);


        /**
         * Sự kiện tvLogin
         */
        TextView tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyển sang màn hình LoginActivity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        /**
         * Sự kiện btnSignUp
         */
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy dữ liệu người dùng nhập
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String email = edtEmail.getText().toString();
                String phoneNumber = edtPhoneNumber.getText().toString();
                String address = edtAddress.getText().toString();

                //Kiểm tra đã nhập thông tin đầy đủ chưa?
                if (username.equals("") || password.equals("") || confirmPassword.equals("") || email.equals("") || phoneNumber.equals("") || address.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please complete all information!", Toast.LENGTH_SHORT).show();
                } else {
                    //Kiểm tra tên đăng nhập có bị trùng không?
                    if (userDAO.checkExistingUsername(username)) {
                        Toast.makeText(RegisterActivity.this, "Username available. Please enter another usernam!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Kiểm tra mật khẩu nhập có hợp lệ không?
                        if (!isValid(password)) {
                            Toast.makeText(RegisterActivity.this, "Password must be \ngreater than 8 characters\n including numbers\n capital letters, and special characters\nEx: PuPuChaCha@9389", Toast.LENGTH_SHORT).show();
                        } else {
                            //Kiểm tra xác nhận mật khẩu có chính xác không?
                            if (!confirmPassword.equals(password)) {
                                Toast.makeText(RegisterActivity.this, "Password confirmation is incorrect!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                //Kiểm tra độ dài của số điện thoại
                                if (!(phoneNumber.length() == 10)) {
                                    Toast.makeText(RegisterActivity.this, "Phone number must be 10 characters", Toast.LENGTH_SHORT).show();
                                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                    //Kiểm tra kiểu email có hợp lệ không?
                                    Toast.makeText(RegisterActivity.this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Ghi tài khoản vào csdl
                                    User user = new User(username, password, phoneNumber, email, address);
                                    boolean check = userDAO.register(user);
                                    if (check) {
                                        //Thông báo và chuyển sang màn hình LoginActivity khi thành công
                                        Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                                        //Transition
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this).toBundle();
                                            startActivity(intent, bundle);
                                        }
                                    } else {
                                        //Thông báo khi thất bại
                                        Toast.makeText(RegisterActivity.this, "Registration failed!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }
                    }
                }
            }
        });
    }

    //Phương thức kiểm tra mật khẩu có hợp lệ không?
    public static boolean isValid(String password) {
        //Khởi tạo các biến kiểm tra
        int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0;

        //Xét độ dài mật khẩu phải hơn 8 kí tự
        if (password.length() > 8) {
            for (int i = 0; i < password.length(); i++) {
                String c = password.charAt(i) + "";
                //Kiểm tra mật khẩu có chữ số không?
                if (Character.isLetter(password.charAt(i))) {
                    flag1 = 1;
                    //Kiểm tra mật khẩu có chữ viết hoa không?
                    if (c == c.toUpperCase()) {
                        flag2 = 1;
                        break;
                    }
                }
            }

            for (int i = 0; i < password.length(); i++) {
                //kiểm tra mật khẩu có chứa chữ số không?
                if (Character.isDigit(password.charAt(i))) {
                    flag3 = 1;
                    break;
                }
            }

            for (int i = 0; i < password.length(); i++) {
                char l = password.charAt(i);
                //Kiểm tra mật khẩu có chứa kí tự đặc biệt không?
                if (l >= 33 && l <= 46 || l == 64) {
                    flag4 = 1;
                    break;
                }
            }
        } else {
            return false;
        }

        //Kiểm tra mật khẩu có thỏa mãn tất cả điều kiện không?
        if (flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1) {
            return true;
        } else {
            return false;
        }
    }
}