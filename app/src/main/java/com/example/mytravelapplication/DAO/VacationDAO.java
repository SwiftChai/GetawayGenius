package com.example.mytravelapplication.DAO;

import static androidx.room.OnConflictStrategy.IGNORE;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytravelapplication.entities.Vacations;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = IGNORE)
    void insert(Vacations vacation);

    @Update
    void update(Vacations vacation);

    @Delete
    void delete(Vacations vacation);

    @Query("SELECT * FROM vacations ORDER BY vacationID ASC")
    List<Vacations> getAllVacations();

    @Query("SELECT * FROM vacations WHERE vacationID = :id")
    Vacations getVacationById(int id);
}