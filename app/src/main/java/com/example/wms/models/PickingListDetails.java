package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PickingListDetails implements Parcelable {
    public int sn, upc;
    public String location, productName, sku, skuScanned;

    public PickingListDetails(int sn, String location, int upc, String productName, String sku, String skuScanned) {
        this.sn = sn;
        this.upc = upc;
        this.sku = sku;
        this.skuScanned = skuScanned;
        this.location = location;
        this.productName = productName;
    }

    protected PickingListDetails(Parcel in) {
        sn = in.readInt();
        upc = in.readInt();
        sku = in.readString();
        skuScanned = in.readString();
        location = in.readString();
        productName = in.readString();
    }

    public static final Creator<PickingListDetails> CREATOR = new Creator<PickingListDetails>() {
        @Override
        public PickingListDetails createFromParcel(Parcel in) {
            return new PickingListDetails(in);
        }

        @Override
        public PickingListDetails[] newArray(int size) {
            return new PickingListDetails[size];
        }
    };

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

    public String getSkuScanned() {
        return skuScanned;
    }

    public void setSkuScanned(String skuScanned) {
        this.skuScanned = skuScanned;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sn);
        dest.writeInt(upc);
        dest.writeString(sku);
        dest.writeString(skuScanned);
        dest.writeString(location);
        dest.writeString(productName);
    }
}
