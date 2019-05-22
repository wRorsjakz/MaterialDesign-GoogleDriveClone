package com.example.googledriveclone.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.googledriveclone.R;
import com.example.googledriveclone.Transitions.AnimationUtils;
import com.example.googledriveclone.Transitions.RevealAnimationSetting;


public class DetailsFragment extends Fragment {

    private RevealAnimationSetting animationSettings;

    public DetailsFragment(RevealAnimationSetting settings) {
        this.animationSettings = settings;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.files_item_detail, container, false);

        AnimationUtils.registerCircularRevealAnimation(getContext(), view, animationSettings,
                R.color.colorDarkGrey, R.color.colorAccent);

        return view;
    }
}
