package com.duarbd.duarclientapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelHybread {
    @SerializedName("deliveryRequestId")
    @Expose
    private String deliveryRequestId;

    @SerializedName("clientID")
    @Expose
    private String clientID;

    @SerializedName("clientName")
    @Expose
    private String clientName;


    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerNumber")
    @Expose
    private String customerNumber;

    @SerializedName("productType")
    @Expose
    private  String productType;
    @SerializedName("deliveryArea")
    @Expose
    private String deliveryArea;
    @SerializedName("deliveryAddressExtra")
    @Expose
    private String deliveryAddressExtra;
    @SerializedName("pickUpAddress")
    @Expose
    private String pickUpAddress;
    @SerializedName("pickUpAddressLat")
    @Expose
    private String pickUpAddressLat;
    @SerializedName("pickUpAddressLang")
    @Expose
    private String pickUpAddressLang;


    @SerializedName("clientToken")
    @Expose
    private String clientToken;

    @SerializedName("pickupCharge")
    @Expose
    private int pickupCharge;
    @SerializedName("deliveryCharge")
    @Expose
    private int deliveryCharge;
    @SerializedName("productPrice")
    @Expose
    private int productPrice;


    @SerializedName("pickupTime")
    @Expose
    private int pickupTime;//in minute
    @SerializedName("requestPlacedTime")
    @Expose
    private String requestPlacedTime;

    @SerializedName("deliveryStatus")
    @Expose
    private int deliveryStatus;
    @SerializedName("riderName")
    @Expose
    private String riderName;
    @SerializedName("clientPaymentStatus")
    @Expose
    private String clientPaymentStatus;

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("status")
    @Expose
    private String status;
}
