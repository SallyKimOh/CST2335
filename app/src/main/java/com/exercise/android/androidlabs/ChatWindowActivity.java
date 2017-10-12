package com.exercise.android.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//public class ChatWindowActivity extends AppCompatActivity {
public class ChatWindowActivity extends Activity {

    protected static final String Activity_Name = "ChatWindowActivity";
    ListView lv;
    EditText et;
    Button btn;

    ArrayList<String> msg;
//    ArrayList<String> dbmsg;
    ChatAdapter adapter;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        lv = (ListView)findViewById(R.id.listMsg);
        et = (EditText)findViewById(R.id.edit1);
        btn = (Button)findViewById(R.id.btnSend);

        msg = new ArrayList<String>();
        adapter = new ChatAdapter(this);
        lv.setAdapter(adapter);

 //       String TABLE_DROP = "DROP TABLE IF EXISTS" + ChatDatabaseHelper.TABLE_NAME;

 //       db.execSQL(TABLE_DROP);

        ChatDatabaseHelper cdHelp = new ChatDatabaseHelper(getApplicationContext());
        db = cdHelp.getWritableDatabase();

        String sql = "select "+ cdHelp.KEY_ID +", " + cdHelp.KEY_MESSAGE +"  from " + cdHelp.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);


        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i(Activity_Name,"SQL MESSAGE:"+ cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            msg.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ));
            cursor.moveToNext();
        }

//        if (cursor.moveToFirst()) {
//            do {
//                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
//                msg.add(message);
//                Log.i(Activity_Name, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
//                cursor.moveToNext();
//            } while (!cursor.isAfterLast());
//        }



        Log.i(Activity_Name,"Cursor's column count=" + cursor.getColumnCount());

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(Activity_Name, cursor.getColumnName(i));
        }


        final ContentValues content = new ContentValues();

        btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick (View v) {
                        String editMsg = et.getText().toString();
                        msg.add(editMsg);
                        content.put("msg",editMsg);
                        db.insert(ChatDatabaseHelper.TABLE_NAME, null, content);
                        adapter.notifyDataSetChanged();
                       et.setText("");
                    }
                }
        );


    }


    public void onDestroy(){
        Log.i(Activity_Name, "In onDestroy()");
        super.onDestroy();
        db.close();

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        Context mContext;
        int layoutResourceId;
        ArrayList<String> data = null;
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

//        public ChatAdapter(Context ctx, int layoutResourceId, ArrayList<String> data) {
//            super(ctx, layoutResourceId, data);
//            this.layoutResourceId = layoutResourceId;
//            this.mContext = ctx;
//            this.data = data;
//        }

        public int getCount() {
            return msg.size();
        }

        public String getItem(int position) {
            return msg.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindowActivity.this.getLayoutInflater();

            View result = null;
            if (position%2==0)
                result = inflater.inflate(R.layout.chat_row_incoming,null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing,null);

            TextView message = (TextView)result.findViewById(R.id.messageText);
            message.setText(getItem(position));

            return result;
        }

    }

}
