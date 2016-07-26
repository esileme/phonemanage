package com.android.yl.phonemanager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用来读取网络上的数据保存到本地
 * Created by Administrator on 2016/7/26.
 */
public class StreamUtils {

    public static String readFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer))!= -1) {
            out.write(buffer, 0, len);
        }
        String result = out.toString();
        in.close();
        out.close();
        return result;
    }
}
