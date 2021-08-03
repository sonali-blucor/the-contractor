package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InsertSubActivityResponseModel implements Parcelable {
    @SerializedName("success")
   public boolean success;

    @SerializedName("sub_activity")
    public SubActivityModel sub_activity;

    @SerializedName("sub_contractor")
    public SubContractor sub_contractor;

    @SerializedName("message")
    public String message;

    protected InsertSubActivityResponseModel(Parcel in) {
        success = in.readByte() != 0;
        sub_activity = in.readParcelable(SubActivityModel.class.getClassLoader());
        sub_contractor = in.readParcelable(SubContractor.class.getClassLoader());
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeParcelable(sub_activity, flags);
        dest.writeParcelable(sub_contractor, flags);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InsertSubActivityResponseModel> CREATOR = new Creator<InsertSubActivityResponseModel>() {
        @Override
        public InsertSubActivityResponseModel createFromParcel(Parcel in) {
            return new InsertSubActivityResponseModel(in);
        }

        @Override
        public InsertSubActivityResponseModel[] newArray(int size) {
            return new InsertSubActivityResponseModel[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public SubActivityModel getActivity() {
        return sub_activity;
    }

    public String getMessage() {
        return message;
    }
}
