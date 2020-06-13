package com.example.databasemodule.Controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.databasemodule.Models.PRESS;

import java.util.List;

@Dao
public interface PRESSDao {
    @Query("SELECT * FROM PRESS")
    List<PRESS> selectAllPresses();

    @Query("SELECT * FROM PRESS WHERE TIMESTAMP >= :time1 AND TIMESTAMP <= :time2")
    List<PRESS> getPressBetweenTimestamps(long time1, long time2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPresses(PRESS... presses);

    @Query("SELECT * FROM PRESS WHERE id == :id")
    PRESS selectPress(int id);

    @Query("UPDATE PRESS SET value = :value, TIMESTAMP = :timestamp WHERE id == :id")
    void updatePress(int id, double value, long timestamp);

    @Query("DELETE FROM PRESS")
    void deleteAllPresses();

    @Query("DELETE FROM PRESS WHERE id == :id")
    void deletePress(int id);
}
