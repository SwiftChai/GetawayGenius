package com.example.mytravelapplication.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mytravelapplication.R;
import com.example.mytravelapplication.database.Repository;
import com.example.mytravelapplication.entities.Excursions;
import com.example.mytravelapplication.entities.Vacations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    private Repository repository;
    private int vacationID;
    private EditText vacationNameEdit, hotelNameEdit, startDateEdit, endDateEdit;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        repository = new Repository(getApplication());

        vacationID = getIntent().getIntExtra("vacationID", -1);

        vacationNameEdit = findViewById(R.id.vacation_title);
        hotelNameEdit    = findViewById(R.id.hotel_name);
        startDateEdit    = findViewById(R.id.start_date);
        endDateEdit      = findViewById(R.id.end_date);

        vacationNameEdit.setText(getIntent().getStringExtra("vacationTitle"));
        hotelNameEdit.setText(getIntent().getStringExtra("hotelName"));
        startDateEdit.setText(getIntent().getStringExtra("startDate"));
        endDateEdit.setText(getIntent().getStringExtra("endDate"));

        setupDatePicker(startDateEdit);
        setupDatePicker(endDateEdit);

        FloatingActionButton fab = findViewById(R.id.add_excursion_button);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExcursionDetails.class);
            intent.putExtra("vacationID", vacationID);
            intent.putExtra("vacationStartDate", startDateEdit.getText().toString());
            intent.putExtra("vacationEndDate", endDateEdit.getText().toString());
            startActivity(intent);
        });
    }

    private void setupDatePicker(EditText field) {
        field.setOnClickListener(v -> {
            String existing = field.getText().toString();
            if (!existing.isEmpty()) {
                try { calendar.setTime(sdf.parse(existing)); } catch (ParseException ignored) {}
            }
            new DatePickerDialog(this, (DatePicker view, int y, int m, int d) -> {
                calendar.set(y, m, d);
                field.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.vacation_save)   { saveVacation();   return true; }
        if (item.getItemId() == R.id.vacation_delete) { deleteVacation(); return true; }
        if (item.getItemId() == R.id.vacation_share)  { shareVacation();  return true; }
        if (item.getItemId() == R.id.vacation_alert)  { setAlerts();      return true; }
        return super.onOptionsItemSelected(item);
    }

    private void saveVacation() {
        String title = vacationNameEdit.getText().toString().trim();
        String hotel = hotelNameEdit.getText().toString().trim();
        String start = startDateEdit.getText().toString().trim();
        String end   = endDateEdit.getText().toString().trim();

        if (title.isEmpty() || hotel.isEmpty() || start.isEmpty() || end.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Date startD = sdf.parse(start);
            Date endD   = sdf.parse(end);
            if (!endD.after(startD)) {
                Toast.makeText(this, "End date must be after start date", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format. Use MM/dd/yy", Toast.LENGTH_LONG).show();
            return;
        }

        if (vacationID == -1) {
            repository.getAllVacations(all -> {
                int newId = all.isEmpty() ? 1 : all.get(all.size() - 1).getVacationID() + 1;
                repository.insert(new Vacations(newId, title, hotel, start, end), v ->
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Vacation saved", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                );
            });
        } else {
            repository.update(new Vacations(vacationID, title, hotel, start, end), v ->
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Vacation updated", Toast.LENGTH_SHORT).show();
                        finish();
                    })
            );
        }
    }

    private void deleteVacation() {
        if (vacationID == -1) { finish(); return; }
        repository.getAllExcursions(all -> {
            boolean hasExcursions = false;
            for (Excursions e : all) {
                if (e.getVacationID() == vacationID) { hasExcursions = true; break; }
            }
            if (hasExcursions) {
                runOnUiThread(() -> Toast.makeText(this, "Cannot delete vacation with excursions", Toast.LENGTH_LONG).show());
                return;
            }
            String title = vacationNameEdit.getText().toString();
            String hotel = hotelNameEdit.getText().toString();
            String start = startDateEdit.getText().toString();
            String end   = endDateEdit.getText().toString();
            repository.delete(new Vacations(vacationID, title, hotel, start, end), v ->
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Vacation deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
            );
        });
    }

    private void setAlerts() {
        String start = startDateEdit.getText().toString().trim();
        String end   = endDateEdit.getText().toString().trim();
        String title = vacationNameEdit.getText().toString().trim();
        if (start.isEmpty() || end.isEmpty() || title.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields first", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            scheduleAlert(sdf.parse(start).getTime(), title + " is starting!");
            scheduleAlert(sdf.parse(end).getTime(), title + " is ending!");
            Toast.makeText(this, "Alerts set for start and end dates", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleAlert(long triggerMs, String message) {
        Intent i = new Intent(this, MyReceiver.class);
        i.putExtra("key", message);
        PendingIntent pi = PendingIntent.getBroadcast(this, MainActivity.numAlert++, i, PendingIntent.FLAG_IMMUTABLE);
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, triggerMs, pi);
    }

    private void shareVacation() {
        String text = "Vacation: " + vacationNameEdit.getText() + "\n"
                + "Hotel: "  + hotelNameEdit.getText()  + "\n"
                + "Start: "  + startDateEdit.getText()  + "\n"
                + "End: "    + endDateEdit.getText();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, text);
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, "Share vacation via"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rv = findViewById(R.id.excursion_recyclerview);
        ExcursionAdapter adapter = new ExcursionAdapter(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        repository.getAllExcursions(excursions -> {
            List<Excursions> filtered = new ArrayList<>();
            for (Excursions e : excursions) {
                if (e.getVacationID() == vacationID) filtered.add(e);
            }
            runOnUiThread(() -> adapter.setExcursion(filtered));
        });
    }
}