package com.example.unlimited_store.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.Cart;

import java.util.ArrayList;
import java.util.Collection;

public class CartDAO {
    private final AppDatabase appDatabase;

    public CartDAO(Context context) {
        appDatabase = new AppDatabase(context);
    }

    //Lấy danh sách
//    public ArrayList<Cart> getListCart() {
//        ArrayList<Cart> list = new ArrayList<>();
//        SQLiteDatabase database = appDatabase.getReadableDatabase();
//        database.beginTransaction();
//        try {
//            Cursor c = database.rawQuery("select * from CART", null);
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//                do {
//                    list.add(new Cart(
//                            c.getInt(0),
//                            c.getInt(1),
//                            c.getInt(2),
//                            c.getInt(3),
//                            c.getInt(4),
//                            c.getInt(5),
//                            c.getInt(6),
//                            c.getString(7)
//                    ));
//                } while (c.moveToNext());
//                database.setTransactionSuccessful();
//                c.close();
//            }
//        } catch (Exception e) {
//            Log.e("Error", "getListCart: " + e);
//        } finally {
//            database.endTransaction();
//            database.close();
//        }
//        return list;
//    }

    public boolean deleteCart(int idCart) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        long check = database.delete("CART", "idCart = ?", new String[]{String.valueOf(idCart)});
        return check != -1;
    }

    public boolean deleteCartByUsername(String username) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        long check = database.delete("CART", "username=?", new String[]{username});

        database.close();

        return check != -1;
    }


    public boolean addCart(int idProduct, int quantity, String username) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("idProduct", idProduct);
        values.put("quantity", quantity + "");
        values.put("username", username + "");
        values.put("state", String.valueOf(2));
        database.setTransactionSuccessful();

        long check = database.insert("CART", null, values);
        database.endTransaction();

        return check != -1;
    }


    public boolean updateCart(Cart cart) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("quantity", cart.getQuantity() + "");
        values.put("state", cart.getState() + "");
        values.put("total", cart.getTotal() + "");
        values.put("topping", cart.getTopping() + "");
        values.put("extraCream", cart.getExtraCream() + "");
        values.put("idProduct", cart.getIdProduct() + "");
        database.setTransactionSuccessful();

        long check = database.update("CART", values, "idCart = ?", new String[]{cart.getIdCart() + ""});
        database.endTransaction();

        return check != -1;
    }

    //Truy vấn khóa ngoại idProduct lấy name
    @SuppressLint("Range")
    public String getProductName(int idProduct) {
        String name = "";
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "SELECT PRODUCTS.name FROM PRODUCTS " + "INNER JOIN CART ON PRODUCTS.idProduct = CART.idProduct " + "WHERE PRODUCTS.idProduct = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idProduct)});
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        db.close();

        return name;
    }

    //Truy vấn khóa ngoại idProduct lấy image
    @SuppressLint("Range")
    public String getImagePath(int idProduct) {
        String image = "";
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "SELECT PRODUCTS.image FROM PRODUCTS " + "INNER JOIN CART ON PRODUCTS.idProduct = CART.idProduct " + "WHERE PRODUCTS.idProduct = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idProduct)});
        if (cursor.moveToFirst()) {
            image = cursor.getString(cursor.getColumnIndex("image"));
        }
        cursor.close();
        db.close();

        return image;
    }


    //Truy vấn khóa ngoại idProduct lấy description
    @SuppressLint("Range")
    public String getDescription(int idProduct) {
        String description = "";
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "SELECT PRODUCTS.description FROM PRODUCTS " + "INNER JOIN CART ON PRODUCTS.idProduct = CART.idProduct " + "WHERE PRODUCTS.idProduct = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idProduct)});
        if (cursor.moveToFirst()) {
            description = cursor.getString(cursor.getColumnIndex("description"));
        }
        cursor.close();
        db.close();

        return description;
    }

    //Truy vấn khóa ngoại idProduct lấy type
    @SuppressLint("Range")
    public String getType(int idProduct) {
        String type = "";
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "SELECT PRODUCTS.type FROM PRODUCTS " + "INNER JOIN CART ON PRODUCTS.idProduct = CART.idProduct " + "WHERE PRODUCTS.idProduct = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idProduct)});
        if (cursor.moveToFirst()) {
            type = cursor.getString(cursor.getColumnIndex("type"));
        }
        cursor.close();
        db.close();

        return type;
    }

    //Truy vấn khóa ngoại idProduct lấy price
    @SuppressLint("Range")
    public int getPrice(int idProduct) {
        int price = -1;
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "SELECT PRODUCTS.price FROM PRODUCTS " + "INNER JOIN CART ON PRODUCTS.idProduct = CART.idProduct " + "WHERE PRODUCTS.idProduct = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idProduct)});
        if (cursor.moveToFirst()) {
            price = cursor.getInt(cursor.getColumnIndex("price"));
        }
        cursor.close();
        db.close();

        return price;
    }

    //Lấy danh sách cart bằng username
    public ArrayList<Cart> getCartByUsername(String username) {
        ArrayList<Cart> cartList = new ArrayList<>();

        SQLiteDatabase db = appDatabase.getReadableDatabase();

        Cursor c = db.rawQuery("select * from CART where username = ?", new String[]{username});

        if (c.moveToFirst()) {
            do {
                cartList.add(new Cart(
                        c.getInt(0),
                        c.getInt(1),
                        c.getInt(2),
                        c.getInt(3),
                        c.getInt(4),
                        c.getInt(5),
                        c.getInt(6),
                        c.getString(7)
                ));
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return cartList;
    }


}
