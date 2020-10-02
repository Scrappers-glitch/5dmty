package com.scrappers.churchService.allServantsRV;

import android.view.View;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.churchService.realTimeDatabase.ReadServantsChanges;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TakeAbsence implements View.OnClickListener {
    private final AppCompatActivity context;
    private CheckBox absence;
    private String servantName;

    public TakeAbsence(AppCompatActivity context, CheckBox absence, String servantName) {
        this.context=context;
        this.absence=absence;
        this.servantName=servantName;
    }

    @Override
    public void onClick(final View v) {

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference servantNode=databaseReference.child("Gnod").child("servants").child(servantName).child("details");
        servantNode.child("absence").child(SimpleDateFormat.getDateInstance().format(new Date())).setValue(((CheckBox)v).isChecked());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data= String.valueOf(snapshot.child("Gnod").child("servants").child(servantName).child("details").
                        child("numberOfAbsence").getValue());

                int numberOfAbsence=Integer.parseInt(data);
                System.out.println(numberOfAbsence);
                if(((CheckBox)v).isChecked()){
                    ++numberOfAbsence;
                    System.out.println(numberOfAbsence);
                }else{
                    --numberOfAbsence;
                }
                servantNode.child("numberOfAbsence").setValue(numberOfAbsence);
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
