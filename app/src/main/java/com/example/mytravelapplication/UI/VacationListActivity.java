package com.example.mytravelapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mytravelapplication.R;
import com.example.mytravelapplication.database.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VacationListActivity extends AppCompatActivity {
    private Repository repository;
    private VacationAdapter vacationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        repository = new Repository(getApplication());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> startActivity(new Intent(this, VacationDetails.class)));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.recyclerView).postDelayed(() ->
                repository.getAllVacations(vacations ->
                        runOnUiThread(() -> vacationAdapter.setVacations(vacations))
                ), 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
