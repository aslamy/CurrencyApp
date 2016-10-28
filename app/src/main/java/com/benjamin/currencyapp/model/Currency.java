package com.benjamin.currencyapp.model;

/**
 * Created by Benjamin on 2015-11-21.
 */
public class Currency {

    String title;
    int description;
    int image;

    public Currency() {

    }

    public Currency(String title, int description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
