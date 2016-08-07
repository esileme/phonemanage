package com.android.yl.phonemanager.db.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * 来电归属地中的地址dao
 * Created by Administrator on 2016/8/3.
 */
public class AddressDao {
    public static final String path="/data/data/com.android.yl.phonemanager/files/address.db";
    public static String getAddress(String number){
        SQLiteDatabase database=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
            return null;
    }
}


