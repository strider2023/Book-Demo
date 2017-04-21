package com.touchmenotapps.bookdemo.dao;

import android.support.v4.app.Fragment;

/**
 * Created by i7 on 21-04-2017.
 */

public class PagerAdapterDAO {

    private String name;
    private Fragment fragment;

    public PagerAdapterDAO(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
