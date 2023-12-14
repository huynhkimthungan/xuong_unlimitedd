package com.example.unlimited_store.model;

import com.example.unlimited_store.dao.CartDAO;

public class Cart {
    //idCart,idProduct
    int idCart;
    int quantity;
    int state;
    int total;
    int topping;
    int extraCream;
    int idProduct;
    String username;

    public Cart(int idCart, int quantity, int state, int total, int topping, int extraCream, int idProduct, String username) {
        this.idCart = idCart;
        this.quantity = quantity;
        this.state = state;
        this.total = total;
        this.topping = topping;
        this.extraCream = extraCream;
        this.idProduct = idProduct;
        this.username = username;
    }

    public Cart(int quantity, int state, int total, int topping, int extraCream, int idProduct, String username) {
        this.quantity = quantity;
        this.state = state;
        this.total = total;
        this.topping = topping;
        this.extraCream = extraCream;
        this.idProduct = idProduct;
        this.username = username;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTotal() {
        return total;
    }
    public int getTotal(int price, int topping, int extraCream) {
        return (quantity * price) + (quantity * topping) + (quantity * extraCream);
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTopping() {
        return topping;
    }

    public void setTopping(int topping) {
        this.topping = topping;
    }

    public int getExtraCream() {
        return extraCream;
    }

    public void setExtraCream(int extraCream) {
        this.extraCream = extraCream;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
