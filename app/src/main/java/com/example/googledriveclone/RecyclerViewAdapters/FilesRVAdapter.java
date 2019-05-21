package com.example.googledriveclone.RecyclerViewAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.googledriveclone.R;
import com.example.googledriveclone.RecyclerViewModels.FilesRVModel;

import java.util.ArrayList;

public class FilesRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private ArrayList<FilesRVModel> items;
    private filesRVItemClickedListener listener;
    private String layoutConfig; // RecyclerView layout configuration

    public FilesRVAdapter(Context context, ArrayList<FilesRVModel> items, String layoutConfig) {
        this.context = context;
        this.items = items;
        this.layoutConfig = layoutConfig;
    }

    /**
     *  Interface for RV Item Clicked Listener
     */
    public interface filesRVItemClickedListener{
        void onItemClicked(int position); // RV Item Clicked
        void onLayoutToggle(String layout); // Icon to toggle layout of RV
        void onItemMenuClicked(int position); // RV Item Menu Clicked
    }

    public void setFilesRVItemClickedListener(filesRVItemClickedListener listener){
        this.listener = listener;
    }

    // RV Identifier for item type
    private static final int headerItemType = 0;
    private static final int fileItemType = 1;

    /**
     * ViewHolder of header item (index 0) (The same regardless of grid or linear layout)
     */
    public class FilesRVHeaderVH extends RecyclerView.ViewHolder{

        private TextView headerTitle;
        private ImageButton toogleOrder, toggleLayout;

        public FilesRVHeaderVH(@NonNull View itemView) {
            super(itemView);

            headerTitle = itemView.findViewById(R.id.files_rv_header_title);
            toogleOrder = itemView.findViewById(R.id.files_rv_header_arrow);
            toggleLayout = itemView.findViewById(R.id.files_rv_header_layout);

            if(layoutConfig.equals(context.getString(R.string.grid_layout_key))){
                toggleLayout.setSelected(false);
            } else {
                toggleLayout.setSelected(true);
            }

            toggleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(toggleLayout.isSelected()){
                        v.setSelected(false);
                        listener.onLayoutToggle(context.getString(R.string.grid_layout_key));
                    } else {
                        v.setSelected(true);
                        listener.onLayoutToggle(context.getString(R.string.linear_layout_key));
                    }
                }
            });
        }
    }

    /**
     * RecyclerView ViewHolder for Grid layout for items
     */
    public class FilesRVGridItemVH extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView itemTitle;
        private ImageButton menuToggle;
        private RelativeLayout rvItemContainer;

        public FilesRVGridItemVH(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.files_rv_grid_icon);
            itemTitle = itemView.findViewById(R.id.files_rv_title_grid);
            menuToggle = itemView.findViewById(R.id.files_rv_openmenu_grid);
            rvItemContainer = itemView.findViewById(R.id.files_rv_item_grid_container);

            rvItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getAdapterPosition());
                }
            });

            menuToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemMenuClicked(getAdapterPosition());
                }
            });
        }
    }

    /**
     * RecyclerView ViewHolder for Linear layout for items
     */
    public class FilesRVLinearItemVH extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView itemTitle, modified;
        private ImageButton menuToggle;
        private RelativeLayout rvItemContainer;

        public FilesRVLinearItemVH(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.files_rv_linear_icon);
            itemTitle = itemView.findViewById(R.id.files_rv_linear_title);
            modified = itemView.findViewById(R.id.files_rv_linear_modified);
            menuToggle = itemView.findViewById(R.id.files_rv_openmenu_linear);
            rvItemContainer = itemView.findViewById(R.id.files_rv_item_linear_container);

            rvItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (layoutConfig.equals(context.getString(R.string.grid_layout_key))) {
            switch (i) {
                case headerItemType:
                    view = LayoutInflater.from(context).inflate(R.layout.files_rv_header, viewGroup, false);
                    return new FilesRVHeaderVH(view);
                case fileItemType:
                    view = LayoutInflater.from(context).inflate(R.layout.files_rv_item_grid, viewGroup, false);
                    return new FilesRVGridItemVH(view);

            }
        } else if(layoutConfig.equals(context.getString(R.string.linear_layout_key))) {
            switch (i) {
                case headerItemType:
                    view = LayoutInflater.from(context).inflate(R.layout.files_rv_header, viewGroup, false);
                    return new FilesRVHeaderVH(view);
                case fileItemType:
                    view = LayoutInflater.from(context).inflate(R.layout.files_rv_item_linear, viewGroup, false);
                    return new FilesRVLinearItemVH(view);
            }

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FilesRVModel currentItem = items.get(i);
        // Grid Layout
        if(layoutConfig.equals(context.getString(R.string.grid_layout_key))){
            switch (viewHolder.getItemViewType()){
                case headerItemType:
                    break;
                case fileItemType:
                    FilesRVGridItemVH filesRVItemVH = (FilesRVGridItemVH)viewHolder;
                    filesRVItemVH.itemTitle.setText(currentItem.getTitle());

                    /**
                     * Modify color of folder based on saved data
                     */
                    String color = currentItem.getColor();
                    switch (color){
                        case "Blue":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_blue_24dp);
                            break;
                        case "Yellow":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_yellow_48dp);
                            break;
                        case "Red":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_red_48dp);
                            break;
                        case "Green":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_green_48dp);
                            break;
                        case "Grey":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_grey_48dp);
                            break;
                    }
            }
        } else if(layoutConfig.equals(context.getString(R.string.linear_layout_key))) { // Linear Layout
            switch (viewHolder.getItemViewType()) {
                case headerItemType:
                    break;
                case fileItemType:
                    FilesRVLinearItemVH filesRVItemVH = (FilesRVLinearItemVH) viewHolder;
                    filesRVItemVH.itemTitle.setText(currentItem.getTitle());

                    /**
                     * Modify color of folder based on saved data
                     */
                    String color = currentItem.getColor();
                    switch (color) {
                        case "Blue":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_blue_24dp);
                            break;
                        case "Yellow":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_yellow_48dp);
                            break;
                        case "Red":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_red_48dp);
                            break;
                        case "Green":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_green_48dp);
                            break;
                        case "Grey":
                            filesRVItemVH.icon.setImageResource(R.drawable.ic_folder_grey_48dp);
                            break;
                    }
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return headerItemType;
        } else {
            return fileItemType;
        }
    }

}


