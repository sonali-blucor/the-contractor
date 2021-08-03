package com.blucor.tcthecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubContractorAddSearchModel {
/*
    @SerializedName("sub_contractor_id")
    @Expose
    public String sub_contractor_id;*/

    @SerializedName("sub_contractors")
    @Expose
    public ArrayList<SubContractor> sub_contractors;
}
