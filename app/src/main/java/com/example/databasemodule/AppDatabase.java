package com.example.databasemodule;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.databasemodule.Controllers.ConfigDao;
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Models.Config;
import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.Models.User;

@androidx.room.Database(
        entities = {
                Config.class,
                User.class,
                Measurement.class
        }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ConfigDao configDao();
    public abstract UserDao userDao();
    public abstract MeasurementDao measurementDao();

}