package com.example.sarahli.androidlabs;


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
        messageID.setText(Long.toString(messageBundle.getLong("ID")));
        messageText.setText(messageBundle.getString("message"));


        deleteMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPhone()){
                    Intent intent = new Intent(getActivity(),ChatWindow.class);
                    intent.putExtra("delete",messageBundle.getLong("ID"));
                    MessageDetails md = (MessageDetails)getActivity();
                    md.setResult(-1,intent);
                    md.finish();
                }else{
                    ChatWindow chatWindow = (ChatWindow) getActivity();
                    chatWindow.deleteMessage(messageBundle.getLong("ID"));
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                }
            }
        });

        return view;
    }

    public boolean isPhone(){
        if(messageBundle.getBoolean("phone"))
         return true;
        else
            return false;
    }



}
