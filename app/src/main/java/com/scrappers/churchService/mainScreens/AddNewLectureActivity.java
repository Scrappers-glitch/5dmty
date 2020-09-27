package com.scrappers.churchService.mainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewLectureActivity extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference=firebaseDatabase.getReference();
    private String servantName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewlecture);

        LocalDatabase database=new LocalDatabase(AddNewLectureActivity.this,"/user/user.json");
        try {
             servantName =database.readData().getJSONObject(0).getString("name");
             System.err.println(servantName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button sendLecture=findViewById(R.id.sendLecture);
        sendLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        String lectureName=((EditText)findViewById(R.id.lecture)).getText().toString();
                        /*get Lectures number from RealTime Database*/
                        /*send lecture details to RealTime Cloud Servers*/
                        DatabaseReference servantNode=databaseReference.child("Gnod").child("servants").child(servantName);
                        DatabaseReference lectureNode=servantNode.child("lectures").child(lectureName);

                        lectureNode.child("lecture").setValue(lectureName);
                        lectureNode.child("date").setValue(((EditText)findViewById(R.id.lectureDate)).getText().toString());
                        lectureNode.child("verse").setValue(((EditText)findViewById(R.id.quote)).getText().toString());
                        lectureNode.child("notes").setValue("");

                startActivity(new Intent(AddNewLectureActivity.this,AllLecturesActivity.class));
            }
        });
    }

}
