package com.android.yl.phonemanager.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SmsUtils {
    /**
     * 使用内容观察者访问短信，
     * <p/>
     * 将备份短信备份到手机上
     *
     * @param context
     * @return
     */
    public static boolean backUp(Context context) {

        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);

        try {

            File file = new File(Environment.getExternalStorageDirectory(), "backup.xml");
            FileOutputStream fos = new FileOutputStream(file);
            XmlSerializer serializer = Xml.newSerializer();//new出一个xmlserializer，一般xml都用pull解析
            serializer.setOutput(fos, "utf-8");//給序列化器設置文件，并設置編碼格式
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "smss");

            while (cursor.moveToNext()) {
                System.err.println("----------------------------------");
                System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
                System.out.println("address:" + cursor.getString(0));
                System.out.println("date:" + cursor.getString(1));
                System.out.println("type:" + cursor.getString(2));
                System.out.println("body:" + cursor.getString(3));
                System.out.println("count:" + cursor.getCount());
                serializer.startTag(null, "sms");

                serializer.startTag(null, "address");
                serializer.text(cursor.getString(0));
                serializer.endTag(null, "address");

                serializer.startTag(null, "date");
                serializer.text(cursor.getString(1));
                serializer.endTag(null, "date");

                serializer.startTag(null, "type");
                serializer.text(cursor.getString(2));
                serializer.endTag(null, "type");

                serializer.startTag(null, "body");
                serializer.text(cursor.getString(3));
                serializer.endTag(null, "body");

                serializer.endTag(null, "sms");

            }
            serializer.endTag(null, "smss");
            serializer.endDocument();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
}
