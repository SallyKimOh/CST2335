package com.exercise.android.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import java.util.ArrayList;

import static com.exercise.android.androidlabs.ChatDatabaseHelper.KEY_ID;

public class ChatWindowActivity extends Activity {
//public class ChatWindowActivity extends FragmentActivity {

    protected static final String Activity_Name = "ChatWindowActivity";
    ListView lv;
    EditText et;
    Button btn;
    FrameLayout flo;

    ArrayList<String> msg = new ArrayList<>();
    ChatAdapter adapter;
    SQLiteDatabase db;
    ChatDatabaseHelper cdHelp;
    Cursor cursor;
    long curID;
    FragmentTransaction ft;
    int frameType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        Log.i("ADSFADSF","ADSFADS");

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
        getList();

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
                        getList();
                        adapter.notifyDataSetChanged();
                       et.setText("");

                    }
                }
        );


        final MessageFragment mf = new MessageFragment();
        final Bundle info = new Bundle();

        info.putString("key","value");
        if (flo != null) {
//        if (findViewById(R.id.txt_hear) != null) {
            //            mf.setArguments(info);
////            FragmentTransaction ft =  getFragmentManager().beginTransaction();
//            ft =  getFragmentManager().beginTransaction();
//            ft.add(R.id.msg_bg, mf);
//            ft.addToBackStack("A string name");
////            ft.commit();
            frameType = 1;
        } else {
//            Intent phoneIntent = new Intent(ChatWindowActivity.this, MessageDetailActivity.class);
//            phoneIntent.putExtras(info);
//            startActivity(phoneIntent);

        }
        Log.i("IsTablet?" , String.valueOf(frameType));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                curID = adapter.getItemId(pos);
                Intent intent = new Intent(ChatWindowActivity.this,MessageDetailActivity.class);
//                intent.putExtra("Message",lv.getItemIdAtPosition(pos));
                info.putString("msg",msg.get(pos));
                info.putString("id",String.valueOf(curID));
                info.putString("type",String.valueOf(frameType));
                info.putString("pos",String.valueOf(pos));
                info.putStringArrayList("msgList",msg);
                intent.putExtras(info);
//                startActivity(intent);
                if (flo == null) {

//                    if (findViewById(R.id.txt_hear) == null) {
                    startActivityForResult(intent, 2, info);
                } else {

                    MessageFragment mesF = new MessageFragment();
                    mesF.setArguments(info);
                    Log.i("return 11111:",""+mesF.getReturnTransition());
//                    getFragmentManager().beginTransaction().replace(R.id.msg_bg,mesF).commit();
                    getFragmentManager().beginTransaction().add(R.id.msg_bg,mesF).commit();

                    Log.i("return mesf:",""+mesF.getArguments());

                }

            }
        });

//         ft.commit();

        lv.setAdapter(adapter);


    }
    public void  setMsg(ArrayList<String> message) {
        msg = message;
    }


    public ArrayList<String>  getMsg() {
        return msg;
    }


    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Log.i("resultCode:",""+resultCode);
        if(requestCode==2 && resultCode !=0)
        {
            Log.i("resultCode:",""+resultCode);
            deleteContact(resultCode);
        }
    }

    // Deleting single contact
    public void deleteContact(int resultCode) {

            String rowId = cursor.getString(cursor.getColumnIndex(KEY_ID));
            Log.i("=======rowId===>", rowId);

//        db.delete(cdHelp.TABLE_NAME, KEY_ID + "=?",  new String[]{rowId});
            db.delete(cdHelp.TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(resultCode)});
            Log.i("=======remove===>", "");

            msg.remove(cursor.getPosition());
            adapter.notifyDataSetChanged();
            et.setText("");

    }

    // Deleting single contact
    public void deleteContactTab(int resultCode,int pos) {
        Log.i("=======resultcode===>", ""+resultCode);
//            db.delete(cdHelp.TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(resultCode)});
//        db.delete(cdHelp.TABLE_NAME, KEY_ID + "=3", null);
//            Log.i("=======remove===>", "");
//            getList();

        msg = getMsg();
        Log.i("=======size===>", ""+msg.size());
        msg.remove(2);

//        ChatAdapter adapter1 = new ChatAdapter(this);
//        lv.setAdapter(adapter1);
//
//        adapter1.notifyDataSetChanged();

        new ChatWindowActivity();

//        cdHelp = new ChatDatabaseHelper(getApplicationContext());
//        db = cdHelp.getWritableDatabase();
//        getList();
    }


    public void getList(){
        Log.i("=======size===>", ""+msg.size());


        String sql = "select "+ KEY_ID +", " + cdHelp.KEY_MESSAGE +"  from " + cdHelp.TABLE_NAME;
        cursor = db.rawQuery(sql,null);

        msg = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
//            Log.i(Activity_Name,"SQL MESSAGE:"+ cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
//            Log.i(Activity_Name,"SQL key:"+ KEY_ID);
//            Log.i(Activity_Name,"SQL key:"+ cursor.getString( cursor.getColumnIndex( KEY_ID) ));

            msg.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ));
            cursor.moveToNext();
        }

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
//           id =  cursor.getInt(position);  //database ID

            Log.i("cursor:",cursor.getString( cursor.getColumnIndex( KEY_ID) ));
            id = Long.parseLong(cursor.getString( cursor.getColumnIndex( KEY_ID) ));

            return id;

        }

    }

}
