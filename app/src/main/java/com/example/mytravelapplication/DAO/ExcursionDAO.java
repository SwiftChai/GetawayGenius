package com.example.mytravelapplication.DAO;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytravelapplication.entities.Excursions;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = IGNORE)
    void insert(Excursions excursion);

    @Update
    void update(Excursions excursion);

    @Delete
    void delete(Excursions excursion);

    @Query("SELECT * FROM excursions ORDER BY excursionID ASC")
    List<Excursions> getAllExcursions();

    @Query("SELECT * FROM excursions WHERE excursionID = :id")
    Excursions getExcursionById(int id);

    @Query("SELECT * FROM excursions WHERE vacationID = :vacationID ORDER BY excursionID ASC")
    List<Excursions> getAssociatedExcursions(int vacationID);

    @Query("SELECT * FROM excursions WHERE excursionName LIKE '%' || :query || '%'")
    List<Excursions> searchExcursions(String query);
}
