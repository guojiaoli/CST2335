package com.example.sarahli.androidlabs;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    protected static final String ACTIVITY_NAME = "MessageFragment";

    private View view;
    private Bundle messageBundle;
    TextView messageText;
    TextView messageID;
    Button deleteMessageButton;

    private long passedId;
    private String passedMsg;
    private boolean passedTablet;

    public MessageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for the fragment from step 4
        messageBundle = getArguments();
        view = inflater.inflate(R.layout.activity_fragment_details, container, false);

        messageText = (TextView)view.findViewById(R.id.fragmentMessage);
        messageID =(TextView)view.findViewById(R.id.fragmentID);

        deleteMessageButton =(Button)view.findViewById(R.id.fragmentDeleteButton);

        passedId = messageBundle.getLong("MessageID");
        messageID.setText(Long.toString(passedId));

        passedMsg = messageBundle.getString("Message");
        messageText.setText(passedMsg);

        passedTablet = messageBundle.getBoolean("isTablet");


        deleteMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passedTablet){
                    ChatWindow chatWindow = (ChatWindow) getActivity();
                    chatWindow.deleteMessage(passedId);
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("id", passedId);
                    MessageDetails md = (MessageDetails)getActivity();
                    md.setResult(Activity.RESULT_OK, intent);
                    md.finish();
                }
            }
        });

        return view;
    }
}
