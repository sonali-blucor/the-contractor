package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierModal implements Parcelable {
    @SerializedName("id")
    @Expose
    public int supplierId;
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


    public SupplierModal() {
    }

    protected SupplierModal(Parcel in) {
        supplierId = in.readInt();
        supplierName = in.readString();
        supplierContact = in.readString();
        supplierEmail = in.readString();
        supplierAddress = in.readString();
    }

    public static final Parcelable.Creator<SupplierModal> CREATOR = new Parcelable.Creator<SupplierModal>() {
        @Override
        public SupplierModal createFromParcel(Parcel in) {
            return new SupplierModal(in);
        }

        @Override
        public SupplierModal[] newArray(int size) {
            return new SupplierModal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(supplierId);
        dest.writeString(supplierName);
        dest.writeString(supplierContact);
        dest.writeString(supplierEmail);
        dest.writeString(supplierAddress);
    }
}
