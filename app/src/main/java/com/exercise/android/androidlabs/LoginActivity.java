package com.exercise.android.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String Activity_Name = "LoginActivity";
    public static final String KEY_REF = "DefaultEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(Activity_Name, "In onCreate()");
//        getSharedPreferences("DefaultEmail",startActionMode());
        Button loginBtn = (Button) findViewById(R.id.btnLogin);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String emailString = preferences.getString("DefaultEmail", "email@domain.com");
//

//        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
 //       startActivity(intent);

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick (View v) {
                        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                        startActivity(intent);

                    }
                }
       );

        SharedPreferences prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        String emailString = prefs.getString(KEY_REF, "email@domain.com");
//        String emailString = prefs.getString(KEY_REF, "");

        EditText email = (EditText) findViewById(R.id.loginName);
        email.setText(emailString);


    }

    @Override
    public void onResume() {
        super.onResume();   // Always call the superclass method first
        // Get the Camera instance as the activity achieves full user focus
        Log.i(Activity_Name, "In onResume()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Activity_Name, "In onStart()");

    }

    @Override
    public void onPause() {
        super.onPause();    // Always call the superclass method first
        Log.i(Activity_Name, "In onPause()");

    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        Log.i(Activity_Name, "In onStop()");
        EditText editText = (EditText) findViewById(R.id.loginName);
        String text = editText.getText().toString();
        Log.i(Activity_Name, "test==>"+text);

        // 데이타를저장합니다.
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_REF, text);
        editor.commit();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(Activity_Name, "In onDestroy()");
    }



}
