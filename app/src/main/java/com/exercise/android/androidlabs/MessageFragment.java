package com.exercise.android.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kim00335 on 2017-12-09.
 */

public class MessageFragment extends Fragment {

    private Activity parent;
    private String txt_hear;
    private String txt_id;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle passedInfo = getArguments();

        String s = "none";
        if(passedInfo != null) {
            s= passedInfo.getString("key");
            txt_hear = passedInfo.getString("msg");
            txt_id = passedInfo.getString("id");
        }
        Log.i("passedInfo key", passedInfo.get("key").toString());
        Log.i("Passed key", s);

         parent = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_message_fragment, null);
//        TextView txtMsg = (TextView)v.findViewById(R.id.txt_hear);
//        txtMsg.setText(txt_hear);
        ((TextView) v.findViewById(R.id.txt_hear)).setText(txt_hear);
        ((TextView) v.findViewById(R.id.txt_id)).setText(txt_id);

        Button b = (Button)v.findViewById(R.id.btn_del);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                getActivity().setResult(Activity.RESULT_OK,parent.getIntent());
                String returnVal=parent.getIntent().getStringExtra("id");

                Log.i("returnVal:",returnVal);
                parent.setResult(Integer.parseInt(returnVal));
                parent.finish();

            }
        });


        return v;
    }



}
