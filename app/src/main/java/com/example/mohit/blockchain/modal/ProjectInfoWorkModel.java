package com.example.mohit.blockchain.modal;

/**
 * Created by MOHIT on 15-09-2017.
 */

public class ProjectInfoWorkModel {
    private String projectName,dayValue,Technology,Category1,projectAbstract,priceValue,expertLevel,estTime,applicationCount;
    private int id;

    public ProjectInfoWorkModel(){}

    public ProjectInfoWorkModel(int id, String projectName, String dayValue, String Technology, String Category1, String projectAbstract, String priceValue, String expertLevel, String estTime, String applicationCount){
        this.id=id;
        this.projectName=projectName;
        this.dayValue=dayValue;
        this.Technology=Technology;
        this.Category1=Category1;
        this.projectAbstract=projectAbstract;
        this.priceValue=priceValue;
        this.expertLevel=expertLevel;
        this.estTime=estTime;
        this.applicationCount=applicationCount;
    }

    public String getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(String applicationCount) {
        this.applicationCount = applicationCount;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDayValue() {
        return dayValue;
    }

    public void setDayValue(String dayValue) {
        this.dayValue = dayValue;
    }

    public String getTechnology() {
        return Technology;
    }

    public void setTechnology(String technology) {
        Technology = technology;
    }

    public String getCategory1() {
        return Category1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory1(String category1) {
        Category1 = category1;
    }


    public String getProjectAbstract() {
        return projectAbstract;
    }

    public void setProjectAbstract(String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }

    public String getExpertLevel() {
        return expertLevel;
    }

    public void setExpertLevel(String expertLevel) {
        this.expertLevel = expertLevel;
    }

    public String getEstTime() {
        return estTime;
    }

    public void setEstTime(String estTime) {
        this.estTime = estTime;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }
}
