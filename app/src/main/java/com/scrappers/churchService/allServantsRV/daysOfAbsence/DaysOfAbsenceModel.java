package com.scrappers.churchService.allServantsRV.daysOfAbsence;

public class DaysOfAbsenceModel {
    private String day;
    private boolean state;
    public DaysOfAbsenceModel(String day,boolean state){
        this.day=day;
        this.state=state;
    }

    public String getDay() {
        return day;
    }

    public Boolean getState(){
        return state;
    }
}
