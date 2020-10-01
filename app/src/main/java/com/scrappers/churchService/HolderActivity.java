package com.scrappers.churchService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.scrappers.churchService.localDatabase.LocalDatabase;
import com.scrappers.churchService.localDatabase.ProfileImage;
import com.scrappers.churchService.mainScreens.AllLecturesScreen;
import com.scrappers.churchService.navigationDrawer.NavigationDrawer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.scrappers.churchService.navigationDrawer.NavigationDrawer.profileImageWidget;

public class HolderActivity extends AppCompatActivity {

    public static final int OPEN_DOC = 12;
    private static final int READ_EXTERNAL_STORAGE = 2;
    @SuppressLint("StaticFieldLeak")
    public static NavigationDrawer navigationDrawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);
        NavigationView navigationView=findViewById(R.id.navigation);
        DrawerLayout drawerLayout=findViewById(R.id.drawer);
        Toolbar toolbar=findViewById(R.id.toolbar);

        /*Define Default Fragment*/
        try {
            displayFragment(new AllLecturesScreen(this,
                    new LocalDatabase(this,"/user/user.json").readData(0,"name").toString(),false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Define Nav Drawer Instance*/
        navigationDrawer=new NavigationDrawer(HolderActivity.this,drawerLayout,navigationView,toolbar);
        navigationDrawer.activate();

        profileImageWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(HolderActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE);
            }
        });
    }

    private void displayFragment(@NonNull Fragment window){
        FragmentTransaction fragmentTransaction=this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,window);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(navigationDrawer.isShownUp()){
            navigationDrawer.dismiss();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && requestCode==OPEN_DOC ){
            assert data!=null;
            ProfileImage image=new ProfileImage(HolderActivity.this,data);
            image.saveProfileImage();
            profileImageWidget.setImageBitmap(image.getProfileImageFromRawData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==READ_EXTERNAL_STORAGE){
            Intent openDocIntent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            openDocIntent.setType("image/*");
            openDocIntent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(openDocIntent,"Select a Profile Image from Local Storage"),OPEN_DOC);
        }
    }
}
