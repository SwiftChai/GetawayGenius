package com.example.mytravelapplication.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytravelapplication.R;
import com.example.mytravelapplication.database.Repository;
import com.example.mytravelapplication.entities.Excursions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    private Repository repository;
    private int excursionID;
    private int vacationID;
    private String vacationStartDate;
    private String vacationEndDate;
    private EditText excursionTitle;
    private EditText excursionDate;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());
        sdf.setLenient(false);

        Intent intent = getIntent();
        excursionID = intent.getIntExtra("excursionID", -1);
        vacationID = intent.getIntExtra("vacationID", -1);
        vacationStartDate = intent.getStringExtra("vacationStartDate");
        vacationEndDate = intent.getStringExtra("vacationEndDate");

        initViews();
        setupDatePicker();

        if (vacationStartDate == null || vacationEndDate == null) {
            repository.getVacationById(vacationID, vacation -> {
                if (vacation != null) {
                    vacationStartDate = vacation.getStartDate();
                    vacationEndDate = vacation.getEndDate();
                }
                if (excursionID != -1) {
                    populateExcursionDetails();
                }
            });
        } else {
            if (excursionID != -1) {
                populateExcursionDetails();
            }
        }
    }
    private void initViews() {
        excursionTitle = findViewById(R.id.excursion_title);
        excursionDate = findViewById(R.id.excursion_date);
    }

    private void setupDatePicker() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                excursionDate.setText(sdf.format(calendar.getTime()));
            }
        };

        excursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateStr = excursionDate.getText().toString();
                if (!dateStr.isEmpty()) {
                    try {
                        calendar.setTime(sdf.parse(dateStr));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                new DatePickerDialog(ExcursionDetails.this, dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void populateExcursionDetails() {
        repository.getExcursionById(excursionID, excursion -> {
            if (excursion != null) {
                runOnUiThread(() -> {
                    excursionTitle.setText(excursion.getExcursionName());
                    excursionDate.setText(excursion.getExcursionDate());
                });
            }
        });
    }
    private void saveExcursion() {
        String title = excursionTitle.getText().toString().trim();
        String date = excursionDate.getText().toString().trim();

        if (title.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isDateInRange(date)) {
            Toast.makeText(this, "Excursion date must be within vacation dates", Toast.LENGTH_LONG).show();
            return;
        }

        if (excursionID == -1) {
            repository.getAllExcursions(excursions -> {
                int newId = excursions.isEmpty() ? 1 : excursions.get(excursions.size() - 1).getExcursionID() + 1;
                Excursions newExcursion = new Excursions(newId, title, date, vacationID);
                repository.insert(newExcursion, v ->
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Excursion saved", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                );
            });
        } else {
            repository.update(new Excursions(excursionID, title, date, vacationID), v ->
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Excursion updated", Toast.LENGTH_SHORT).show();
                        finish();
                    })
            );
        }
    }

    private boolean isDateInRange(String dateStr) {
        try {
            Date excDate = sdf.parse(dateStr);
            Date startDate = sdf.parse(vacationStartDate);
            Date endDate = sdf.parse(vacationEndDate);
            return !excDate.before(startDate) && !excDate.after(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteExcursion() {
        if (excursionID != -1) {
            repository.getExcursionById(excursionID, excursion -> {
                if (excursion != null) {
                    repository.delete(excursion, null); // ← add null here
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Excursion deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        }
    }

    private void setExcursionAlert() {
        String dateStr = excursionDate.getText().toString();
        if (dateStr.isEmpty()) {
            Toast.makeText(this, "Please set an excursion date first", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Date excDate = sdf.parse(dateStr);
            long trigger = excDate.getTime();

            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            intent.putExtra("key", "Excursion alert: " + excursionTitle.getText().toString());

            PendingIntent sender = PendingIntent.getBroadcast(
                    ExcursionDetails.this,
                    MainActivity.numAlert++,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            Toast.makeText(this, "Alert set for excursion", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveExcursion();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            deleteExcursion();
            return true;
        } else if (item.getItemId() == R.id.action_notify) {
            setExcursionAlert();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}