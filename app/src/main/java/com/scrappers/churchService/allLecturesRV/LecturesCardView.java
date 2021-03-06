package com.scrappers.churchService.allLecturesRV;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.scrappers.churchService.R;
import com.scrappers.churchService.allLecturesRV.lecturesModel.LecturesModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class LecturesCardView extends Adapter<CardViewHolder> implements Filterable {

    private ArrayList<LecturesModel> model;
    private ArrayList<LecturesModel> filteredItems=new ArrayList<>();
    private String servantName;
    private final AppCompatActivity context;
    private String searchType="";
    private boolean isNotesEnabled;

    public LecturesCardView(ArrayList<LecturesModel> model, String servantName, AppCompatActivity context, boolean isNotesEnabled) {
        this.model=model;
        this.servantName=servantName;
        this.context=context;
        this.isNotesEnabled=isNotesEnabled;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         * Inflate a Single layout view
         */
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lecturecardview,parent,false));
    }

    public void setModel(ArrayList<LecturesModel> model) {
        this.model = model;
    }


    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            holder.lecture.setText(model.get(position).getLecture());
            holder.lectureDate.setText(model.get(position).getDate());
            holder.quote.setText(model.get(position).getVerse());
            holder.lectureNotes.setText(model.get(position).getNotes());
            holder.lectureNotes.setEnabled(isNotesEnabled);

            holder.save.setOnClickListener(new SaveNewEdits(holder.lecture,holder.lectureNotes,holder.quote,holder.lectureDate,servantName,this));
            holder.removeLecture.setOnClickListener(new RemoveLecture(context,holder.lecture,servantName));
            holder.dropDownButton.setOnClickListener(new DropDownDetails(context,holder.card,holder.dropDownButton));
    }


    @Override
    public int getItemCount() {
        return model.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(constraint.toString().isEmpty() || constraint.toString().length()==0){
                    filteredItems.addAll(model);
                }else{
                    filteredItems.clear();
                    for(LecturesModel lectureCard : model){
                        lectureCard.applySearchType(getSearchType());
                        if(lectureCard.getSearchType().toLowerCase().contains(constraint.toString())){
                            filteredItems.add(lectureCard);
                        }
                    }
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    model.clear();
                    model.addAll( (List<LecturesModel>)results.values);
                    notifyDataSetChanged();

            }
        };
    }
}
