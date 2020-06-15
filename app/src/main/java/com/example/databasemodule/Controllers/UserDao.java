package com.example.databasemodule.Controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.databasemodule.Models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... user);

    @Query("UPDATE users SET login = :login, password = :password, is_admin = :isAdmin WHERE id == :id")
    void updateUser(int id, String login, String password, boolean isAdmin);

    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Query("DELETE FROM users where id == :id")
    void deleteUser(int id);

    @Query("SELECT * FROM users WHERE login=:login AND password=:password LIMIT 1")
    User findUser(String login, String password);
}
