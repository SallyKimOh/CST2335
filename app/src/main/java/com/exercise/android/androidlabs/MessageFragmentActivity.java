package com.exercise.android.androidlabs;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessageFragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message_fragment);

        View view = (View) getLayoutInflater().inflate(R.layout.activity_message_fragment,null);
        RelativeLayout bg = (RelativeLayout) view.findViewById(R.id.msg_bg);

        TextView tv = (TextView) view.findViewById(R.id.txt_hear);
        tv.setGravity(Gravity.LEFT);

        TextView tvId = (TextView) view.findViewById(R.id.txt_id);
        tvId.setGravity(Gravity.LEFT);

        Button bxt = (Button) view.findViewById(R.id.btn_del);
        bxt.setGravity(Gravity.LEFT);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;

        tv.setLayoutParams(params);




    }
}
