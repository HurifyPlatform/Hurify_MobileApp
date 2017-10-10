package com.example.admin.blockchain.modal;

/**
 * Created by MOHIT on 15-09-2017.
 */

public class ProjectAppliedInfoModel {
    private String projectName, dayValue, bidValue, expertLevel,select,status;
    private int id;

    public ProjectAppliedInfoModel() {
    }

    public ProjectAppliedInfoModel(int id, String projectName, String dayValue, String bidValue, String expertLevel, String select,String status) {
        this.id = id;
        this.projectName = projectName;
        this.dayValue = dayValue;
        this.expertLevel = expertLevel;
        this.bidValue = bidValue;
        this.select = select;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
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

    public String getBidValue() {
        return bidValue;
    }

    public void setBidValue(String bidValue) {
        this.bidValue = bidValue;
    }

    public String getExpertLevel() {
        return expertLevel;
    }

    public void setExpertLevel(String expertLevel) {
        this.expertLevel = expertLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}