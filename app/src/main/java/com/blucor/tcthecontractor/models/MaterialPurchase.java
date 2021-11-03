package com.blucor.tcthecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialPurchase implements Parcelable {
    @SerializedName("id")
    @Expose
    public int material_purchase_id;

    @SerializedName("material_id")
    @Expose
    public int material_id;
    @SerializedName("material_brand")
    @Expose
    public String material_brand = "";
    @SerializedName("material_type")
    @Expose
    public String material_type = "";
    @SerializedName("unit")
    @Expose
    public String material_unit = "";

    @SerializedName("supplier_id")
    @Expose
    public int supplier_id;

    @SerializedName("supplier_name")
    @Expose
    public String supplier_name = "";
    @SerializedName("contact")
    @Expose
    public String supplier_contact = "";

    @SerializedName("contractor_id")
    @Expose
    public int contractor_id;

    @SerializedName("project_id")
    @Expose
    public int project_id;

    @SerializedName("quantity")
    @Expose
    public String quantity;

    @SerializedName("rate")
    @Expose
    public String rate;

    @SerializedName("amount")
    @Expose
    public String amount;

    @SerializedName("gst")
    @Expose
    public String gst;

    @SerializedName("gst_amt")
    @Expose
    public String gst_amt;

    @SerializedName("total_amt")
    @Expose
    public String total_amt;

    @SerializedName("paid_amt")
    @Expose
    public String paid_amt;

    @SerializedName("balance_amt")
    @Expose
    public String balance_amt;
    @SerializedName("paid_to")
    @Expose
    public String paid_to;
    @SerializedName("payment_type")
    @Expose
    public String payment_type;
    @SerializedName("updated_at")
    @Expose
    public String updated_at;


    public MaterialPurchase() {

    }

    public MaterialPurchase(int material_purchase_id, int material_id, String material_brand, String material_type, String material_unit, int supplier_id, String supplier_name, String supplier_contact, int contractor_id, int project_id, String quantity, String rate, String amount, String gst, String gst_amt, String total_amt, String paid_amt, String balance_amt, String paid_to, String payment_type, String updated_at) {
        this.material_purchase_id = material_purchase_id;
        this.material_id = material_id;
        this.material_brand = material_brand;
        this.material_type = material_type;
        this.material_unit = material_unit;
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.supplier_contact = supplier_contact;
        this.contractor_id = contractor_id;
        this.project_id = project_id;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
        this.gst = gst;
        this.gst_amt = gst_amt;
        this.total_amt = total_amt;
        this.paid_amt = paid_amt;
        this.balance_amt = balance_amt;
        this.paid_to = paid_to;
        this.payment_type = payment_type;
        this.updated_at = updated_at;
    }

    protected MaterialPurchase(Parcel in) {
        material_purchase_id = in.readInt();
        material_id = in.readInt();
        supplier_id = in.readInt();
        contractor_id = in.readInt();
        project_id = in.readInt();
        quantity = in.readString();
        rate = in.readString();
        amount = in.readString();
        gst = in.readString();
        gst_amt = in.readString();
        total_amt = in.readString();
        paid_amt = in.readString();
        balance_amt = in.readString();
        paid_to = in.readString();
        payment_type = in.readString();
        updated_at = in.readString();
        material_brand = in.readString();
        material_type = in.readString();
        material_unit = in.readString();
        supplier_name = in.readString();
        supplier_contact = in.readString();
    }

    public static final Creator<MaterialPurchase> CREATOR = new Creator<MaterialPurchase>() {
        @Override
        public MaterialPurchase createFromParcel(Parcel in) {
            return new MaterialPurchase(in);
        }

        @Override
        public MaterialPurchase[] newArray(int size) {
            return new MaterialPurchase[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_purchase_id);
        dest.writeInt(material_id);
        dest.writeInt(supplier_id);
        dest.writeInt(contractor_id);
        dest.writeInt(project_id);
        dest.writeString(quantity);
        dest.writeString(rate);
        dest.writeString(amount);
        dest.writeString(gst);
        dest.writeString(gst_amt);
        dest.writeString(total_amt);
        dest.writeString(paid_amt);
        dest.writeString(balance_amt);
        dest.writeString(paid_to);
        dest.writeString(payment_type);
        dest.writeString(updated_at);

        dest.writeString(material_brand);
        dest.writeString(material_type);
        dest.writeString(material_unit);
        dest.writeString(supplier_name);
        dest.writeString(supplier_contact);
    }
}
