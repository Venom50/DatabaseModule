package com.example.databasemodule.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "configuration_variables")
public class Config implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "TEMP_F")
    public double TEMP_F;

    @ColumnInfo(name = "HUM_F")
    public double HUM_F;

    @ColumnInfo(name = "PRESS_F")
    public double PRESS_F;

    @ColumnInfo(name = "ENERGY_F")
    public double ENERGY_F;

    @ColumnInfo(name = "TIMESTAMP")
    public String TIMESTAMP;
}
