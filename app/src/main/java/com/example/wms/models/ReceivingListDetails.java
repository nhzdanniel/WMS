package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.toolbox.StringRequest;

import java.util.Date;

public class ReceivingListDetails implements Parcelable {

    public String productName;
    public String upc, qtyReceived;
    public String year, month, day;
    public int sn, qtyOrdered, qtyRemaining;
    public String expirydate;


/*    public ReceivingListDetails(int sn, String productName, , String year, String month, String day, int qtyOrdered, String upc, String qtyReceived, int qtyRemaining) {
        this.productName = productName;
        this.upc = upc;
        this.qtyReceived = qtyReceived;
        this.year = year;
        this.month = month;
        this.day = day;
        this.sn = sn;
        this.qtyOrdered = qtyOrdered;
        this.qtyRemaining = qtyRemaining;
    }*/

    public ReceivingListDetails(int sn, String productName, int qtyOrdered, String upc, String qtyReceived, int qtyRemaining, String expirydate) {
        this.productName = productName;
        this.sn = sn;
        this.upc = upc;
        this.qtyOrdered = qtyOrdered;
        this.qtyReceived = qtyReceived;
        this.qtyRemaining = qtyRemaining;
        this.expirydate = expirydate;
    }


    protected ReceivingListDetails(Parcel in) {
        productName = in.readString();
        upc = in.readString();
        qtyReceived = in.readString();
        sn = in.readInt();
        qtyOrdered = in.readInt();
        qtyRemaining = in.readInt();
        year = in.readString();
        month = in.readString();
        day = in.readString();
        expirydate= in.readString();
    }

    public static final Creator<ReceivingListDetails> CREATOR = new Creator<ReceivingListDetails>() {
        @Override
        public ReceivingListDetails createFromParcel(Parcel in) {
            return new ReceivingListDetails(in);
        }

        @Override
        public ReceivingListDetails[] newArray(int size) {
            return new ReceivingListDetails[size];
        }
    };


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getUpc() {return upc;}

    public void setUpc(String upc) {this.upc = upc;}

    public int getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(int qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public String getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(String qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public int getQtyRemaining() {
        return qtyRemaining;
    }

    public void setQtyRemaining(int qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getExpirydate() {return expirydate;}

    public void setExpirydate(String expirydate){this.expirydate= expirydate;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(productName);
        dest.writeString(upc);
        dest.writeString(qtyReceived);
        dest.writeInt(sn);
        dest.writeInt(qtyOrdered);
        dest.writeInt(qtyRemaining);
        dest.writeString(year);
        dest.writeString(month);
        dest.writeString(day);
        dest.writeString(expirydate);
    }

}
