package com.duarbd.duarclientapp.network.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.model.ModelToken;
import com.duarbd.duarclientapp.network.repository.RepositoryDuarClientApp;
import com.duarbd.duarclientapp.network.work.WorkStoreToken;
import com.duarbd.duarclientapp.network.work.WorkUpdateToken;

public class ViewModelDuarClientApp extends AndroidViewModel {
    private RepositoryDuarClientApp repository;
    private Context context;
    public static final String PRAM1="key1";
    public static final String PRAM2="key2";
    public static final String PRAM3="key3";
    public static final String PRAM4="key4";
    public static final String PRAM5="key5";

    public ViewModelDuarClientApp(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
        if(repository==null) repository=new RepositoryDuarClientApp();
    }

    public LiveData<ModelResponse> clientLogin(ModelClient client){
        return repository.clientLogin(client);
    }

    public LiveData<ModelResponse> updateToken2(ModelToken clientToken){
        return repository.updateToken2(clientToken);
    }

    public LiveData<ModelResponse> storeToken2(ModelToken clientToken){
        return repository.storeToken2(clientToken);
    }

    public void updateFCMToken(ModelToken token){
        Data inputdata=new Data.Builder()
                .putString(PRAM1,token.getUid())
                .putString(PRAM2,token.getToken())
                .build();

        Constraints constraints=new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest request=new OneTimeWorkRequest.Builder(WorkUpdateToken.class)
                .setInputData(inputdata)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(context).enqueue(request);
    }

    public void storeFCMToken(ModelToken token){
        Data inputdata=new Data.Builder()
                .putString(PRAM1,token.getUid())
                .putString(PRAM2,token.getTokencategory())
                .putString(PRAM3,token.getToken())
                .build();

        Constraints constraints=new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest request=new OneTimeWorkRequest.Builder(WorkStoreToken.class)
                .setInputData(inputdata)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(context).enqueue(request);
    }
}
