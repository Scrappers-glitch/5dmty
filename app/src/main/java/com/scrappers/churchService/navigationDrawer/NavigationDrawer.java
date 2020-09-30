package com.scrappers.churchService.navigationDrawer;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.scrappers.churchService.R;
import com.scrappers.churchService.dialogBox.DialogBox;
import com.scrappers.churchService.localDatabase.LocalDatabase;
import com.scrappers.churchService.mainScreens.AddNewLectureScreen;
import com.scrappers.churchService.mainScreens.AllLecturesScreen;
import com.scrappers.churchService.mainScreens.AllServantsScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class NavigationDrawer {
    private static final int OPEN_DRAWER = 1;
    private static final int CLOSE_DRAWER = 2;
    private final AppCompatActivity context;
    private  DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private String servantName;
    private LocalDatabase localDatabase;
    private boolean isRememberAdmin;

    public NavigationDrawer(AppCompatActivity context,DrawerLayout drawerLayout,NavigationView navigationView,Toolbar toolbar){
        this.context=context;
        this.drawerLayout=drawerLayout;
        this.navigationView=navigationView;
        this.toolbar=toolbar;
    }
    public void activate(){
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(context,drawerLayout,toolbar,OPEN_DRAWER,CLOSE_DRAWER);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.allLecturesItem):
                        try{
                            localDatabase=new LocalDatabase(context,"/user/user.json");
                            servantName=localDatabase.readData().getJSONObject(0).getString("name");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        dismiss();
                        displayFragment(new AllLecturesScreen(context,servantName,false));
                        break;
                    case (R.id.addNewLectureItem):
                        dismiss();
                        displayFragment(new AddNewLectureScreen(context));
                        break;
                    case (R.id.serviceKeeper):
                        dismiss();
                        localDatabase=new LocalDatabase(context,"/user/user.json");
                        try {
                            isRememberAdmin=localDatabase.readData().getJSONObject(2).getBoolean("isRememberAdmin");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(!isRememberAdmin){
                            final DialogBox dialogBox = new DialogBox(context);
                            dialogBox.showDialog(R.layout.dialog_servicekeeper_key, Gravity.CENTER);
                            assert dialogBox.getAlertDialog().getWindow() != null;
                            dialogBox.getAlertDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.radial_dailog));

                            final EditText pinField = dialogBox.getInflater().findViewById(R.id.pinField);
                            /*
                             * Remember me checkBox
                             */
                            final CheckBox rememberAdmin=dialogBox.getInflater().findViewById(R.id.rememberMe);

                            /*
                             *Listeners
                             */
                            Button signIn = dialogBox.getInflater().findViewById(R.id.keeperSignIn);
                            signIn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if ( pinField.getText().toString().equals("000333444") ){
                                        dialogBox.getAlertDialog().dismiss();
                                        try {
                                            localDatabase.writeData(localDatabase.readData().getJSONObject(0).getString("name"),
                                                    localDatabase.readData().getJSONObject(1).getBoolean("isRememberMe"),
                                                    rememberAdmin.isChecked());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        displayFragment(new AllServantsScreen(context));
                                    } else {
                                        Toast.makeText(context, "الرجاء التأكد من كلمه المرور", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            /*
                             *Dismiss the Dialog
                             */
                            Button cancel = dialogBox.getInflater().findViewById(R.id.keeperCancel);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBox.getAlertDialog().dismiss();
                                }
                            });


                        }else{
                            displayFragment(new AllServantsScreen(context));
                        }

                        break;

                }
                return false;
            }
        });
    }
    private void displayFragment(Fragment window){
        FragmentTransaction fragmentTransaction=context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,window);
        fragmentTransaction.commit();
    }
    public void dismiss(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void showUp(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public boolean isShownUp() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }
}
