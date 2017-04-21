package com.touchmenotapps.bookdemo.views;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchmenotapps.bookdemo.R;
import com.touchmenotapps.bookdemo.dao.FeePeriod;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by i7 on 20-04-2017.
 */

public class FeePeriodViewHolder extends GroupViewHolder {

    @BindView(R.id.list_item_fee_period) TextView feePeriod;
    @BindView(R.id.list_item_fee_period_arrow) ImageView arrow;

    public FeePeriodViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setFeePeriodTitle(ExpandableGroup genre) {
        if (genre instanceof FeePeriod) {
            feePeriod.setText(genre.getTitle());
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}