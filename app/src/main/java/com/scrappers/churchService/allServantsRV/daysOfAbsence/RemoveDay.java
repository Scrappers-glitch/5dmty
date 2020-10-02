package com.scrappers.churchService.allServantsRV.daysOfAbsence;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class RemoveDay implements View.OnClickListener {
    private String servantName;
    private String day;
    private TextView absenceNumberWidget;
    private CheckBox days;
    public RemoveDay(@NonNull String servantName, @NonNull String day, @NonNull TextView absenceNumberWidget, CheckBox days){
        this.servantName=servantName;
        this.day=day;
        this.absenceNumberWidget=absenceNumberWidget;
        this.days=days;
    }
    @Override
    public void onClick(final View v) {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference dayNode=databaseReference.child("Gnod").child("servants").child(servantName).child("details").child("absence").child(day);
                DataSnapshot numberOfAbsenceDays=snapshot.child("Gnod").child("servants").child(servantName).child("details").child("numberOfAbsence");
                DatabaseReference sendNumberOfDays=databaseReference.child("Gnod").child("servants").child(servantName).child("details").child("numberOfAbsence");
                dayNode.removeValue();

                if(days.isChecked()){
                    int numberOfDays = Integer.parseInt(String.valueOf(numberOfAbsenceDays.getValue()));
                    --numberOfDays;
                    sendNumberOfDays.setValue(numberOfDays);
                    absenceNumberWidget.setText(String.valueOf(numberOfDays));
                }
                Snackbar.make(v,"تم إذاله هذا اليوم من ايام حضور الخادم",Snackbar.LENGTH_LONG).show();

                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
