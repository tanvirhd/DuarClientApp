package com.duarbd.duarclientapp.model;

public class ModelOngoingDelivery {
    private String deliveryid,coustomerName,deliveryAddress;

    public ModelOngoingDelivery(String deliveryid, String coustomerName, String deliveryAddress) {
        this.deliveryid = deliveryid;
        this.coustomerName = coustomerName;
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(String deliveryid) {
        this.deliveryid = deliveryid;
    }

    public String getCoustomerName() {
        return coustomerName;
    }

    public void setCoustomerName(String coustomerName) {
        this.coustomerName = coustomerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
