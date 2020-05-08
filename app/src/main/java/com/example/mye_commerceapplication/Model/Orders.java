package com.example.mye_commerceapplication.Model;

public class Orders {
    private String name,phone,address,date,state,totalAmount;

    public Orders(String name, String phone, String address, String date, String state, String totalAmount) {

        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.state = state;
        this.totalAmount = totalAmount;
    }

    public Orders() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
