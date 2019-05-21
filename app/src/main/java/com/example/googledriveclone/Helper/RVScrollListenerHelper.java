package com.example.googledriveclone.Helper;

import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;

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
