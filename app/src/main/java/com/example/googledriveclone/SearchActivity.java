package com.example.googledriveclone;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.googledriveclone.Transitions.FadeInTransition;
import com.example.googledriveclone.Transitions.FadeOutTransition;
import com.example.googledriveclone.Transitions.SimpleTransitionListener;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivityTAG";
    private RelativeLayout relativeLayout;
    private EditText editText;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;

    int childCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //getWindow().setEnterTransition(null);
        initialiseViews();

        childCount = toolbar.getChildCount();

        if (savedInstanceState == null) {
            // Start with an empty looking Toolbar
            // We are going to fade its contents in, as long as the activity finishes rendering
            for(int i = 0; i < childCount; i++){
                toolbar.getChildAt(i).setVisibility(View.GONE);
            }

            ViewTreeObserver viewTreeObserver = toolbar.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    // after the activity has finished drawing the initial layout, we are going to continue the animation
                    // that we left off from the MainActivity
                    showSearch();
                }

                private void showSearch() {
                    // use the TransitionManager to animate the changes of the Toolbar
                    TransitionManager.beginDelayedTransition(toolbar, FadeInTransition.createTransition());
                    // here we are just changing all children to VISIBLE
                    for(int i = 0; i < childCount; i++){
                        toolbar.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }

    private void initialiseViews() {
        relativeLayout = findViewById(R.id.activitySearch_relativeLayout);
        editText = findViewById(R.id.searchActivity_edittext);
        editText.requestFocus();
        appBarLayout = findViewById(R.id.searchActivity_appbarlayout);
        toolbar = findViewById(R.id.searchActivity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_darkgrey_24dp);
    }

    @Override
    public void finish() {
        // when the user tries to finish the activity we have to animate the exit
        // let's start by hiding the keyboard so that the exit seems smooth

        // at the same time, start the exit transition
        exitTransitionWithAction(new Runnable() {
            @Override
            public void run() {
                // which finishes the activity (for real) when done
                SearchActivity.super.finish();

                // override the system pending transition as we are handling ourselves
                overridePendingTransition(0, 0);
            }
        });
    }

    private void exitTransitionWithAction(final Runnable endingAction) {

        Transition transition = FadeOutTransition.withAction(new SimpleTransitionListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                endingAction.run();
            }
        });

        android.transition.TransitionManager.beginDelayedTransition(toolbar, transition);

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            toolbar.getChildAt(i).setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_clear) {
            editText.setText("");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
