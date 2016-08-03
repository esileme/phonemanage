package com.android.yl.phonemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseSetupActivity extends Activity {
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //手势识别，通过识别手势来判断是划过上一页还是下一页
        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage();
                    return true;
                }
                if (e2.getRawX() - e1.getRawX() > 200) {
                    showPreviousPage();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 显示xia一页
     */
    public abstract void showNextPage();

    /**
     * 显示上一页
     */
    public abstract void showPreviousPage();

    /**
     * 跳转到下一个页面
     *
     * @param v
     */
    public void next(View v) {
        showNextPage();

    }

    /**
     * 跳转到上一个页面
     *
     * @param v
     */
    public void prev(View v) {

        showPreviousPage();

    }



}
