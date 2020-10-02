package com.scrappers.churchService.allServantsRV.daysOfAbsence;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.R;
import com.scrappers.churchService.optionPane.OptionPane;
import com.scrappers.churchService.realTimeDatabase.ReadDaysOfAbsenceChanges;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DaysOfAbsence implements View.OnClickListener {

    private final AppCompatActivity context;
    private String servantName;
    private String daysOfAbsence;

    public DaysOfAbsence(AppCompatActivity context,String servantName,String daysOfAbsence){
        this.context=context;
        this.servantName=servantName;
        this.daysOfAbsence=daysOfAbsence;
    }
    @Override
    public void onClick(View v) {
        final OptionPane optionPane=new OptionPane(context);
        optionPane.showDialog(R.layout.dialog_days_of_absence, Gravity.CENTER);
        Objects.requireNonNull(
                optionPane.getAlertDialog().getWindow())
                .setBackgroundDrawable(
                        ContextCompat.getDrawable(context,R.drawable.radial_dailog));

        TextView servantNameWidget=optionPane.getInflater().findViewById(R.id.servantName);
        servantNameWidget.setText(servantName);

        TextView absenceDaysWidget=optionPane.getInflater().findViewById(R.id.numberOfAbsenceDays);
        absenceDaysWidget.setText(daysOfAbsence);

        Button addNewDay=optionPane.getInflater().findViewById(R.id.addDay);
        addNewDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final OptionPane optionPanel=new OptionPane(context);
                optionPanel.showDialog(R.layout.dialog_calendar_addday,Gravity.CENTER);
                final CalendarView calendarView=optionPanel.getInflater().findViewById(R.id.calendar);
                calendarView.setFirstDayOfWeek(Calendar.SATURDAY);
                calendarView.requestApplyInsets();
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        Calendar calendar=Calendar.getInstance();
                        calendar.set(year,month,dayOfMonth);
                        calendarView.setDate(calendar.getTimeInMillis());
                    }
                });
                Button addDay=optionPanel.getInflater().findViewById(R.id.addNewDay);
                addDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionPanel.getAlertDialog().dismiss();
                        String currentSelectedDate=SimpleDateFormat.getDateInstance().format(calendarView.getDate());
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                        DatabaseReference absenceNode=databaseReference.child("Gnod").child("servants").child(servantName).child("details").child("absence");
                        absenceNode.orderByKey();
                        absenceNode.child(currentSelectedDate).setValue(false);

                    }
                });
            }
        });

        DaysOfAbsenceCardView daysOfAbsenceCardView=new DaysOfAbsenceCardView(servantName,new ArrayList<DaysOfAbsenceModel>(),absenceDaysWidget);
        RecyclerView recyclerView=optionPane.getInflater().findViewById(R.id.daysOfAbsenceHolder);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(daysOfAbsenceCardView);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ReadDaysOfAbsenceChanges("Gnod",daysOfAbsenceCardView,servantName));
    }
}
