package com.scrappers.churchService.allLecturesRV;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scrappers.churchService.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class LecturesCardView extends Adapter<CardViewHolder> {

    private  final ArrayList<String> lecturesList;
    private  final ArrayList<String> verseList;
    private  final ArrayList<String> lectureDate;
    private  final ArrayList<String> generalNotes;
    private String servantName;
    private final AppCompatActivity context;
    private final RecyclerView lecturesRV;

    public LecturesCardView(ArrayList<String> lecturesList, ArrayList<String> verseList, ArrayList<String> lectureDate, ArrayList<String> generalNotes,String servantName,AppCompatActivity context,RecyclerView lecturesRV) {
        this.lecturesList=lecturesList;
        this.lectureDate=lectureDate;
        this.verseList=verseList;
        this.generalNotes=generalNotes;
        this.servantName=servantName;
        this.context=context;
        this.lecturesRV=lecturesRV;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         * Inflate a Single layout view
         */
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            holder.lecture.setText(lecturesList.get(position));
            holder.lectureDate.setText(lectureDate.get(position));
            holder.quote.setText(verseList.get(position));
            holder.lectureNotes.setText(generalNotes.get(position));

            holder.save.setOnClickListener(new SaveNewEdits(context,holder.lecture,holder.lectureNotes,holder.quote,holder.lectureDate,servantName,this,lecturesRV));
            holder.removeLecture.setOnClickListener(new RemoveLecture(context,holder.lecture));
    }


    @Override
    public int getItemCount() {
        return lecturesList.size();
    }
}
