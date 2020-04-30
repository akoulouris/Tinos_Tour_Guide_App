package com.example.sailinn.model;

public class MainMenu {

    private String title;
    private Integer image;

    public MainMenu(String title,Integer image){
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = image;
    }
}
