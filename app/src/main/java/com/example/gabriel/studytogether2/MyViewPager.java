package com.example.gabriel.studytogether2;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Gabriel on 10/8/2017.
 */

public class MyViewPager extends ViewPager {

    private boolean enableSwipe = true;

    public MyViewPager(Context context) {
        super(context);
        init();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() { enableSwipe = true; }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return enableSwipe && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return enableSwipe && super.onTouchEvent(ev);
    }

    public void setEnableSwipe(boolean enableSwipe) {
        this.enableSwipe = enableSwipe;
    }
}
