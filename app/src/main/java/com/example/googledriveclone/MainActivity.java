package com.example.googledriveclone;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.googledriveclone.Fragments.FilesFragment;
import com.example.googledriveclone.Fragments.HomeFragment;
import com.example.googledriveclone.Fragments.SharedFragment;
import com.example.googledriveclone.Fragments.StarredFragment;
import com.example.googledriveclone.Transitions.FadeInTransition;
import com.example.googledriveclone.Transitions.FadeOutTransition;
import com.example.googledriveclone.Transitions.SimpleTransitionListener;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivityTAG";
    // Views
    private EditText searchInput;
    private BottomNavigationView bottomNav;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout fragmentContainer;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private RoundedLetterView profileIcon;
    private ImageButton hamburgerIcon;
    private int tbMargin_Horizontal, tbMargin_Vertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbMargin_Horizontal = 12;
        tbMargin_Vertical = 4;
        setupViews();
        setupFAB();
        setIcon();
        setupHamburgerIcon();

        searchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToSearch();
            }
        });
    }

    private void setupViews() {
        searchInput = findViewById(R.id.main_search_input);
        appBarLayout = findViewById(R.id.main_appbarlayout);
        toolbar = findViewById(R.id.main_toolbar);
        collapsingToolbarLayout = findViewById(R.id.main_collapsingToolBar);
        bottomNav = findViewById(R.id.main_bottom_nav);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigationview_id);
        fragmentContainer = findViewById(R.id.main_fragment_container);
        fab = findViewById(R.id.main_fab);
        profileIcon = findViewById(R.id.main_icon);
        hamburgerIcon = findViewById(R.id.main_hamurger_icon);

        bottomNav.setItemIconTintList(null);
        bottomNav.setOnNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.bottom_nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new HomeFragment()).commit();
                break;
            case R.id.bottom_nav_starred:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new StarredFragment()).commit();
                break;
            case R.id.bottom_nav_shared:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new SharedFragment()).commit();
                break;
            case R.id.bottom_nav_files:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new FilesFragment()).commit();
                break;
        }

        return true;
    }

    /**
     * FloatingActionButton method
     */
    private void setupFAB() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Set profileIcon for the round image view and onClickListener
     */
    private void setIcon() {
        profileIcon.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        profileIcon.setTitleText("N");
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Handle hamburger icon onClickListener
     */
    private void setupHamburgerIcon(){
        hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.START);

            }
        });
    }

    @Override
    public void onBackPressed() {

        // Back press will close drawer if it is open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fadeToolbarIn();
    }

    private void transitionToSearch() {
        // create a transition that navigates to search when complete
        Transition transition = FadeOutTransition.withAction(navigateToSearchWhenDone());

        // let the TransitionManager do the heavy work for us!
        // all we have to do is change the attributes of the toolbar and the TransitionManager animates the changes
        // in this case I am removing the bounds of the toolbar (to hide the blue padding on the screen) and
        // I am hiding the contents of the Toolbar (Navigation icon, Title and Option Items)
        TransitionManager.beginDelayedTransition(toolbar, transition);
        FrameLayout.LayoutParams frameLP = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        frameLP.setMargins(0, 0, 0, 0);
        toolbar.setLayoutParams(frameLP);

        toolbar.findViewById(R.id.main_hamurger_icon).setVisibility(View.GONE);
        toolbar.findViewById(R.id.main_icon).setVisibility(View.GONE);
        searchInput.setHint("");

    }

    private Transition.TransitionListener navigateToSearchWhenDone() {
        return new SimpleTransitionListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorCardBackground));
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

                // we are handing the enter transitions ourselves
                // this line overrides that
                overridePendingTransition(0, 0);
                //getWindow().setExitTransition(null);
                // by this point of execution we have animated the 'expansion' of the Toolbar and hidden its contents.
                // We are half way there. Continue to the SearchActivity to finish the animation
            }
        };
    }

    /**
     * Fades toolbar in when returning to MainActivity Screen
     */
    private void fadeToolbarIn() {
        toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.defaultBackgroundColor));

        TransitionManager.beginDelayedTransition(toolbar, FadeInTransition.createTransition());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        Log.d(TAG, "fadeToolbarIn: " + layoutParams.topMargin + "," + layoutParams.leftMargin);
        layoutParams.setMargins(48, 6, 48, 6); //WHATEVER REASON THIS ARE THE VALUES
        toolbar.findViewById(R.id.main_hamurger_icon).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.main_icon).setVisibility(View.VISIBLE);
        toolbar.setLayoutParams(layoutParams);
        searchInput.setHint("Search Drive");
    }
}
