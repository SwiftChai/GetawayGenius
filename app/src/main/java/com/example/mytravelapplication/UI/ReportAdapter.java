package com.example.mytravelapplication.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravelapplication.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    // Simple data holder for one report row
    public static class ReportRow {
        public final String vacationName;
        public final String hotel;
        public final String startDate;
        public final String endDate;
        public final int excursionCount;

        public ReportRow(String vacationName, String hotel, String startDate,
                         String endDate, int excursionCount) {
            this.vacationName    = vacationName;
            this.hotel           = hotel;
            this.startDate       = startDate;
            this.endDate         = endDate;
            this.excursionCount  = excursionCount;
        }
    }

    private List<ReportRow> rows;

    public ReportAdapter(List<ReportRow> rows) {
        this.rows = rows;
    }

    public void updateRows(List<ReportRow> newRows) {
        this.rows = newRows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportRow row = rows.get(position);
        holder.vacationName.setText(row.vacationName);
        holder.hotel.setText("Hotel: " + row.hotel);
        holder.dates.setText("Dates: " + row.startDate + " – " + row.endDate);
        holder.excursionCount.setText("Excursions: " + row.excursionCount);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView vacationName;
        TextView hotel;
        TextView dates;
        TextView excursionCount;

        ReportViewHolder(View itemView) {
            super(itemView);
            vacationName   = itemView.findViewById(R.id.reportVacationName);
            hotel          = itemView.findViewById(R.id.reportHotel);
            dates          = itemView.findViewById(R.id.reportDates);
            excursionCount = itemView.findViewById(R.id.reportExcursionCount);
        }
    }
}