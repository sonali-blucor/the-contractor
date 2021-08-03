package com.blucor.tcthecontractor.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    private final Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_the_contractor";
    public static final String TABLE_LOGIN_ACCOUNT = "login_account";

    public static final String COL_ID = "id";

    public static final String COL_USER_ACCOUNT_ID = "user_id";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_USER_MOBILE_NUMBER = "mobile_number";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_DEVICE_ID = "device_id";

    public SQLiteDatabase db;

    private static DbHandler mDbHandler = null;

    public static DbHandler getInstance(Context context){
        if (mDbHandler == null){
            mDbHandler = new DbHandler(context);
        }
        return mDbHandler;
    }
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
             String CREATE_TABLE_ACCOUNT_LOGIN = "CREATE TABLE " + TABLE_LOGIN_ACCOUNT + " ("
                     + COL_ID + " INTEGER PRIMARY KEY,"
                     + COL_USER_ACCOUNT_ID + " TEXT,"
                     + COL_FIRST_NAME + " TEXT,"
                     + COL_LAST_NAME + " TEXT,"
                     + COL_USER_MOBILE_NUMBER + " TEXT,"
                     + COL_USER_EMAIL + " TEXT,"
                     + COL_USER_PASSWORD + " TEXT )";
        db.execSQL(CREATE_TABLE_ACCOUNT_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_ACCOUNT);
    }

    public SQLiteDatabase getReadableDb() {
        this.db = this.getReadableDatabase();
        return db;
    }

    public SQLiteDatabase getWritableDb() {
        this.db = this.getWritableDatabase();
        return db;
    }

    public void closeDb() {
        db.close();
    }

    public int insert(String TBL_NAME, ContentValues values) {
        return (int) db.insert(TBL_NAME, null, values);
    }

    public int empty(String TBL_NAME) {
        return db.delete(TBL_NAME, null, null);
    }

    public int delete(String TBL_NAME, int _id) {
        return db.delete(TBL_NAME, COL_ID + "=" + _id, null);
    }

    public int update(String TBL_NAME, ContentValues values) {
        return db.update(TBL_NAME, values, null, null);
    }

    public int update(String TBL_NAME, ContentValues values, int _id) {
        return db.update(TBL_NAME, values, COL_ID + "=" + _id, null);
    }

    public int updateAccess(ContentValues values, String _id) {
        return db.update(TABLE_LOGIN_ACCOUNT, values, COL_USER_ACCOUNT_ID + "='" + _id + "'", null);
    }


    public boolean hasLogin() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGIN_ACCOUNT, null);
        return cursor.getCount() > 0;
    }


   /* public LAccount getLoggedAccount() {
        LAccount teamLM = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGIN_ACCOUNT, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                teamLM = new LAccount();
                teamLM.setId(cursor.getString(cursor.getColumnIndex(COL_USER_ACCOUNT_ID)));
                teamLM.setRegActivityId(cursor.getString(cursor.getColumnIndex(COL_REG_ACTIVITY_ID)));
                teamLM.setName(cursor.getString(cursor.getColumnIndex(COL_USER_NAME)));
                teamLM.setMobileNo(cursor.getString(cursor.getColumnIndex(COL_USER_MOBILE_NUMBER)));
                teamLM.setMobileNo(cursor.getString(cursor.getColumnIndex(COL_USER_DEVICE_ID)));
                teamLM.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)));
                teamLM.setRegMaxCount(cursor.getInt(cursor.getColumnIndex(COL_REG_COUNT)));
                teamLM.setRegLimitCount(cursor.getInt(cursor.getColumnIndex(COL_REG_LIMIT_COUNT)));
                teamLM.setActiveFlag(cursor.getInt(cursor.getColumnIndex(COL_ACTIVE_FLAG)));
                teamLM.setRegAccess(cursor.getInt(cursor.getColumnIndex(COL_ACCESS_FARMER_REG_FLAG)));
                teamLM.setSearchAccess(cursor.getInt(cursor.getColumnIndex(COL_ACCESS_FARMER_SEARCH_FLAG)));
                teamLM.setDistAccess(cursor.getInt(cursor.getColumnIndex(COL_ACCESS_DISTRIBUTION_FLAG)));
                teamLM.setOtpSendFlag(cursor.getInt(cursor.getColumnIndex(COL_ACCESS_OTP_SEND_FLAG)));

            } while (cursor.moveToNext());
        }

        return teamLM;
    }

*/
}
