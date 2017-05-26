package com.touchmenotapps.bookdemo.header;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchmenotapps.bookdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i7 on 24-05-2017.
 */

public class ProfileDAO {

    private String name;
    private Bitmap image;
    private Context context;
    private LayoutInflater layoutInflater;
    private View baseView;
    @BindView(R.id.user_profile_image)
    ImageView userImage;
    @BindView(R.id.user_profile_text)
    TextView userName;

    public ProfileDAO(Context context, String name) {
        this.name = name;
        this.context = context;
        init();
    }

    public ProfileDAO(Context context, String name, Bitmap image) {
        this.context = context;
        this.name = name;
        this.image = image;
        init();
    }

    private void init() {
        this.layoutInflater = LayoutInflater.from(context);
        baseView = layoutInflater.inflate(R.layout.user_profile_view, null, false);
        ButterKnife.bind(this, baseView);

        if(getImage() != null) {
            userImage.setVisibility(View.VISIBLE);
            userName.setVisibility(View.GONE);
            userImage.setImageBitmap(getImage());
        } else {
            userName.setText(getShortName());
        }
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        String shortName = "";
        String[] nameSplit = name.split("\\s+");
        if(nameSplit.length > 1) {
            shortName += nameSplit[0].substring(0,1) + nameSplit[nameSplit.length - 1].substring(0,1);
        } else {
            shortName += nameSplit[0].substring(0,0);
        }
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public View getBaseView() {
        return baseView;
    }
}
