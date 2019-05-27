package com.example.googledriveclone.PagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * PagerAdapter for the main activity's bottomNav
 */
public class BottomNavPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public BottomNavPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
