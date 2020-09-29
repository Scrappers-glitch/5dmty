package com.scrappers.churchService.mainScreens;

import android.os.Bundle;
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
import com.scrappers.churchService.allLecturesRV.LecturesCardView;
import com.scrappers.churchService.allLecturesRV.lecturesModel.LecturesModel;
import com.scrappers.churchService.dialogBox.DialogBox;
import com.scrappers.churchService.localDatabase.LocalDatabase;
import com.scrappers.churchService.realTimeDatabase.ReadDatabaseChanges;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllLecturesActivity extends Fragment {

    private String servantName;
    private RecyclerView lecturesRV;
    private LecturesCardView lecturesCardView;
    private DatabaseReference databaseReference;
    private int spanCount=1;
    private final AppCompatActivity context;

    public AllLecturesActivity(AppCompatActivity context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewInflater=inflater.inflate(R.layout.activity_all_lectures,container,false);

        Toolbar toolbar=viewInflater.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolderActivity.navigationDrawer.showUp();
            }
        });
        try{
            servantName=new LocalDatabase(context,"/user/user.json").readData().getJSONObject(0).getString("name");
        }catch (Exception e){
            e.printStackTrace();
        }

        loadAllLectures(viewInflater);

        SearchView searchView=viewInflater.findViewById(R.id.searchLecture);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lecturesCardView.getFilter().filter(query);
                lecturesCardView.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                databaseReference.addValueEventListener(new ReadDatabaseChanges(context,lecturesCardView,lecturesRV,servantName));
                lecturesCardView.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                databaseReference.addValueEventListener(new ReadDatabaseChanges(context,lecturesCardView,lecturesRV,servantName));
                lecturesCardView.notifyDataSetChanged();
                return false;
            }
        });

        ImageButton adapterSettings=viewInflater.findViewById(R.id.adapterSettings);
        adapterSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogBox dialogBox=new DialogBox(context);
                dialogBox.showDialog(R.layout.adapter_settings_dialog, Gravity.TOP);
                assert dialogBox.getAlertDialog().getWindow() !=null;
                dialogBox.getAlertDialog().getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.radial_dailog));
//                dialogBox.getAlertDialog().getWindow().getAttributes().windowAnimations=R.style.Widget_AppCompat_PopupWindow;
                /*SearchType Settings Listeners*/
                Button[] searchType={dialogBox.getInflater().findViewById(R.id.searchByLecture),
                        dialogBox.getInflater().findViewById(R.id.searchByDate),
                        dialogBox.getInflater().findViewById(R.id.searchByVerse),
                        dialogBox.getInflater().findViewById(R.id.searchByNotes)};

                final String[] applySearchType={"lecture",
                        "date",
                        "verse",
                        "notes"};

                for(int position=0;position<searchType.length;position++){
                    final int finalPosition = position;
                    searchType[position].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lecturesCardView.setSearchType(applySearchType[finalPosition]);
                            dialogBox.getAlertDialog().dismiss();
                        }
                    });
                }

                /*GridLayout Settings Listeners*/
                final Button[] gridType={
                        dialogBox.getInflater().findViewById(R.id.gridOneByOne),
                        dialogBox.getInflater().findViewById(R.id.gridTwoByTwo)};

                final int[] applyGridType={1,2};

                for(int position=0;position<gridType.length;position++){
                    final int finalPosition = position;
                    gridType[position].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setSpanCount(applyGridType[finalPosition]);
                            lecturesRV.setLayoutManager(new GridLayoutManager(context,getSpanCount()));
                            dialogBox.getAlertDialog().dismiss();
                        }
                    });
                }
            }
        });



        return viewInflater;
    }


    private void loadAllLectures(View viewInflater){
        lecturesRV=viewInflater.findViewById(R.id.allLecturesRV);
        lecturesRV.setLayoutManager(new GridLayoutManager(context,getSpanCount()));
        lecturesCardView=new LecturesCardView(new ArrayList<LecturesModel>(),servantName,context,lecturesRV);
        lecturesCardView.setSearchType("lecture");
        lecturesRV.setAdapter(lecturesCardView);

        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ReadDatabaseChanges(context,lecturesCardView,lecturesRV,servantName));
    }

    private void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    private int getSpanCount() {
        return spanCount;
    }
}
