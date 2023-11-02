package com.example.test2;

import android.os.Parcel;
import android.os.Parcelable;

public class MarkerInfo implements Parcelable {
    private String itemName;
    private double latitude;
    private double longitude;

    public MarkerInfo(String itemName, double latitude, double longitude) {
        this.itemName = itemName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getItemName() {
        return itemName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    protected MarkerInfo(Parcel in) {
        itemName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<MarkerInfo> CREATOR = new Creator<MarkerInfo>() {
        @Override
        public MarkerInfo createFromParcel(Parcel in) {
            return new MarkerInfo(in);
        }

        @Override
        public MarkerInfo[] newArray(int size) {
            return new MarkerInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}

