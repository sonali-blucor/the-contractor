package com.blucor.thecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client implements Parcelable {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("fname")
    @Expose
    public String fname;

    @SerializedName("lname")
    @Expose
    public String lname;

    @SerializedName("mobile")
    @Expose
    public String mobile;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("profile_pic")
    @Expose
    public String profile_pic;

    @SerializedName("client_id")
    @Expose
    public String client_id;

    @SerializedName("contractor_id")
    @Expose
    public int contractor_id;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    protected Client(Parcel in) {
        id = in.readInt();
        fname = in.readString();
        lname = in.readString();
        mobile = in.readString();
        password = in.readString();
        email = in.readString();
        profile_pic = in.readString();
        created_at = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(mobile);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(profile_pic);
        dest.writeString(created_at);
    }
}
