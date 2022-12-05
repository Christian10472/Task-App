package com.example.taskappproject;

public class TaskModel {
    private String taskTitle;

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }


    public TaskModel(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}
