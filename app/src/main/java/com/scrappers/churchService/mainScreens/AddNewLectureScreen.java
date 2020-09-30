package com.scrappers.churchService.mainScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.HolderActivity;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;

import org.json.JSONException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddNewLectureScreen extends Fragment {

    private final FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference=firebaseDatabase.getReference();
    private final AppCompatActivity context;

    private View viewInflater;
    private String servantName;

    public AddNewLectureScreen(AppCompatActivity context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewInflater=inflater.inflate(R.layout.fragment_addnewlecture,container,false);

        Toolbar toolbar=viewInflater.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolderActivity.navigationDrawer.showUp();
            }
        });

        LocalDatabase database=new LocalDatabase(context,"/user/user.json");
        try {
            servantName =database.readData().getJSONObject(0).getString("name");
            System.err.println(servantName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button sendLecture=viewInflater.findViewById(R.id.sendLecture);
        sendLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lectureName=((EditText)viewInflater.findViewById(R.id.lecture)).getText().toString();
                if( lectureName.length() != 0 | !lectureName.isEmpty() ){
                    /*get Lectures number from RealTime Database*/
                    /*send lecture details to RealTime Cloud Servers*/
                    DatabaseReference servantNode = databaseReference.child("Gnod").child("servants").child(servantName);
                    DatabaseReference lectureNode = servantNode.child("lectures").child(lectureName);

                    lectureNode.child("lecture").setValue(lectureName);
                    lectureNode.child("date").setValue(((EditText) viewInflater.findViewById(R.id.lectureDate)).getText().toString());
                    lectureNode.child("verse").setValue(((EditText) viewInflater.findViewById(R.id.quote)).getText().toString());
                    lectureNode.child("notes").setValue("");

                    try {
                        displayFragment(new AllLecturesScreen(context,
                                new LocalDatabase(context,"/user/user.json").readData()
                                        .getJSONObject(0).getString("name"),false));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Snackbar.make(view,"يتعذر ارسال درس خالي !",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return viewInflater;
    }

    private void displayFragment(Fragment window){
        FragmentTransaction fragmentTransaction=context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,window);
        fragmentTransaction.commit();
    }

}
