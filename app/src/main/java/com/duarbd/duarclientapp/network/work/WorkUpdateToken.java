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
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;

import static com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp.PRAM1;
import static com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp.PRAM2;

public class WorkUpdateToken extends Worker {
    ApiInterface apirequest;
    public WorkUpdateToken(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        apirequest= ApiClient.getApiInterface();
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        ModelToken updatedToken=new ModelToken(data.getString(PRAM1),data.getString(PRAM2));
        ModelResponse response=apirequest.updateFCMToken(updatedToken);
        if(response.getResponse()==1){
            Utils.savePrefBoolean(GlobalKey.IS_TOKEN_AVAILABLE,true);
            return Result.success();
        } else return Result.failure();
    }
}
