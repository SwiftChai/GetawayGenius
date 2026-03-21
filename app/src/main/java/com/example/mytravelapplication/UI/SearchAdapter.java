package com.example.mytravelapplication.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravelapplication.R;
import com.example.mytravelapplication.entities.Excursions;
import com.example.mytravelapplication.entities.Vacations;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Object> results;

    public SearchAdapter(List<Object> results) {
        this.results = results;
    }

    public void updateResults(List<Object> newResults) {
        this.results = newResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Object item = results.get(position);

        if (item instanceof Vacations) {
            Vacations vacation = (Vacations) item;
            holder.text1.setText(vacation.getVacationName());
            holder.text2.setText("Hotel: " + vacation.getHotel() + " | " + vacation.getStartDate() + " - " + vacation.getEndDate());        } else if (item instanceof Excursions) {
            Excursions excursion = (Excursions) item;
            holder.text1.setText(excursion.getExcursionName());
            holder.text2.setText("Excursion Date: " + excursion.getExcursionDate());
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        SearchViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}