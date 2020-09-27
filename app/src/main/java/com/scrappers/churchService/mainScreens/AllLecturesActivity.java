package com.scrappers.churchService.mainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;
import com.scrappers.churchService.realTimeDatabase.ReadDatabaseChanges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllLecturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lectures);
        loadAllLectures();
        Button addNewLecture=findViewById(R.id.addNewLecture);
        addNewLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllLecturesActivity.this,AddNewLectureActivity.class));
                finish();
            }
        });
    }
    public void loadAllLectures(){
        RecyclerView lecturesRV=findViewById(R.id.allLecturesRV);
        lecturesRV.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        try {
            databaseReference.addValueEventListener(new ReadDatabaseChanges(this,lecturesRV,new LocalDatabase(this,"/user/user.json").readData().getJSONObject(0).getString("name")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
