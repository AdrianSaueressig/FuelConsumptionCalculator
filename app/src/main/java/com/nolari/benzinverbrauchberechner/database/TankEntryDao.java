package com.nolari.benzinverbrauchberechner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
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

    @Query("SELECT * FROM TankEntry ORDER BY date desc LIMIT :amount")
    List<TankEntry> getNewestEntries(int amount);

    @Query("SELECT * FROM TankEntry WHERE date = (SELECT MAX(date) FROM TankEntry where date < :referenceDate)")
    TankEntry getLastEntry(Date referenceDate);

    @Update
    void updateTankEntry(TankEntry tankEntry);
}
