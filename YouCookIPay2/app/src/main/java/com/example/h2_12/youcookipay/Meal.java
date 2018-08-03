
package com.example.h2_12.youcookipay;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meal {

    private String mealId;
    private String mealName;
    private String mealPrice;
    private String mealImageUrl;

    public Meal(String mealId, String mealName, String mealPrice, String mealImageUrl) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealImageUrl = mealImageUrl;
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

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getMealImageUrl() {
        return mealImageUrl;
    }

    public void setMealImageUrl(String mealImageUrl) {
        this.mealImageUrl = mealImageUrl;
    }
}
