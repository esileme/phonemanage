package com.android.yl.phonemanager;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.android.yl.phonemanager.db.dao.BlackNumberDao;
import com.android.yl.phonemanager.db.dao.BlackNumberOpenHelper;

import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public Context mContext;

    @Override
    protected void setUp() throws Exception {
        this.mContext = getContext();
        super.setUp();
    }

    public void testCreateDb() {
        BlackNumberOpenHelper helper = new BlackNumberOpenHelper(getContext());
        helper.getWritableDatabase();
    }


    public void testAdd() {
        BlackNumberDao dao = new BlackNumberDao(mContext);
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            Long number = 13300000000l + i;
            dao.add(number + "", String.valueOf(random.nextInt(3) + 1));
        }
    }
}