package com.example.googledriveclone;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.googledriveclone.BottomSheet.AddBottomSheet;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.googledriveclone.Fragments.FilesFragment;
import com.example.googledriveclone.Fragments.HomeFragment;
import com.example.googledriveclone.Fragments.SharedFragment;
import com.example.googledriveclone.Fragments.StarredFragment;
import com.example.googledriveclone.Transitions.FadeInTransition;
import com.example.googledriveclone.Transitions.FadeOutTransition;
import com.example.googledriveclone.Transitions.SimpleTransitionListener;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, AddBottomSheet.FabBottomSheetListener {

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

    private AddBottomSheet addBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbMargin_Horizontal = 12;
        tbMargin_Vertical = 4;

        initialiseViews();
        setIcon();
        setupHamburgerIcon();
        setupFAB();

        searchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToSearch();
            }
        });

    }

    private void initialiseViews() {
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
                addBottomSheet = new AddBottomSheet();
                addBottomSheet.setFabBottomSheetListener(MainActivity.this);
                addBottomSheet.show(getSupportFragmentManager(), "addBottomSheet");
            }
        });

    }

    /**
     * Set profile Icon for the round image view and onClickListener
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
    private void setupHamburgerIcon() {
        hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

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


    @Override
    public void onItemPressed(View view) {
        Toast.makeText(this, "" + view.getId(), Toast.LENGTH_SHORT).show();
        addBottomSheet.dismiss();
    }
}
