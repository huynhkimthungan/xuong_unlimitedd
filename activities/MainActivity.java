package com.example.unlimited_store.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.fragment.ChangePasswordFragment;
import com.example.unlimited_store.fragment.CustomerFragment;
import com.example.unlimited_store.fragment.FeedbackAdminFragment;
import com.example.unlimited_store.fragment.FeedbackUserFragment;
import com.example.unlimited_store.fragment.HistoryFragment;
import com.example.unlimited_store.fragment.HomeFragment;
import com.example.unlimited_store.fragment.ProductsManagementFragment;
import com.example.unlimited_store.fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<com.example.unlimited_store.model.Cart> listCart;
    ImageView ivCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Khai báo và ánh xạ
         */
        FrameLayout frameLayout = findViewById(R.id.frameLayout);


        /**
         * Khởi tạo userDAO
         */
        UserDAO userDAO = new UserDAO(MainActivity.this);


        /**
         * Set homeFragment mặc định lên màn hình MainActivity
         */
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, homeFragment).commit();


        /**
         * ImageView Cart
         */
        ivCart = findViewById(R.id.ivCart);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra trạng thái Login trong sharedPreferences
                if (!AppDatabase.getLoginState()) {
                    Toast.makeText(MainActivity.this, "Please, log in!", Toast.LENGTH_SHORT).show();
                    //Nếu username là của admin thì không xem được Cart
                } else if (userDAO.getRoleByUsername(AppDatabase.getUsername()) == 1) {
                    Toast.makeText(MainActivity.this, "You are not allowed to access!!!", Toast.LENGTH_SHORT).show();
                } else {
                    //Chuyển màn hình sang CartActivity
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
                }
            }
        });


        /**
         * Login
         */
        TextView tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra nếu login rồi thì thông báo
                if (AppDatabase.getLoginState() == true) {
                    Toast.makeText(MainActivity.this, "You're already logged in!", Toast.LENGTH_SHORT).show();
                } else {
                    //Chuyển đến màn hình LoginActivity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });


        /**
         * Toolbar
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Unlimited Store");


        /**
         * DrawerLayout
         */
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);

        //Bật chức năng hiển thị lên
        drawerToggle.setDrawerIndicatorEnabled(true);
        //Đồng bộ hóa
        drawerToggle.syncState();
        //Gắn drawerToggle vào drawerLayout
        drawerLayout.addDrawerListener(drawerToggle);


        /**
         * NavigationView
         */
        NavigationView navigationView = findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    //Chuyển đến HomeFragment khi click home
                    toolbar.setTitle("Home");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                    drawerLayout.close();
                } else if (item.getItemId() == R.id.history) {
                    //Nếu chưa đăng nhập hiện thông báo
                    if (!AppDatabase.getLoginState()) {
                        Toast.makeText(MainActivity.this, "Please log in!", Toast.LENGTH_SHORT).show();
                    } else if (userDAO.getRoleByUsername(AppDatabase.getUsername()) == 1) {
                        Toast.makeText(MainActivity.this, "You are not allowed to access!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Chuyển đến HistoryFragment khi click history
                        toolbar.setTitle("History");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, new HistoryFragment()).commit();
                        drawerLayout.close();
                    }
                } else if (item.getItemId() == R.id.profile) {
                    //Nếu chưa đăng nhập hiện thông báo
                    if (!AppDatabase.getLoginState()) {
                        Toast.makeText(MainActivity.this, "Please log in!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Chuyển đến ProfileFragment khi click profile
                        toolbar.setTitle("Profile");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
                        drawerLayout.close();
                    }
                } else if (item.getItemId() == R.id.changePassword) {
                    //Nếu chưa đăng nhập hiện thông báo
                    if (!AppDatabase.getLoginState()) {
                        Toast.makeText(MainActivity.this, "Please log in!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Chuyển đến ChangePasswordFragment khi click changePassword
                        toolbar.setTitle("Change password");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, new ChangePasswordFragment()).commit();
                        drawerLayout.close();
                    }
                } else if (item.getItemId() == R.id.giveFeedback) {
                    //Nếu chưa đăng nhập hiện thông báo
                    if (!AppDatabase.getLoginState()) {
                        Toast.makeText(MainActivity.this, "Please log in!", Toast.LENGTH_SHORT).show();
                        //Nếu là admin không được truy cập giveFeedback
                    } else if (userDAO.getRoleByUsername(AppDatabase.getUsername()) == 1) {
                        Toast.makeText(MainActivity.this, "You are not allowed to access!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Chuyển đến FeedbackUserFragment khi click giveFeedback
                        toolbar.setTitle("Give feedback");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, new FeedbackUserFragment()).commit();
                        drawerLayout.close();
                    }
                } else if (item.getItemId() == R.id.logout) {
                    //Nếu chưa đăng nhập hiện thông báo
                    if (!AppDatabase.getLoginState()) {
                        Toast.makeText(MainActivity.this, "Please log in!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Set lại trạng thái login, usename và thông báo
                        AppDatabase.setLoginState(false);
                        AppDatabase.saveUsername(null);
                        Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                //Nếu tài khoản là admin thì có quyền truy cập
                if (userDAO.getRoleByUsername(AppDatabase.getUsername()) == 1) {
                    if (item.getItemId() == R.id.product) {
                        if (userDAO.getRoleByUsername(AppDatabase.getUsername()) != 1) {
                            Toast.makeText(MainActivity.this, "You are not allowed to access!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            //Chuyển đến ProductsManagementFragment khi click product
                            toolbar.setTitle("Product management");
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frameLayout, new ProductsManagementFragment()).commit();
                            drawerLayout.close();
                        }
                    } else if (item.getItemId() == R.id.customer) {
                        if (userDAO.getRoleByUsername(AppDatabase.getUsername()) != 1) {
                            Toast.makeText(MainActivity.this, "You are not allowed to access!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            //Chuyển đến CustomerFragment khi click customer
                            toolbar.setTitle("Customer");
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frameLayout, new CustomerFragment()).commit();
                            drawerLayout.close();
                        }
                    } else if (item.getItemId() == R.id.feedback) {
                        if (userDAO.getRoleByUsername(AppDatabase.getUsername()) != 1) {
                            Toast.makeText(MainActivity.this, "You are not allowed to access!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            //Chuyển đến FeedbackAdminFragment khi click feedback
                            toolbar.setTitle("Feedback");
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frameLayout, new FeedbackAdminFragment()).commit();
                            drawerLayout.close();
                        }
                    }
                }
                return false;
            }
        });
    }
}