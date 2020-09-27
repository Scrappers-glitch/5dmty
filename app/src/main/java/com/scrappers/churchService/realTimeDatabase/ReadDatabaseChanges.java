package com.scrappers.churchService.realTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.churchService.allLecturesRV.LecturesCardView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ReadDatabaseChanges implements ValueEventListener {

    private final AppCompatActivity context;
    private final RecyclerView lecturesRV;
    private final String servantName;
    private ArrayList<String> lecturesList=new ArrayList<>();
    private ArrayList<String> verseList=new ArrayList<>();
    private ArrayList<String> lectureDate =new ArrayList<>();
    private ArrayList<String> generalNotes=new ArrayList<>();

    /**
     * CREATE a database firebase change listener
     * @param context class context of use
     * @param lecturesRV recycle viewer widget to update data
     * @param servantName name of device owner
     */
    public ReadDatabaseChanges(AppCompatActivity context, RecyclerView lecturesRV,String servantName) {
        this.context=context;
        this.lecturesRV=lecturesRV;
        this.servantName = servantName;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        try {
            lecturesList=new ArrayList<>();
            lectureDate=new ArrayList<>();
            verseList=new ArrayList<>();
            generalNotes=new ArrayList<>();

            DataSnapshot lectureNode = snapshot.child("Gnod").child("servants").child(servantName).child("lectures");

            if(lectureNode.hasChildren()){
                for (DataSnapshot dataSnapshot : lectureNode.getChildren()) {

                    lecturesList.add(String.valueOf(dataSnapshot.child("lecture").getValue()));

                    lectureDate.add(String.valueOf(dataSnapshot.child("date").getValue()));

                    verseList.add(String.valueOf(dataSnapshot.child("verse").getValue()));

                    generalNotes.add(String.valueOf(dataSnapshot.child("notes").getValue()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
            LecturesCardView lecturesCardView=new LecturesCardView(lecturesList,verseList, lectureDate,generalNotes, servantName,context,lecturesRV);
            lecturesRV.setAdapter(lecturesCardView);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) { }
}
