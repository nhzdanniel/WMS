package com.example.wms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReceivingListDetails implements Parcelable {

    public String productName;
    //public String upc;
    public int sn, qtyOrdered, qtyReceived, qtyRemaining;

    public ReceivingListDetails(int sn, String productName, int qtyOrdered, int qtyReceived, int qtyRemaining) {
        this.productName = productName;
        this.sn = sn;
        //this.upc = upc;
        this.qtyOrdered = qtyOrdered;
        this.qtyReceived = qtyReceived;
        this.qtyRemaining = qtyRemaining;
    }

    protected ReceivingListDetails(Parcel in) {
        productName = in.readString();
        sn = in.readInt();
        //upc = in.readString();
        qtyOrdered = in.readInt();
        qtyReceived = in.readInt();
        qtyRemaining = in.readInt();
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

    //public String getUpc() {return upc;}

    //public void setUpc(String upc) {this.upc = upc;}

    public int getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(int qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public int getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(int qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public int getQtyRemaining() {
        return qtyRemaining;
    }

    public void setQtyRemaining(int qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeInt(sn);
        //dest.writeString(upc);
        dest.writeInt(qtyOrdered);
        dest.writeInt(qtyReceived);
        dest.writeInt(qtyRemaining);
    }
}
