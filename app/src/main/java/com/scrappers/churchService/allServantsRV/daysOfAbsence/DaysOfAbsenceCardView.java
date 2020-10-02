package com.scrappers.churchService.allServantsRV.daysOfAbsence;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scrappers.churchService.R;
import com.scrappers.churchService.allServantsRV.TakeAbsence;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DaysOfAbsenceCardView extends RecyclerView.Adapter<CardViewHolder> {
    private String servantName;
    private ArrayList<DaysOfAbsenceModel> model;
    private TextView absenceNumberWidget;

    public DaysOfAbsenceCardView(String servantName, ArrayList<DaysOfAbsenceModel> model, TextView absenceNumberWidget){
        this.servantName=servantName;
        this.model=model;
        this.absenceNumberWidget=absenceNumberWidget;

    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(View.inflate(parent.getContext(), R.layout.days_of_absence_cardview,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.days.setText(model.get(position).getDay());
        holder.days.setChecked(model.get(position).getState());
        holder.days.setOnClickListener(new TakeAbsence(servantName,model.get(position).getDay(),absenceNumberWidget,true));
        holder.removeDay.setOnClickListener(new RemoveDay(servantName,model.get(position).getDay(),absenceNumberWidget,holder.days));
    }



    public void setModel(ArrayList<DaysOfAbsenceModel> model) {
        this.model = model;
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
