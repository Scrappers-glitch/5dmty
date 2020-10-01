package com.scrappers.churchService.allLecturesRV.lecturesModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LecturesModel {
    private String lecture;
    private String date;
    private String verse;
    private String notes;
    private String searchType;

    public LecturesModel(@Nullable String lecture, @Nullable String date, @Nullable String verse, @Nullable String notes){
        this.lecture=lecture;
        this.date=date;
        this.verse=verse;
        this.notes=notes;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getLecture() {
        return lecture;
    }

    public String getDate() {
        return date;
    }

    public String getVerse() {
        return verse;
    }

    public String getNotes() {
        return notes;
    }

    public String getSearchType() {
        return searchType;
    }
    public void applySearchType(@NonNull String searchType){
        if(searchType.toLowerCase().trim().contains("lecture")){
            searchType=getLecture();
            this.searchType=searchType;
        }else if(searchType.toLowerCase().trim().contains("date")){
            searchType=getDate();
            this.searchType=searchType;
        }else if(searchType.toLowerCase().trim().contains("verse")){
            searchType=getVerse();
            this.searchType=searchType;
        }else if(searchType.toLowerCase().trim().contains("notes")){
            searchType=getNotes();
            this.searchType=searchType;
        }

    }
}
