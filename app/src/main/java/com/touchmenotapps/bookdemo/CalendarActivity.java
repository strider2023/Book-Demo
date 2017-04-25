package com.touchmenotapps.bookdemo;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.selection_calendar) CalendarView calendarView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout appBarLayout;
    @BindView(R.id.calendar_conatiner) LinearLayout layout;
    @BindView(R.id.calendar_data_conatiner) LinearLayout dataContainer;

    private LayoutTransition appbarTransition, baseContainerTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendarView.setVisibility(View.GONE);

        appbarTransition = new LayoutTransition();
        appbarTransition.enableTransitionType(LayoutTransition.APPEARING | LayoutTransition.DISAPPEARING);

        baseContainerTransition = new LayoutTransition();
        baseContainerTransition.enableTransitionType(LayoutTransition.CHANGING);

        appbarTransition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                if(transitionType == LayoutTransition.APPEARING) {
                    baseContainerTransition.setStartDelay(LayoutTransition.CHANGING, 0l);
                    layout.setLayoutTransition(baseContainerTransition);
                } else if(transitionType == LayoutTransition.DISAPPEARING) {
                    baseContainerTransition.setStartDelay(LayoutTransition.CHANGING, appbarTransition.getDuration(LayoutTransition.DISAPPEARING));
                    layout.setLayoutTransition(baseContainerTransition);
                }
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                if(transitionType == LayoutTransition.APPEARING) {
                    layout.setLayoutTransition(null);
                }
            }
        });

        appBarLayout.setLayoutTransition(appbarTransition);

        dataContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(calendarView.getVisibility() == View.VISIBLE) {
                    calendarView.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fees, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date:
                if(calendarView.getVisibility() == View.VISIBLE) {
                    calendarView.setVisibility(View.GONE);
                } else {
                    calendarView.setVisibility(View.VISIBLE);
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
