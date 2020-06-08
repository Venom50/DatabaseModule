package com.example.databasemodule.Controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.databasemodule.Models.HUM;

import java.util.List;

@Dao
public interface HUMDao {
    @Query("SELECT * FROM HUM")
    List<HUM> selectAllHums();

    @Query("SELECT * FROM HUM WHERE TIMESTAMP >= :time1 AND TIMESTAMP <= :time2")
    HUM getHumBetweenTimestamps(long time1, long time2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllHums(HUM... hums);

    @Query("SELECT * FROM HUM WHERE id == :id")
    HUM selectHum(int id);

    @Query("UPDATE HUM SET value = :value, TIMESTAMP = :timestamp WHERE id == :id")
    void updateHum(int id, double value, long timestamp);

    @Query("DELETE FROM HUM")
    void deleteAllHums();

    @Query("DELETE FROM HUM WHERE id == :id")
    void deleteHum(int id);
}
