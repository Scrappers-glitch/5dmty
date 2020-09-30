package com.scrappers.churchService.mainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.HolderActivity;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private boolean isRememberMe=false;
    private final LocalDatabase localDatabase=new LocalDatabase(RegisterActivity.this, "/user/user.json");
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            isRememberMe = localDatabase.readData().getJSONObject(1).getBoolean("isRememberMe");
        } catch (Exception e) {
            e.printStackTrace();
            /*
             *create directory /user at Local App Data Environment Level
             */
            File directory = new File(RegisterActivity.this.getFilesDir() + "/user");
            if ( directory.mkdirs() ){
                Toast.makeText(getApplicationContext(), "Church Service is ready !", Toast.LENGTH_LONG).show();
            }
        }

        if(isRememberMe){
            startActivity(new Intent(this, HolderActivity.class));
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
            ImageButton register = findViewById(R.id.register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*
                     *save Data at RealTime environment
                     */
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference();


                            /* RealTime Database*/

                            DatabaseReference servantNode =
                                    databaseReference.
                                    child("Gnod").
                                    child("servants").
                                    child(((EditText)findViewById(R.id.servantName)).getText().toString()).
                                    child("details");

                            servantNode.child("name").setValue(((EditText)findViewById(R.id.servantName)).getText().toString());
                            servantNode.child("age").setValue(((EditText)findViewById(R.id.servantAge)).getText().toString());
                            servantNode.child("class").setValue(((EditText)findViewById(R.id.servantClass)).getText().toString());
                            servantNode.child("phoneNumber").setValue(((EditText)findViewById(R.id.phoneNumber)).getText().toString());
                            /*save Local Database as a JSON file*/
                            localDatabase.writeData(((EditText)findViewById(R.id.servantName)).getText().toString(), isRememberMe, false);


                    startActivity(new Intent(getApplicationContext(), HolderActivity.class));
                    finish();
                }
            });
        }
    }
}
