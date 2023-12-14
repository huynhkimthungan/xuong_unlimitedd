package com.example.unlimited_store.model;

public class Product {
    //idProduct,image,name,price,state,topping,extraCream,description,type
    int idProduct;
    String image;
    String name;
    int price;
    String description;
    String type;

    public Product() {
    }

    public Product(String image, String name, int price, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product(int idProduct, String image, String name, int price, String description, String type) {
        this.idProduct = idProduct;
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }

    public Product(int idProduct, String image, String name, int price, String description) {
        this.idProduct = idProduct;
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product(String image, String name, int price, String description, String type) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
