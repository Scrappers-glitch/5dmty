package com.scrappers.churchService.allLecturesRV;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.realTimeDatabase.ReadDatabaseChanges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SaveNewEdits implements View.OnClickListener {
    private EditText lecture;
    private TextView lectureNotes;
    private EditText quote;
    private EditText lectureDate;
    private String servantName;
    private final LecturesCardView lecturesAdapter;
    private final RecyclerView lecturesRV;
    private final AppCompatActivity context;

    SaveNewEdits(AppCompatActivity context,
                 EditText lecture, TextView lectureNotes, EditText quote, EditText lectureDate,
                 String servantName, LecturesCardView lecturesAdapter, RecyclerView lecturesRV) {
        this.context=context;
        this.lecture=lecture;
        this.lectureNotes=lectureNotes;
        this.quote=quote;
        this.lectureDate=lectureDate;
        this.servantName =servantName;
        this.lecturesAdapter=lecturesAdapter;
        this.lecturesRV=lecturesRV;
    }

    @Override
    public void onClick(View v) {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

        DatabaseReference lecturesNode=databaseReference.child("Gnod").child("servants").child(servantName).child("lectures");

            lecturesNode.child(lecture.getText().toString()).child("lecture").setValue(lecture.getText().toString());
            lecturesNode.child(lecture.getText().toString()).child("date").setValue(lectureDate.getText().toString());
            lecturesNode.child(lecture.getText().toString()).child("notes").setValue(lectureNotes.getText().toString());
            lecturesNode.child(lecture.getText().toString()).child("verse").setValue(quote.getText().toString());

        databaseReference.addValueEventListener(new ReadDatabaseChanges(context,lecturesRV, servantName));
        lecturesAdapter.notifyDataSetChanged();
    }
}
