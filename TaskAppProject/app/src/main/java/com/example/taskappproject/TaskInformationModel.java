package com.example.taskappproject;

public class TaskInformationModel {

    private int id;
    private String taskName, taskType, taskPriority;
    private int month, day, year, hour, minute;
    private boolean complete;

    public TaskInformationModel(int id, String taskName, String taskType, String taskPriority, int month, int day, int year, int hour, int minute, boolean complete) {
        this.id = id;
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskPriority = taskPriority;
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.complete = complete;
    }

    public TaskInformationModel() {
    }

    //toString
    @Override
    public String toString() {
        return "TaskInformationModel{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskPriority='" + taskPriority + '\'' +
                ", month=" + month +
                ", day=" + day +
                ", year=" + year +
                ", hour=" + hour +
                ", minute=" + minute +
                ", complete=" + complete +
                '}';
    }

    //Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
