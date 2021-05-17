package com.duarbd.duarclientapp.network.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.model.ModelToken;
import com.duarbd.duarclientapp.network.ApiClient;
import com.duarbd.duarclientapp.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@SuppressLint("CheckResult")
public class RepositoryDuarClientApp {
    private static final String TAG = "RepositoryDuarClientApp";
    private ApiInterface apiRequest;


    public RepositoryDuarClientApp() {
        apiRequest= ApiClient.getApiInterface();
    }

    public LiveData<ModelResponse> clientLogin(ModelClient client){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.clientLogin(client).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "clientLogin: erroe: "+throwable.getMessage());
                    }
                });
        return result;
    }

    public LiveData<ModelResponse> updateToken2(ModelToken clienttoken){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.updateFCMToken2(clienttoken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "updateToken2: error:"+throwable.getMessage());
                    }
                });
        return result;
    }

    public LiveData<ModelResponse> storeToken2(ModelToken clienttoken){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.storeFCMToken2(clienttoken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "storeToken2: error:"+throwable.fillInStackTrace());
                    }
                });
        return result;
    }

    public LiveData<ModelResponse> sendDeliveryRequest(ModelDeliveryRequest deliveryRequest){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.sendDeliveryRequest(deliveryRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "requestNewDelivery: error:"+throwable.getMessage());
                    }
                });
        return result;
    }

    public LiveData<List<ModelDeliveryRequest>> getRequestedDeliveryListByClientId(String clientId){
        ModelClient client=new ModelClient(clientId);
        MutableLiveData<List<ModelDeliveryRequest>> result=new MutableLiveData<>();
        apiRequest.getRequestedDeliveryListByClientId(client).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void accept(List<ModelDeliveryRequest> modelDeliveryRequests) throws Exception {
                        result.postValue(modelDeliveryRequests);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "getRequestedDeliveryListByClientId: error:"+throwable.getMessage());
                    }
                });
        return result;
    }

    public LiveData<List<ModelDeliveryRequest>> getDeliveryHistoryByClientId(String clientid){
        ModelClient client=new ModelClient(clientid);
        MutableLiveData<List<ModelDeliveryRequest>> result=new MutableLiveData<>();
        apiRequest.getDeliveryHistoryByClientId(client).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void accept(List<ModelDeliveryRequest> modelDeliveryRequests) throws Exception {
                        result.postValue(modelDeliveryRequests);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "getDeliveryHistoryByClientId: error"+throwable.getMessage());
                    }
                });
        return  result;
    }

    public LiveData<ModelResponse> updateClientPaymentStatus (ModelDeliveryRequest deliveryRequest){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.updateClientPaymentStatus(deliveryRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse response) throws Exception {
                        result.postValue(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "updateClientPaymentStatus: error:"+throwable.getMessage());
                    }
                });
        return result;
    }

    public LiveData<ModelResponse> updateClientPassword(ModelClient client){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.updateClientPassword(client).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse response) throws Exception {
                        result.postValue(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "updateClientPassword: error: "+throwable.getMessage());
                    }
                });
        return result;
    }
}
