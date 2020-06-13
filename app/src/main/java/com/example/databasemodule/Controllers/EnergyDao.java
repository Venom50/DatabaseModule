package com.example.databasemodule.Controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.databasemodule.Models.Energy;

import java.util.List;

@Dao
public interface EnergyDao {
    @Query("SELECT * FROM energy")
    List<Energy> selectAllEnergy();

    @Query("SELECT * FROM energy WHERE TIMESTAMP >= :time1 AND TIMESTAMP <= :time2")
    List<Energy> getEnergyBetweenTimestamps(long time1, long time2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllEnergy(Energy... energy);

    @Query("SELECT * FROM energy WHERE id == :id")
    Energy selectEnergy(int id);

    @Query("UPDATE energy SET BAT_V = :BAT_V, BAT_I = :BAT_I, SOLAR_V = :SOLAR_V, SOLAR_I = :SOLAR_I, NODE_U = :NODE_U, NODE_I = :NODE_I, TIMESTAMP = :timestamp WHERE id == :id")
    void updateEnergy(int id, double BAT_V, double BAT_I, double SOLAR_V, double SOLAR_I, double NODE_U, double NODE_I, long timestamp);

    @Query("DELETE FROM energy")
    void deleteAllEnergy();

    @Query("DELETE FROM energy WHERE id == :id")
    void deleteEnergy(int id);
}
