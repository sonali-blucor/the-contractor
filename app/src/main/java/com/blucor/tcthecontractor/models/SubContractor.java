package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubContractor implements Parcelable {
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

    @SerializedName("contractor_id")
    @Expose
    public int contractor_id;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("pan_cart_no")
    @Expose
    public String pan_cart_no;
    @SerializedName("aadhar_cart_no")
    @Expose
    public String aadhar_cart_no;
    @SerializedName("gst_no")
    @Expose
    public String gst_no;
    @SerializedName("bank_details")
    @Expose
    public String bank_details;

    protected SubContractor(Parcel in) {
        id = in.readInt();
        fname = in.readString();
        lname = in.readString();
        mobile = in.readString();
        password = in.readString();
        email = in.readString();
        created_at = in.readString();
    }

    public static final Creator<SubContractor> CREATOR = new Creator<SubContractor>() {
        @Override
        public SubContractor createFromParcel(Parcel in) {
            return new SubContractor(in);
        }

        @Override
        public SubContractor[] newArray(int size) {
            return new SubContractor[size];
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
        dest.writeString(created_at);
    }
}
