package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ApprovedReceivedList implements Parcelable {

    public String supplierName, eta;
    public int sn, poNumber, doNumber;

    public ApprovedReceivedList(int sn, int poNumber, int doNumber, String supplierName, String eta) {
        this.supplierName = supplierName;
        this.eta = eta;
        this.sn = sn;
        this.poNumber = poNumber;
        this.doNumber = doNumber;
    }

    protected ApprovedReceivedList(Parcel in) {
        supplierName = in.readString();
        eta = in.readString();
        sn = in.readInt();
        poNumber = in.readInt();
        doNumber = in.readInt();
    }

    public static final Creator<ApprovedReceivedList> CREATOR = new Creator<ApprovedReceivedList>() {
        @Override
        public ApprovedReceivedList createFromParcel(Parcel in) {
            return new ApprovedReceivedList(in);
        }

        @Override
        public ApprovedReceivedList[] newArray(int size) {
            return new ApprovedReceivedList[size];
        }
    };

    public String getSupplierName() {
        return supplierName;
    }

    public String getEta() {
        return eta;
    }

    public int getSn() {
        return sn;
    }

    public int getPoNumber() {
        return poNumber;
    }

    public int getDoNumber() {
        return doNumber;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public void setPoNumber(int poNumber) {
        this.poNumber = poNumber;
    }

    public void setDoNumber(int doNumber) {
        this.doNumber = doNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(supplierName);
        dest.writeString(eta);
        dest.writeInt(sn);
        dest.writeInt(poNumber);
        dest.writeInt(doNumber);
    }
}

