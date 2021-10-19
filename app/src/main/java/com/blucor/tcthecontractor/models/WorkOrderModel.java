package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkOrderModel implements Parcelable {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("work_description")
    @Expose
    public String work_description;

    @SerializedName("unit_id")
    @Expose
    public int unit_id;

    @SerializedName("unit")
    @Expose
    public String unit;

    @SerializedName("quantity")
    @Expose
    public long quantity;

    @SerializedName("rate")
    @Expose
    public long rate;

    @SerializedName("amount")
    @Expose
    public long amount;

    @SerializedName("project_id")
    @Expose
    public int project_id;

    @SerializedName("sub_contractor_id")
    @Expose
    public int sub_contractor_id;


    public WorkOrderModel() {
    }


    protected WorkOrderModel(Parcel in) {
        id = in.readInt();
        work_description = in.readString();
        unit_id = in.readInt();
        unit = in.readString();
        quantity = in.readLong();
        rate = in.readLong();
        amount = in.readLong();
        project_id = in.readInt();
        sub_contractor_id = in.readInt();
    }

    public static final Creator<WorkOrderModel> CREATOR = new Creator<WorkOrderModel>() {
        @Override
        public WorkOrderModel createFromParcel(Parcel in) {
            return new WorkOrderModel(in);
        }

        @Override
        public WorkOrderModel[] newArray(int size) {
            return new WorkOrderModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWork_description() {
        return work_description;
    }

    public void setWork_description(String work_description) {
        this.work_description = work_description;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public static Creator<WorkOrderModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(work_description);
        dest.writeInt(unit_id);
        dest.writeString(unit);
        dest.writeLong(quantity);
        dest.writeLong(rate);
        dest.writeLong(amount);
        dest.writeInt(project_id);
        dest.writeInt(sub_contractor_id);
    }
}
