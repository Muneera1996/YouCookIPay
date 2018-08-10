package com.example.h2_12.youcookipay;

public class OrderHistory {
    private String orderId;
    private String chefId;
    private String userId;
    private String mealId;
    private String mealName;
    private String mealClassification;
    private String mealType;
    private String mealCategory;
    private String portionPrice;
    private String quantity;
    private String totalCost;
    private String serviceCharges;
    private String deliveryCharges;
    private String grandTotal;
    private String userName;
    private String userEmail;
    private String userContact;
    private String date;
    private String time;
    private String deliveryOption;
    private String street;
    private String city;
    private String area;
    private String paymentMethod;
    private String transactionId;
    private String orderStatus;
    private String chefName;

    public OrderHistory(String orderId, String chefId, String userId, String mealId, String mealName, String mealClassification, String mealType, String mealCategory, String portionPrice, String quantity, String totalCost, String serviceCharges, String deliveryCharges, String grandTotal, String userName, String userEmail, String userContact, String date, String time, String deliveryOption, String street, String city, String area, String paymentMethod, String transactionId, String orderStatus, String chefName) {
        this.orderId = orderId;
        this.chefId = chefId;
        this.userId = userId;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealClassification = mealClassification;
        this.mealType = mealType;
        this.mealCategory = mealCategory;
        this.portionPrice = portionPrice;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.serviceCharges = serviceCharges;
        this.deliveryCharges = deliveryCharges;
        this.grandTotal = grandTotal;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContact = userContact;
        this.date = date;
        this.time = time;
        this.deliveryOption = deliveryOption;
        this.street = street;
        this.city = city;
        this.area = area;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.orderStatus = orderStatus;
        this.chefName = chefName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getMealClassification() {
        return mealClassification;
    }

    public void setMealClassification(String mealClassification) {
        this.mealClassification = mealClassification;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getPortionPrice() {
        return portionPrice;
    }

    public void setPortionPrice(String portionPrice) {
        this.portionPrice = portionPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(String serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
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

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }
}
