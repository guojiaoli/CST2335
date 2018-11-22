package com.example.sarahli.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Intent intent = getIntent();
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("MessageID",intent.getLongExtra("MessageID", -1));
        bundle.putString("Message",intent.getStringExtra("Message"));
        bundle.getBoolean("phone",true);
        fragment.setArguments(bundle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.detail_frame,fragment);
        ft.commit();
    }
}
