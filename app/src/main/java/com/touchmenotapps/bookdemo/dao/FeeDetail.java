package com.touchmenotapps.bookdemo.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by i7 on 20-04-2017.
 */

public class FeeDetail implements Parcelable {

    private String name;
    private String date;
    private String description;
    private int amount;
    private boolean isFavorite;

    public FeeDetail(String name, String date, String description, int amount, boolean isFavorite) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.isFavorite = isFavorite;
        this.amount = amount;
    }

    protected FeeDetail(Parcel in) {
        name = in.readString();
        date = in.readString();
        description = in.readString();
        amount = in.readInt();
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeeDetail)) return false;
        FeeDetail feeDetail = (FeeDetail) o;
        if (isFavorite() != feeDetail.isFavorite()) return false;
        return getName() != null ? getName().equals(feeDetail.getName()) : feeDetail.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (isFavorite() ? 1 : 0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeInt(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeeDetail> CREATOR = new Creator<FeeDetail>() {
        @Override
        public FeeDetail createFromParcel(Parcel in) {
            return new FeeDetail(in);
        }

        @Override
        public FeeDetail[] newArray(int size) {
            return new FeeDetail[size];
        }
    };
}
