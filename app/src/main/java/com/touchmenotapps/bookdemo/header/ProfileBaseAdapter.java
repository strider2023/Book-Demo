package com.touchmenotapps.bookdemo.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchmenotapps.bookdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i7 on 25-05-2017.
 */

public class ProfileBaseAdapter extends BaseAdapter {

    private List<ProfileDAO> profileDAOs = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ProfileBaseAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void addProfile(ProfileDAO profileDAO) {
        profileDAOs.add(profileDAO);
        notifyDataSetChanged();
    }

    public void swapProfiles(ProfileDAO from, ProfileDAO to) {
        profileDAOs.remove(from);
        profileDAOs.add(to);
        notifyDataSetChanged();
    }

    public void addProfiles(List<ProfileDAO> profileDAOs) {
        this.profileDAOs = profileDAOs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return profileDAOs.size();
    }

    @Override
    public ProfileDAO getItem(int position) {
        return profileDAOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = layoutInflater.inflate(R.layout.user_profile_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.icon.setVisibility(View.VISIBLE);

        if(profileDAOs.get(position).getImage() != null) {
            holder.icon.setImageBitmap(profileDAOs.get(position).getImage());
        } else {
            holder.title.setText(profileDAOs.get(position).getName());
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.user_profile_list_image)
        ImageView icon;
        @BindView(R.id.user_profile_list_text)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
