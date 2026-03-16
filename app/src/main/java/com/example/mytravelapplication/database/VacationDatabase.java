package com.example.mytravelapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mytravelapplication.DAO.ExcursionDAO;
import com.example.mytravelapplication.DAO.VacationDAO;
import com.example.mytravelapplication.entities.Excursions;
import com.example.mytravelapplication.entities.Vacations;

@Database(entities = {Excursions.class, Vacations.class}, version = 1, exportSchema = false)
public abstract class VacationDatabase extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile VacationDatabase INSTANCE;

    static VacationDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (VacationDatabase.class){
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),VacationDatabase.class, "VacationPlanner.db")
                    .fallbackToDestructiveMigration()
                            .build();


                }

            }
        }

        return INSTANCE;
    }
}
