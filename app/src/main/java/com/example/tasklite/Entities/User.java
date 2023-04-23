package com.example.tasklite.Entities;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tasklite.DB.AppDataBase;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {


    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String username = "";
    private String password = "";
    private boolean isAdmin = false;

    /*public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }*/

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User{" +
                "nUserId='" + userId + '\'' +
                "userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
