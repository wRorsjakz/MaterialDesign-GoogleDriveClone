package com.example.googledriveclone.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.googledriveclone.Helper.RVScrollListenerHelper;
import com.example.googledriveclone.Helper.SwipeRefreshHelper;
import com.example.googledriveclone.R;
import com.example.googledriveclone.RecyclerViewAdapters.FilesRVAdapter;
import com.example.googledriveclone.RecyclerViewModels.FilesRVModel;

import java.util.ArrayList;

public class ComputersFragment extends Fragment implements FilesRVAdapter.filesRVItemClickedListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private FilesRVAdapter adapter;
    private ArrayList<FilesRVModel> items = new ArrayList<>();
    private int size;
    private String layoutConfig; // Store the layoutConfig config of RV in a temp variable

    // Key for sharedPref
    private static final String computers_rv_layout_key = "computersRVLayout";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.files_computers, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.my_computer_rv);
        swipeRefreshLayout = view.findViewById(R.id.my_computer_swipeRefresh);
        fab = getActivity().findViewById(R.id.main_fab);
        setupDummyData();
        setupRV();
        setupSwipeToRefresh();
        // FAB Scroll Listener
        RVScrollListenerHelper.setupFABScroll(fab, recyclerView);
    }

    private void setupRV(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new FilesRVAdapter(getContext(), items, getContext().getString(R.string.grid_layout_key));
        adapter.setFilesRVItemClickedListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        // RV Item at index 0 span across the screen
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return i == 0 ? 2 : 1;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Set up some dummy data for the recyclerview to show
     */
    private void setupDummyData(){
        items.add(new FilesRVModel("NULL", "Blue", "NULL")); // Index 0 is the RV Header
        items.add(new FilesRVModel("Adobe Illustrator Art", "Blue", "15/05/2019"));
    }

    private void setupSwipeToRefresh(){

        //Set color of spinner
        SwipeRefreshHelper.setupSwipeRefreshBehaviour(swipeRefreshLayout, getContext());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                size = items.size();
                items.clear();
                adapter.notifyItemRangeRemoved(1, size);
                setupDummyData(); // Dummy data
                size = items.size();
                adapter.notifyItemRangeInserted(1, size);

                // Delay spinner disappearing
                SwipeRefreshHelper.delayRefresh(swipeRefreshLayout);
            }
        });
    }

    /**
     * Toggle RecyclerView's layout between GridLayout and LinearLayout
     */
    @Override
    public void onLayoutToggle(String layout) {

    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getContext(), "Position : " + position, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onItemMenuClicked(int position) {
        Toast.makeText(getContext(), "Menu Clicked: " + position , Toast.LENGTH_SHORT).show();
    }
}
