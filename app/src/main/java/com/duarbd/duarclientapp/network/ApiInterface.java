package com.duarbd.duarclientapp.network;

import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;
import com.duarbd.duarclientapp.model.ModelPaymentResponse;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.model.ModelToken;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("clientLogin.php")
    Observable<ModelResponse> clientLogin(@Body  ModelClient client);

    @POST("storeFCMToken.php")
    ModelResponse storeFCMToken(@Body ModelToken clientToken);

    @POST("storeFCMToken.php")
    Observable<ModelResponse> storeFCMToken2(@Body ModelToken clientToken);

    @POST("updateFCMToken.php")
    ModelResponse updateFCMToken(@Body ModelToken clientToken);// ModelToken(uid,token)

    @POST("updateFCMToken.php")
    Observable<ModelResponse> updateFCMToken2(@Body ModelToken clientToken);// ModelToken(uid,token)

    @POST("pnRequestNewDelivery.php")
    Observable<ModelResponse> sendDeliveryRequest(@Body ModelDeliveryRequest deliveryRequest);

    @POST("getRequestedDeliveryByClientId.php")
    Observable<List<ModelDeliveryRequest>> getRequestedDeliveryListByClientId(@Body  ModelClient client);

    @POST("getDeliveryHistoryByClientId.php")
    Observable<List<ModelDeliveryRequest>> getDeliveryHistoryByClientId(@Body ModelClient client);


    @POST("updateClientPaymentStatus.php")
    Observable<ModelResponse> updateClientPaymentStatus(@Body ModelDeliveryRequest deliveryRequest);

    @POST("getPaymentStatusByClientId.php")
    Observable<ModelPaymentResponse> getPaymentStatusByClientId(@Body ModelClient client);

}
