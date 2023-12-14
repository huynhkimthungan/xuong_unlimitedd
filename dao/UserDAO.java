package com.example.unlimited_store.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.User;

import java.util.ArrayList;

public class UserDAO {
    AppDatabase appDatabase;

    public UserDAO(Context context) {
        appDatabase = new AppDatabase(context);
    }

    //Kiểm tra username truyền vào đã tồn tại chưa
    public boolean checkExistingUsername(String username) {
        SQLiteDatabase database = appDatabase.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from USERS where username = ?",
                new String[]{username}, null);
        if (cursor.getCount() == 1) {
            return true;
        }

        return false;
    }

    //Ghi thông tin tài khoản vào csdl
    public boolean register(User user) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("username", user.getUsername()+"");
        values.put("password", user.getPassword()+"");
        values.put("email", user.getEmail()+"");
        values.put("address", user.getAddress()+"");
        values.put("phoneNumber", user.getPhoneNumber()+"");
        values.put("role", String.valueOf(0));
        database.setTransactionSuccessful();

        long check = database.insert("USERS", null, values);
        database.endTransaction();

        return check != -1;
    }

    //Cập nhập profile
    public boolean updateProfile(User user) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("email", user.getEmail() + "");
        values.put("phoneNumber", user.getPhoneNumber() + "");
        values.put("address", user.getAddress() + "");
        database.setTransactionSuccessful();

        long check = database.update("USERS", values, "username = ?", new String[]{user.getUsername()});
        database.endTransaction();

        return check != -1;
    }

    //Cập nhập mật khẩu
    public boolean updatePassword(String password, String username, int role) {
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("password", password);
        values.put("role", role + "");
        database.setTransactionSuccessful();

        long check = database.update("USERS", values, "username = ?", new String[]{username});
        database.endTransaction();

        return check != -1;
    }

    //Đăng nhập
    public boolean login(String username, String password) {
        SQLiteDatabase database = appDatabase.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from USERS where username = ? " +
                "and password = ?", new String[]{username, password});
        if (cursor.getCount() == 1) {
            return true;
        } else {
            return false;
        }
    }

    //Lấy role thông qua username
    @SuppressLint("Range")
    public int getRoleByUsername(String username) {
        int role = -1;  // Giá trị mặc định nếu không tìm thấy

        SQLiteDatabase database = appDatabase.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Câu truy vấn SQL
            String query = "SELECT role FROM USERS WHERE username = ?";

            // Thực hiện truy vấn
            cursor = database.rawQuery(query, new String[]{username});

            // Di chuyển con trỏ đến dòng đầu tiên (nếu có)
            if (cursor.moveToFirst()) {
                // Lấy giá trị role từ cột "role"
                role = cursor.getInt(cursor.getColumnIndex("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng cursor để tránh rò rỉ bộ nhớ
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return role;
    }

    //Kiểm tra username, password đầu vào có khớp trong database không?
    public boolean isMatched(String username, String password) {
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Câu truy vấn SQL
            String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";

            // Thực hiện truy vấn
            cursor = database.rawQuery(query, new String[]{username, password});

            // Kiểm tra xem có dữ liệu trả về không
            return cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng cursor để tránh rò rỉ bộ nhớ
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return false;
    }

    //Lấy địa chỉ thông qua username
    @SuppressLint("Range")
    public String getAdressByUsername(String username) {
        String address = "";

        SQLiteDatabase db = appDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT address FROM USERS WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            address = cursor.getString(cursor.getColumnIndex("address"));
        }
        cursor.close();
        db.close();
        return address;
    }

    //Lấy số điện thoại thông qua username
    @SuppressLint("Range")
    public String getPhoneNumberByUsername(String username) {
        String phoneNumber = "";

        SQLiteDatabase db = appDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT phoneNumber FROM USERS WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
        }
        cursor.close();
        db.close();
        return phoneNumber;
    }
    @SuppressLint("Range")
    //Lấy số email thông qua username
    public String getEmailByUsername(String username) {
        String email = "";

        SQLiteDatabase db = appDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email FROM USERS WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("email"));
        }
        cursor.close();
        db.close();
        return email;
    }

    //Lấy danh sách tất cả các khách hàng
    public ArrayList<User> getAllCustomer() {
        ArrayList<User> listCustomer = new ArrayList<>();
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor c = database.rawQuery("select username, phoneNumber, email, address from USERS where role = 0", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    listCustomer.add(new User(
                            c.getString(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3)
                    ));
                } while (c.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e(">>>>>>>>>>>", "getAllCoffee " + e);
        } finally {
            database.endTransaction();
        }
        return listCustomer;
    }


}
