package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectsModel implements Parcelable {

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

    @SerializedName("project_status")
    @Expose
    public int project_status;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("updated_at")
    @Expose
    public String updated_at;

    @SerializedName("fname")
    @Expose
    public String client_fname;

    @SerializedName("lname")
    @Expose
    public String client_lname;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("main_activity_name")
    @Expose
    public String main_activity_name;

    @SerializedName("main_activity_id")
    @Expose
    public int main_activity_id;

    @SerializedName("mobile")
    @Expose
    public String mobile;

    public int view_type = 1;

    public boolean is_material = false;

    //public String client_name = ""+client_fname+" "+client_lname;


    protected ProjectsModel(Parcel in) {
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
        client_fname = in.readString();
        client_lname = in.readString();
        email = in.readString();
        mobile = in.readString();
    }

    public static final Creator<ProjectsModel> CREATOR = new Creator<ProjectsModel>() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getProject_location() {
        return project_location;
    }

    public void setProject_location(String project_location) {
        this.project_location = project_location;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getContractor_id() {
        return contractor_id;
    }

    public void setContractor_id(int contractor_id) {
        this.contractor_id = contractor_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getClient_fname() {
        return client_fname;
    }

    public void setClient_fname(String client_fname) {
        this.client_fname = client_fname;
    }

    public String getClient_lname() {
        return client_lname;
    }

    public void setClient_lname(String client_lname) {
        this.client_lname = client_lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMain_activity_name() {
        return main_activity_name;
    }

    public void setMain_activity_name(String main_activity_name) {
        this.main_activity_name = main_activity_name;
    }

    public int getMain_activity_id() {
        return main_activity_id;
    }

    public void setMain_activity_id(int main_activity_id) {
        this.main_activity_id = main_activity_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getView_type() {
        if (project_status == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public boolean isIs_material() {
        return is_material;
    }

    public void setIs_material(boolean is_material) {
        this.is_material = is_material;
    }

    public static Creator<ProjectsModel> getCREATOR() {
        return CREATOR;
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
        dest.writeString(client_fname);
        dest.writeString(client_lname);
        dest.writeString(email);
        dest.writeString(mobile);
    }
}
