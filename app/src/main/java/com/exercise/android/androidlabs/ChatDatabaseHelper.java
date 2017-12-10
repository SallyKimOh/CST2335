package com.exercise.android.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kim00335 on 2017-10-11.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "ChatDatabaseHelper";
    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 1;
    protected static final String TABLE_NAME = "CHAT";
    protected static final String KEY_ID = "keyId";
    protected static final String KEY_MESSAGE = "msg";

//    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
//            KEY_ID + " TEXT, " + KEY_MESSAGE + " TEXT);";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT);";

    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx,DATABASE_NAME,null,VERSION_NUM)    ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");

        db.execSQL(TABLE_CREATE);

        Log.i(ACTIVITY_NAME, "Calling version()" + db.getVersion());
        Log.i(ACTIVITY_NAME, "Calling onCreate()");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL(TABLE_DROP);
        onCreate(db);
        //drop the table
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + "newVersion="+ newVer);

    }

}
