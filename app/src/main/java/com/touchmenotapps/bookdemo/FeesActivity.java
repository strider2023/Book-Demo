package com.touchmenotapps.bookdemo;

import android.support.design.widget.TabLayout;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.touchmenotapps.bookdemo.adapter.SectionsPagerAdapter;
import com.touchmenotapps.bookdemo.dao.PagerAdapterDAO;
import com.touchmenotapps.bookdemo.fragment.FeeDuesFragment;
import com.touchmenotapps.bookdemo.fragment.FeePaidFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeesActivity extends AppCompatActivity {

    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fee_due_information_container) LinearLayout feeHeaderContainer;
    @BindView(R.id.menu_fee_payment_options) FloatingActionMenu paymentOptions;
    @BindView(R.id.main_content) ViewGroup transitionsContainer;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<PagerAdapterDAO> pagerAdapterDAOList = new ArrayList<>();
    private Transition transition;
    private boolean visible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pagerAdapterDAOList.add(new PagerAdapterDAO("Dues", FeeDuesFragment.newInstance()));
        pagerAdapterDAOList.add(new PagerAdapterDAO("Paid", FeePaidFragment.newInstance()));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), pagerAdapterDAOList);

        transition = new TransitionSet()
                .addTransition(new Fade())
                .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                        new FastOutLinearInInterpolator());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    //feeHeaderContainer.setVisibility(View.VISIBLE);
                    visible = true;
                } else {
                    //feeHeaderContainer.setVisibility(View.GONE);
                    visible = false;
                }
                TransitionManager.beginDelayedTransition(transitionsContainer , transition);
                paymentOptions.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                feeHeaderContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
