package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PickingListDetails implements Parcelable {
    public int sn, upc, sku, skuScanned;
    public String location, productName;

    public PickingListDetails(int sn, String location, int upc, String productName, int sku, int skuScanned) {
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
        sku = in.readInt();
        skuScanned = in.readInt();
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

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getSkuScanned() {
        return skuScanned;
    }

    public void setSkuScanned(int skuScanned) {
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
        dest.writeInt(sku);
        dest.writeInt(skuScanned);
        dest.writeString(location);
        dest.writeString(productName);
    }
}
