package com.example.h2_12.youcookipay;

public class OrderScreen {
    private String name;
    private String email;
    private String contact;
    private String date;
    private String time;
    private String delivery_method;
    private String meal_id;
    private String meal_name;
    private String meal_price;
    private String meal_image;
    private String street;
    private String area;
    private String city;


    public OrderScreen(String name, String email, String contact, String date, String time, String delivery_method, String meal_id, String meal_name, String meal_price, String meal_image) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.delivery_method = delivery_method;
        this.meal_id = meal_id;
        this.meal_name = meal_name;
        this.meal_price = meal_price;
        this.meal_image = meal_image;
    }

    public OrderScreen(String name, String email, String contact, String date, String time, String delivery_method, String meal_id, String meal_name, String meal_price, String meal_image, String street, String area, String city) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.delivery_method = delivery_method;
        this.meal_id = meal_id;
        this.meal_name = meal_name;
        this.meal_price = meal_price;
        this.meal_image = meal_image;
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

    public String getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(String meal_id) {
        this.meal_id = meal_id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_price() {
        return meal_price;
    }

    public void setMeal_price(String meal_price) {
        this.meal_price = meal_price;
    }

    public String getMeal_image() {
        return meal_image;
    }

    public void setMeal_image(String meal_image) {
        this.meal_image = meal_image;
    }
}
