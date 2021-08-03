package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InsertActivityResponseModel implements Parcelable {
    @SerializedName("success")
   public boolean success;

    @SerializedName("activity")
    public Activity activity;

    @SerializedName("message")
    public String message;

    protected InsertActivityResponseModel(Parcel in) {
        success = in.readByte() != 0;
        activity = in.readParcelable(Activity.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<InsertActivityResponseModel> CREATOR = new Creator<InsertActivityResponseModel>() {
        @Override
        public InsertActivityResponseModel createFromParcel(Parcel in) {
            return new InsertActivityResponseModel(in);
        }

        @Override
        public InsertActivityResponseModel[] newArray(int size) {
            return new InsertActivityResponseModel[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeParcelable(activity, flags);
        dest.writeString(message);
    }
}
