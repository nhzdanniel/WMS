package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ApprovedReceivedListDetails implements Parcelable {

    public String productName, location, sku;
    public int sn, upc;

    public ApprovedReceivedListDetails(int sn, String location, String productName, int upc, String sku) {
        this.productName = productName;
        this.location = location;
        this.sn = sn;
        this.upc = upc;
        this.sku = sku;
    }

    protected ApprovedReceivedListDetails(Parcel in) {
        productName = in.readString();
        location = in.readString();
        sn = in.readInt();
        upc = in.readInt();
        sku = in.readString();
    }

    public static final Creator<ApprovedReceivedListDetails> CREATOR = new Creator<ApprovedReceivedListDetails>() {
        @Override
        public ApprovedReceivedListDetails createFromParcel(Parcel in) {
            return new ApprovedReceivedListDetails(in);
        }

        @Override
        public ApprovedReceivedListDetails[] newArray(int size) {
            return new ApprovedReceivedListDetails[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public int getUpc() {
        return upc;
    }

    public void setUpc(int upc) {
        this.upc = upc;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(location);
        dest.writeInt(sn);
        dest.writeInt(upc);
        dest.writeString(sku);
    }
}
