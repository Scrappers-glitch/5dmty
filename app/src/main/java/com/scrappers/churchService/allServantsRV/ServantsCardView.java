package com.scrappers.churchService.allServantsRV;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.scrappers.churchService.R;
import com.scrappers.churchService.allServantsRV.servantsModel.ServantsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ServantsCardView extends RecyclerView.Adapter<CardViewHolder> implements Filterable {
    private final AppCompatActivity context;
    private ArrayList<ServantsModel> model;
    private ArrayList<ServantsModel> filteredItems;
    private String searchType="";

    public ServantsCardView(@NonNull AppCompatActivity context, ArrayList<ServantsModel> model){
        this.context=context;
        this.model=model;
    }

    public void setModel(ArrayList<ServantsModel> model) {
        this.model = model;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(View.inflate(parent.getContext(), R.layout.servantcardview,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.servantName.setText(model.get(position).getServantName());
        holder.servantAge.setText(model.get(position).getServantAge());
        holder.servantClass.setText(model.get(position).getServantClass());
        holder.servantPhoneNumber.setText(model.get(position).getServantPhoneNumber());
        holder.absence.setChecked(Boolean.parseBoolean(model.get(position).isAbsence()));
        holder.numberOfAbsence.setText(model.get(position).getNumberOfAbsence());

        holder.servantLectures.setOnClickListener(new ShowLectures(context,model.get(position).getServantName()));
        holder.dropDownButton.setOnClickListener(new DropDownDetails(context,holder.card,holder.dropDownButton));
        holder.absence.setOnClickListener(new TakeAbsence(context,holder.absence,model.get(position).getServantName()));

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
                if(constraint.toString().length()==0 | constraint.toString().isEmpty()){
                        filteredItems=new ArrayList<>(model);
                }else {
                    filteredItems=new ArrayList<>();
                    filteredItems.clear();
                  for(ServantsModel servantsModel : model){
                      servantsModel.setSearchType(getSearchType());
                      if(servantsModel.getSearchType().contains(constraint.toString())){
                          filteredItems.add(servantsModel);
                      }
                  }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                model.clear();
                model=(ArrayList<ServantsModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setSearchType(@NonNull String searchType) {
        this.searchType=searchType;
    }

    public String getSearchType() {
        return searchType;
    }
}
