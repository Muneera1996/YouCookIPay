package com.example.h2_12.youcookipay;

public class ViewYourAd {
    private String mealId;
    private String mealName;
    private String placeName;
    private String mealDescription;
    private String classification;
    private String category;
    private String type;
    private String portionPrice;
    private String mealImages;

    public ViewYourAd(String mealId, String mealName, String placeName, String mealDescription, String classification, String category, String type, String portionPrice, String mealImages) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.placeName = placeName;
        this.mealDescription = mealDescription;
        this.classification = classification;
        this.category = category;
        this.type = type;
        this.portionPrice = portionPrice;
        this.mealImages = mealImages;
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

    public String getMealImages() {
        return mealImages;
    }

    public void setMealImages(String mealImages) {
        this.mealImages = mealImages;
    }
}
