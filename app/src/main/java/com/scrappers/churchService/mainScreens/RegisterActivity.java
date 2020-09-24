package com.scrappers.churchService.mainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private boolean isRememberMe=false;
    private final LocalDatabase localDatabase=new LocalDatabase(RegisterActivity.this, "/user/user.json");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            isRememberMe = localDatabase.readData().getJSONObject(1).getBoolean("isRememberMe");
        } catch (Exception e) {
            e.printStackTrace();
            /*
             *save Data at LocalDatabase Environment
             */
            File directory = new File(RegisterActivity.this.getFilesDir() + "/user");
            if ( directory.mkdirs() ){
                Toast.makeText(getApplicationContext(), "Church Service is ready !", Toast.LENGTH_LONG).show();
            }
        }

        if(isRememberMe){

            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else {
            /*Remember Me checkBox*/
            CheckBox rememberMe=findViewById(R.id.rememberMe);
            rememberMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isRememberMe=((CheckBox)view).isChecked();
                }
            });

            /*Register Button*/
            Button register = findViewById(R.id.register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*
                     *save Data at RealTime environment
                     */
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();

                    DatabaseReference servantNode = databaseReference.child("جمعيه الجنود").child("الخدام").child(((EditText)findViewById(R.id.servantName)).getText().toString()).child("الخادم");
                    servantNode.child(((TextView)findViewById(R.id.nameTitle)).getText().toString()).
                                                        setValue(((EditText)findViewById(R.id.servantName)).getText().toString());
                    servantNode.child(((TextView)findViewById(R.id.ageTitle)).getText().toString()).
                                                        setValue(((EditText)findViewById(R.id.servantAge)).getText().toString());
                    servantNode.child(((TextView)findViewById(R.id.classTitle)).getText().toString()).
                                                        setValue(((EditText)findViewById(R.id.servantClass)).getText().toString());

                    /*save Local Database as a JSON file*/
                    localDatabase.writeData(((EditText)findViewById(R.id.servantName)).getText().toString(), isRememberMe);

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
        }
    }
}
