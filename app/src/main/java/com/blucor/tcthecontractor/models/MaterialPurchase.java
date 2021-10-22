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
    //    @SerializedName("material_name")
//    @Expose
    public String material_brand ="";
    //    @SerializedName("material_type")
//    @Expose
    public String material_type="";
    //    @SerializedName("material_unit")
//    @Expose
    public String material_unit="";

    @SerializedName("supplier_id")
    @Expose
    public int supplier_id;

    //    @SerializedName("supplier_name")
//    @Expose
    public String supplier_name="";

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
//        material_name = in.readString();
//        material_type = in.readString();
//        material_unit = in.readString();
//        supplier_name = in.readString();
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

//          dest.writeString(material_name );
//          dest.writeString(material_type );
//          dest.writeString(material_unit );
//          dest.writeString(supplier_name );
    }
}
