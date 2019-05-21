package com.example.googledriveclone.Helper;

import android.content.Context;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.googledriveclone.R;

public class SwipeRefreshHelper {
    public static void setupSwipeRefreshBehaviour(SwipeRefreshLayout swipeRefreshLayout, Context context){
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorYellow),
                ContextCompat.getColor(context, R.color.colorRed),
                ContextCompat.getColor(context, R.color.colorGreen));
    }

    public static void delayRefresh(final SwipeRefreshLayout swipeRefreshLayout){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
