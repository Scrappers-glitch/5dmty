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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class LecturesCardView extends Adapter<CardViewHolder> implements Filterable {

    private ArrayList<LecturesModel> model;
    private ArrayList<LecturesModel> filteredItems=new ArrayList<>();
    private String servantName;
    private final AppCompatActivity context;
    private final RecyclerView lecturesRV;
    private String searchType="";

    public LecturesCardView(ArrayList<LecturesModel>  model, String servantName, AppCompatActivity context, RecyclerView lecturesRV) {
        this.model=model;
        this.servantName=servantName;
        this.context=context;
        this.lecturesRV=lecturesRV;
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
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlayout,parent,false));
    }

    public void setModel(ArrayList<LecturesModel> model) {
        this.model = model;
    }

    public ArrayList<LecturesModel> getModel() {
        return model;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            holder.lecture.setText(model.get(position).getLecture());
            holder.lectureDate.setText(model.get(position).getDate());
            holder.quote.setText(model.get(position).getVerse());
            holder.lectureNotes.setText(model.get(position).getNotes());

            holder.save.setOnClickListener(new SaveNewEdits(context,holder.lecture,holder.lectureNotes,holder.quote,holder.lectureDate,servantName,this,lecturesRV));
            holder.removeLecture.setOnClickListener(new RemoveLecture(context,holder.lecture));
            holder.dropDownButton.setOnClickListener(new DropDownLecture(context,holder.card,holder.dropDownButton));
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
                        lectureCard.applySearchType(searchType);
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
