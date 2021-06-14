package com.example.wms;

public class Product {
    public String id, name, location, quantity, uom, weight, dimension;

    public Product(String name, String id, String location, String quantity, String uom, String weight, String dimension) {
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

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUom() {
        return uom;
    }

    public String getWeight() {
        return weight;
    }

    public String getDimension() {
        return dimension;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
