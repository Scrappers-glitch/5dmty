package com.scrappers.churchService;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.scrappers.churchService.mainScreens.AllLecturesActivity;
import com.scrappers.churchService.navigationDrawer.NavigationDrawer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HolderActivity extends AppCompatActivity {

    public static NavigationDrawer navigationDrawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView=findViewById(R.id.navigation);
        DrawerLayout drawerLayout=findViewById(R.id.drawer);
        Toolbar toolbar=findViewById(R.id.toolbar);
        /*Define Default Fragment*/
        displayFragment(new AllLecturesActivity(this));
        /*Define Nav Drawer Instance*/
        navigationDrawer=new NavigationDrawer(this,drawerLayout,navigationView,toolbar);
        navigationDrawer.activate();
    }

    private void displayFragment(Fragment window){
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
}
