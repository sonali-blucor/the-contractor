package com.blucor.thecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordModel {
    @SerializedName("otp")
    @Expose
    public String otp;

    @SerializedName("server_id")
    @Expose
    public String server_id;

    @SerializedName("is_sent")
    @Expose
    public boolean is_sent;

    @SerializedName("message_sent_str")
    @Expose
    public String message_sent_str;

    @SerializedName("url")
    @Expose
    public String url;
}
