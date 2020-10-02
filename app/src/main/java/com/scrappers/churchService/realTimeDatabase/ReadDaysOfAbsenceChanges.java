package com.scrappers.churchService.realTimeDatabase;

import android.os.Build;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.churchService.allServantsRV.daysOfAbsence.DaysOfAbsenceCardView;
import com.scrappers.churchService.allServantsRV.daysOfAbsence.DaysOfAbsenceModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import androidx.annotation.NonNull;

public class ReadDaysOfAbsenceChanges implements ValueEventListener {
    private String churchName;
    private DaysOfAbsenceCardView daysOfAbsenceCardView;
    private String servantName;
    public ReadDaysOfAbsenceChanges(String churchName, DaysOfAbsenceCardView daysOfAbsenceCardView,String servantName) {
        this.churchName=churchName;
        this.daysOfAbsenceCardView=daysOfAbsenceCardView;
        this.servantName=servantName;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        ArrayList<DaysOfAbsenceModel> model = new ArrayList<>();
        DataSnapshot absenceNode=snapshot.child(churchName).child("servants")
                .child(servantName).child("details").child("absence");
        if(absenceNode.hasChildren()){
            for(DataSnapshot days:absenceNode.getChildren()){
                model.add(new DaysOfAbsenceModel(days.getKey(),Boolean.parseBoolean(String.valueOf(days.getValue()))));
            }
        }
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ){
            model.sort(new Comparator<DaysOfAbsenceModel>() {
                @Override
                public int compare(DaysOfAbsenceModel day1, DaysOfAbsenceModel day2) {
                    try {
                        return Objects.requireNonNull(
                                SimpleDateFormat.getDateInstance().parse(day1.getDay()))
                                .compareTo(
                                        SimpleDateFormat.getDateInstance().parse(day2.getDay()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
        }
        daysOfAbsenceCardView.setModel(model);
        daysOfAbsenceCardView.notifyDataSetChanged();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
