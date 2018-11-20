package com.example.sarahli.androidlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ChatDatabaseHelper";
    public static String DATABASE_NAME = "messageDase";
    public static int VERSION_NUM = 2;
    public String TABLE_NAME = "message";
    public String ID = "_id";
    public String MESSAGE = "message";
    public String[] ALL_COLUMNS = new String[] {ID, MESSAGE};

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

    public List<MessageResult> getAllMessage() {
        final SQLiteDatabase db = getWritableDatabase();
        final List<MessageResult> messageResults = new ArrayList<>();
        final Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null, null);

        Log.i(TAG, "Cursor's  column count =" + cursor.getColumnCount());

        if(cursor != null) {
            while (cursor.moveToNext()) {
                final long id = cursor.getLong(cursor.getColumnIndex(ID));
                final String msg = cursor.getString(cursor.getColumnIndex(MESSAGE));

                final MessageResult result = new MessageResult(id, msg);
                messageResults.add(result);

                Log.i(TAG, "SQL MESSAGE:" + msg);
            }

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i(TAG, "Column name is: " + cursor.getColumnName(i));
            }
        }

        cursor.close();
        return messageResults;
    }


}
