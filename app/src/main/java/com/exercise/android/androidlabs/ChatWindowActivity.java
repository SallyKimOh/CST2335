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
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
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

//public class ChatWindowActivity extends AppCompatActivity {
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
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        isTablet = findViewById(R.id.frame) != null;

        Log.i("tablet",""+isTablet);

        lv = (ListView)findViewById(R.id.listMsg);
        et = (EditText)findViewById(R.id.edit1);
        btn = (Button)findViewById(R.id.btnSend);
        flo = (FrameLayout)findViewById(R.id.frame) ;

 //       msg = new ArrayList<String>();
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



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {

                Bundle info = new Bundle();
                curID = adapter.getItemId(pos);
                Intent intent = new Intent(ChatWindowActivity.this,MessageDetailActivity.class);
                intent.putExtra("Message",lv.getItemIdAtPosition(pos));
                info.putString("msg",msg.get(pos));
                info.putString("id",String.valueOf(curID));
                info.putBoolean("isTablet",isTablet);
                info.putString("type",String.valueOf(frameType));
                info.putString("pos",String.valueOf(pos));
                info.putStringArrayList("msgList",msg);
                intent.putExtras(info);

                   if (isTablet) {
                    MessageFragment mf1 = new MessageFragment();
                    mf1.setArguments(info);
//                    getFragmentManager().beginTransaction().add(R.id.frame,mf1).commit();
                    getFragmentManager().beginTransaction().replace(R.id.frame,mf1).commit();

                } else {
                    startActivityForResult(intent, 2, info);

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
//    public void deleteContactTab(int resultCode,int pos) {
    public ArrayList<String> deleteContactTab(int resultCode,int pos) {
        Log.i("=======resultcode===>", ""+resultCode);
//            db.delete(cdHelp.TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(resultCode)});
//        db.delete(cdHelp.TABLE_NAME, KEY_ID + "=3", null);
//            Log.i("=======remove===>", "");
//            getList();

//        msg = getMsg();
        Log.i("=======size===>", ""+msg.size()+"/"+pos);
//        msg.remove(pos);
        msg.clear();
//        msg = new ArrayList<>();
//        adapter = new ChatAdapter(new ChatWindowActivity());
//        lv.removeAllViews();
//        lv.setAdapter(adapter);
//        msg.clear();



        System.out.println(msg);
//        ChatWindowActivity chA = new ChatWindowActivity();
//        chA.setMsg(msg);

//        ChatAdapter adapter1 = new ChatAdapter(new ChatWindowActivity());
//        lv.setAdapter(adapter);
//
//        adapter1.notifyDataSetChanged();

//          new ChatWindowActivity();
//
//        cdHelp = new ChatDatabaseHelper(getApplicationContext());
//        db = cdHelp.getWritableDatabase();
//        getList();


        return msg;
    }


    public void getList(){
        Log.i("=======size===>", ""+msg.size());


        String sql = "select "+ KEY_ID +", " + cdHelp.KEY_MESSAGE +"  from " + cdHelp.TABLE_NAME;
        cursor = db.rawQuery(sql,null);

        msg = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            msg.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ));
            cursor.moveToNext();
        }

    }


    public void getList1(){
        String sql = "select "+ KEY_ID +", " + cdHelp.KEY_MESSAGE +"  from " + cdHelp.TABLE_NAME;
        cursor = db.rawQuery(sql,null);

        msg.add("AAAA");
        msg.add("bb");
        msg.add("cc");
        msg.add("dd");

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
