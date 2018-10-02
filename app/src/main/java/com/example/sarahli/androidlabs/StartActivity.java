package com.example.sarahli.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        setContentView(R.layout.activity_start);

        Button b2 = (Button)findViewById(R.id.button2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {

                Intent nextScreen = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(nextScreen, 50);
            }
        });

        Button b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                Log.i(ACTIVITY_NAME,"User clicked Start Chat");
                Intent nextScreen = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(nextScreen);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode == 50)
        {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            if(responseCode == Activity.RESULT_OK){
                String messagePassed = data.getStringExtra("Response");
                Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG); //this is the ListActivity
                toast.show(); //display your message box
            }
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");}
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");}
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");}
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");}
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");}
}
