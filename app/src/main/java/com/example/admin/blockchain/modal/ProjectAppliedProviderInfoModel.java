package com.example.admin.blockchain.modal;

/**
 * Created by MOHIT on 15-09-2017.
 */

public class ProjectAppliedProviderInfoModel {
    private String projectName, dayValue, proAbstract;
    private int id;

    public ProjectAppliedProviderInfoModel() {
    }

    public ProjectAppliedProviderInfoModel(int id, String projectName, String dayValue, String proAbstract) {
        this.id = id;
        this.projectName = projectName;
        this.dayValue = dayValue;
        this.proAbstract = proAbstract;

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

    public String getProAbstract() {
        return proAbstract;
    }

    public void setProAbstract(String proAbstract) {
        this.proAbstract = proAbstract;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}