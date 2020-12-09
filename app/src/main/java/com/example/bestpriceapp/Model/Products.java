package com.example.bestpriceapp.Model;

import android.widget.ImageView;

public class Products {

    private String name, price;

    public Products(){

    }

    public Products(String name, String price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
