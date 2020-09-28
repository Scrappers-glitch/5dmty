package com.scrappers.churchService.allLecturesRV;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scrappers.churchService.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CardViewHolder extends RecyclerView.ViewHolder {
    LinearLayout card;
    EditText lecture;
    EditText lectureDate;
    TextView lectureNotes;
    EditText quote;
    Button save;
    ImageButton removeLecture;
    ImageButton dropDownButton;

    CardViewHolder(@NonNull View itemView) {
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
