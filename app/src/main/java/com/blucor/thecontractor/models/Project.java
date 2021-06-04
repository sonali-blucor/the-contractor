package com.blucor.thecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("project_name")
    @Expose
    public String project_name; // = $request->project_name;

    @SerializedName("project_type")
    @Expose
    public String project_type; // = $request->project_type;

    @SerializedName("contract_type")
    @Expose
    public String contract_type; // = $request->contract_type;

    @SerializedName("client_id")
    @Expose
    public int client_id; // = $request->client_id;

    @SerializedName("project_location")
    @Expose
    public String project_location; // = $request->project_location;

    @SerializedName("start_date")
    @Expose
    public String start_date; // = $request->start_date;

    @SerializedName("end_date")
    @Expose
    public String end_date; // = $request->end_date;

    @SerializedName("duration")
    @Expose
    public String duration; // = $request->duration;


    @SerializedName("contractor_id")
    @Expose
    public String contractor_id; // = $request->contractor_id;
}
