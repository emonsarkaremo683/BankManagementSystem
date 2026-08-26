package com.ensark.ensarkbank.ui.dashboard;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

public class QuickAction {
    private final String id;
    private final String title;
    private final @DrawableRes int icon;
    private final @ColorRes int tintColor;
    private final int destinationId;
    private int count = 0;

    public QuickAction(String id, String title, @DrawableRes int icon, @ColorRes int tintColor, int destinationId) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.tintColor = tintColor;
        this.destinationId = destinationId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getIcon() { return icon; }
    public int getTintColor() { return tintColor; }
    public int getDestinationId() { return destinationId; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
