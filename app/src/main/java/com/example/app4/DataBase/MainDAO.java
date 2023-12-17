package com.example.app4.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.example.app4.Models.Box;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert (Box box);

    @Query("SELECT * FROM boxes ORDER BY id DESC")
    List<Box> getAll();

    @Query("UPDATE boxes SET title = :title, notes = :notes WHERE ID = :ID")
    void update(int ID, String title, String notes);

    @Delete
    void delete(Box box);



}
