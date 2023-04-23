package com.example.tasklite.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.tasklite.Entities.Task;
import com.example.tasklite.Entities.User;


import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void insert(Task... Tasks);

    @Update
    void update(Task... Tasks);

    @Delete
    void delete(Task... Tasks);

    @Query("SELECT * FROM " + AppDataBase.TASK_TABLE)
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM " + AppDataBase.TASK_TABLE + " WHERE taskId = :task_id")
    LiveData<Task> getTasksById(int task_id);

    @Query("SELECT * FROM " + AppDataBase.TASK_TABLE + " WHERE userId = :userId")
    LiveData<List<Task>> getAllTasksById(int userId);

    @Query("SELECT * FROM " + AppDataBase.TASK_TABLE + " ORDER BY priority DESC")
    LiveData<List<Task>> getAllTasks();

    @Query(" DELETE FROM " + AppDataBase.TASK_TABLE)
    void deleteAllTasks();

    @Query("DELETE FROM " + AppDataBase.TASK_TABLE + " WHERE userId = :userId")
    void deleteAllTasksById(int userId);

    @Insert
    void insert(User... Users);

    @Update
    void update(User... Users);

    @Delete
    void delete(User... Users);

    @Query("DELETE FROM USER_TABLE" )
    void deleteAllUsers();

    @Query("SELECT * FROM USER_TABLE ORDER BY username DESC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM USER_TABLE WHERE USER_TABLE.username == :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM USER_TABLE WHERE USER_TABLE.userId == :userId")
    User getUserByUserId(int userId);


}
