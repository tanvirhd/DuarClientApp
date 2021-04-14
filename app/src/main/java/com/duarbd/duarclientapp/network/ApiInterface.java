package com.duarbd.duarclientapp.network;

import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.model.ModelToken;


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

}
