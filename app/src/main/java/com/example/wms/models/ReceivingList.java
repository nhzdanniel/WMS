package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReceivingList implements Parcelable {

    public String supplierName, status, eta;
    public int sn, poNumber;

    public ReceivingList(int sn, int poNumber, String supplierName, String eta, String status) {
        this.supplierName = supplierName;
        this.status = status;
        this.sn = sn;
        this.poNumber = poNumber;
        this.eta = eta;
    }

    protected ReceivingList(Parcel in) {
        supplierName = in.readString();
        status = in.readString();
        sn = in.readInt();
        poNumber = in.readInt();
        eta = in.readString();
    }

    public static final Creator<ReceivingList> CREATOR = new Creator<ReceivingList>() {
        @Override
        public ReceivingList createFromParcel(Parcel in) {
            return new ReceivingList(in);
        }

        @Override
        public ReceivingList[] newArray(int size) {
            return new ReceivingList[size];
        }
    };

    public String getSupplierName() {
        return supplierName;
    }

    public String getStatus() {
        return status;
    }

    public int getSn() {
        return sn;
    }

    public int getPoNumber() {
        return poNumber;
    }

    public String getEta() {
        return eta;
    }

    public void setSupplierName(String supplierName) {

        this.supplierName = supplierName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public void setPoNumber(int poNumber) {
        this.poNumber = poNumber;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(supplierName);
        dest.writeString(status);
        dest.writeInt(sn);
        dest.writeInt(poNumber);
        dest.writeString(eta);
    }
}
