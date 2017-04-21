package com.touchmenotapps.bookdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.touchmenotapps.bookdemo.dao.PagerAdapterDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i7 on 20-04-2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List<PagerAdapterDAO> sectionsPagerAdapterList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm, List<PagerAdapterDAO> data) {
        super(fm);
        this.sectionsPagerAdapterList = data;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return sectionsPagerAdapterList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return sectionsPagerAdapterList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sectionsPagerAdapterList.get(position).getName();
    }
}
