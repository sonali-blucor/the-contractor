package com.blucor.tcthecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitModal {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("unit")
    @Expose
    public String unit;
}
