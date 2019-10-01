package com.nolari.benzinverbrauchberechner.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Nolari on 03.03.2018.
 */

@Dao
public interface TankEntryDao {

    @Insert
    void insertAll(TankEntry... entries);

    @Query("DELETE FROM TankEntry")
    void deleteAll();

    @Delete
    void delete(TankEntry entry);

    @Query("SELECT * FROM TankEntry ORDER BY uid desc LIMIT :amount")
    List<TankEntry> getNewestEntries(int amount);

    @Query("SELECT * FROM TankEntry WHERE uid = (SELECT MAX(uid) FROM TankEntry)")
    TankEntry getLastEntry();

    @Update
    void updateTankEntry(TankEntry tankEntry);
}
