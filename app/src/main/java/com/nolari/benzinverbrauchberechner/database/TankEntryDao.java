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

    @Query("SELECT * FROM TankEntry ORDER BY kilometres desc LIMIT :amount")
    List<TankEntry> getNewestEntries(int amount);

    @Query("SELECT * FROM TankEntry WHERE uid = (SELECT uid FROM TankEntry where kilometres <= :referenceKilometres order by kilometres desc, date desc limit 1)")
    TankEntry getLastEntry(float referenceKilometres);

    @Query("SELECT * FROM TankEntry where uid = :uid")
    TankEntry getEntry(int uid);

    @Update
    void updateTankEntry(TankEntry tankEntry);
}
