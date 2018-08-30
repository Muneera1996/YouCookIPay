package com.example.h2_12.youcookipay;

public class Reviews_Rating {

    private String id;
    private String name;
    private String rating;
    private String comments;

    public Reviews_Rating(String id, String name, String rating, String comments) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
