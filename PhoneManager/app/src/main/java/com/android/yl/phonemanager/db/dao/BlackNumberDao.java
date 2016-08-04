package com.android.yl.phonemanager.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.android.yl.phonemanager.bean.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑名单dao
 * Created by Administrator on 2016/8/3.
 */
public class BlackNumberDao {

    private final BlackNumberOpenHelper helper;

    public BlackNumberDao(Context context) {
        helper = new BlackNumberOpenHelper(context);
    }

    /**
     * @param number 黑名单号码
     * @param mode   拦截模式
     */
    public boolean add(String number, String mode) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("mode", mode);
        long rowId = db.insert("blacknumber", null, contentValues);
        if (rowId != -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 通过电话号码的方式删除
     *
     * @param number
     */
    public boolean delete(String number) {
        final SQLiteDatabase db = helper.getWritableDatabase();
        final int rowNumber = db.delete("blacknumber", "name=?", new String[]{number});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 通过电话号码的方式修改拦截模式
     *
     * @param number
     */
    public boolean changeNumberMode(String number, String mode) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mode", mode);
        int rowNumber = db.update("blacknumber", contentValues, "number=?", new String[]{number});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 通过电话号码的方式查询
     *
     * @param number
     */
    public String findNumber(String number) {
        String mode = "";
        final SQLiteDatabase db = helper.getReadableDatabase();
        final Cursor cursor = db.query("blacknumber", new String[]{"mode"}, "number=?", new String[]{number}, null, null, null);
        if (cursor.moveToNext()) {
            mode = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return mode;

    }

    /**
     * 查询出所有的黑名单
     * <p/>
     * 集合很迷茫
     *
     * @return
     */
    public List<BlackNumberInfo> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<BlackNumberInfo> blackNumberInfos = new ArrayList<BlackNumberInfo>();
        Cursor cursor = db.query("blacknumber", new String[]{"number", "mode"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            final BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.setNumber(cursor.getColumnName(0));
            blackNumberInfo.setMode(cursor.getString(1));
            blackNumberInfos.add(blackNumberInfo);
        }
        cursor.close();
        db.close();
        SystemClock.sleep(3000);//等三秒时间加载
        return blackNumberInfos;

    }
}
