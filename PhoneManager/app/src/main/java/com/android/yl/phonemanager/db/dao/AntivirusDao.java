package com.android.yl.phonemanager.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * =================================
 * <p>
 * Created by Administrator on 2016/8/15.
 * <p>
 * 描述:
 */

public class AntivirusDao {
    /**
     * 检查病毒是否在病毒库中
     */
    public static String checkFileVirus(String md5) {

        String desc = "";
        SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/user/0/com.android.yl.phonemanager/files/antivirus.db", null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("select desc from datable where MD5=?", new String[]{md5}, null);
        if (cursor.moveToNext()) {
            desc = cursor.getString(0);
            cursor.close();
        }
        cursor.close();
        database.close();

        return desc;
    }

    /**
     * 给数据库添加数据的dao
     *
     * @param md5
     * @param desc
     */
    public void addVirus(String md5, String desc) {
        SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/user/0/com.android.yl.phonemanager/files/antivirus.db", null, SQLiteDatabase.OPEN_READWRITE);

        ContentValues contentValues = new ContentValues();
        contentValues.put("md5", md5);
        contentValues.put("type", 5);
        contentValues.put("name", "Android.Troj.AirAD.b");
        contentValues.put("desc", desc);

        database.insert("datable", null, contentValues);

    }
}
