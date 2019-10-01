package com.nolari.benzinverbrauchberechner.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Nolari on 03.03.2018.
 */

@Entity
public class TankEntry {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private String date;

    @ColumnInfo
    private float litres;

    @ColumnInfo
    private float pricePerLitre;

    @ColumnInfo
    private float tripmeter;

    @ColumnInfo
    private float kilometres;

    @ColumnInfo
    private float fuelConsumption;

    public float getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(float fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getLitres() {
        return litres;
    }

    public void setLitres(float litres) {
        this.litres = litres;
    }

    public float getPricePerLitre() {
        return pricePerLitre;
    }

    public void setPricePerLitre(float pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
    }

    public float getTripmeter() {
        return tripmeter;
    }

    public void setTripmeter(float tripmeter) {
        this.tripmeter = tripmeter;
    }

    public float getKilometres() {
        return kilometres;
    }

    public void setKilometres(float kilometres) {
        this.kilometres = kilometres;
    }

    @Override
    public String toString(){
        return "Uid: " + uid + " Date: " + date + " tripmeter: " + tripmeter + " kilometers: " + kilometres + " litres: " + litres + " price per litre: " + pricePerLitre + " fuelConsumption: " + fuelConsumption;
    }
}
