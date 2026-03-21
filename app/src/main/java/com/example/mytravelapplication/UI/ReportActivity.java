package com.example.mytravelapplication.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravelapplication.R;
import com.example.mytravelapplication.database.Repository;
import com.example.mytravelapplication.entities.Excursions;
import com.example.mytravelapplication.entities.Vacations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {

    private TextView startDateTextView;
    private TextView endDateTextView;
    private Button reportButton;
    private TextView genReport;
    private TextView timeStamp;
    private RecyclerView reportRecyclerView;

    private Repository repository;
    private ReportAdapter reportAdapter;


    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    private String startDate = "";
    private String endDate   = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        startDateTextView  = findViewById(R.id.startDateTextView);
        endDateTextView    = findViewById(R.id.endDateTextView);
        reportButton       = findViewById(R.id.reportButton);
        genReport          = findViewById(R.id.genReport);
        timeStamp          = findViewById(R.id.timeStamp);
        reportRecyclerView = findViewById(R.id.reportRecyclerView);

        repository = new Repository(getApplication());


        reportAdapter = new ReportAdapter(new ArrayList<>());
        reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportRecyclerView.setAdapter(reportAdapter);

        setupDatePickers();
        setupReportButton();
    }

    private void setupDatePickers() {
        startDateTextView.setOnClickListener(v -> showDatePicker(true));
        endDateTextView.setOnClickListener(v   -> showDatePicker(false));
    }

    private void showDatePicker(final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();

        if (isStartDate && !startDate.isEmpty()) {
            try { calendar.setTime(sdf.parse(startDate)); } catch (ParseException ignored) {}
        } else if (!isStartDate && !endDate.isEmpty()) {
            try { calendar.setTime(sdf.parse(endDate)); } catch (ParseException ignored) {}
        }

        new DatePickerDialog(
                ReportActivity.this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    Calendar picked = Calendar.getInstance();
                    picked.set(year, month, dayOfMonth);
                    // Store in the same MM/dd/yy format the rest of the app uses
                    String formatted = sdf.format(picked.getTime());
                    if (isStartDate) {
                        startDate = formatted;
                        startDateTextView.setText("Start: " + startDate);
                    } else {
                        endDate = formatted;
                        endDateTextView.setText("End: " + endDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }


    private void setupReportButton() {
        reportButton.setOnClickListener(v -> generateReport());
    }

    private void generateReport() {
        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please select both a start and end date", Toast.LENGTH_SHORT).show();
            return;
        }

        Date filterStart, filterEnd;
        try {
            filterStart = sdf.parse(startDate);
            filterEnd   = sdf.parse(endDate);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!filterEnd.after(filterStart)) {
            Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show();
            return;
        }


        genReport.setText("Vacation Report: " + startDate + " – " + endDate);
        String currentDateTime = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        timeStamp.setText("Generated on: " + currentDateTime);

        final Date fStart = filterStart;
        final Date fEnd   = filterEnd;

        repository.getAllExcursions(allExcursions -> {


            Map<Integer, Integer> excursionCountMap = new HashMap<>();
            if (allExcursions != null) {
                for (Excursions e : allExcursions) {
                    int vid = e.getVacationID();
                    excursionCountMap.put(vid, excursionCountMap.getOrDefault(vid, 0) + 1);
                }
            }

            repository.getAllVacations(allVacations -> {
                List<ReportAdapter.ReportRow> rows = new ArrayList<>();

                if (allVacations != null) {
                    for (Vacations v : allVacations) {
                        try {
                            Date vacStart = sdf.parse(v.getStartDate());
                            Date vacEnd   = sdf.parse(v.getEndDate());


                            boolean startsBeforeFilterEnd   = !vacStart.after(fEnd);
                            boolean endsAfterFilterStart    = !vacEnd.before(fStart);

                            if (startsBeforeFilterEnd && endsAfterFilterStart) {
                                int count = excursionCountMap.getOrDefault(v.getVacationID(), 0);
                                rows.add(new ReportAdapter.ReportRow(
                                        v.getVacationName(),
                                        v.getHotel(),
                                        v.getStartDate(),
                                        v.getEndDate(),
                                        count
                                ));
                            }
                        } catch (ParseException ignored) {

                        }
                    }
                }

                runOnUiThread(() -> {
                    reportAdapter.updateRows(rows);
                    if (rows.isEmpty()) {
                        Toast.makeText(ReportActivity.this,
                                "No vacations found in that date range",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }
}
