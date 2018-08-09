
package com.example.h2_12.youcookipay;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    private String userId;
    private String userName;
    private String userDescription;
    private String userAddress;
    private String userImage;
    private String rating;
    private String seller_type;
    private String mealId;
    private String mealName;
    private String placeName;
    private String mealDescription;
    private String classification;
    private String category;
    private String type;
    private String portionPrice;
    private ArrayList<String> mylist=new ArrayList<>();
    private String mealImage;

  /* public Datum(String userId, String userName, String userDescription, String userAddress, String userImage, String rating, String seller_type, String mealId, String mealName, String placeName, String mealDescription, String classification, String category, String type, String portionPrice, String mealImage) {
        this.userId = userId;
        this.userName = userName;
        this.userDescription = userDescription;
        this.userAddress = userAddress;
        this.userImage = userImage;
        this.rating = rating;
        this.seller_type = seller_type;
        this.mealId = mealId;
        this.mealName = mealName;
        this.placeName = placeName;
        this.mealDescription = mealDescription;
        this.classification = classification;
        this.category = category;
        this.type = type;
        this.portionPrice = portionPrice;
        this.mealImage = mealImage;
    }*/



 public Datum(String userId, String userName, String userDescription, String userAddress, String userImage, String rating, String seller_type, String mealId, String mealName, String placeName, String mealDescription, String classification, String category, String type, String portionPrice, ArrayList<String> mylist) {
        this.userId = userId;
        this.userName = userName;
        this.userDescription = userDescription;
        this.userAddress = userAddress;
        this.userImage = userImage;
        this.rating = rating;
        this.seller_type = seller_type;
        this.mealId = mealId;
        this.mealName = mealName;
        this.placeName = placeName;
        this.mealDescription = mealDescription;
        this.classification = classification;
        this.category = category;
        this.type = type;
        this.portionPrice = portionPrice;
        this.mylist = mylist;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPortionPrice() {
        return portionPrice;
    }

    public void setPortionPrice(String portionPrice) {
        this.portionPrice = portionPrice;
    }

    public String getMealImage() {
        return mealImage;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }

    public ArrayList<String> getMylist() {
        return mylist;
    }

    public void setMylist(ArrayList<String> mylist) {
        this.mylist = mylist;
    }
}

