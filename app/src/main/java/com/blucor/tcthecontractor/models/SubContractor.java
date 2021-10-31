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

    @SerializedName("firm_name")
    @Expose
    public String firm_name;

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

//    @SerializedName("contractor_id")
//    @Expose
    public int contractor_id;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("pan_no")
    @Expose
    public String pan_cart_no;
    @SerializedName("aadhar_no")
    @Expose
    public String aadhar_cart_no;
    @SerializedName("gst_no")
    @Expose
    public String gst_no;
    @SerializedName("bank_details")
    @Expose
    public String bank_details;

    public SubContractor(int id, String fname, String firm_name, String mobile, String email, int contractor_id, String created_at, String address, String pan_cart_no, String aadhar_cart_no, String gst_no, String bank_details) {
        this.id = id;
        this.fname = fname;
        this.firm_name = firm_name;
        this.mobile = mobile;
        this.email = email;
        this.contractor_id = contractor_id;
        this.created_at = created_at;
        this.address = address;
        this.pan_cart_no = pan_cart_no;
        this.aadhar_cart_no = aadhar_cart_no;
        this.gst_no = gst_no;
        this.bank_details = bank_details;
    }

    protected SubContractor(Parcel in) {
        id = in.readInt();
        fname = in.readString();
        firm_name = in.readString();
        lname = in.readString();
        mobile = in.readString();
        email = in.readString();
        contractor_id = in.readInt();
        created_at = in.readString();
        address = in.readString();
        pan_cart_no = in.readString();
        aadhar_cart_no = in.readString();
        gst_no = in.readString();
        bank_details = in.readString();
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
        dest.writeString(firm_name);
        dest.writeString(lname);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeInt(contractor_id);
        dest.writeString(created_at);
        dest.writeString(address);
        dest.writeString(pan_cart_no);
        dest.writeString(aadhar_cart_no);
        dest.writeString(gst_no);
        dest.writeString(bank_details);
    }
}
