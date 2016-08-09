package com.android.yl.phonemanager;

import android.content.Context;
import android.test.AndroidTestCase;

import com.android.yl.phonemanager.db.dao.BlackNumberDao;

import java.util.Random;

/**
 * Created by Administrator on 2016/8/3.
 */
public class TestBlackNumberDao1 extends AndroidTestCase {

    public Context mContext;


    @Override
    protected void setUp() throws Exception {
        this.mContext = getContext();
        super.setUp();
    }

    public void testAdd() {
        BlackNumberDao dao = new BlackNumberDao(mContext);
        for (int i = 0; i < 200; i++) {
            Random random = new Random();
            Long number = 13000000000l + i;
            //需要number和mode，所以new 出number和mode.
            dao.add(number + "", String.valueOf(random.nextInt(3) + i));
        }

    }
}
