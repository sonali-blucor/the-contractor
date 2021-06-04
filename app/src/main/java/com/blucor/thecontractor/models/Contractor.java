package com.blucor.thecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contractor {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("fname")
    @Expose
    public String fname;

    @SerializedName("lname")
    @Expose
    public String lname;

    @SerializedName("company_name")
    @Expose
    public String company_name;

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

    @SerializedName("created_at")
    @Expose
    public String created_at;
}
