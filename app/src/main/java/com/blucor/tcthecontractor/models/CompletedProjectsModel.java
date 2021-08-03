package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletedProjectsModel implements Parcelable {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("project_name")
    @Expose
    public String project_name;

    @SerializedName("project_type")
    @Expose
    public String project_type;

    @SerializedName("contract_type")
    @Expose
    public String contract_type;

    @SerializedName("client_id")
    @Expose
    public String client_id;

    @SerializedName("project_location")
    @Expose
    public String project_location;

    @SerializedName("start_date")
    @Expose
    public String start_date;

    @SerializedName("end_date")
    @Expose
    public String end_date;

    @SerializedName("duration")
    @Expose
    public String duration;

    @SerializedName("contractor_id")
    @Expose
    public int contractor_id;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("updated_at")
    @Expose
    public String updated_at;

    @SerializedName("fname")
    @Expose
    public String fname;

    @SerializedName("lname")
    @Expose
    public String lname;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("mobile")
    @Expose
    public String mobile;


    protected CompletedProjectsModel(Parcel in) {
        id = in.readInt();
        project_name = in.readString();
        project_type = in.readString();
        contract_type = in.readString();
        client_id = in.readString();
        project_location = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        duration = in.readString();
        contractor_id = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        fname = in.readString();
        lname = in.readString();
        email = in.readString();
        mobile = in.readString();
    }

    public static final Parcelable.Creator<ProjectsModel> CREATOR = new Parcelable.Creator<ProjectsModel>() {
        @Override
        public ProjectsModel createFromParcel(Parcel in) {
            return new ProjectsModel(in);
        }

        @Override
        public ProjectsModel[] newArray(int size) {
            return new ProjectsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(project_name);
        dest.writeString(project_type);
        dest.writeString(contract_type);
        dest.writeString(client_id);
        dest.writeString(project_location);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(duration);
        dest.writeInt(contractor_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(email);
        dest.writeString(mobile);
    }
}
