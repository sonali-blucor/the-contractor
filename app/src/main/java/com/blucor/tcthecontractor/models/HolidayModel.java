package com.blucor.tcthecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HolidayModel {


    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("note")
    @Expose
    public String note;

    @SerializedName("project_id")
    @Expose
    public int project_id;

    @SerializedName("updated_at")
    @Expose
    public String updated_at;

    @SerializedName("created_at")
    @Expose
    public String created_at;
}
