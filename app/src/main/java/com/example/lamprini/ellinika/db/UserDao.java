package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * @author Lamprini Georgatsou
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT * FROM User WHERE name LIKE :name")
    User getUser(String name);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Delete
    void deleteUsers(List<User> users);

}
