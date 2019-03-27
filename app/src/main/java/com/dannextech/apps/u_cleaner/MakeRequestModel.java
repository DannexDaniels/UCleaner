package com.dannextech.apps.u_cleaner;

public class MakeRequestModel {
    String name;
    int price;

    public MakeRequestModel(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public MakeRequestModel(){

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
}
