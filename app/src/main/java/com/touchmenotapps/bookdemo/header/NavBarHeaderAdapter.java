package com.touchmenotapps.bookdemo.header;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.touchmenotapps.bookdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i7 on 22-05-2017.
 */

public class NavBarHeaderAdapter {

    private static final long ANIMATION_TIME = 700l;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @BindView(R.id.child_list_container)
    LinearLayout childListContainer;
    @BindView(R.id.list_toggle)
    LinearLayout childToggle;
    @BindView(R.id.container_layout)
    LinearLayout container;
    @BindView(R.id.header_bar)
    RelativeLayout headerBar;
    @BindView(R.id.choose_kid_toggle_icon)
    ImageView chooseKidToggleIcon;
    @BindView(R.id.selected_child)
    LinearLayout selectedChild;
    @BindView(R.id.child_list)
    ListView childList;
    @BindView(R.id.selected_child_name)
    TextView childName;

    private Context context;
    private View headerView;
    private OnHeaderItemSelected onHeaderItemSelected;
    private ProfileBaseAdapter profileBaseAdapter;

    private List<ProfileDAO> profileDAOs = new ArrayList<>();
    private ProfileDAO defaultProfile;

    private LinearLayout.LayoutParams childParams, selectedChildParams;

    public NavBarHeaderAdapter(final NavigationView navigationView, final DrawerLayout drawerLayout) {
        this.context = navigationView.getContext();
        this.profileBaseAdapter = new ProfileBaseAdapter(context);
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
        this.headerView = navigationView.getHeaderView(0);
        ButterKnife.bind(this, headerView);

        childParams = new LinearLayout.LayoutParams(
                (int) (36 * context.getResources().getDisplayMetrics().density),
                (int) (36 * context.getResources().getDisplayMetrics().density));
        childParams.setMargins((int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density));

        selectedChildParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        selectedChildParams.setMargins((int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density));

        childToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childList.getVisibility() == View.GONE){
                    showHeaderMenu();
                } else {
                    hideHeaderMenu();
                }
            }
        });

        childList.setAdapter(profileBaseAdapter);
        childList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(onHeaderItemSelected != null) {
                    onHeaderItemSelected.onHeaderItemSelected(profileBaseAdapter.getItem(position));
                }
                onProfileSelectedListener(profileBaseAdapter.getItem(position));
            }
        });
    }

    private void showHeaderMenu() {
        chooseKidToggleIcon.animate().rotation(180f).setDuration(300l).start();
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                navigationView.getHeight() - headerBar.getHeight()));
        childList.setVisibility(View.VISIBLE);
        hideNavMenuItems();
    }

    private void hideHeaderMenu() {
        chooseKidToggleIcon.animate().rotation(0f).setDuration(300l).start();
        childList.setVisibility(View.GONE);
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                headerBar.getHeight()));
        showNavMenuItems();
    }

    private void onProfileSelectedListener(final ProfileDAO selectedView) {
        if(!selectedView.equals(defaultProfile)) {
            int[] locationFrom = new int[2];
            int[] locationTo = new int[2];
            selectedView.getBaseView().getLocationOnScreen(locationFrom);
            selectedChild.getLocationOnScreen(locationTo);

            AnimationSet animationSet = new AnimationSet(true);

            //TODO fine tune
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, -((locationFrom[0] - locationTo[0]) / 1.5f),
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, locationTo[1] / 4f);
            translateAnimation.setDuration(ANIMATION_TIME);
            animationSet.addAnimation(translateAnimation);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f);
            scaleAnimation.setDuration(ANIMATION_TIME);
            animationSet.addAnimation(scaleAnimation);

            selectedView.getBaseView().startAnimation(animationSet);
            selectedChild.setVisibility(View.GONE);

            selectedView.getBaseView().getAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //profileDAOs.remove(selectedView);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    View oldChid = selectedChild.getChildAt(0);
                    selectedChild.removeAllViews();
                    childListContainer.removeView(selectedView.getBaseView());
                    childListContainer.addView(oldChid, childParams);

                    profileBaseAdapter.swapProfiles(selectedView, defaultProfile);
                    defaultProfile = selectedView;
                    selectedChild.addView(selectedView.getBaseView(), selectedChildParams);
                    childName.setText(defaultProfile.getName());

                    selectedChild.setVisibility(View.VISIBLE);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    if (onHeaderItemSelected != null) {
                        onHeaderItemSelected.onHeaderItemSelected(null);
                    }
                    hideHeaderMenu();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public void addChild(final ProfileDAO profileDAO) {
        profileDAOs.add(profileDAO);
        if(profileDAOs.size() > 1) {
            childListContainer.addView(profileDAO.getBaseView(), childParams);
            profileBaseAdapter.addProfile(profileDAO);
        } else {
            defaultProfile = profileDAO;
            childName.setText(defaultProfile.getName());
            selectedChild.addView(profileDAO.getBaseView(), selectedChildParams);
        }
        profileDAO.getBaseView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileSelectedListener(profileDAO);
            }
        });
    }

    public void addChild(List<ProfileDAO> profileDAOs) {
        for(ProfileDAO profileDAO : profileDAOs) {
            addChild(profileDAO);
        }
    }

    private void hideNavMenuItems() {
        for(int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setVisible(false);
        }
    }

    private void showNavMenuItems() {
        for(int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setVisible(true);
        }
    }

    public void setOnHeaderItemSelected(OnHeaderItemSelected onHeaderItemSelected) {
        this.onHeaderItemSelected = onHeaderItemSelected;
    }

    public void setChildListCustomAdapter(BaseAdapter adapter) {
        childList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
