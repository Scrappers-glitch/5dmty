package com.scrappers.churchService.allServantsRV.servantsModel;

public class ServantsModel {

    private String servantName;
    private String servantAge;
    private String servantClass;
    private String servantPhoneNumber;

    public ServantsModel(String servantName,String servantAge,String servantClass,String servantPhoneNumber){
        this.setServantName(servantName);
        this.setServantAge(servantAge);
        this.setServantClass(servantClass);
        this.setServantPhoneNumber(servantPhoneNumber);
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
}
