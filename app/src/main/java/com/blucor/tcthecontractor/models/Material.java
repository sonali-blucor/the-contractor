package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Material implements Parcelable {
    @SerializedName("id")
    @Expose
    public int material_id;

    @SerializedName("material_name")
    @Expose
    public String material_name;

    @SerializedName("material_brand")
    @Expose
    public String material_brand;

    @SerializedName("material_date")
    @Expose
    public String material_date;

    @SerializedName("material_type")
    @Expose
    public String material_type;

    @SerializedName("supplier_name")
    @Expose
    public String supplier_name;

    @SerializedName("supplier_contact")
    @Expose
    public String supplier_contact;

    @SerializedName("unit")
    @Expose
    public String unit;

    @SerializedName("quantity")
    @Expose
    public String quantity;

    @SerializedName("rate")
    @Expose
    public String rate;

    @SerializedName("amount")
    @Expose
    public String amount;

    @SerializedName("project_id")
    @Expose
    public String project_id;



    public Material() {

    }
    protected Material(Parcel in) {
        material_id = in.readInt();
        material_name = in.readString();
        material_brand = in.readString();
        material_date = in.readString();
        material_type = in.readString();
        supplier_name = in.readString();
        supplier_contact = in.readString();
        unit = in.readString();
        quantity = in.readString();
        rate = in.readString();
        amount = in.readString();
        project_id = in.readString();
    }

    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_id);
        dest.writeString(material_name);
        dest.writeString(material_brand);
        dest.writeString(material_date);
        dest.writeString(material_type);
        dest.writeString(supplier_name);
        dest.writeString(supplier_contact);
        dest.writeString(unit);
        dest.writeString(quantity);
        dest.writeString(rate);
        dest.writeString(amount);
        dest.writeString(project_id);
    }
}
