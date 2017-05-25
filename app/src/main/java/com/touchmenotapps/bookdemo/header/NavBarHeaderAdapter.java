package com.touchmenotapps.bookdemo.header;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

    private static final long ANIMATION_TIME = 1000l;

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
    ImageView selectedChild;
    @BindView(R.id.child_list)
    ListView childList;

    private Context context;
    private View headerView;
    private OnHeaderItemSelected onHeaderItemSelected;
    private LayoutInflater layoutInflater;
    private ProfileBaseAdapter profileBaseAdapter;

    private List<ProfileDAO> profileDAOs = new ArrayList<>();

    public interface OnHeaderItemSelected {
        void onHeaderItemSelected (Object object);
    }

    public NavBarHeaderAdapter(final NavigationView navigationView, final DrawerLayout drawerLayout) {
        this.context = navigationView.getContext();
        this.layoutInflater = LayoutInflater.from(context);
        this.profileBaseAdapter = new ProfileBaseAdapter(context);
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
        this.headerView = navigationView.getHeaderView(0);
        ButterKnife.bind(this, headerView);

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
                    onHeaderItemSelected.onHeaderItemSelected(parent.getAdapter().getItem(position));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                hideHeaderMenu();
            }
        });
    }

    private void showHeaderMenu() {
        RotateAnimation rotate = new RotateAnimation(0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setFillAfter(true);
        rotate.setDuration(300l);
        chooseKidToggleIcon.startAnimation(rotate);
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                navigationView.getHeight() - headerBar.getHeight()));
        childList.setVisibility(View.VISIBLE);
        hideNavMenuItems();
    }

    private void hideHeaderMenu() {
        RotateAnimation rotate = new RotateAnimation(180f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setFillAfter(true);
        rotate.setDuration(300l);
        chooseKidToggleIcon.startAnimation(rotate);
        childList.setVisibility(View.GONE);
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                headerBar.getHeight()));
        showNavMenuItems();
    }

    private void onProfileSelectedListener(View selectedView) {
        int[] locationFrom = new int[2];
        int[] locationTo = new int[2];
        selectedView.getLocationOnScreen(locationFrom);
        selectedChild.getLocationOnScreen(locationTo);

        AnimationSet animationSet = new AnimationSet(true);

        //TODO fine tune
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, -((locationFrom[0]-locationTo[0])/1.5f),
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, locationTo[1]/4f);
        translateAnimation.setDuration(ANIMATION_TIME);
        animationSet.addAnimation(translateAnimation);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f);
        scaleAnimation.setDuration(ANIMATION_TIME);
        animationSet.addAnimation(scaleAnimation);

        selectedView.startAnimation(animationSet);
        selectedChild.setVisibility(View.GONE);

        selectedView.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                selectedChild.setVisibility(View.VISIBLE);
                drawerLayout.closeDrawer(GravityCompat.START);
                if(onHeaderItemSelected != null) {
                    onHeaderItemSelected.onHeaderItemSelected(null);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void addChild(View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) (36 * context.getResources().getDisplayMetrics().density),
                (int) (36 * context.getResources().getDisplayMetrics().density));
        params.setMargins((int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density));
        childListContainer.addView(view, params);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileSelectedListener(v);
            }
        });
    }

    public void addChild(ProfileDAO profileDAO) {
        profileDAOs.add(profileDAO);
        View view = layoutInflater.inflate(R.layout.user_profile_view, null, false);
        ImageView userImage = (ImageView) view.findViewById(R.id.user_profile_image);
        TextView userName = (TextView) view.findViewById(R.id.user_profile_text);
        if(profileDAO.getImage() != null) {
            userImage.setVisibility(View.VISIBLE);
            userName.setVisibility(View.GONE);
            userImage.setImageBitmap(profileDAO.getImage());
        } else {
            userName.setText(profileDAO.getShortName());
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) (36 * context.getResources().getDisplayMetrics().density),
                (int) (36 * context.getResources().getDisplayMetrics().density));
        params.setMargins((int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density),
                (int) (5 * context.getResources().getDisplayMetrics().density));
        childListContainer.addView(view, params);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileSelectedListener(v);
            }
        });
        profileBaseAdapter.addProfile(profileDAO);
    }

    public void addChild(List<ProfileDAO> profileDAOs) {
        this.profileDAOs = profileDAOs;
        for(ProfileDAO profileDAO : profileDAOs) {
            View view = layoutInflater.inflate(R.layout.user_profile_view, null, false);
            ImageView userImage = (ImageView) view.findViewById(R.id.user_profile_image);
            TextView userName = (TextView) view.findViewById(R.id.user_profile_text);
            if (profileDAO.getImage() != null) {
                userImage.setVisibility(View.VISIBLE);
                userName.setVisibility(View.GONE);
                userImage.setImageBitmap(profileDAO.getImage());
            } else {
                userName.setText(profileDAO.getShortName());
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (36 * context.getResources().getDisplayMetrics().density),
                    (int) (36 * context.getResources().getDisplayMetrics().density));
            params.setMargins((int) (5 * context.getResources().getDisplayMetrics().density),
                    (int) (5 * context.getResources().getDisplayMetrics().density),
                    (int) (5 * context.getResources().getDisplayMetrics().density),
                    (int) (5 * context.getResources().getDisplayMetrics().density));
            childListContainer.addView(view, params);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProfileSelectedListener(v);
                }
            });
        }
        profileBaseAdapter.addProfiles(profileDAOs);
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
