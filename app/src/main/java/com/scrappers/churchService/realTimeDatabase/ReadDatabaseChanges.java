package com.scrappers.churchService.realTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.churchService.allLecturesRV.LecturesCardView;
import com.scrappers.churchService.allLecturesRV.lecturesModel.LecturesModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ReadDatabaseChanges implements ValueEventListener {

    private final AppCompatActivity context;
    private final RecyclerView lecturesRV;
    private LecturesCardView lecturesCardView;
    private final String servantName;
    private ArrayList<LecturesModel> model;
    /**
     * CREATE a database firebase change listener
     * @param context class context of use
     * @param lecturesCardView
     * @param lecturesRV recycle viewer widget to update data
     * @param servantName name of device owner
     */
    public ReadDatabaseChanges(AppCompatActivity context, LecturesCardView lecturesCardView, RecyclerView lecturesRV, String servantName) {
        this.context=context;
        this.lecturesRV=lecturesRV;
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
