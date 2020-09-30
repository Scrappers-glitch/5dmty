package com.scrappers.churchService.navigationDrawer;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.scrappers.churchService.R;
import com.scrappers.churchService.localDatabase.LocalDatabase;
import com.scrappers.churchService.localDatabase.ProfileImage;
import com.scrappers.churchService.mainScreens.AddNewLectureScreen;
import com.scrappers.churchService.mainScreens.AllLecturesScreen;
import com.scrappers.churchService.mainScreens.AllServantsScreen;
import com.scrappers.churchService.optionPane.OptionPane;
import com.scrappers.churchService.signOut.SignOut;

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
    @SuppressLint("StaticFieldLeak")
    public static ImageView profileImage;

    public NavigationDrawer(AppCompatActivity context,DrawerLayout drawerLayout,NavigationView navigationView,Toolbar toolbar){
        this.context=context;
        this.drawerLayout=drawerLayout;
        this.navigationView=navigationView;
        this.toolbar=toolbar;
    }
    public void activate(){
        navigationView.bringToFront();
        profileImage=navigationView.getHeaderView(0).findViewById(R.id.profileImage);
        try {
            profileImage.setImageBitmap(new ProfileImage(context, context.getFilesDir() + "/user/profileImage.png").getProfileImageFromFile());
        }catch (NullPointerException e) {
            profileImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_person_24));
        }

        TextView profileName=navigationView.getHeaderView(0).findViewById(R.id.profileName);
        profileName.setText(new LocalDatabase(context, "/user/user.json").readData(0,"name").toString());

        TextView className=navigationView.getHeaderView(0).findViewById(R.id.servantClass);
        className.setText(new LocalDatabase(context, "/user/user.json").readData(1,"class").toString());

        TextView phoneNumber=navigationView.getHeaderView(0).findViewById(R.id.phoneNumber);
        phoneNumber.setText(new LocalDatabase(context, "/user/user.json").readData(2,"phoneNumber").toString());


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
                            servantName=localDatabase.readData(0,"name").toString();
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
                        isRememberAdmin= (boolean) localDatabase.readData(4,"isRememberAdmin");
                        if(!isRememberAdmin){
                            final OptionPane optionPane = new OptionPane(context);
                            optionPane.showDialog(R.layout.dialog_servicekeeper_key, Gravity.CENTER);
                            assert optionPane.getAlertDialog().getWindow() != null;
                            optionPane.getAlertDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.radial_dailog));

                            final EditText pinField = optionPane.getInflater().findViewById(R.id.pinField);
                            /*
                             * Remember me checkBox
                             */
                            final CheckBox rememberAdmin= optionPane.getInflater().findViewById(R.id.rememberMe);

                            /*
                             *Listeners
                             */
                            Button signIn = optionPane.getInflater().findViewById(R.id.keeperSignIn);
                            signIn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if ( pinField.getText().toString().equals("000333444") ){
                                        optionPane.getAlertDialog().dismiss();
                                        try {
                                            localDatabase.writeData(
                                                    localDatabase.readData(0,"name").toString(),
                                                    localDatabase.readData(1,"class").toString(),
                                                    localDatabase.readData(2,"phoneNumber").toString(),
                                                    Boolean.parseBoolean(localDatabase.readData(3,"isRememberMe").toString()),
                                                    rememberAdmin.isChecked()
                                            );

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
                            Button cancel = optionPane.getInflater().findViewById(R.id.keeperCancel);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    optionPane.getAlertDialog().dismiss();
                                }
                            });

                        }else{
                            displayFragment(new AllServantsScreen(context));
                        }

                        break;
                    case (R.id.signOut):
                        dismiss();
                        final OptionPane optionPane =new OptionPane(context);
                        optionPane.showDialog(R.layout.dialog_signout_prompt,Gravity.CENTER);
                        assert optionPane.getAlertDialog().getWindow() !=null;
                        optionPane.getAlertDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.radial_dailog));

                        final CheckBox deleteData= optionPane.getInflater().findViewById(R.id.deleteMyDataCheck);
                        Button yesOut= optionPane.getInflater().findViewById(R.id.yesOut);
                        yesOut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(deleteData.isChecked()){
                                    SignOut signOut=new SignOut(context);
                                    signOut.startSession();
                                    signOut.deleteRealTimeDataBase();
                                }else{
                                    SignOut signOut=new SignOut(context);
                                    signOut.startSession();

                                }
                            }
                        });
                        Button noClose= optionPane.getInflater().findViewById(R.id.noClose);
                        noClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               optionPane.getAlertDialog().dismiss();
                            }
                        });
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
