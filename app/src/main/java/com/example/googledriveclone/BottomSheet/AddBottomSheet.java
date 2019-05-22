package com.example.googledriveclone.BottomSheet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.googledriveclone.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    // Widgets
    private LinearLayout folder, upload, scan, googleDocs, googleSheets, googleSlides;

    /**
     * Interface for item click
     */

    private FabBottomSheetListener mListener;

    public interface FabBottomSheetListener {
        void onAddBottomSheetItemPressed(View view);
    }

    public void setFabBottomSheetListener(FabBottomSheetListener mListener) {
        this.mListener = mListener;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fab_bottomsheet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseViews(view);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Set phone's bottom nav to its original color instead of being faded out when displayed
        Activity activity = getActivity();
        if (activity != null) {
            BottomNavColor.setWhiteNavigationBar(dialog, getActivity());
        }
        return dialog;
    }

    private void initialiseViews(View v) {
        folder = v.findViewById(R.id.add_fab_bs_folder);
        folder.setOnClickListener(this);

        upload = v.findViewById(R.id.add_fab_bs_upload);
        upload.setOnClickListener(this);

        scan = v.findViewById(R.id.add_fab_bs_scan);
        scan.setOnClickListener(this);

        googleDocs = v.findViewById(R.id.add_fab_bs_googledocs);
        googleDocs.setOnClickListener(this);

        googleSheets = v.findViewById(R.id.add_fab_bs_googlesheets);
        googleSheets.setOnClickListener(this);

        googleSlides = v.findViewById(R.id.add_fab_bs_googleslides);
        googleSlides.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onAddBottomSheetItemPressed(v);
        }
    }
}
