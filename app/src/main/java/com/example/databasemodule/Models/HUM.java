package com.example.databasemodule.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HUM")
public class HUM {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "value")
    public double value;

    @ColumnInfo(name = "TIMESTAMP")
    public long TIMESTAMP;
}
