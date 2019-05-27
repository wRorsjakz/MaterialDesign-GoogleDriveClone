package com.example.googledriveclone.ViewPagers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * This is a custom viewpager that allows the ability to swipe left or right to switch fragment to be disabled.
 * It is currently implemented for the main activity's bottomNav
 */

public class BottomNavCustomViewPager extends ViewPager {

    private Boolean disable = false;

    // Use this method to enable or disable scroll
    public void disableScroll(Boolean disable){
        //When disable = true not work the scroll and when disable = false work the scroll
        this.disable = disable;
    }

    public BottomNavCustomViewPager(@NonNull Context context) {
        super(context);
    }

    public BottomNavCustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return disable ? false : super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return disable ? false : super.onTouchEvent(event);
    }







}
