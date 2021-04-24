package com.duarbd.duarclientapp.room.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.duarbd.duarclientapp.model.ModelClientRoom;
import com.duarbd.duarclientapp.room.DCADao;
import com.duarbd.duarclientapp.room.RoomDB;

import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM1;
import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM2;
import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM3;
import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM4;
import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM5;
import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM6;
import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM7;
import static com.duarbd.duarclientapp.room.ViewModelRoom.PRAM8;

public class WorkSaveClientInfo extends Worker {
    private static final String TAG = "WorkSaveClientInfo";
    DCADao dao;

    public WorkSaveClientInfo(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        dao= RoomDB.getDbInstnace(context).getDao();
    }

    @NonNull
    @Override
    public Result doWork() {
        Data inputdata=getInputData();
        ModelClientRoom client=new
                ModelClientRoom(inputdata.getString(PRAM1), inputdata.getString(PRAM2), inputdata.getString(PRAM3),
                inputdata.getString(PRAM4), inputdata.getString(PRAM5), inputdata.getString(PRAM6),
                inputdata.getString(PRAM7), inputdata.getInt(PRAM8,0));

        try{
            dao.saveClientInfo(client);
            return Result.success();
        }catch (Exception e){
            Log.d(TAG, "doWork: error" + e.getMessage());
            Data error = new Data.Builder()
                    .putString("SQL_exception", e.getMessage())
                    .build();
            return Result.failure(error);
        }
    }
}
