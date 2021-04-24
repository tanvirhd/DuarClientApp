package com.duarbd.duarclientapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "client_table")
public class ModelClientRoom {

    @PrimaryKey (autoGenerate = false)
    @NonNull
    private String clientid;

    private String clientContactNumber;

    private String clientBusinessName;

    private String clientBusinessLocationlat;

    private String clientBusinessLocationlang;

    private String clientAddress;

    private String clientProductType;

    private Integer pickupCharge;

    public ModelClientRoom(@NonNull String clientid, String clientContactNumber, String clientBusinessName,
                           String clientBusinessLocationlat, String clientBusinessLocationlang, String clientAddress,
                           String clientProductType, Integer pickupCharge) {
        this.clientid = clientid;
        this.clientContactNumber = clientContactNumber;
        this.clientBusinessName = clientBusinessName;
        this.clientBusinessLocationlat = clientBusinessLocationlat;
        this.clientBusinessLocationlang = clientBusinessLocationlang;
        this.clientAddress = clientAddress;
        this.clientProductType = clientProductType;
        this.pickupCharge = pickupCharge;
    }

    @NonNull
    public String getClientid() {
        return clientid;
    }

    public void setClientid(@NonNull String clientid) {
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

    public Integer getPickupCharge() {
        return pickupCharge;
    }

    public void setPickupCharge(Integer pickupCharge) {
        this.pickupCharge = pickupCharge;
    }
}
