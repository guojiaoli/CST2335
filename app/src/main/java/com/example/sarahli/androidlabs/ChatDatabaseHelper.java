package com.example.sarahli.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "messageDase";
    public static int VERSION_NUM = 2;
    public String TABLE_NAME = "message";
    public String ID = "_id";
    public String MESSAGE = "message";

    public ChatDatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MESSAGE + " text NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }




}
