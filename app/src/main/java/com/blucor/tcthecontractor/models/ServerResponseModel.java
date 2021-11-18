package com.blucor.tcthecontractor.models;

import com.google.gson.annotations.SerializedName;

public class ServerResponseModel {
    @SerializedName("success")
   public boolean success;

    @SerializedName("message")
    public String message;


    @SerializedName("unit")
    public UnitModal unitModal;

    String getMessage() {
        return message;
    }
    boolean getSuccess() {
        return success;
    }
}
