package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientProjectActivityModel implements Parcelable {

    @SerializedName("id")
    @Expose
    public int id; //": 5,

    @SerializedName("project_name")
    @Expose
    public String project_name; //": "bdndndndn",

    @SerializedName("project_type")
    @Expose
    public String project_type; //": "Building Apartment",

    @SerializedName("contract_type")
    @Expose
    public String contract_type; //": "Contract type 1",

    @SerializedName("client_id")
    @Expose
    public String client_id; //": 6,

    @SerializedName("project_location")
    @Expose
    public String project_location; //": "hxhdjd,hshsdh,hshsjd,676595,bzhdjdxd,bsjddjk",

    @SerializedName("start_date")
    @Expose
    public String start_date; //": "2021-06-02",

    @SerializedName("end_date")
    @Expose
    public String end_date; //": "2021-06-25",

    @SerializedName("actual_start_date")
    @Expose
    public String actual_start_date; //": "0000-00-00",

    @SerializedName("actual_end_date")
    @Expose
    public String actual_end_date; //": "0000-00-00",

    @SerializedName("duration")
    @Expose
    public String duration; //": "12 Days",

    @SerializedName("contractor_id")
    @Expose
    public int contractor_id; //": 8,

    @SerializedName("project_status")
    @Expose
    public String project_status; //": 0,

    @SerializedName("created_at")
    @Expose
    public String created_at; //": "2021-06-03 13:01:42",

    @SerializedName("updated_at")
    @Expose
    public String updated_at; //": "2021-06-03 13:01:42",

    @SerializedName("fname")
    @Expose
    public String fname; //": "Swapna",

    @SerializedName("lname")
    @Expose
    public String lname; //": "Thakur",

    @SerializedName("email")
    @Expose
    public String email; //": "sappi.cd@gmail.com",

    @SerializedName("mobile")
    @Expose
    public String mobile; //": "9503037943"

    public int view_type = 1;

    public boolean is_material = false;

    public ClientProjectActivityModel() {
    }

    protected ClientProjectActivityModel(Parcel in) {
        id = in.readInt();
        project_name = in.readString();
        project_type = in.readString();
        contract_type = in.readString();
        client_id = in.readString();
        project_location = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        actual_start_date = in.readString();
        actual_end_date = in.readString();
        duration = in.readString();
        contractor_id = in.readInt();
        project_status = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        fname = in.readString();
        lname = in.readString();
        email = in.readString();
        mobile = in.readString();
        view_type = in.readInt();
        is_material = in.readByte() != 0;
    }

    public static final Creator<ClientProjectActivityModel> CREATOR = new Creator<ClientProjectActivityModel>() {
        @Override
        public ClientProjectActivityModel createFromParcel(Parcel in) {
            return new ClientProjectActivityModel(in);
        }

        @Override
        public ClientProjectActivityModel[] newArray(int size) {
            return new ClientProjectActivityModel[size];
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
        dest.writeString(actual_start_date);
        dest.writeString(actual_end_date);
        dest.writeString(duration);
        dest.writeInt(contractor_id);
        dest.writeString(project_status);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(email);
        dest.writeString(mobile);
        dest.writeInt(view_type);
        dest.writeByte((byte) (is_material ? 1 : 0));
    }
}
