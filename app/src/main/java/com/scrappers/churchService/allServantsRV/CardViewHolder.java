package com.scrappers.churchService.allServantsRV;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scrappers.churchService.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {

    public TextView servantName;
    public TextView servantAge;
    public TextView servantClass;
    public TextView servantPhoneNumber;
    public Button servantLectures;
    public LinearLayout card;
    public ImageButton dropDownButton;
    public CheckBox absence;
    public TextView numberOfAbsence;
    public Button allAbsenceDays;


    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        servantName=itemView.findViewById(R.id.name);
        servantAge=itemView.findViewById(R.id.age);
        servantClass=itemView.findViewById(R.id.servantClass);
        servantPhoneNumber=itemView.findViewById(R.id.phoneNumber);

        servantLectures=itemView.findViewById(R.id.servantLectures);
        card=itemView.findViewById(R.id.dropDownMenu);
        dropDownButton=itemView.findViewById(R.id.dropDownButton);

        absence=itemView.findViewById(R.id.servantsAbsence);
        numberOfAbsence=itemView.findViewById(R.id.numberOfAbsence);
        allAbsenceDays=itemView.findViewById(R.id.daysOfAbsence);
    }
}
