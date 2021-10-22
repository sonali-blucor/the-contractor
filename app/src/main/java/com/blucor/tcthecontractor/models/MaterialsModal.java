package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialsModal implements Parcelable{
    @SerializedName("id")
    @Expose
    public int material_id;

    @SerializedName("material_brand")
    @Expose
    public String material_brand;

    @SerializedName("material_description")
    @Expose
    public String material_description;

    @SerializedName("material_type")
    @Expose
    public String material_type;

    @SerializedName("unit")
    @Expose
    public String unit;

    public MaterialsModal() {
    }

    protected MaterialsModal(Parcel in) {
        material_id = in.readInt();
        material_brand = in.readString();
        material_description = in.readString();
        material_type = in.readString();
        unit = in.readString();
    }

    public static final Parcelable.Creator<MaterialsModal> CREATOR = new Parcelable.Creator<MaterialsModal>() {
        @Override
        public MaterialsModal createFromParcel(Parcel in) {
            return new MaterialsModal(in);
        }

        @Override
        public MaterialsModal[] newArray(int size) {
            return new MaterialsModal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_id);
        dest.writeString(material_brand);
        dest.writeString(material_description);
        dest.writeString(material_type);
        dest.writeString(unit);
    }
}
