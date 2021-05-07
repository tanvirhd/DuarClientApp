package com.duarbd.duarclientapp.interfaces;

import com.duarbd.duarclientapp.model.ModelDeliveryRequest;

public interface AdapterPaymentCallback {
    void onViewDetailsClicked(ModelDeliveryRequest deliveryRequest);
    void markAsPaid(ModelDeliveryRequest deliveryRequest);
}
