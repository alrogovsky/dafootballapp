package com.sashqua.wowser.models;

/**
 * Created by Ivankov Igor on 12.05.16.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String image;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, String img) {
        this.showNotify = showNotify;
        this.title = title;
        this.image = img;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() { return image; }

    public void setImage(String img) { this.image = img; }
}
