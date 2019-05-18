package com.example.googledriveclone;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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
import com.github.pavlospt.roundedletterview.RoundedLetterView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // Views
    private EditText searchInput;
    private BottomNavigationView bottomNav;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout fragmentContainer;
    private FloatingActionButton fab;
    private RoundedLetterView profileIcon;
    private ImageButton hamburgerIcon;

    // Variables
    private boolean drawerOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerOpen = false;

        setupViews();
        setupFAB();
        setIcon();
        setupHamburgerIcon();
    }

    private void setupViews() {
        searchInput = findViewById(R.id.main_search_input);
        bottomNav = findViewById(R.id.main_bottom_nav);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigationview_id);
        fragmentContainer = findViewById(R.id.main_fragment_container);
        fab = findViewById(R.id.main_fab);
        profileIcon = findViewById(R.id.main_icon);
        hamburgerIcon = findViewById(R.id.main_hamurger_icon);
        bottomNav.setItemIconTintList(null);
        bottomNav.setOnNavigationItemSelectedListener(this);
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
}
