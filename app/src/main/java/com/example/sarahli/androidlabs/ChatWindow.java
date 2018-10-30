package com.example.sarahli.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    ArrayList<String> arrayList;
    ChatDatabaseHelper cdh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        cdh = new ChatDatabaseHelper(this);

        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        Button b4 = (Button) findViewById(R.id.sendButton);
        ListView listview = (ListView) findViewById(R.id.listView);
        final ChatAdapter messageAdapter = new ChatAdapter(this);

        SQLiteDatabase db = cdh.getReadableDatabase();
        arrayList = new ArrayList<>();

        Cursor cursor = db.query(cdh.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(cdh.MESSAGE)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(cdh.MESSAGE)));
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Column name is: " + cursor.getColumnName(i));
        }
        cursor.close();

        listview.setAdapter(messageAdapter);

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText2.getText().toString();
                arrayList.add(input);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
                editText2.setText("");
                SQLiteDatabase db = cdh.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(cdh.MESSAGE, input);
                db.insert(cdh.TABLE_NAME, null, cv);

            }
        });


    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public String getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);


            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cdh.close();
    }
}
