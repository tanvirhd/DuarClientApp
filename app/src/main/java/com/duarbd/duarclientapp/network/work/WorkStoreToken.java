package com.duarbd.duarclientapp.network.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.model.ModelToken;
import com.duarbd.duarclientapp.network.ApiClient;
import com.duarbd.duarclientapp.network.ApiInterface;

import static com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp.PRAM1;
import static com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp.PRAM2;
import static com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp.PRAM3;

public class WorkStoreToken extends Worker {
    ApiInterface apirequest;
    public WorkStoreToken(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        apirequest= ApiClient.getApiInterface();
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        ModelToken clientToken=new ModelToken(data.getString(PRAM1),data.getString(PRAM2),data.getString(PRAM3));
        ModelResponse response=apirequest.storeFCMToken(clientToken);
        if(response.getResponse()==1)
            return Result.success();
        else return Result.failure();
    }
}
