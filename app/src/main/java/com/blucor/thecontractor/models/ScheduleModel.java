package com.blucor.thecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleModel implements Parcelable {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("project_id")
    @Expose
    public int project_id;

    @SerializedName("project_name")
    @Expose
    public String project_name;

    @SerializedName("project_status")
    @Expose
    public int project_status;

    @SerializedName("week_days")
    @Expose
    public String week_days;

    @SerializedName("no_of_days")
    @Expose
    public int no_of_days;

    @SerializedName("rating")
    @Expose
    public float rating;

    protected ScheduleModel(Parcel in) {
        id = in.readInt();
        project_id = in.readInt();
        project_name = in.readString();
        project_status = in.readInt();
        week_days = in.readString();
        no_of_days = in.readInt();
        rating = in.readFloat();
    }

    public static final Creator<ScheduleModel> CREATOR = new Creator<ScheduleModel>() {
        @Override
        public ScheduleModel createFromParcel(Parcel in) {
            return new ScheduleModel(in);
        }

        @Override
        public ScheduleModel[] newArray(int size) {
            return new ScheduleModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getProject_status() {
        return project_status;
    }

    public void setProject_status(int project_status) {
        this.project_status = project_status;
    }

    public String getWeek_days() {
        return week_days;
    }

    public void setWeek_days(String week_days) {
        this.week_days = week_days;
    }

    public int getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(int no_of_days) {
        this.no_of_days = no_of_days;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public static Creator<ScheduleModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(project_id);
        dest.writeString(project_name);
        dest.writeInt(project_status);
        dest.writeString(week_days);
        dest.writeInt(no_of_days);
        dest.writeFloat(rating);
    }
}
