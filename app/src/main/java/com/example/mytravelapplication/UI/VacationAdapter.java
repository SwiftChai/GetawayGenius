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
import com.example.mytravelapplication.entities.Vacations;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private List<Vacations> mVacations;
    private final Context context;
    private final LayoutInflater mInflator;
    public VacationAdapter(Context context){
        mInflator= LayoutInflater.from(context);
        this.context = context;
    }
    public class VacationViewHolder extends RecyclerView.ViewHolder{

        private final TextView vacationItemView;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView=itemView.findViewById(R.id.vacation_item_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Vacations current;
                    current = mVacations.get(position);
                    Intent intent=new Intent(context,VacationDetails.class);
                    intent.putExtra("vacationID",current.getVacationID());
                    intent.putExtra("vacationTitle",current.getVacationName());
                    intent.putExtra("hotelName",current.getHotel());
                    intent.putExtra("startDate",current.getStartDate());
                    intent.putExtra("endDate",current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.vacation_list_item,parent,false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations!=null){
            Vacations current =mVacations.get(position);
            String name= current.getVacationName();;
            holder.vacationItemView.setText(name);
        } else {
            holder.vacationItemView.setText("No vacation name");
        }

    }

    @Override
    public int getItemCount() {
        if(mVacations!=null){
            return mVacations.size();
        }
        else return 0;
    }

    public void setVacations(List<Vacations> vacations){
        mVacations=vacations;
        notifyDataSetChanged();
    }


}
