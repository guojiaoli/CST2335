package com.example.sarahli.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";

    ArrayList<MessageResult> arrayList;
    ChatDatabaseHelper cdh;
    SQLiteDatabase db;
    Cursor cursor;
    ChatAdapter messageAdapter;
    ListView listView;
    boolean isTablet;
    FrameLayout frameLayout;
    EditText editText2;
    String input;
    MessageResult messageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        isTablet = findViewById(R.id.frameLayout) != null;

        cdh = new ChatDatabaseHelper(this);

        editText2 = (EditText) findViewById(R.id.editText2);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        listView = (ListView) findViewById(R.id.listView);
        messageAdapter = new ChatAdapter(this);

        db = cdh.getReadableDatabase();
        arrayList = (ArrayList<MessageResult>) cdh.getAllMessage();

        listView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = editText2.getText().toString();

                messageResult = new MessageResult(-1, input);
                arrayList.add(messageResult);
                messageAdapter.notifyDataSetChanged();
                /*
                SQLiteDatabase db = cdh.getWritableDatabase();
                */
                ContentValues contentValues = new ContentValues();
                contentValues.put(cdh.MESSAGE, input);
                db.insert(cdh.TABLE_NAME, "null", contentValues);

                editText2.setText("");

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String item = adapterView.getItemAtPosition(position).toString();
                Long messageId = adapterView.getItemIdAtPosition(position);
                //Use a Bundle to pass the message string, and the database id of the selected item to the fragment in the FragmentTransaction
                if(isTablet) {
                    MessageFragment messageFragment = new MessageFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Message", item);
                    bundle.putLong("MessageID", messageId);
                    bundle.putBoolean("isTablet", isTablet);

                    messageFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, messageFragment);
                    //Call transaction.addToBackStack(String name) if you want to undo this transaction with the back button.
                    fragmentTransaction.addToBackStack("A string");
                    fragmentTransaction.commit();

                    Log.i(ACTIVITY_NAME, "Run on Tablet");
                } else {
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("Message", item);
                    intent.putExtra("MessageID", messageId + "");
                    startActivityForResult(intent, 10);

                    Log.i(ACTIVITY_NAME, "Run on Phone");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 10) {
            long id = data.getLongExtra("id", -1);
            if (id != -1) deleteMessage(id);
        }
    }

    private class ChatAdapter extends ArrayAdapter<MessageResult> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public MessageResult getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return arrayList.get(position).getId();
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
            message.setText(getItem(position).getMsgText()); // get the string at position
            return result;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cdh.close();
    }

    public void deleteMessage(long id) {
//        db = cdh.getWritableDatabase();
        final int deleted = db.delete(cdh.TABLE_NAME, cdh.ID + " = ?", new String[]{String.valueOf(id)});
        arrayList.clear();
//        cursor = db.rawQuery("SELECT * FROM " + cdh.TABLE_NAME + ";", null);
//        cursor.moveToFirst();
        cdh.getAllMessage();
        arrayList.add(messageResult);
        messageAdapter.notifyDataSetChanged();
    }
}
