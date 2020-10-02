package com.scrappers.churchService.allServantsRV.servantsModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServantsModel {

    private String servantName;
    private String servantAge;
    private String servantClass;
    private String servantPhoneNumber;
    private String searchType;
    private String numberOfAbsence;
    private String absence;

    public ServantsModel(@Nullable String servantName, @Nullable String servantAge, @Nullable String servantClass, @Nullable String servantPhoneNumber, String numberOfAbsence, String absence){
        this.setServantName(servantName);
        this.setServantAge(servantAge);
        this.setServantClass(servantClass);
        this.setServantPhoneNumber(servantPhoneNumber);
        this.setNumberOfAbsence(numberOfAbsence);
        this.setAbsence(absence);
    }

    public String getServantName() {
        return servantName;
    }

    public void setServantName(String servantName) {
        this.servantName = servantName;
    }

    public String getServantAge() {
        return servantAge;
    }

    public void setServantAge(String servantAge) {
        this.servantAge = servantAge;
    }

    public String getServantClass() {
        return servantClass;
    }

    public void setServantClass(String servantClass) {
        this.servantClass = servantClass;
    }

    public String getServantPhoneNumber() {
        return servantPhoneNumber;
    }

    public void setServantPhoneNumber(String servantPhoneNumber) {
        this.servantPhoneNumber = servantPhoneNumber;
    }

    public void setNumberOfAbsence(String numberOfAbsence) {
        this.numberOfAbsence = numberOfAbsence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

    public String isAbsence() {
        return absence;
    }

    public String getNumberOfAbsence() {
        return numberOfAbsence;
    }

    public void setSearchType(@NonNull String searchType) {
        switch (searchType){
            case "name":
                this.searchType=getServantName();
                break;
            case "age":
                this.searchType=getServantAge();
                break;
            case "class":
                this.searchType=getServantClass();
                break;
            case "phoneNumber":
                this.searchType=getServantPhoneNumber();
                break;
        }
    }

    public String getSearchType() {
        return searchType;
    }
}
