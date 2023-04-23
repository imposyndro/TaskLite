package com.example.tasklite.DB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tasklite.Entities.Task;
import com.example.tasklite.Entities.User;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static TaskLiteRepository repository;
    public final LiveData<List<Task>> allTasks;
    public final LiveData<List<User>> allUsers;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskLiteRepository(application);
        allTasks = repository.getAllTasks();
        allUsers = repository.getAllUsers();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    public LiveData<List<Task>> getAllTasksById(int id) {
        return repository.getAllTasksById(id);
    }
    public LiveData<Task> getTaskById(int id){
        return repository.getTask(id);
    }
    public static void insert(Task task){
        repository.insertTask(task);
    }
    public static void update(Task task){
        repository.updateTask(task);
    }
    public static void delete(Task task){
        repository.deleteTask(task);
    }
    public void deleteAllTasks(){repository.deleteAllTasks();}


    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    public User getUserById(int id){
        return repository.getUser(id);
    }
    public User getUserByUsername(String username){
        return repository.getUser(username);
    }
    public static void insert(User user){
        repository.insertUser(user);
    }
    public static void update(User user){
        repository.updateUser(user);
    }
    public static void delete(User user){
        repository.deleteUser(user);
    }
    public void deleteAllUsers(){repository.deleteAllUsers();}

}
