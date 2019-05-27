package com.example.googledriveclone.BottomNavFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.googledriveclone.Fragments.ComputersFragment;
import com.example.googledriveclone.Fragments.DetailsFragment;
import com.example.googledriveclone.Fragments.MyDriveFragment;
import com.example.googledriveclone.PagerAdapter.FilesPagerAdapter;
import com.example.googledriveclone.R;
import com.example.googledriveclone.Transitions.RevealAnimationSetting;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FilesFragment extends Fragment implements MyDriveFragment.MyDriveItemClickedListener {

    private FilesPagerAdapter filesPagerAdapter;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RelativeLayout relativeLayout;

    // Main Activity Layouts
    private FloatingActionButton fab;
    private AppBarLayout appBarLayout;

    private static final String TAG = "FilesFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: FilesFragment Created");
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        setupTabLayout();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        fab = getActivity().findViewById(R.id.main_fab);
        appBarLayout = getActivity().findViewById(R.id.main_appbarlayout);

    }

    private void setupViews(View view) {
        tabLayout = view.findViewById(R.id.files_tabs);
        viewPager = view.findViewById(R.id.files_viewpager);
        relativeLayout = view.findViewById(R.id.fragment_files_container);
    }

    private void setupTabLayout() {
        titles.clear();
        fragments.clear();

        // Header for tabs
        titles.add("My Drive");
        titles.add("Computers");
        // Fragments
        MyDriveFragment myDriveFragment = new MyDriveFragment();
        ComputersFragment computersFragment = new ComputersFragment();
        fragments.add(myDriveFragment);
        fragments.add(computersFragment);

        filesPagerAdapter = new FilesPagerAdapter(getChildFragmentManager(), titles, fragments);
        viewPager.setAdapter(filesPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        myDriveFragment.setMyDriveItemClickedListener(this);
    }

    /**
     * Show new fragment (item_detail_fragment)
     */
    @Override
    public void myDriveItemClicked(int position, float[] coordinates) {

        // Ensure that appbar is shown after animation is completed
        if (appBarLayout != null) {
            appBarLayout.setExpanded(true);
        }

        // Ensure that FAB is shown after animation is completed
        if (fab != null) {
            fab.show();
        }
        Log.d(TAG, "myDriveItemClicked: X : " + coordinates[0] + " Y : " + coordinates[1]);
        //Log.d(TAG, "myDriveItemClicked: FAB :  X : " + fab.getX() + " Y : " + fab.getY());
        // Get dimension for the reveal animations and pass it to the new fragment to be shown
        RevealAnimationSetting animationSettings = new RevealAnimationSetting((int) coordinates[0],
                (int) coordinates[1], relativeLayout.getWidth(),
                relativeLayout.getHeight());

        // Carry out fragment transaction
        if (getActivity() != null) {
            Log.d(TAG, "myDriveItemClicked: getActivity != null");
            FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();

            if (fragmentManager2 != null) {
                Log.d(TAG, "myDriveItemClicked: fragmentManager2 != null");
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                DetailsFragment fragment2 = new DetailsFragment(animationSettings);
                fragmentTransaction2.addToBackStack(null);
                //fragmentTransaction2.remove(FilesFragment.this);
                fragmentTransaction2.hide(FilesFragment.this);
                fragmentTransaction2.add(R.id.main_fragment_container, fragment2);
                fragmentTransaction2.commit();
                Log.d(TAG, "myDriveItemClicked: Commit");
            }
        }
    }

}
