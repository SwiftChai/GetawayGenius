package com.example.mytravelapplication.database;

import android.app.Application;
import com.example.mytravelapplication.DAO.ExcursionDAO;
import com.example.mytravelapplication.DAO.VacationDAO;
import com.example.mytravelapplication.entities.Excursions;
import com.example.mytravelapplication.entities.Vacations;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final ExcursionDAO mExcursionDAO;
    private final VacationDAO mVacationDAO;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    public interface OnDataReceivedCallback<T> {
        void onDataReceived(T data);
    }

    public Repository(Application application) {
        VacationDatabase db = VacationDatabase.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }

    public void getAllVacations(OnDataReceivedCallback<List<Vacations>> callback) {
        databaseExecutor.execute(() -> callback.onDataReceived(mVacationDAO.getAllVacations()));
    }

    public void insert(Vacations v, OnDataReceivedCallback<Void> callback) {
        databaseExecutor.execute(() -> { mVacationDAO.insert(v); if (callback != null) callback.onDataReceived(null); });
    }

    public void update(Vacations v, OnDataReceivedCallback<Void> callback) {
        databaseExecutor.execute(() -> { mVacationDAO.update(v); if (callback != null) callback.onDataReceived(null); });
    }

    public void delete(Vacations v, OnDataReceivedCallback<Void> callback) {
        databaseExecutor.execute(() -> { mVacationDAO.delete(v); if (callback != null) callback.onDataReceived(null); });
    }

    public void getVacationById(int id, OnDataReceivedCallback<Vacations> callback) {
        databaseExecutor.execute(() -> callback.onDataReceived(mVacationDAO.getVacationById(id)));
    }

    public void getAllExcursions(OnDataReceivedCallback<List<Excursions>> callback) {
        databaseExecutor.execute(() -> callback.onDataReceived(mExcursionDAO.getAllExcursions()));
    }

    public void insert(Excursions e, OnDataReceivedCallback<Void> callback) {
        databaseExecutor.execute(() -> { mExcursionDAO.insert(e); if (callback != null) callback.onDataReceived(null); });
    }

    public void update(Excursions e, OnDataReceivedCallback<Void> callback) {
        databaseExecutor.execute(() -> { mExcursionDAO.update(e); if (callback != null) callback.onDataReceived(null); });
    }

    public void delete(Excursions e, OnDataReceivedCallback<Void> callback) {
        databaseExecutor.execute(() -> { mExcursionDAO.delete(e); if (callback != null) callback.onDataReceived(null); });
    }

    public void getExcursionById(int id, OnDataReceivedCallback<Excursions> callback) {
        databaseExecutor.execute(() -> callback.onDataReceived(mExcursionDAO.getExcursionById(id)));
    }
}