package com.blucor.thecontractor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClientAddSearchModel {

    @SerializedName("client_id")
    @Expose
    public String client_id;

    @SerializedName("clients")
    @Expose
    public ArrayList<Client> clients;
}
