package com.example.unlimited_store.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.adapter.CartAdapter;
import com.example.unlimited_store.dao.CartDAO;
import com.example.unlimited_store.dao.HistoryDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.History;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static ArrayList<com.example.unlimited_store.model.Cart> listCart;
    public static CartDAO cartDAO;
    public static TextView tvTotal;
    HistoryDAO historyDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        /**
         * Hiển thị Recyclerview
         */
        RecyclerView rcvList = findViewById(R.id.rcvList);

        cartDAO = new CartDAO(CartActivity.this);
        listCart = cartDAO.getCartByUsername(AppDatabase.getUsername());
        rcvList.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, listCart, cartDAO);
        rcvList.setAdapter(cartAdapter);


        /**
         * Nút order và tvTotal
         */
        Button btnOrder = findViewById(R.id.btnOrder);
        tvTotal = findViewById(R.id.ivTotal);
        updateTotal();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCart.size() == 0) {
                    Toast.makeText(CartActivity.this, "There are no products in the cart!", Toast.LENGTH_SHORT).show();
                } else {
                    //Thêm dữ liệu từ bảng cart theo username vào bảng history
                    historyDAO = new HistoryDAO(CartActivity.this);
                    for (int i = 0; i < listCart.size(); i++) {
                        int quantity = listCart.get(i).getQuantity();
                        int state = listCart.get(i).getState();
                        int topping = listCart.get(i).getTopping();
                        int extraCream = listCart.get(i).getExtraCream();
                        int idProduct = listCart.get(i).getIdProduct();
                        int price = cartDAO.getPrice(idProduct);
                        int total = listCart.get(i).getTotal(price, topping, extraCream);
                        String username = listCart.get(i).getUsername();

                        History history = new History(quantity, state, total, topping, extraCream, idProduct, username);
                        historyDAO.addHistory(history);
                    }
                    //Xóa dữ liệu cart trong database theo username và set lại lên recyclerview
                    cartDAO.deleteCartByUsername(AppDatabase.getUsername().trim());
                    listCart.clear();
                    listCart.addAll(cartDAO.getCartByUsername(AppDatabase.getUsername()));
                    cartAdapter.notifyDataSetChanged();
                    updateTotal();
                    startActivity(new Intent(CartActivity.this, SuccessfulOrder.class));
                }
            }
        });

        /**
         * Nút Back
         */
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    /**
     * Phương thức cập nhập tổng tiền cho tvTotal
     */
    public static void updateTotal() {
        int total = 0;
        for (int i = 0; i < listCart.size(); i++) {
            int idProdcut = cartDAO.getPrice(listCart.get(i).getIdProduct());
            int topping = listCart.get(i).getTopping();
            int extraCream = listCart.get(i).getExtraCream();
            total += listCart.get(i).getTotal(idProdcut, topping, extraCream);
        }
        String totalText = CartAdapter.moneyText(total);
        tvTotal.setText(totalText + "");
    }
}