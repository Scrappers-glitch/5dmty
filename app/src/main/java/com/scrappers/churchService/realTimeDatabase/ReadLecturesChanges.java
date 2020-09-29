package com.scrappers.churchService.realTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.churchService.allLecturesRV.LecturesCardView;
import com.scrappers.churchService.allLecturesRV.lecturesModel.LecturesModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ReadLecturesChanges implements ValueEventListener {


    private LecturesCardView lecturesCardView;
    private final String servantName;
    private ArrayList<LecturesModel> model;
    /**
     * CREATE a database firebase change listener
     * @param lecturesCardView the single view in the all lectures RV
     * @param servantName name of device owner
     */
    public ReadLecturesChanges(LecturesCardView lecturesCardView, String servantName) {

        this.servantName = servantName;
        this.lecturesCardView=lecturesCardView;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        try {
            model=new ArrayList<>();
            DataSnapshot lectureNode = snapshot.child("Gnod").child("servants").child(servantName).child("lectures");

            if(lectureNode.hasChildren()){
                for (DataSnapshot dataSnapshot : lectureNode.getChildren()) {

                    model.add(new LecturesModel(
                            String.valueOf(dataSnapshot.child("lecture").getValue()),
                            String.valueOf(dataSnapshot.child("date").getValue()),
                            String.valueOf(dataSnapshot.child("verse").getValue()),
                            String.valueOf(dataSnapshot.child("notes").getValue())));

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

            lecturesCardView.setModel(model);
            lecturesCardView.notifyDataSetChanged();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) { }
}
