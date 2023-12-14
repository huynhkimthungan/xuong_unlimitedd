package com.example.unlimited_store.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.Cart;
import com.example.unlimited_store.model.History;

import java.util.ArrayList;

public class HistoryDAO {
    AppDatabase appDatabase;

    public HistoryDAO(Context context) {
        appDatabase = new AppDatabase(context);
    }

    //Lấy danh sách
//    public ArrayList<History> getListHistory() {
//        ArrayList<History> list = new ArrayList<>();
//        SQLiteDatabase database = appDatabase.getReadableDatabase();
//        database.beginTransaction();
//        try {
//            Cursor c = database.rawQuery("select * from HISTORY", null);
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//                do {
//                    list.add(new History(
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
//            Log.e("Error", "getListHistory: " + e);
//        } finally {
//            database.endTransaction();
//            database.close();
//        }
//        return list;
//    }

    //Xóa
    public boolean deleteHistory(int idHistory) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        long check = database.delete("HISTORY", "idHistory = ?", new String[]{String.valueOf(idHistory)});
        return check != -1;
    }

    public boolean addHistory(History history) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("idProduct", history.getIdProduct() + "");
        values.put("quantity", history.getQuantity() + "");
        values.put("extraCream", history.getExtraCream() + "");
        values.put("state", history.getState() + "");
        values.put("total", history.getTotal() + "");
        values.put("topping", history.getTopping() + "");
        values.put("username", history.getUsername() + "");
        database.setTransactionSuccessful();

        long check = database.insert("HISTORY", null, values);
        database.endTransaction();
        return check != -1;
    }


    //Truy vấn khóa ngoại idProduct lấy name
    @SuppressLint("Range")
    public String getProductName(int idProduct) {
        String name = "";
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "SELECT PRODUCTS.name FROM PRODUCTS " + "INNER JOIN HISTORY ON PRODUCTS.idProduct = HISTORY.idProduct " + "WHERE PRODUCTS.idProduct = ?";
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
        String query = "SELECT PRODUCTS.image FROM PRODUCTS " + "INNER JOIN HISTORY ON PRODUCTS.idProduct = HISTORY.idProduct " + "WHERE PRODUCTS.idProduct = ?";
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
        String query = "SELECT PRODUCTS.description FROM PRODUCTS " + "INNER JOIN HISTORY ON PRODUCTS.idProduct = HISTORY.idProduct " + "WHERE PRODUCTS.idProduct = ?";
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
        String query = "SELECT PRODUCTS.type FROM PRODUCTS " + "INNER JOIN HISTORY ON PRODUCTS.idProduct = HISTORY.idProduct " + "WHERE PRODUCTS.idProduct = ?";
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
        String query = "SELECT PRODUCTS.price FROM PRODUCTS " + "INNER JOIN HISTORY ON PRODUCTS.idProduct = HISTORY.idProduct " + "WHERE PRODUCTS.idProduct = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idProduct)});
        if (cursor.moveToFirst()) {
            price = cursor.getInt(cursor.getColumnIndex("price"));
        }
        cursor.close();
        db.close();

        return price;
    }

    //Lấy danh sách history bằng username
    @SuppressLint("Range")
    public ArrayList<History> getHistoryByUsername(String username) {
        ArrayList<History> historyList = new ArrayList<>();

        SQLiteDatabase db = appDatabase.getReadableDatabase();

        // Specify column names in the query
        Cursor c = db.rawQuery("SELECT idHistory, quantity, state, total, topping, extraCream, username, idProduct FROM HISTORY WHERE username = ?", new String[]{username});

        if (c.moveToFirst()) {
            do {
                historyList.add(new History(
                        c.getInt(c.getColumnIndex("idHistory")),
                        c.getInt(c.getColumnIndex("quantity")),
                        c.getInt(c.getColumnIndex("state")),
                        c.getInt(c.getColumnIndex("total")),
                        c.getInt(c.getColumnIndex("topping")),
                        c.getInt(c.getColumnIndex("extraCream")),
                        c.getInt(c.getColumnIndex("idProduct")),
                        c.getString(c.getColumnIndex("username"))
                ));
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return historyList;
    }

}
