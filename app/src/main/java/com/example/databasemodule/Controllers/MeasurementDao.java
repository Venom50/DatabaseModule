package com.example.databasemodule.Controllers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.databasemodule.Models.Measurement;

import java.util.List;

@Dao
public interface MeasurementDao {
    @Query("SELECT * FROM measurement_variables")
    List<Measurement> selectAllMeasurements();

    @Query("SELECT * FROM measurement_variables WHERE id == :id")
    void selectMeasurement(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMeasurements(Measurement... measurements);

    @Query("UPDATE measurement_variables SET `TEMP` = :TEMP, HUM = :HUM, PRESS = :PRESS, BAT_V = :BAT_V, BAT_I = :BAT_I, SOLAR_V = :SOLAR_V, SOLAR_I = :SOLAR_I, NODE_U = :NODE_U, NODE_I = :NODE_I WHERE id == :id")
    void updateMeasurement(int id, double TEMP, double HUM, double PRESS, double BAT_V, double BAT_I, double SOLAR_V, double SOLAR_I, double NODE_U, double NODE_I);

    @Query("DELETE FROM measurement_variables")
    void deleteAllMeasurements();

    @Query("DELETE FROM measurement_variables WHERE id == :id")
    void deleteMeasurement(int id);
}
