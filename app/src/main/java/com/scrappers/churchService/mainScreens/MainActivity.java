package com.scrappers.churchService.mainScreens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference=firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LocalDatabase database=new LocalDatabase(this,"/user/user.json");
        try {
            ((EditText)findViewById(R.id.servantName)).setText(database.readData().getJSONObject(0).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button sendLecture=findViewById(R.id.sendLecture);
        sendLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String servantName=((EditText)findViewById(R.id.servantName)).getText().toString();
                String lectureName=((EditText)findViewById(R.id.lecture)).getText().toString();

                DatabaseReference servantNode=databaseReference.child("جمعيه الجنود").child("الخدام").child(servantName);
                DatabaseReference lectureNode=servantNode.child("الدروس").child(lectureName);
                lectureNode.child(((TextView)findViewById(R.id.date)).getText().toString()).setValue(((EditText)findViewById(R.id.lectureDate)).getText().toString());
                lectureNode.child(((TextView)findViewById(R.id.verse)).getText().toString()).setValue(((EditText)findViewById(R.id.quote)).getText().toString());
                lectureNode.child(((TextView)findViewById(R.id.notes)).getText().toString()).setValue(((EditText)findViewById(R.id.lectureNotes)).getText().toString());

            }
        });
    }

}
