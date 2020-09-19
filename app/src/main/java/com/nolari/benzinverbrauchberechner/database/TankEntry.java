package com.nolari.benzinverbrauchberechner.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Nolari on 03.03.2018.
 */

@Entity
public class TankEntry {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private Date date;

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

    @ColumnInfo
    private String notes;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes(){
        return notes;
    }

    @Override
    public String toString(){
        return "Uid: " + uid + "\r\nDate: " + date + "\r\ntripmeter: " + tripmeter + "\nkilometers: " + kilometres + "\nlitres: " + litres + "\nprice per litre: " + pricePerLitre + "\nfuelConsumption: " + fuelConsumption + "\nnotes: " + notes;
    }
}
