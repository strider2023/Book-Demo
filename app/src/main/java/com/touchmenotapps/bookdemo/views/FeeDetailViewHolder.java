package com.touchmenotapps.bookdemo.views;

import android.view.View;
import android.widget.TextView;

import com.touchmenotapps.bookdemo.R;
import com.touchmenotapps.bookdemo.dao.FeeDetail;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i7 on 20-04-2017.
 */

public class FeeDetailViewHolder extends ChildViewHolder {

    @BindView(R.id.list_item_fee_detail_dummy) TextView childTextView;
    @BindView(R.id.list_item_fee_detail_date) TextView dateTextView;
    @BindView(R.id.list_item_fee_detail_particular) TextView descriptionTextView;
    @BindView(R.id.list_item_fee_detail_amount) TextView amountTextView;

    public FeeDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDetails(FeeDetail feeDetail) {
        childTextView.setText(feeDetail.getName());
        dateTextView.setText(feeDetail.getDate());
        descriptionTextView.setText(feeDetail.getDescription());
        amountTextView.setText(String.valueOf(feeDetail.getAmount()));
    }
}
