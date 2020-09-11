package com.nolari.benzinverbrauchberechner.database;

import androidx.room.Room;
import android.content.Context;

/**
 * Created by Nolari on 03.03.2018.
 */

public class DatabaseSingleton {

    private static TankDatabase tankDatabase = null;

    private DatabaseSingleton(){}

    public static TankDatabase getInstance(Context applicationContext){
        if(tankDatabase == null){
            tankDatabase = Room.databaseBuilder(applicationContext,
                    TankDatabase.class, "tank_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return tankDatabase;
    }
}
