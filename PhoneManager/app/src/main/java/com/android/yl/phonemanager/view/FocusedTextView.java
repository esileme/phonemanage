package com.android.yl.phonemanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/27.
 */
public class FocusedTextView extends TextView {

    //用代码调用方法时，写对象属性调用此方法
    public FocusedTextView(Context context) {
        super(context);
    }

    //有属性时调用此方法
    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //有样式时调用此方法
    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //在此更改属性，如果没有获取焦点 ，强制获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
