package com.scrappers.churchService.mainScreens;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.HolderActivity;
import com.scrappers.churchService.R;
import com.scrappers.churchService.allServantsRV.ServantsCardView;
import com.scrappers.churchService.allServantsRV.servantsModel.ServantsModel;
import com.scrappers.churchService.optionPane.OptionPane;
import com.scrappers.churchService.realTimeDatabase.ReadServantsChanges;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class AllServantsScreen extends Fragment {


    private final AppCompatActivity context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View viewInflater;
    private RecyclerView recyclerView;
    private ServantsCardView servantsCardView;
    private int spanCount=1;
    private ImageButton adapterSettings;


    public AllServantsScreen(@NonNull AppCompatActivity context) {
        this.context=context;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewInflater=inflater.inflate(R.layout.fragment_all_servants_screen, container, false);

        loadAllServants();

        Toolbar toolbar=viewInflater.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolderActivity.navigationDrawer.showUp();
            }
        });

        swipeRefreshLayout=viewInflater.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent2,null),
                getResources().getColor(R.color.colorPrimaryDark,null)
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CountDownTimer(2500,2000){

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


        SearchView searchView=viewInflater.findViewById(R.id.searchServant);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                servantsCardView.getFilter().filter(query);
                servantsCardView.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                databaseReference.addValueEventListener(new ReadServantsChanges("Gnod",servantsCardView));
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterSettings.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                databaseReference.addValueEventListener(new ReadServantsChanges("Gnod",servantsCardView));
                adapterSettings.setVisibility(View.INVISIBLE);
                return false;
            }
        });


        adapterSettings=viewInflater.findViewById(R.id.adapterSettings);
        adapterSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OptionPane optionPane =new OptionPane(context);
                optionPane.showDialog(R.layout.dialog_adapter_settings_allservants, Gravity.TOP);
                assert optionPane.getAlertDialog().getWindow() !=null;
                optionPane.getAlertDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.radial_dailog));
//                optionPane.getAlertDialog().getWindow().getAttributes().windowAnimations=R.style.Widget_AppCompat_PopupWindow;
                /*SearchType Settings Listeners*/
                Button[] searchType={optionPane.getInflater().findViewById(R.id.searchByName),
                        optionPane.getInflater().findViewById(R.id.searchByAge),
                        optionPane.getInflater().findViewById(R.id.searchByClass),
                        optionPane.getInflater().findViewById(R.id.searchByPhoneNumber)};

                final String[] applySearchType={
                        "name",
                        "age",
                        "class",
                        "phoneNumber"};

                for(int position=0;position<searchType.length;position++){
                    final int finalPosition = position;
                    searchType[position].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            servantsCardView.setSearchType(applySearchType[finalPosition]);
                            optionPane.getAlertDialog().dismiss();
                        }
                    });
                }

                /*GridLayout Settings Listeners*/
                final Button[] gridType={
                        optionPane.getInflater().findViewById(R.id.gridOneByOne),
                        optionPane.getInflater().findViewById(R.id.gridTwoByTwo)};

                final int[] applyGridType={1,2};

                for(int position=0;position<gridType.length;position++){
                    final int finalPosition = position;
                    gridType[position].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setSpanCount(applyGridType[finalPosition]);
                            recyclerView.setLayoutManager(new GridLayoutManager(context,getSpanCount()));
                            optionPane.getAlertDialog().dismiss();
                        }
                    });
                }
            }
        });


        return viewInflater;
    }

    private void setSpanCount(int spanCount) {
        this.spanCount=spanCount;
    }

    public int getSpanCount() {
        return spanCount;
    }

    private void loadAllServants() {
        recyclerView=viewInflater.findViewById(R.id.allServantsRV);
        recyclerView.setLayoutManager(new GridLayoutManager(context,getSpanCount()));

        servantsCardView=new ServantsCardView(context,new ArrayList<ServantsModel>());
        recyclerView.setAdapter(servantsCardView);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ReadServantsChanges("Gnod",servantsCardView));
    }
}