package com.example.winb.Model;

public class Product {

    private int id;

    private String name;
    private String qty;
    private String dateAdded;

    public Product(String name, String qty, String dateAdded, int id) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.dateAdded = dateAdded;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qty='" + qty + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }
}
