package com.blucor.thecontractor.models;

import com.google.gson.annotations.SerializedName;

public class InsertActivityResponseModel {
    @SerializedName("success")
   public boolean success;

    @SerializedName("activity")
    public Activity activity;

    @SerializedName("message")
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getMessage() {
        return message;
    }
}
