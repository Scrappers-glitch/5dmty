package com.scrappers.churchService.mainScreens;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.allServantsRV.ServantsCardView;
import com.scrappers.churchService.allServantsRV.servantsModel.ServantsModel;
import com.scrappers.churchService.realTimeDatabase.ReadServantsChanges;

import java.util.ArrayList;


public class AllServantsScreen extends Fragment {


    private final AppCompatActivity context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View viewInflater;

    public AllServantsScreen(AppCompatActivity context) {
        this.context=context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewInflater=inflater.inflate(R.layout.fragment_all_servants_screen, container, false);

        loadAllServants();


        swipeRefreshLayout=viewInflater.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.RED,Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CountDownTimer(4000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        loadAllServants();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });
        return viewInflater;
    }

    private void loadAllServants() {
        RecyclerView recyclerView=viewInflater.findViewById(R.id.allServantsRV);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));

        ServantsCardView servantsCardView=new ServantsCardView(context,new ArrayList<ServantsModel>());
        recyclerView.setAdapter(servantsCardView);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ReadServantsChanges("Gnod",servantsCardView));
    }
}