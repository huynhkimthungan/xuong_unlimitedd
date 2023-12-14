package com.example.unlimited_store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.Feedback;

import java.util.ArrayList;

public class FeedbackDAO {
    AppDatabase appDatabase;

    public FeedbackDAO(Context context) {
        appDatabase = new AppDatabase(context);
    }

    //Hàm lấy toàn bộ Feedback
    public ArrayList<Feedback> getAllFeedback(){
        ArrayList<Feedback> listFB = new ArrayList<>();
        SQLiteDatabase database = appDatabase.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor c = database.rawQuery("select * from FEEDBACK", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    listFB.add(new Feedback(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2)
                    ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
                c.close();
            }

        }catch (Exception e){
            Log.e(">>>>>>>>>>>","getAllFeedback: " + e);
        }finally {
            database.endTransaction();
        }
        return listFB;
    }

    //Hàm thêm Feedback
    public boolean insertFeedback(Feedback feedback){
        SQLiteDatabase database = appDatabase.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("content", feedback.getContent());
        values.put("username", feedback.getUsername());
        database.setTransactionSuccessful();

        long check = database.insert("FEEDBACK", null, values);
        database.endTransaction();

        return check != -1;

    }
}
