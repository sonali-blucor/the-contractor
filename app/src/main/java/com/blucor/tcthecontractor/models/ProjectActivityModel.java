package com.blucor.tcthecontractor.models;

public class ProjectActivityModel {
    private String no;
    private String activityName;
    private String startDate;
    private String endDate;
    private String duration;
    private String workerName;
    private String workerMobileNo;
    private boolean editFlag;

    public ProjectActivityModel() {
    }

    /**
     *
     * @param no
     * @param activityName
     * @param startDate
     * @param endDate
     * @param duration
     * @param workerName
     * @param workerMobileNo
     * @param editFlag
     */
    public ProjectActivityModel(String no, String activityName, String startDate, String endDate, String duration, String workerName, String workerMobileNo,boolean editFlag) {
        this.no = no;
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.workerName = workerName;
        this.workerMobileNo = workerMobileNo;
        this.editFlag = editFlag;
    }

    /**
     *
     * @param editFlag
     */
    public ProjectActivityModel(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerMobileNo() {
        return workerMobileNo;
    }

    public void setWorkerMobileNo(String workerMobileNo) {
        this.workerMobileNo = workerMobileNo;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }
}
