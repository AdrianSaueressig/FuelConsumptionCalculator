package com.nolari.benzinverbrauchberechner.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Nolari on 03.03.2018.
 */

@Database(entities = {TankEntry.class}, version = 3)
public abstract class TankDatabase extends RoomDatabase {

    public abstract TankEntryDao getTankEntryDao();
}
