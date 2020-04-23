package com.example.databasemodule.Controllers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.databasemodule.Models.Config;

import java.util.List;

@Dao
public interface ConfigDao {
    @Query("SELECT * FROM configuration_variables")
    List<Config> selectAllConfigs();

    @Query("SELECT * FROM configuration_variables WHERE id == :id")
    void selectConfig(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllConfigs(Config... configs);

    @Query("UPDATE configuration_variables SET TEMP_F = :TEMP_F, HUM_F = :HUM_F, PRESS_F = :PRESS_F, ENERGY_F = :ENERGY_F, TIMESTAMP = :TIMESTAMP WHERE id == :id")
    void updateConfig(int id, double TEMP_F, double HUM_F, double PRESS_F, double ENERGY_F, String TIMESTAMP);

    @Query("DELETE FROM configuration_variables")
    void deleteAllConfigs();

    @Query("DELETE FROM configuration_variables WHERE id == :id")
    void deleteConfig(int id);
}
