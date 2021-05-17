package com.duarbd.duarclientapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelResponse {

    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("clientContactNumber")
    @Expose
    private String clientContactNumber;
    @SerializedName("clientBusinessName")
    @Expose
    private String clientBusinessName;
    @SerializedName("clientBusinessLocationlat")
    @Expose
    private String clientBusinessLocationlat;
    @SerializedName("clientBusinessLocationlang")
    @Expose
    private String clientBusinessLocationlang;
    @SerializedName("clientAddress")
    @Expose
    private String clientAddress;
    @SerializedName("clientProductType")
    @Expose
    private String clientProductType;
    @SerializedName("response")
    @Expose
    private Integer response;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("pickupCharge")
    @Expose
    private Integer pickupCharge;


    public Integer getPickupCharge() {
        return pickupCharge;
    }

    public void setPickupCharge(Integer pickupCharge) {
        this.pickupCharge = pickupCharge;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientContactNumber() {
        return clientContactNumber;
    }

    public void setClientContactNumber(String clientContactNumber) {
        this.clientContactNumber = clientContactNumber;
    }

    public String getClientBusinessName() {
        return clientBusinessName;
    }

    public void setClientBusinessName(String clientBusinessName) {
        this.clientBusinessName = clientBusinessName;
    }

    public String getClientBusinessLocationlat() {
        return clientBusinessLocationlat;
    }

    public void setClientBusinessLocationlat(String clientBusinessLocationlat) {
        this.clientBusinessLocationlat = clientBusinessLocationlat;
    }

    public String getClientBusinessLocationlang() {
        return clientBusinessLocationlang;
    }

    public void setClientBusinessLocationlang(String clientBusinessLocationlang) {
        this.clientBusinessLocationlang = clientBusinessLocationlang;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientProductType() {
        return clientProductType;
    }

    public void setClientProductType(String clientProductType) {
        this.clientProductType = clientProductType;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
