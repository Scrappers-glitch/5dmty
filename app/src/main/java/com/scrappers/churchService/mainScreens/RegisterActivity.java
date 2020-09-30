package com.scrappers.churchService.mainScreens;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.HolderActivity;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;
import com.scrappers.churchService.localDatabase.ProfileImage;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class RegisterActivity extends AppCompatActivity {

    private static final int OPEN_DOC = 10;
    private static final int READ_EXTERNAL_STORAGE = 22;
    private boolean isRememberMe=false;
    private final LocalDatabase localDatabase=new LocalDatabase(RegisterActivity.this, "/user/user.json");
    private DatabaseReference databaseReference;
    private ImageView personalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            isRememberMe = (boolean) localDatabase.readData(3,"isRememberMe");
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
            personalImage=findViewById(R.id.personalImage);
            personalImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE);
                }
            });

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
                     if(!((EditText)findViewById(R.id.servantName)).getText().toString().isEmpty()
                             |
                             ((EditText)findViewById(R.id.servantName)).getText().toString().length()!=0){
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
                                         child(((EditText) findViewById(R.id.servantName)).getText().toString()).
                                         child("details");

                         servantNode.child("name").setValue(((EditText) findViewById(R.id.servantName)).getText().toString());
                         servantNode.child("age").setValue(((EditText) findViewById(R.id.servantAge)).getText().toString());
                         servantNode.child("class").setValue(((EditText) findViewById(R.id.servantClass)).getText().toString());
                         servantNode.child("phoneNumber").setValue(((EditText) findViewById(R.id.phoneNumber)).getText().toString());
                         /*save Local Database as a JSON file*/
                         localDatabase.writeData(
                                 ((EditText) findViewById(R.id.servantName)).getText().toString(),
                                 ((EditText) findViewById(R.id.servantClass)).getText().toString(),
                                 ((EditText) findViewById(R.id.phoneNumber)).getText().toString(),
                                 isRememberMe, false);


                         startActivity(new Intent(getApplicationContext(), HolderActivity.class));
                         finish();
                     }else{
                         Snackbar.make(view,"رجاء التأكد من إسم الخادم",Snackbar.LENGTH_LONG).show();
                     }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == READ_EXTERNAL_STORAGE){
            Intent docIntent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            docIntent.addCategory(Intent.CATEGORY_OPENABLE);
            docIntent.setType("image/*");
            startActivityForResult(Intent.createChooser(docIntent,"Select a Profile Image from Local Storage"),OPEN_DOC);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==OPEN_DOC && resultCode==Activity.RESULT_OK){
            assert data !=null;
            ProfileImage profileImage=new ProfileImage(RegisterActivity.this,data);
            personalImage.setImageBitmap(profileImage.getProfileImageFromRawData());
            profileImage.saveProfileImage();
        }
    }


}
