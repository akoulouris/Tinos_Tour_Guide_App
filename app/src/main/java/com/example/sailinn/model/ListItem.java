package com.example.sailinn.model;

public class ListItem {

    private int imageResourse;
    private String title;
    private String key_id;
    private String favStatus;
    private String category;


    public ListItem() {
    }

    public ListItem(int imageResourse, String title, String key_id, String favStatus,String category) {
        this.imageResourse = imageResourse;
        this.title = title;
        this.key_id = key_id;
        this.favStatus = favStatus;
        this.category = category;
    }
    public int getImageResourse() {
        return imageResourse;
    }

    public void setImageResourse(int imageResourse) {
        this.imageResourse = imageResourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String favStatus) {
        this.category = category;
    }


}
