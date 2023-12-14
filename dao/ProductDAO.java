package com.example.unlimited_store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.Product;

import java.util.ArrayList;

public class ProductDAO {
    AppDatabase appDatabase;

    public ProductDAO(Context context) {
        appDatabase = new AppDatabase(context);
    }

    public ArrayList<Product> getAllProduct(){
        ArrayList<Product> listProduct = new ArrayList<>();
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor c = database.rawQuery("select idProduct, image, name, price, description from PRODUCTS", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    listProduct.add(new Product(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4)
                    ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }
        }catch (Exception e){
            Log.e(">>>>>>>>>>>","getAllTea " + e);
        }finally {
            database.endTransaction();
        }
        return listProduct;
    }

    //Hàm lấy toàn bộ thức uống thuộc loại cafe
    public ArrayList<Product> getAllCoffee(){
        ArrayList<Product> listCoffee = new ArrayList<>();
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor c = database.rawQuery("select idProduct, image, name, price, description from PRODUCTS where type = 'Coffee'", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    listCoffee.add(new Product(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4)
                    ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }

        }catch (Exception e){
            Log.e(">>>>>>>>>>>","getAllCoffee " + e);
        }finally {
            database.endTransaction();
        }
        return listCoffee;
    }

    //Hàm lấy toàn bộ thức uống thuộc loại trà
    public ArrayList<Product> getAllTea(){
        ArrayList<Product> listTea = new ArrayList<>();
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor c = database.rawQuery("select idProduct, image, name, price, description from PRODUCTS where type = 'Tea'", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    listTea.add(new Product(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4)
                    ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }

        }catch (Exception e){
            Log.e(">>>>>>>>>>>","getAllTea " + e);
        }finally {
            database.endTransaction();
        }
        return listTea;
    }

    //Hàm lấy toàn bộ thức uống thuộc loại bia
    public ArrayList<Product> getAllBeer(){
        ArrayList<Product> listBeer = new ArrayList<>();
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor c = database.rawQuery("select idProduct, image, name, price, description from PRODUCTS where type = 'Beer'", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    listBeer.add(new Product(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4)
                    ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }

        }catch (Exception e){
            Log.e(">>>>>>>>>>>","getAllBeer " + e);
        }finally {
            database.endTransaction();
        }
        return listBeer;
    }

    //Hàm lấy toàn bộ thức uống thuộc loại nước ép
    public ArrayList<Product> getAllJuice(){
        ArrayList<Product> listJuice = new ArrayList<>();
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor c = database.rawQuery("select idProduct, image, name, price, description from PRODUCTS where type = 'Juice'", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    listJuice.add(new Product(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4)
                    ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }

        }catch (Exception e){
            Log.e(">>>>>>>>>>>","getAllJuice " + e);
        }finally {
            database.endTransaction();
        }
        return listJuice;
    }

    //Hàm thêm Product(coffee, tea, beer, juice)
    public boolean insertProduct(Product product){
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", product.getImage());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("description", product.getDescription());
        values.put("type", product.getType());
        long check = database.insert("PRODUCTS", null, values);
        return check != -1;
    }

    //Hàm Xóa Product (coffee, tea, beer, juice)
    public boolean deleteProduct(int id){
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        long check = database.delete("PRODUCTS", "idProduct=?", new String[]{String.valueOf(id)});
        return check != -1;
    }

    //Hàm cập nhật Product(coffee, tea, beer, juice)
    public boolean updateProduct(Product product){
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", product.getImage());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("description", product.getDescription());
        values.put("type", product.getType());
        long check = database.update("PRODUCTS", values, "idProduct=?", new String[]{String.valueOf(product.getIdProduct())});
        return check != -1;
    }

}
