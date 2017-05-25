package com.touchmenotapps.bookdemo.header;

import android.graphics.Bitmap;

/**
 * Created by i7 on 24-05-2017.
 */

public class ProfileDAO {

    private String name;
    private Bitmap image;

    public ProfileDAO(String name) {
        this.name = name;
    }

    public ProfileDAO(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        String shortName = "";
        String[] nameSplit = name.split("\\s+");
        if(nameSplit.length > 1) {
            shortName += nameSplit[0].substring(0) + nameSplit[nameSplit.length - 1].substring(0);
        } else {
            shortName += nameSplit[0].substring(0);
        }
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
