package com.scrappers.churchService.allLecturesRV;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.optionPane.OptionPane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RemoveLecture implements View.OnClickListener {

    private final AppCompatActivity context;
    private final EditText lecture;
    private String servantName;

    public RemoveLecture(AppCompatActivity context, EditText lecture, String servantName) {
        this.context=context;
        this.lecture=lecture;
        this.servantName=servantName;
    }

    @Override
    public void onClick(View v) {
        final OptionPane optionPane =new OptionPane(context);
        optionPane.showDialog(R.layout.dialog_prompt, Gravity.CENTER);
        assert  optionPane.getAlertDialog().getWindow() !=null;
        optionPane.getAlertDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.radial_dailog));
        final View view= optionPane.getInflater();
        Button yesDelete=view.findViewById(R.id.yesDelete);
        yesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                DatabaseReference lecturesNode=databaseReference.child("Gnod").child("servants").child(servantName).child("lectures");
                lecturesNode.child(lecture.getText().toString()).removeValue();

                Toast.makeText(context,"تم الغاء الدرس",Toast.LENGTH_LONG).show();
                optionPane.getAlertDialog().dismiss();
            }
        });
        Button noClose=view.findViewById(R.id.noClose);
        noClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionPane.getAlertDialog().dismiss();
            }
        });

    }
}
