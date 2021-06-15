package com.example.wms;

public class PickingList {

    public String poNumber, companyName, Date;

    public PickingList(String poNumber, String companyName, String date) {
        this.poNumber = poNumber;
        this.companyName = companyName;
        Date = date;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDate() {
        return Date;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDate(String date) {
        Date = date;
    }
}
