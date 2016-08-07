package com.android.yl.phonemanager.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 来电归属地中的地址dao
 * Created by Administrator on 2016/8/3.
 */
public class AddressDao {
    public static final String path = "/data/user/0/com.android.yl.phonemanager/files/address.db";


    public static String getAddress(String number) {
        String address = "";
        //获取数据库对象
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        /**
         * 利用正则表达式来判断电话号码的属性进行对数据库匹配
         */
        if (number.matches("^1[3-8]\\d{9}$")) {
            Cursor cursor = database.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)", new String[]{number.substring(0, 7)});
            if (cursor.moveToNext()) {
                address = cursor.getString(0);
                cursor.close();
            }
        } else if (number.matches("^\\d+$")) {
            switch (number.length()) {
                case 3:
                    address = "报警电话";
                    break;
                case 4:
                    address = "模拟器";

                    break;
                case 5:
                    address = "客服电话";
                    break;
                case 7:
                case 8:
                    address = "本地电话";
                    break;
                default:
                    break;
            }

        }

        return address;
    }
}


