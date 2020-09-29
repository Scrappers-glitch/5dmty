package com.scrappers.churchService.navigationDrawer;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.scrappers.churchService.R;
import com.scrappers.churchService.mainScreens.AddNewLectureActivity;
import com.scrappers.churchService.mainScreens.AllLecturesActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
                        dismiss();
                        displayFragment(new AllLecturesActivity(context));
                        break;
                    case (R.id.addNewLectureItem):
                        dismiss();
                        displayFragment(new AddNewLectureActivity(context));
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
