package com.leobee.animeconvention.manager.library;

import java.util.HashMap;

/*
 * 
 * A hash function is any algorithm or subroutine that maps large data sets of variable length,
 *  called keys, to smaller data sets of a fixed length.
 *  
 * Hash tables are good for doing a quick search on things. 
 * 
 * Hash table based implementation of the Map interface. This implementation provides 
 * all of the optional map operations, and permits null values and the null key.
 * This implementation provides constant-time performance for the basic operations (get and put),
 * 
 * An instance of HashMap has two parameters that affect its performance: initial capacity
 *  and load factor. The capacity is the number of buckets in the hash table, and the initial 
 *  capacity is simply the capacity at the time the hash table is created. The load factor is
 *   a measure of how full the hash table is allowed to get before its capacity is automatically 
 *   increased.
 *   
 *   
 *   
 * */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "OtakonSchedulePushApp";
 
    // Login table name
    private static final String TABLE_LOGIN = "login";
 
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        
       System.out.println("DatabaseHandler on create");
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        System.out.println("DatabaseHandler on upgrade");
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
 
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
        
        System.out.println("DatabaseHandler adduser");
    }
 
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        System.out.println("DatabaseHandler on user details "+user);
        return user;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        System.out.println("DatabaseHandler on getrowcount"+rowCount);
        // return row count
        return rowCount;
    }
 
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        System.out.println("DatabaseHandler resettables");
        db.close();
    }
 
}
