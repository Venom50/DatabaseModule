package com.example.databasemodule.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "measurement_variables")
public class Measurement {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "TEMP")
    public double TEMP;

    @ColumnInfo(name = "HUM")
    public double HUM;

    @ColumnInfo(name = "PRESS")
    public double PRESS;

    @ColumnInfo(name = "BAT_V")
    public double BAT_V;

    @ColumnInfo(name = "BAT_I")
    public double BAT_I;

    @ColumnInfo(name = "SOLAR_V")
    public double SOLAR_V;

    @ColumnInfo(name = "SOLAR_I")
    public double SOLAR_I;

    @ColumnInfo(name = "NODE_U")
    public double NODE_U;

    @ColumnInfo(name = "NODE_I")
    public double NODE_I;

    @ColumnInfo(name = "TIMESTAMP")
    public long TIMESTAMP;
}
