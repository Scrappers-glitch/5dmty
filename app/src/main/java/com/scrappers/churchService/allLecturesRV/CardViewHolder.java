package com.scrappers.churchService.allLecturesRV;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.scrappers.churchService.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout card;
    public EditText lecture;
    public EditText lectureDate;
    public EditText lectureNotes;
    public EditText quote;
    public Button save;
    public ImageButton removeLecture;
    public ImageButton dropDownButton;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
         card=itemView.findViewById(R.id.dropDownMenu);
         lecture=itemView.findViewById(R.id.lecture);
         lectureDate=itemView.findViewById(R.id.lectureDate);
         lectureNotes=itemView.findViewById(R.id.lectureNotes);

         quote=itemView.findViewById(R.id.quote);

         save=itemView.findViewById(R.id.saveLecture);
         removeLecture=itemView.findViewById(R.id.removeLecture);
         dropDownButton=itemView.findViewById(R.id.dropDownButton);

    }
}
