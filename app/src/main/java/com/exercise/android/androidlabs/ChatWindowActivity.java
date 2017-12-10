package com.exercise.android.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.exercise.android.androidlabs.ChatDatabaseHelper.KEY_ID;

//public class ChatWindowActivity extends AppCompatActivity {
public class ChatWindowActivity extends Activity {
//public class ChatWindowActivity extends FragmentActivity {

    protected static final String Activity_Name = "ChatWindowActivity";
    ListView lv;
    EditText et;
    Button btn;
    FrameLayout flo;

    ArrayList<String> msg;
//    ArrayList<String> dbmsg;
    ChatAdapter adapter;
    SQLiteDatabase db;
    ChatDatabaseHelper cdHelp;
    Cursor cursor;
    long curID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        lv = (ListView)findViewById(R.id.listMsg);
        et = (EditText)findViewById(R.id.edit1);
        btn = (Button)findViewById(R.id.btnSend);
        flo = (FrameLayout)findViewById(R.id.msg_bg) ;


//        ExampleFragment fragment = (ExampleFragment) getFragmentManager().findFragmentById(R.id.example_fragment);

        msg = new ArrayList<String>();
        adapter = new ChatAdapter(this);
        lv.setAdapter(adapter);


        cdHelp = new ChatDatabaseHelper(getApplicationContext());
        db = cdHelp.getWritableDatabase();

        String sql = "select "+ KEY_ID +", " + cdHelp.KEY_MESSAGE +"  from " + cdHelp.TABLE_NAME;
//        Cursor cursor = db.rawQuery(sql,null);
        cursor = db.rawQuery(sql,null);


        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i(Activity_Name,"SQL MESSAGE:"+ cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            Log.i(Activity_Name,"SQL key:"+ KEY_ID);
            Log.i(Activity_Name,"SQL key:"+ cursor.getString( cursor.getColumnIndex( KEY_ID) ));

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


        String s = "Phone";
        final MessageFragment mf = new MessageFragment();
        final Bundle info = new Bundle();

        info.putString("key","value");

        if (flo != null) {
            mf.setArguments(info);
            FragmentTransaction ft =  getFragmentManager().beginTransaction();
            ft.add(R.id.msg_bg, mf);
            ft.addToBackStack("A string name");
            ft.commit();
            s = "Tablet";
        } else {
//            Intent phoneIntent = new Intent(ChatWindowActivity.this, MessageDetailActivity.class);
//            phoneIntent.putExtras(info);
//            startActivity(phoneIntent);

        }
        Log.i("IsTablet?" , s);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                curID = adapter.getItemId(pos);
//                Toast.makeText(ChatWindowActivity.this,msg.get(pos),Toast.LENGTH_SHORT).show();
//                Toast.makeText(ChatWindowActivity.this,String.valueOf(id),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChatWindowActivity.this,MessageDetailActivity.class);
//                intent.putExtra("Message",lv.getItemIdAtPosition(pos));
                info.putString("msg",msg.get(pos));
                info.putString("id",String.valueOf(curID));
                intent.putExtras(info);
//                startActivity(intent);
                startActivityForResult(intent, 2, info);

            }
        });


        lv.setAdapter(adapter);


    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            Log.i("resultCode:",""+resultCode);

//            db.delete(cdHelp.TABLE_NAME, cdHelp.KEY_ID + "=" + resultCode, null);
//            db.execSQL("DELETE FROM " + cdHelp.TABLE_NAME + " WHERE " + cdHelp.KEY_ID + "= '" + resultCode + "'");
//
//            //Close the database
//            db.close();

            deleteContact(resultCode);


        }
    }

    // Deleting single contact
    public void deleteContact(int resultCode) {
        int abc = db.delete(cdHelp.TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(""+resultCode) });
//        int abc = db.delete(cdHelp.TABLE_NAME, cdHelp.KEY_ID + " = 3",
//                null);
        Log.i("remove===>",""+abc);
//        adapter.notifyDataSetChanged();
//        et.setText("");

 //       db.close();

//        int result = db.delete(tableName, "name=?", new String[] {name});
//        Log.d(tag, result + "개 row delete 성공");

        String rowId = cursor.getString(cursor.getColumnIndex(KEY_ID));
        Log.i("=======rowId===>",rowId);

        db.delete(cdHelp.TABLE_NAME, KEY_ID + "=?",  new String[]{rowId});
        Log.i("=======remove===>","");

//
//        boolean result = cdHelp..deleteColumn(position + 1);
//        DLog.e(TAG, "result = " + result);
//
//        if(result){
//            mInfoArray.remove(position);
//            mAdapter.setArrayList(mInfoArray);
//            mAdapter.notifyDataSetChanged();
//        }else {
//            Toast.makeText(getApplicationContext(), "INDEX를 확인해 주세요.",
//                    Toast.LENGTH_LONG).show();
//        }








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

        long id = 0;

        public long getItemId(int position) {

            Log.i("cursor:",""+cursor.getPosition());
            cursor.moveToPosition(position);
            cursor.getPosition();

            Log.i("position:",""+position);
//            id =  cursor.getInt(position);  //database ID
            id = cursor.getPosition();
            return id;

        }

    }

}
