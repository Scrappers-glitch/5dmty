package com.scrappers.churchService.allLecturesRV.lecturesModel;

public class LecturesModel {
    private String lecture;
    private String date;
    private String verse;
    private String notes;
    private String searchType;

    public LecturesModel(String lecture,String date,String verse,String notes){
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getVerse() {
        return verse;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public String getSearchType() {
        return searchType;
    }
    public void applySearchType(String searchType){
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
