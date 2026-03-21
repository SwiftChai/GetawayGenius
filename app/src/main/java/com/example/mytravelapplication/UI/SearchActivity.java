package com.example.mytravelapplication.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravelapplication.R;
import com.example.mytravelapplication.database.Repository;
import com.example.mytravelapplication.entities.Excursions;
import com.example.mytravelapplication.entities.Vacations;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView searchRecyclerView;
    private Repository repository;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);


        repository = new Repository(getApplication());

        adapter = new SearchAdapter(new ArrayList<>());
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(adapter);


        searchButton.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String query = searchEditText.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Object> combinedResults = new ArrayList<>();


        repository.searchVacations(query, vacations -> {
            if (vacations != null) {
                combinedResults.addAll(vacations);
            }


            repository.searchExcursions(query, excursions -> {
                if (excursions != null) {
                    combinedResults.addAll(excursions);
                }


                runOnUiThread(() -> {
                    if (combinedResults.isEmpty()) {
                        Toast.makeText(SearchActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                    }
                    adapter.updateResults(combinedResults);
                });
            });
        });
    }
}