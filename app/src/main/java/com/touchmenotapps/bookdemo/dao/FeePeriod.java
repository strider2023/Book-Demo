package com.touchmenotapps.bookdemo.dao;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by i7 on 20-04-2017.
 */

public class FeePeriod extends ExpandableGroup<FeeDetail> {


    public FeePeriod(String title, List<FeeDetail> items) {
        super(title, items);
    }

    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (this == o) isEqual = true;
        else if (!(o instanceof FeePeriod)) isEqual = false;
        return isEqual;
    }
}
