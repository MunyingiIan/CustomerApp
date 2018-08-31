package com.ellixar.app.customer.sembe.customerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Munyingi Ian on 5/1/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sembe_customer_app_db";

    // Login table name
    private static final String TABLE_USER = "user";


    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_IS_SHOP_OWNER = "is_shop_owner";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_IS_SHOP_OWNER + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);



        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);


        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at, String is_shop_owner) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        values.put(KEY_IS_SHOP_OWNER, is_shop_owner); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }



    /**
     * Getting user isShopOwner data from database
     * */
    public ArrayList<String> getUserIsShopOwner() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        ArrayList<String> string_is_shop_owner = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                String Is_Shop_Owner=c.getString(c.getColumnIndex(KEY_IS_SHOP_OWNER));
                string_is_shop_owner.add(Is_Shop_Owner);

            }while (c.moveToNext());
        }
        c.close();
        db.close();

        return string_is_shop_owner;

    }


    /**
     * Getting user uniqueId data from database
     * */
    public ArrayList<String> getUserUniqueId() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        ArrayList<String> string_user_unique_id = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                String User_Unique_Id=c.getString(c.getColumnIndex(KEY_UID));
                string_user_unique_id.add(User_Unique_Id);

            }while (c.moveToNext());
        }
        c.close();
        db.close();

        return string_user_unique_id;

    }


    /**
     * Getting username data from database
     * */
    public ArrayList<String> getSellersUserName() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        ArrayList<String> string_seller_user_name = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                String User_user_name=c.getString(c.getColumnIndex(KEY_NAME));
                string_seller_user_name.add(User_user_name);

            }while (c.moveToNext());
        }
        c.close();
        db.close();

        return string_seller_user_name;

    }


    /**
     * Getting username data from database
     * */
    public ArrayList<String> getUserName() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        ArrayList<String> string_seller_user_name = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                String User_user_name=c.getString(c.getColumnIndex(KEY_NAME));
                string_seller_user_name.add(User_user_name);

            }while (c.moveToNext());
        }
        c.close();
        db.close();

        return string_seller_user_name;

    }


    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}