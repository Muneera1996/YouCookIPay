package com.example.h2_12.youcookipay;

public class OrderScreen {
    private String name;
    private String email;
    private String contact;
    private String date;
    private String time;
    private String delivery_method;
    private String street;
    private String area;
    private String city;

    public OrderScreen(String name, String email, String contact, String date, String time, String delivery_method) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.delivery_method = delivery_method;
    }

    public OrderScreen(String name, String email, String contact, String date, String time, String delivery_method, String street, String area, String city) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.delivery_method = delivery_method;
        this.street = street;
        this.area = area;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
