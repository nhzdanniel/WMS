package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ReceivingListDetails implements Parcelable {

    public String productName;
    public String upc, qtyReceived;
    public String expiryDate;
    public int sn, qtyOrdered, qtyRemaining;



/*    public ReceivingListDetails(int sn, String productName, String expiryDate, String upc, String qtyReceived, int qtyOrdered, int qtyRemaining) {
        this.productName = productName;
        this.upc = upc;
        this.qtyReceived = qtyReceived;
        this.expiryDate = expiryDate;
        this.sn = sn;
        this.qtyOrdered = qtyOrdered;
        this.qtyRemaining = qtyRemaining;
    }*/

    public ReceivingListDetails(int sn, String productName, int qtyOrdered, String upc, String qtyReceived, int qtyRemaining) {
        this.productName = productName;
        this.sn = sn;
        this.upc = upc;
        this.qtyOrdered = qtyOrdered;
        this.qtyReceived = qtyReceived;
        this.qtyRemaining = qtyRemaining;
    }


    protected ReceivingListDetails(Parcel in) {
        productName = in.readString();
        upc = in.readString();
        qtyReceived = in.readString();
        sn = in.readInt();
        qtyOrdered = in.readInt();
        qtyRemaining = in.readInt();
        expiryDate = in.readString();
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

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
        dest.writeString(expiryDate);
    }

}
