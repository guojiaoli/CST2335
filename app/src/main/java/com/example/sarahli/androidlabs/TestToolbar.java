package com.example.sarahli.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import android.app.Activity;



public class TestToolbar extends AppCompatActivity {

    Toolbar lab8_toolbar;
    Button toolbarButton;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder1;
    String currentMessage = "You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        lab8_toolbar = (Toolbar)findViewById(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar) ;

        toolbarButton = (Button)findViewById(R.id.toolbarButton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {

                Snackbar.make(findViewById(R.id.toolbarButton), "Message to show", Snackbar.LENGTH_SHORT).show();
            }
        });


    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        builder = new AlertDialog.Builder(this);

        switch(id){
            case R.id.choice_one:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(findViewById(R.id.choice_one), currentMessage, Snackbar.LENGTH_SHORT).show();

                break;
            case R.id.choice_two:
                Log.d("Toolbar", "Option 2 selected");
                builder.setTitle(R.string.toolbar_choice_two_title);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);

                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.choice_three:
                Log.d("Toolbar", "Option 3 selected");
                builder1 = new AlertDialog.Builder(this);

                final View view=this.getLayoutInflater().inflate(R.layout.activity_custom_dialog,null);

                builder1.setView(view);

                builder1.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        EditText message = (EditText)view.findViewById(R.id.newMessage);
                        currentMessage = message.getText().toString();
                    }
                });

                builder1.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog1 = builder1.create();
                dialog1.show();

                break;
            case R.id.about:
                Log.d("Toolbar", "About selected");
                Toast.makeText(this, "Version 1.0, by Guojiao(Sarah) Li", Toast.LENGTH_SHORT).show();

                break;

        }
        return true;
    }
}
