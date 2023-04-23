package com.example.tasklite.Entities;

import com.example.tasklite.DB.AppDataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = AppDataBase.TASK_TABLE)
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int taskId;
    private String name;

    private String description;
    private int priority;
    private int userId = -1;

    @ColumnInfo(name = "due_date")
    private Date due;
    @ColumnInfo(name = "date_created")
    private Date creationDate;
    @ColumnInfo(name = "completed")
    private boolean complete;

    public Task(String name, String description, int priority, int userId, boolean complete) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.due = due;
        this.userId = userId;
        creationDate = Calendar.getInstance().getTime();
        this.complete = complete;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public PriorityLevel getPriority() {
//        return priority;
//    }
//
//    public void setPriority(PriorityLevel priority) {
//        this.priority = priority;
//    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Date getSetDate() {
        return creationDate;
    }

    public void setSetDate(Date setDate) {
        this.creationDate = setDate;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", due=" + due +
                ", creationDate=" + creationDate +
                ", complete=" + complete +
                '}';
    }
}
