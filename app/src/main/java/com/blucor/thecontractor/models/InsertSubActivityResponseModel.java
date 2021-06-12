package com.blucor.thecontractor.models;

import com.google.gson.annotations.SerializedName;

public class InsertSubActivityResponseModel {
    @SerializedName("success")
   public boolean success;

    @SerializedName("sub_activity")
    public SubActivityModel sub_activity;

    @SerializedName("sub_contractor")
    public SubContractor sub_contractor;

    @SerializedName("message")
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public SubActivityModel getActivity() {
        return sub_activity;
    }

    public String getMessage() {
        return message;
    }
}
