package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PickingItem implements Parcelable {

    public String companyName,date;
    public int poNumber, sn, soNumber;

    public PickingItem(int sn, String companyName, int poNumber, String date, int soNumber) {
        this.sn = sn;
        this.companyName = companyName;
        this.poNumber = poNumber;
        this.date = date;
        this.soNumber=soNumber;
    }

    protected PickingItem(Parcel in) {
        companyName = in.readString();
        date = in.readString();
        poNumber = in.readInt();
        soNumber = in.readInt();
    }

    public static final Creator<PickingItem> CREATOR = new Creator<PickingItem>() {
        @Override
        public PickingItem createFromParcel(Parcel in) {
            return new PickingItem(in);
        }

        @Override
        public PickingItem[] newArray(int size) {
            return new PickingItem[size];
        }
    };

    public int getSn() {
        return sn;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getPoNumber() {
        return poNumber;
    }

    public String getDate() {
        return date;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPoNumber(int poNumber) {
        this.poNumber = poNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSoNumber() {return soNumber;}
    public void setSoNumber(int soNumber) {this.soNumber= soNumber;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyName);
        dest.writeString(date);
        dest.writeInt(poNumber);
        dest.writeInt(soNumber);
    }
}
