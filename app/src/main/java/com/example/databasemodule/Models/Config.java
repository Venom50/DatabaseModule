package com.example.databasemodule.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "configuration_variables")
public class Config implements Serializable {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "TEMP_F")
    private double TEMP_F;

    @ColumnInfo(name = "HUM_F")
    private double HUM_F;

    @ColumnInfo(name = "PRESS_F")
    private double PRESS_F;

    @ColumnInfo(name = "ENERGY_F")
    private double ENERGY_F;

    @ColumnInfo(name = "TIMESTAMP")
    private String TIMESTAMP;
}
