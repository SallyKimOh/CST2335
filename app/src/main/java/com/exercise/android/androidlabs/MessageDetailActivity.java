package com.exercise.android.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MessageDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Log.i("MessageDetailActive","");
        Bundle info = getIntent().getExtras();

        info.putString("Key", "From phone");
        //start Transaction to insert fragment in screen:
        FragmentTransaction ft =  getFragmentManager().beginTransaction();
        MessageFragment df = new MessageFragment();
        df.setArguments(info);
        ft.add(R.id.frame, df );
        ft.commit();


    }
}
