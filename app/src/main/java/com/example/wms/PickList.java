package com.example.wms;

public class PickList {
    private String upcCode, quantity;

    public PickList(String upcCode, String quantity) {
        this.upcCode = upcCode;
        this.quantity = quantity;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
