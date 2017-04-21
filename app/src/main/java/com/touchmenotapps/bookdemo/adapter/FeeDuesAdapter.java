package com.touchmenotapps.bookdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touchmenotapps.bookdemo.R;
import com.touchmenotapps.bookdemo.dao.FeeDetail;
import com.touchmenotapps.bookdemo.dao.FeePeriod;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.touchmenotapps.bookdemo.views.FeeDetailViewHolder;
import com.touchmenotapps.bookdemo.views.FeePeriodViewHolder;

import java.util.List;

/**
 * Created by i7 on 20-04-2017.
 */

public class FeeDuesAdapter extends ExpandableRecyclerViewAdapter<FeePeriodViewHolder, FeeDetailViewHolder> {

    public FeeDuesAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public FeePeriodViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_fee_period, parent, false);
        return new FeePeriodViewHolder(view);
    }

    @Override
    public FeeDetailViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_fee_detail, parent, false);
        return new FeeDetailViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(FeeDetailViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {
        final FeeDetail feeDetail = ((FeePeriod) group).getItems().get(childIndex);
        holder.setDetails(feeDetail);
    }

    @Override
    public void onBindGroupViewHolder(FeePeriodViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setFeePeriodTitle(group);
    }
}