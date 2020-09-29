package com.scrappers.churchService.realTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.churchService.allServantsRV.ServantsCardView;
import com.scrappers.churchService.allServantsRV.servantsModel.ServantsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ReadServantsChanges implements ValueEventListener {

    private ServantsCardView servantsCardView;
    private ArrayList<ServantsModel> model;
    private String churchName;

    public ReadServantsChanges(String churchName, ServantsCardView servantsCardView) {

        this.servantsCardView=servantsCardView;
        this.churchName=churchName;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        try {
            model=new ArrayList<>();
            DataSnapshot servantsNode = snapshot.child(churchName).child("servants");
            if(servantsNode.hasChildren()){
                for (DataSnapshot dataSnapshot : servantsNode.getChildren()) {
                    model.add(new ServantsModel(
                            String.valueOf(dataSnapshot.child("details").child("name").getValue()),
                            String.valueOf(dataSnapshot.child("details").child("age").getValue()),
                            String.valueOf(dataSnapshot.child("details").child("class").getValue()),
                            String.valueOf(dataSnapshot.child("details").child("phoneNumber").getValue())));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        servantsCardView.setModel(model);
        servantsCardView.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) { }

}
