package com.exercise.android.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String Activity_Name = "StartActivity";
    Button btn ;
    int MESSAGE_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(Activity_Name, "In onCreate()");

        btn = (Button) findViewById(R.id.btn1) ;
        btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick (View v) {
                        Intent getResult = new Intent(
                                getApplicationContext(), ListItemActivity.class);
                        startActivityForResult(getResult, MESSAGE_REQUEST_CODE);

                    } 
                }
        );

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Log.i(Activity_Name, getString(R.string.LogMesStartChat));
                Intent intent = new Intent(StartActivity.this, ChatWindowActivity.class);
                startActivity(intent);
//                Intent getResult = new Intent(
//                        getApplicationContext(), ChatWindowActivity.class);
//                startActivityForResult(getResult, MESSAGE_REQUEST_CODE);

            }
        });

        Button btn_weather = (Button) findViewById(R.id.btn_weather);
        btn_weather.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Log.i(Activity_Name, getString(R.string.LogMesStartChat));
                Intent intent = new Intent(StartActivity.this, WeatherForecastActivity.class);
                startActivity(intent);

            }
        });

        Button btn_toolbar = (Button) findViewById(R.id.toolbarBtn);
        btn_toolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Log.i(Activity_Name, getString(R.string.LogMesStartToolbar));
                Intent intent = new Intent(StartActivity.this, TestToolbarActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode == MESSAGE_REQUEST_CODE) {
            Log.i(Activity_Name, getString(R.string.toast_alt2));
        }

//        String messagePassed = data.getStringExtra("data");

        if (responseCode == Activity.RESULT_OK) {
            Log.i(Activity_Name, getString(R.string.toast_alt2));
        }

        CharSequence text = getString(R.string.toast_alt1);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

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

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(Activity_Name, "In onDestroy()");
    }



}
