package com.android.yl.phonemanager.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/8/17.
 * <p/>
 * 描述:
 */

public class ApplockHelper extends SQLiteOpenHelper {

    public ApplockHelper(Context context) {
        super(context, "applock.db", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info (_id integer primary key autoincrement,packagename varchar(20)) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
