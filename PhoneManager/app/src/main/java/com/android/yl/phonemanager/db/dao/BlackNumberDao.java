package com.android.yl.phonemanager.db.dao;

/**
 * 黑名单dao
 * Created by Administrator on 2016/8/3.
 *
 * @param number 黑名单号码
 * @param mode   拦截模式
 * <p/>
 * 通过电话号码的方式删除
 * @param number
 * <p/>
 * 通过电话号码的方式修改拦截模式
 * @param number
 * <p/>
 * 通过电话号码的方式查询
 * @param number
 * <p/>
 * 查询出所有的黑名单
 * <p/>
 * 集合很迷茫
 * @return
 *//*
public class BlackNumberDao {

    private final BlackNumberOpenHelper helper;

    public BlackNumberDao(Context context) {
        helper = new BlackNumberOpenHelper(context);
    }

    *//**
 * @param number 黑名单号码
 * @param mode   拦截模式
 *//*
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

    *//**
 * 通过电话号码的方式删除
 *
 * @param number
 *//*
    public boolean delete(String number) {
        final SQLiteDatabase db = helper.getWritableDatabase();
        final int rowNumber = db.delete("blacknumber", "name=?", new String[]{number});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }

    }

    *//**
 * 通过电话号码的方式修改拦截模式
 *
 * @param number
 *//*
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

    *//**
 * 通过电话号码的方式查询
 *
 * @param number
 *//*
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

    *//**
 * 查询出所有的黑名单
 * <p/>
 * 集合很迷茫
 *
 * @return
 *//*
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

    */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.android.yl.phonemanager.bean.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页加载数据
 * <p/>
 * limit 表示限制有多少数据
 * offset表示跳过  从第几条开始
 *
 * @param pageNumber 表示当前有那几个页
 * @param pageSize   表示当前页面大小
 * @return
 *//*
    public List<BlackNumberInfo> findPar(int pageNumber, int pageSize) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select number,mode from blacknumber limit ?offset ?",
                new String[]{String.valueOf(pageSize), String.valueOf(pageNumber * pageSize)});
        ArrayList<BlackNumberInfo> blackNumberInfos = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()) {
            final BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.setMode(cursor.getString(1));
            blackNumberInfo.setNumber(cursor.getString(0));
            blackNumberInfos.add(blackNumberInfo);
        }
        cursor.close();
        db.close();
        return blackNumberInfos;

    }
}*/
public class BlackNumberDao {

    public BlackNumberOpenHelper helper;

    public BlackNumberDao(Context context) {
        helper = new BlackNumberOpenHelper(context);
    }


    /**
     * @param number 黑名单号码
     * @param mode   拦截模式
     */
    public boolean add(String number, String mode) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("mode", mode);
        long rowid = db.insert("blacknumber", null, contentValues);
        if (rowid == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 通过电话号码删除
     *
     * @param number 电话号码
     */
    public boolean delete(String number) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowNumber = db.delete("blacknumber", "number=?", new String[]{number});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 通过电话号码修改拦截的模式
     *
     * @param number
     */
    public boolean changeNumberMode(String number, String mode) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode", mode);
        int rownumber = db.update("blacknumber", values, "number=?", new String[]{number});
        if (rownumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 返回一个黑名单号码拦截模式
     *
     * @return
     */
    public String findNumber(String number) {
        String mode = "";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("blacknumber", new String[]{"mode"}, "number=?", new String[]{number}, null, null, null);
        if (cursor.moveToNext()) {
            mode = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return mode;
    }

    /**
     * 查询所有的黑名单
     *
     * @return
     */
    public List<BlackNumberInfo> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<BlackNumberInfo> blackNumberInfos = new ArrayList<BlackNumberInfo>();
        Cursor cursor = db.query("blacknumber", new String[]{"number", "mode"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.setNumber(cursor.getString(0));
            blackNumberInfo.setMode(cursor.getString(1));
            blackNumberInfos.add(blackNumberInfo);
        }
        cursor.close();
        db.close();

        SystemClock.sleep(3000);
        return blackNumberInfos;
    }

    /**
     * 分页加载数据
     *
     * @param pageNumber 表示当前是哪一页
     * @param pageSize   表示每一页有多少条数据
     * @return limit 表示限制当前有多少数据
     * offset 表示跳过 从第几条开始
     */
    public List<BlackNumberInfo> findPar(int pageNumber, int pageSize) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select number,mode from blacknumber limit ? offset ?", new String[]{String.valueOf(pageSize),
                String.valueOf(pageSize * pageNumber)});
        List<BlackNumberInfo> blackNumberInfos = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()) {
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.setMode(cursor.getString(1));
            blackNumberInfo.setNumber(cursor.getString(0));
            blackNumberInfos.add(blackNumberInfo);
        }
        cursor.close();
        db.close();
        return blackNumberInfos;
    }

    /**
     * 分批加载数据
     * @param startIndex  开始的位置
     * @param maxCount    每页展示的最大的条目
     * @return
     */
    public List<BlackNumberInfo> findPar2(int startIndex, int maxCount) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select number,mode from blacknumber limit ? offset ?", new String[]{String.valueOf(maxCount),
                String.valueOf(startIndex)});
        List<BlackNumberInfo> blackNumberInfos = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()) {
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.setMode(cursor.getString(1));
            blackNumberInfo.setNumber(cursor.getString(0));
            blackNumberInfos.add(blackNumberInfo);
        }
        cursor.close();
        db.close();
        return blackNumberInfos;
    }


    /**
     * 获取总的记录数
     * @return
     */
    public int getTotalNumber() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from blacknumber", null);
        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }


}

