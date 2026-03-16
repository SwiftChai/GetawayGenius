package com.example.mytravelapplication.UI;

import android.content.Context;
import android.content.Intent;
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

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursions> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final TextView excursionItemView;
        private final TextView excursionItemview2;



        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.excursion_item_title);
            excursionItemview2 = itemView.findViewById(R.id.excursion_item_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Excursions current = mExcursions.get(position);

                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("excursionID", current.getExcursionID());
                    intent.putExtra("excursionName", current.getExcursionName());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    intent.putExtra("vacationID", current.getVacationID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
@Override
public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
public void onBindViewHolder(@NonNull ExcursionViewHolder holder,int position) {
        if (mExcursions != null) {
            Excursions current = mExcursions.get(position);
            String name = current.getExcursionName();
            holder.excursionItemView.setText(name);
            holder.excursionItemview2.setText(current.getExcursionDate());
        } else {
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemview2.setText("No excursion date");
        }
    }

        public void setExcursion(List<Excursions> excursions) {
            mExcursions = excursions;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (mExcursions != null) {
                return mExcursions.size();
            }
            return 0;
         }
    }

