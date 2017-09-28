package com.exercise.android.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemActivity extends Activity {

    protected static final String Activity_Name = "ListItemActivity";
    ImageButton imgBtn;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static int LENGTH_SHORT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        imgBtn = (ImageButton) findViewById(R.id.image_button);
        imgBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick (View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                });

        onSwitch();
        onCheckBox();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            Log.i(Activity_Name, getString(R.string.toast_alt2));
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.i(Activity_Name, "=============Image");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgBtn.setImageBitmap(imageBitmap);
        }


    }

    protected  void setToast(String result,int duration){
        Log.i(Activity_Name, "Switch is Off");
        CharSequence text = result;
//        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();


    }
    protected void onSwitch() {

        Switch sw = (Switch) findViewById(R.id.switch_button);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    Log.i(Activity_Name, getString(R.string.sw_on));
                    setToast(getString(R.string.sw_on),0);
                } else {
                    setToast(getString(R.string.sw_off),1);
                }
            }
        });

    }

    protected void onCheckBox() {

        CheckBox cb = (CheckBox) findViewById(R.id.check_button);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                setDialog();
            }
            public void onClicked () {

            }
        });

    }

    protected void setDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemActivity.this);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response",R.string.dialog_yes);
                        setResult(Activity.RESULT_OK,resultIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response",R.string.dialog_no);
                        setResult(Activity.RESULT_CANCELED,resultIntent);
                    }
                })
                .show();

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
