package com.example.googledriveclone.RecyclerViewModels;

public class FilesRVModel {
    private String title, color, modified;

    public FilesRVModel(String title, String color, String modified) {
        this.title = title;
        this.color = color;
        this.modified = modified;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public String getModified() {
        return modified;
    }
}
