package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BilliModel implements Parcelable {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("percentage")
    @Expose
    public float percentage;

    @SerializedName("amount")
    @Expose
    public double amount;

    @SerializedName("remark")
    @Expose
    public String remark;

    @SerializedName("balance")
    @Expose
    public double balance;

    @SerializedName("paid")
    @Expose
    public double paid;

    @SerializedName("payment_date")
    @Expose
    public String payment_date;

    @SerializedName("billing_date")
    @Expose
    public String billing_date;

    @SerializedName("project_id")
    @Expose
    public int project_id;
    @SerializedName("sub_contractor_id")
    @Expose
    public int sub_contractor_id;

    public BilliModel() {
    }

    protected BilliModel(Parcel in) {
        id = in.readInt();
        percentage = in.readFloat();
        amount = in.readLong();
        remark = in.readString();
        balance = in.readLong();
        paid = in.readLong();
        payment_date = in.readString();
        billing_date = in.readString();
        project_id = in.readInt();
        sub_contractor_id = in.readInt();
    }

    public static final Creator<BilliModel> CREATOR = new Creator<BilliModel>() {
        @Override
        public BilliModel createFromParcel(Parcel in) {
            return new BilliModel(in);
        }

        @Override
        public BilliModel[] newArray(int size) {
            return new BilliModel[size];
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

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getBilling_date() {
        return billing_date;
    }

    public void setBilling_date(String billing_date) {
        this.billing_date = billing_date;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public static Creator<BilliModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(percentage);
        dest.writeDouble(amount);
        dest.writeString(remark);
        dest.writeDouble(balance);
        dest.writeDouble(paid);
        dest.writeString(payment_date);
        dest.writeString(billing_date);
        dest.writeInt(project_id);
        dest.writeInt(sub_contractor_id);
    }
}
