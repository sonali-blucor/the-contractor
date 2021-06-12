package com.blucor.thecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActivityResponseModel implements Parcelable {

    @SerializedName("activity")
    @Expose
    public Activity activity;

    @SerializedName("sub_activities")
    @Expose
    public ArrayList<SubActivityModel> subActivities;

    protected ActivityResponseModel(Parcel in) {
        activity = in.readParcelable(Activity.class.getClassLoader());
        subActivities = in.createTypedArrayList(SubActivityModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(activity, flags);
        dest.writeTypedList(subActivities);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActivityResponseModel> CREATOR = new Creator<ActivityResponseModel>() {
        @Override
        public ActivityResponseModel createFromParcel(Parcel in) {
            return new ActivityResponseModel(in);
        }

        @Override
        public ActivityResponseModel[] newArray(int size) {
            return new ActivityResponseModel[size];
        }
    };
}
