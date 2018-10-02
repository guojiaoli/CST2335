package com.example.sarahli.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        setContentView(R.layout.activity_login);

        final EditText et = (EditText)findViewById(R.id.editText);

       final  SharedPreferences prefs = getSharedPreferences("MyNewSaveEmailAddress", Context.MODE_PRIVATE);
        String userString = prefs.getString("UserInput","email@domain.com");

         et.setText(userString);

    Button b1 = (Button)findViewById(R.id.button);

    b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String input = et.getText().toString();

            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("UserInput", input);

            edit.putBoolean("Hello", false);
            edit.commit();//write to disk
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
        }
    });






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
