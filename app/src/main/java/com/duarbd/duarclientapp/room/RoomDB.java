package com.duarbd.duarclientapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.duarbd.duarclientapp.model.ModelClientRoom;

@Database(entities = {ModelClientRoom.class},version = 1)
public abstract class RoomDB  extends RoomDatabase {
    private static RoomDB databaseInstance ;
    public abstract DCADao getDao();

    public static synchronized RoomDB getDbInstnace(Context context){
        if(databaseInstance==null){
            databaseInstance= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class,"dca_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return databaseInstance;
    }
}
