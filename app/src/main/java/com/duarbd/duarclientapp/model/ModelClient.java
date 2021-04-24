package com.duarbd.duarclientapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelClient {

    @SerializedName("clientid")
    @Expose
    private String clientid;

    @SerializedName("clientPassword")
    @Expose
    private String clientPassword;

    public ModelClient(String clientid, String clientPassword) {
        this.clientid = clientid;
        this.clientPassword = clientPassword;
    }

    public ModelClient(String clientid) {
        this.clientid = clientid;
    }

    public ModelClient() {
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }
}
