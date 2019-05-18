package com.example.googledriveclone.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googledriveclone.PagerAdapter.FilesPagerAdapter;
import com.example.googledriveclone.R;

import java.util.ArrayList;

public class FilesFragment extends Fragment {

    private FilesPagerAdapter filesPagerAdapter;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        setupTabLayout();
    }

    private void setupViews(View view){
        tabLayout = view.findViewById(R.id.files_tabs);
        viewPager = view.findViewById(R.id.files_viewpager);
    }

    private void setupTabLayout(){

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

    }


}
