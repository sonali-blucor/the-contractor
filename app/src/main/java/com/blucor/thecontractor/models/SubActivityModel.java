package com.blucor.thecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubActivityModel implements Parcelable {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("project_id")
    @Expose
    public int project_id;

    @SerializedName("start_date")
    @Expose
    public String start_date;

    @SerializedName("end_date")
    @Expose
    public String end_date;

    @SerializedName("sub_contractor_id")
    @Expose
    public int sub_contractor_id;

    @SerializedName("duration")
    @Expose
    public int duration;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("updated_at")
    @Expose
    public String updated_at;

    protected SubActivityModel(Parcel in) {
        id = in.readInt();
        project_id = in.readInt();
        start_date = in.readString();
        end_date = in.readString();
        sub_contractor_id = in.readInt();
        duration = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<SubActivityModel> CREATOR = new Creator<SubActivityModel>() {
        @Override
        public SubActivityModel createFromParcel(Parcel in) {
            return new SubActivityModel(in);
        }

        @Override
        public SubActivityModel[] newArray(int size) {
            return new SubActivityModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(project_id);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeInt(sub_contractor_id);
        dest.writeInt(duration);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
