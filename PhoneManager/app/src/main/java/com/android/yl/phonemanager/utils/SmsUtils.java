package com.android.yl.phonemanager.utils;

import android.app.ProgressDialog;
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
     * @param dialog
     * @return
     */
    public static boolean backUp(Context context, ProgressDialog dialog) {

        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);

        int count = cursor.getCount();
        dialog.setMax(count);//给进度条设置最大个数
        int process = 0;//定义process进度条数量的值，从0开始，在每备份成功一条短信后，对其值+1;


        try {

            File file = new File(Environment.getExternalStorageDirectory(), "backup.xml");
            FileOutputStream fos = new FileOutputStream(file);
            XmlSerializer serializer = Xml.newSerializer();//new出一个xmlserializer，一般xml都用pull解析
            serializer.setOutput(fos, "utf-8");//給序列化器設置文件，并設置編碼格式
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "smss");
            serializer.attribute(null, "size", String.valueOf(count));

            while (cursor.moveToNext()) {
                System.err.println("----------------------------------");
                System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
                System.out.println("address:" + cursor.getString(0));
                System.out.println("date:" + cursor.getString(1));
                System.out.println("type:" + cursor.getString(2));
                System.out.println("body:" + cursor.getString(3));
                System.out.println("count:" + count);
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

                process++;//在备份一条短信后，对数量++
                dialog.setProgress(process);
                //SystemClock.sleep(20);

            }
            serializer.endTag(null, "smss");
            cursor.close();
            serializer.endDocument();

            fos.flush();
            fos.close();//对fos进行关闭，提升性能
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
}
