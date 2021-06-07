package com.blucor.thecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Activity implements Parcelable {

    @SerializedName("id")
    @Expose
    public int activity_id;

    @SerializedName("activity_name")
    @Expose
    public String activity_name;

    @SerializedName("start_date")
    @Expose
    public String start_date;

    @SerializedName("end_date")
    @Expose
    public String end_date;

    @SerializedName("project_id")
    @Expose
    public String project_id;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("updated_at")
    @Expose
    public String updated_at;

    protected Activity(Parcel in) {
        activity_id = in.readInt();
        activity_name = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        project_id = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel in) {
            return new Activity(in);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(activity_id);
        dest.writeString(activity_name);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(project_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
