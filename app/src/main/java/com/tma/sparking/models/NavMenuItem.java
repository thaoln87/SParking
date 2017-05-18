package com.tma.sparking.models;

/**
 * Created by lnthao on 5/17/2017.
 */

public class NavMenuItem {
    private int iconResId;
    private String title;

    public NavMenuItem(int iconResId, String title){
        setIconResId(iconResId);
        setTitle(title);
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
