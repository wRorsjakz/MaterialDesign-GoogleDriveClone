package com.example.googledriveclone.Helper;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

public class RVScrollListenerHelper {

    public static void setupFABScroll(final FloatingActionButton fab, RecyclerView recyclerView){
        // Hide FAB when scrolling up, show when scrolling up
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    fab.hide();
                }else if (dy < 0)
                    fab.show();
            }
        });
    }

}
