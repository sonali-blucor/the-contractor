package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkOrderResponseModel implements Parcelable {
    @SerializedName("success")
    @Expose
    public boolean sucess;

    @SerializedName("work_order")
    @Expose
    public WorkOrderModel workOrderModel;

    @SerializedName("message")
    @Expose
    public String message;

    protected WorkOrderResponseModel(Parcel in) {
        sucess = in.readByte() != 0;
        workOrderModel = in.readParcelable(WorkOrderModel.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<WorkOrderResponseModel> CREATOR = new Creator<WorkOrderResponseModel>() {
        @Override
        public WorkOrderResponseModel createFromParcel(Parcel in) {
            return new WorkOrderResponseModel(in);
        }

        @Override
        public WorkOrderResponseModel[] newArray(int size) {
            return new WorkOrderResponseModel[size];
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

    public WorkOrderModel getWorkOrderModel() {
        return workOrderModel;
    }

    public void setWorkOrderModel(WorkOrderModel workOrderModel) {
        this.workOrderModel = workOrderModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Creator<WorkOrderResponseModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (sucess ? 1 : 0));
        dest.writeParcelable(workOrderModel, flags);
        dest.writeString(message);
    }
}
