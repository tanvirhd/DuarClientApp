package com.duarbd.duarclientapp.room;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelClientRoom;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.room.work.WorkSaveClientInfo;

import java.util.concurrent.Executors;

public class ViewModelRoom extends AndroidViewModel {
    private static final String TAG = "ViewModelRoom";
    public static final String PRAM1="key1";
    public static final String PRAM2="key2";
    public static final String PRAM3="key3";
    public static final String PRAM4="key4";
    public static final String PRAM5="key5";
    public static final String PRAM6="key6";
    public static final String PRAM7="key7";
    public static final String PRAM8="key8";

    DCADao dao;
    Context context;
    public ViewModelRoom(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        dao=RoomDB.getDbInstnace(context).getDao();
    }

    public void saveClientInfo(ModelResponse clientInfo){
        Data inputdata=new Data.Builder()
                .putString(PRAM1,clientInfo.getClientid())
                .putString(PRAM2,clientInfo.getClientContactNumber())
                .putString(PRAM3,clientInfo.getClientBusinessName())
                .putString(PRAM4,clientInfo.getClientBusinessLocationlat())
                .putString(PRAM5,clientInfo.getClientBusinessLocationlang())
                .putString(PRAM6,clientInfo.getClientAddress())
                .putString(PRAM7,clientInfo.getClientProductType())
                .putInt(PRAM8,clientInfo.getPickupCharge())
                .build();

        OneTimeWorkRequest request=new OneTimeWorkRequest.Builder(WorkSaveClientInfo.class)
                .setInputData(inputdata)
                .build();
        WorkManager.getInstance(context).enqueue(request);

    }

    public LiveData<ModelClientRoom> getClientInfo(String clientID){
        MutableLiveData<ModelClientRoom> result=new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(()->{
            result.postValue(dao.getClientInfo(clientID));
        });
        return result;
    }
}
