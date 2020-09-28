package com.scrappers.churchService.allLecturesRV;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.scrappers.churchService.R;

import androidx.appcompat.app.AppCompatActivity;

class DropDownLecture implements View.OnClickListener {
    private final AppCompatActivity context;
    private final LinearLayout card;
    private final ImageButton dropDownButton;
    DropDownLecture(AppCompatActivity context, LinearLayout card, ImageButton dropDownButton) {
        this.context=context;
        this.card=card;
        this.dropDownButton=dropDownButton;
    }

    @Override
    public void onClick(View v) {
        if(card.getVisibility()==View.VISIBLE){
            card.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            card.setVisibility(View.INVISIBLE);
            dropDownButton.setImageDrawable(context.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        }else {
            card.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            card.setVisibility(View.VISIBLE);
            dropDownButton.setImageDrawable(context.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        }
    }
}
