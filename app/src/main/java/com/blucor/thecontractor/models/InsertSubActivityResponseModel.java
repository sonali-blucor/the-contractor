package com.blucor.thecontractor.models;

import com.google.gson.annotations.SerializedName;

public class InsertSubActivityResponseModel {
    @SerializedName("success")
   public boolean success;

    @SerializedName("activity")
    public SubActivityModel activity;

    @SerializedName("message")
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public SubActivityModel getActivity() {
        return activity;
    }

    public String getMessage() {
        return message;
    }
}
