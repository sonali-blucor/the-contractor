package com.blucor.tcthecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project_Type {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("project_type")
    @Expose
    public String project_type;

    public boolean is_enabled;
}
