package com.scrappers.churchService.allServantsRV;

import android.view.View;
import android.view.ViewGroup;

import com.scrappers.churchService.R;
import com.scrappers.churchService.allServantsRV.servantsModel.ServantsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ServantsCardView extends RecyclerView.Adapter<CardViewHolder> {
    private final AppCompatActivity context;
    private ArrayList<ServantsModel> model;
    public  ServantsCardView(AppCompatActivity context, ArrayList<ServantsModel> model){
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

        holder.servantLectures.setOnClickListener(new ShowLectures(context,model.get(position).getServantName()));
        holder.dropDownButton.setOnClickListener(new DropDownDetails(context,holder.card,holder.dropDownButton));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
