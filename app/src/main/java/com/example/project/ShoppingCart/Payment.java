package com.example.project.ShoppingCart;

public class Payment {
    String userid;
    String totalPrice;
    String date;

    public Payment(String userid, String totalPrice, String date) {
        this.userid = userid;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }
}
