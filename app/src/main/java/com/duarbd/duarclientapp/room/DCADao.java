package com.duarbd.duarclientapp.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelClientRoom;

@Dao
public interface DCADao {
    @Insert
    void saveClientInfo(ModelClientRoom client);

    @Query("select * from client_table where clientid=:clientID")
    ModelClientRoom getClientInfo(String clientID);

    @Query("delete from client_table where clientid=:clientID")
    void deleteClientInfo(String clientID);
}
