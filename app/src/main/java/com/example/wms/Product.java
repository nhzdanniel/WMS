package com.example.wms;

public class Product {
    public String id, quantity, weight, name, location, dimension, uom;

    public Product(String id, String quantity, String weight, String uom, String name, String location, String dimension) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.quantity = quantity;
        this.uom = uom;
        this.weight = weight;
        this.dimension = dimension;
    }

    public String getId() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDimension() {
        return dimension;
    }

    public String getUom() {
        return uom;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
