package com.scrappers.churchService.allServantsRV;

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
import androidx.annotation.Nullable;

public class TakeAbsence implements View.OnClickListener {
    private String servantName;
    private String day;
    private int numberOfAbsence;
    private TextView absenceNumberWidget;
    private boolean isEditEntry;
    public TakeAbsence(String servantName, String day, @Nullable TextView absenceNumberWidget, boolean isEditEntry) {
        this.servantName=servantName;
        this.day=day;
        this.absenceNumberWidget=absenceNumberWidget;
        this.isEditEntry=isEditEntry;
    }

    @Override
    public void onClick(final View v) {

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference servantNode=databaseReference.child("Gnod").child("servants").child(servantName).child("details");
        servantNode.child("absence").child(day).setValue(((CheckBox)v).isChecked());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data= String.valueOf(snapshot.child("Gnod").child("servants").child(servantName).child("details").
                        child("numberOfAbsence").getValue());

                numberOfAbsence=Integer.parseInt(data);
                if(((CheckBox)v).isChecked()){
                    ++numberOfAbsence;
                    Snackbar.make(v,"تم اخذ الحضور لهذا الخادم",Snackbar.LENGTH_LONG).show();
                }else{
                    --numberOfAbsence;
                    Snackbar.make(v,"تم إلغاء الحضور لهذا الخادم",Snackbar.LENGTH_LONG).show();
                }
                servantNode.child("numberOfAbsence").setValue(numberOfAbsence);
                if(isEditEntry){
                    absenceNumberWidget.setText(String.valueOf(numberOfAbsence));
                }
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
