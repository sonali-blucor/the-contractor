package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectMaterialModel implements Parcelable {

    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("materialPurchase")
    @Expose
    public MaterialPurchase materialPurchase;

    @SerializedName("materials")
    @Expose
    public MaterialsModal materialsModal;
    @SerializedName("supplier")
    @Expose
    public MaterialsModal supplier;

    @SerializedName("message")
    @Expose
    public String message;


    public ProjectMaterialModel() {
    }


    protected ProjectMaterialModel(Parcel in) {
        success = in.readByte() != 0;
        materialPurchase = in.readParcelable(MaterialPurchase.class.getClassLoader());
        materialsModal = in.readParcelable(MaterialsModal.class.getClassLoader());
        supplier = in.readParcelable(SupplierModal.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<ProjectMaterialModel> CREATOR = new Creator<ProjectMaterialModel>() {
        @Override
        public ProjectMaterialModel createFromParcel(Parcel in) {
            return new ProjectMaterialModel(in);
        }

        @Override
        public ProjectMaterialModel[] newArray(int size) {
            return new ProjectMaterialModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeParcelable(materialPurchase, flags);
        dest.writeParcelable(materialsModal, flags);
        dest.writeParcelable(supplier, flags);
        dest.writeString(message);
    }
}
