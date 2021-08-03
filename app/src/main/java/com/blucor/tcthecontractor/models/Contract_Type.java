package com.blucor.tcthecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contract_Type {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("contract_type")
    @Expose
    public String contract_type;
}
