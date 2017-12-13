package com.exercise.android.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class EmptyFragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_fragment);
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
