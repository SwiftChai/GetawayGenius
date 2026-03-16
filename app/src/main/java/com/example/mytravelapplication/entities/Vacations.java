package com.example.mytravelapplication.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacations {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String hotel;
    private String startDate;
    private String endDate;


    public Vacations(String vacationName, String hotel, String startDate, String endDate) {
        this.vacationName = vacationName;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Ignore
    public Vacations(int vacationID, String vacationName, String hotel, String startDate, String endDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public void setVacationID(int VacationID) {
        this.vacationID = VacationID;
    }

    public int getVacationID() {
        return vacationID;
    }
    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

