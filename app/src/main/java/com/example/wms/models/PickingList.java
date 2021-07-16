package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PickingList implements Parcelable {

    public String companyName,date;
    public int poNumber, sn;

    public PickingList(int sn, String companyName, int poNumber, String date) {
        this.sn = sn;
        this.companyName = companyName;
        this.poNumber = poNumber;
        this.date = date;
    }

    protected PickingList(Parcel in) {
        companyName = in.readString();
        date = in.readString();
        poNumber = in.readInt();
    }

    public static final Creator<PickingList> CREATOR = new Creator<PickingList>() {
        @Override
        public PickingList createFromParcel(Parcel in) {
            return new PickingList(in);
        }

        @Override
        public PickingList[] newArray(int size) {
            return new PickingList[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyName);
        dest.writeString(date);
        dest.writeInt(poNumber);
    }
}
