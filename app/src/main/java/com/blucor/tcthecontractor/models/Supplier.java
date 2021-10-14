package com.blucor.tcthecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplier {
    @SerializedName("id")
    @Expose
    public String supplierId;
    @SerializedName("supplier_name")
    @Expose
    public String supplierName;
    @SerializedName("contact")
    @Expose
    public String supplierContact;
    @SerializedName("email")
    @Expose
    public String supplierEmail;
    @SerializedName("address")
    @Expose
    public String supplierAddress;


}
