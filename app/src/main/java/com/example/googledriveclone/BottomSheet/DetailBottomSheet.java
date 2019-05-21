package com.example.googledriveclone.BottomSheet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.googledriveclone.R;

public class DetailBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {


    private static final String TAG = "DetailBottomSheet";
    private LinearLayout addToStarred, share, linkSharingOff, copyLink, rename, changeColour, move,
    addToHomeScreen, detailsAndActivity, remove;
    private RelativeLayout profile;

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Interface to handle item clicked
     */
    private DetailBottomSheetInterface detailBottomSheetListener;
    public interface DetailBottomSheetInterface {
        void detailBSItemClicked(View view);
    }
    public void setDetailBottomSheetListener(DetailBottomSheetInterface detailBottomSheetListener) {
        this.detailBottomSheetListener = detailBottomSheetListener;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.file_detail_bottomsheet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseViews(view);
    }


    /**
     * Initialise Views and set onClickListener
     * @param v
     */
    private void initialiseViews(View v){
        addToStarred = v.findViewById(R.id.file_detail_bs_addToStarred);
        addToStarred.setOnClickListener(this);

        share = v.findViewById(R.id.file_detail_bs_share);
        share.setOnClickListener(this);

        linkSharingOff = v.findViewById(R.id.file_detail_bs_linksharingoff);
        linkSharingOff.setOnClickListener(this);

        copyLink = v.findViewById(R.id.file_detail_bs_copylink);
        copyLink.setOnClickListener(this);

        rename = v.findViewById(R.id.file_detail_bs_rename);
        rename.setOnClickListener(this);

        changeColour = v.findViewById(R.id.file_detail_bs_changecolour);
        changeColour.setOnClickListener(this);

        move = v.findViewById(R.id.file_detail_bs_move);
        move.setOnClickListener(this);

        addToHomeScreen = v.findViewById(R.id.file_detail_bs_homescreen);
        addToHomeScreen.setOnClickListener(this);

        detailsAndActivity = v.findViewById(R.id.file_detail_bs_detailsandactivity);
        detailsAndActivity.setOnClickListener(this);

        remove = v.findViewById(R.id.file_detail_bs_remove);
        remove.setOnClickListener(this);

        profile = v.findViewById(R.id.detail_bottomsheet_profile);
        profile.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(detailBottomSheetListener != null){
            Log.d(TAG, "onClick: detailBottomSheetListener NOT NULL");
            detailBottomSheetListener.detailBSItemClicked(v);
        } else {
            Log.d(TAG, "onClick: detailBottomSheetListener IS NULL");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Set phone's bottom nav to its original color instead of being faded out when displayed
        Activity activity = getActivity();
        if(activity != null) {
            BottomNavColor.setWhiteNavigationBar(dialog, getActivity());
        }
        return dialog;
    }
}
