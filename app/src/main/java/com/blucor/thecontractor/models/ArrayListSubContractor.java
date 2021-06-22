package com.blucor.thecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArrayListSubContractor {

    @SerializedName("sub_contractors")
    @Expose
    private ArrayList<SubContractor> subContractors;

    public ArrayListSubContractor(ArrayList<SubContractor> subContractors) {
        this.subContractors=subContractors;
    }
}
