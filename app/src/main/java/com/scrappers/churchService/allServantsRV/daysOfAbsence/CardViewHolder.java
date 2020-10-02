package com.scrappers.churchService.allServantsRV.daysOfAbsence;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.scrappers.churchService.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public CheckBox days;
    public ImageView removeDay;
    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        days=itemView.findViewById(R.id.dayOfAbsenceCheck);
        removeDay=itemView.findViewById(R.id.removeDay);
    }
}
