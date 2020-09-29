package com.scrappers.churchService.allLecturesRV;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.dialogBox.DialogBox;
import com.scrappers.churchService.localDatabase.LocalDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RemoveLecture implements View.OnClickListener {

    private final AppCompatActivity context;
    private final EditText lecture;
    private String servantName;

    public RemoveLecture(AppCompatActivity context, EditText lecture) {
        this.context=context;
        this.lecture=lecture;
    }

    @Override
    public void onClick(View v) {
        final DialogBox dialogBox=new DialogBox(context);
        dialogBox.showDialog(R.layout.remove_lecture_prompt, Gravity.CENTER);
        assert  dialogBox.getAlertDialog().getWindow() !=null;
        dialogBox.getAlertDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.radial_dailog));
        final View view=dialogBox.getInflater();
        Button yesDelete=view.findViewById(R.id.yesDelete);
        yesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                LocalDatabase localDatabase=new LocalDatabase(context,"/user/user.json");
                try{
                    servantName =localDatabase.readData().getJSONObject(0).getString("name");
                }catch (Exception e){
                    e.printStackTrace();
                }
                DatabaseReference lecturesNode=databaseReference.child("Gnod").child("servants").child(servantName).child("lectures");
                lecturesNode.child(lecture.getText().toString()).removeValue();

                Snackbar.make(view,"تم الغاء الدرس",Snackbar.LENGTH_LONG).show();
                dialogBox.getAlertDialog().dismiss();
            }
        });
        Button noClose=view.findViewById(R.id.noClose);
        noClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.getAlertDialog().dismiss();
            }
        });

    }
}
