package com.example.h2_12.youcookipay;

public class Chef_Profile {

    private String id;
    private String name;
    private String address;
    private String type;
    private String description;
    private String rating;
    private String image;


    public Chef_Profile(String id, String name, String address, String type, String description, String rating, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
