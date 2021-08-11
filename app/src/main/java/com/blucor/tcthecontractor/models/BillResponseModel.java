package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillResponseModel implements Parcelable {
    @SerializedName("success")
    @Expose
    public boolean sucess;

    @SerializedName("bill")
    @Expose
    public BilliModel bill;

    @SerializedName("message")
    @Expose
    public String message;

    protected BillResponseModel(Parcel in) {
        sucess = in.readByte() != 0;
        bill = in.readParcelable(BilliModel.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<BillResponseModel> CREATOR = new Creator<BillResponseModel>() {
        @Override
        public BillResponseModel createFromParcel(Parcel in) {
            return new BillResponseModel(in);
        }

        @Override
        public BillResponseModel[] newArray(int size) {
            return new BillResponseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public BilliModel getBill() {
        return bill;
    }

    public void setBill(BilliModel bill) {
        this.bill = bill;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Creator<BillResponseModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (sucess ? 1 : 0));
        dest.writeParcelable(bill, flags);
        dest.writeString(message);
    }
}
