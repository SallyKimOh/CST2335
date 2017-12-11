package com.exercise.android.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.exercise.android.androidlabs.ChatDatabaseHelper.KEY_ID;

/**
 * Created by kim00335 on 2017-12-09.
 */

public class MessageFragment extends Fragment {

    private Activity parent;
    private String txt_hear;
    private String txt_id;
    private String frameType;
    private String pos;
    private ArrayList<String> list;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("fsafdsfa:","fadsfdasfadsf");

        Bundle passedInfo = getArguments();

//        String s = "none";
        if(passedInfo != null) {
  //          s= passedInfo.getString("key");
            txt_hear = passedInfo.getString("msg");
            txt_id = passedInfo.getString("id");
            frameType = passedInfo.getString("frameType");
            pos = passedInfo.getString("pos");
            list = passedInfo.getStringArrayList("msgList");
        }
 //       Log.i("passedInfo key", passedInfo.get("key").toString());
//        Log.i("Passed key", s);

         parent = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("frame:","=================");

        View v = inflater.inflate(R.layout.activity_message_fragment, container, false);
//        TextView txtMsg = (TextView)v.findViewById(R.id.txt_hear);
//        txtMsg.setText(txt_hear);
        ((TextView) v.findViewById(R.id.txt_hear)).setText(txt_hear);
        ((TextView) v.findViewById(R.id.txt_id)).setText(txt_id);

        Button b = (Button)v.findViewById(R.id.btn_del);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (frameType == "0") {
                    parent.getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                    getActivity().setResult(Activity.RESULT_OK, parent.getIntent());
                    String returnVal = parent.getIntent().getStringExtra("id");

                    Log.i("returnVal:", returnVal);
                    parent.setResult(Integer.parseInt(returnVal));
                    parent.finish();
                } else {
                    parent.getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                    Log.i("txt_id:", txt_id);
                    parent.setResult(Integer.parseInt(txt_id));
                    getActivity().setResult(Activity.RESULT_OK, parent.getIntent());

                    ChatWindowActivity chA = new ChatWindowActivity();
                    chA.setMsg(list);

                    SQLiteDatabase db;
                    ChatDatabaseHelper cdHelp;
                    cdHelp = new ChatDatabaseHelper(parent.getApplicationContext());
                    db = cdHelp.getWritableDatabase();
                    db.delete(cdHelp.TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(txt_id)});
                    Log.i("=======remove===>", "");
                    chA.deleteContactTab(Integer.parseInt(txt_id),Integer.parseInt(pos)); ;


                }

            }
        });

       return v;
    }



}
