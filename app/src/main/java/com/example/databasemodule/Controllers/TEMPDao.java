package com.example.databasemodule.Controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.databasemodule.Models.TEMP;

import java.util.List;

@Dao
public interface TEMPDao {
    @Query("SELECT * FROM `TEMP`")
    List<TEMP> selectAllTemps();

    @Query("SELECT * FROM `TEMP` WHERE TIMESTAMP >= :time1 AND TIMESTAMP <= :time2")
    List<TEMP> getTempBetweenTimestamps(long time1, long time2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTemps(TEMP... temps);

    @Query("SELECT * FROM `TEMP` WHERE id == :id")
    TEMP selectTemp(int id);

    @Query("UPDATE `TEMP` SET value = :value, TIMESTAMP = :timestamp WHERE id == :id")
    void updateTemp(int id, double value, long timestamp);

    @Query("DELETE FROM `TEMP`")
    void deleteAllTemps();

    @Query("DELETE FROM `TEMP` WHERE id == :id")
    void deleteTemp(int id);
}
