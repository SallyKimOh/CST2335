package com.exercise.android.androidlabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestToolbarActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edTxt;
    String snackbarMsg = "You selected item 1";
    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.diaTitle);
//
//        builder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                //User clicked OK button
//                finish();
//            }
//        });
//        builder.setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                //User cancelled the dialog
//                dialog.cancel();
////                onOptionsItemSelected(R.id.action_two);
//            }
//        });
//
//        //Create the Alert Dialog
//        AlertDialog dialog =builder.create();
//        dialog.show();
//





    }

    public boolean onCreateOptionsMenu(Menu m) {

        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        switch(id) {
            case R.id.action_one :
                Log.d("Toolbar","One selected");
                Snackbar.make(toolbar, getMsg(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Log.d("Toolbar","Two selected");
                //start an action
                Toast.makeText(getApplicationContext(),R.string.itemSelect1,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.diaTitle);

                builder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //User cancelled the dialog
                        dialog.cancel();
//                onOptionsItemSelected(R.id.action_two);
                        Toast.makeText(getApplicationContext(),R.string.itemSelect1,Toast.LENGTH_SHORT).show();
                    }
                });

                //Create the Alert Dialog
                AlertDialog dialog =builder.create();
                dialog.show();



                break;
            case R.id.action_three:
                Log.d("Toolbar","Three selected");
                dialog1 = new Dialog(TestToolbarActivity.this);
                dialog1.setContentView(R.layout.dialog_signin);
                dialog1.setTitle("New Message");
//                dialog1.setCancelable(true);

                edTxt = (EditText)dialog1.findViewById(R.id.newMsg);

                Button okBtn = (Button) dialog1.findViewById(R.id.okay);
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbarMsg = edTxt.getText().toString();
                        setMsg(snackbarMsg);
                        Snackbar.make(toolbar, getMsg(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
 //                       finish();
                        dialog1.dismiss();
                    }

                });
                dialog1.setCancelable(false);
                dialog1.show();
                break;
            default:
                Log.d("Toolbar","anything selected");

        }

        return true;
    }

    public void setMsg(String sMsg) {
        snackbarMsg = sMsg;
    }

    public String getMsg() {
        return snackbarMsg;
    }

}
