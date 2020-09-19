package com.nolari.benzinverbrauchberechner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

    @Query("SELECT AVG(pricePerLitre) FROM TankEntry")
    float getAveragePricePerLitre();

    @Query("SELECT AVG(litres) FROM TankEntry")
    float getAverageLitres();

    @Query("SELECT AVG(tripmeter) FROM TankEntry where tripmeter > 0")
    float getAverageTripmeter();

    @Query("SELECT 100*(SUM(litres)/SUM(tripmeter)) FROM TankEntry where tripmeter > 0")
    float getAverageConsumption();

    @Update
    void updateTankEntry(TankEntry tankEntry);
}
