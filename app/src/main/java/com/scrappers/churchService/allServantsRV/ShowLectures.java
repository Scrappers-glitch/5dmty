package com.scrappers.churchService.allServantsRV;

import android.view.View;

import com.scrappers.churchService.R;
import com.scrappers.churchService.mainScreens.AllLecturesScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ShowLectures implements View.OnClickListener {
    private final AppCompatActivity context;
    private String servantName;
    public ShowLectures(@NonNull AppCompatActivity context, String servantName) {
        this.context=context;
        this.servantName=servantName;
    }

    @Override
    public void onClick(View v) {
        displayFragment(new AllLecturesScreen(context,servantName,true));
    }
    private void displayFragment(@NonNull Fragment window){
        FragmentTransaction fragmentTransaction=context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,window);
        fragmentTransaction.commit();
    }
}
