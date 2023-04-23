package com.example.tasklite.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tasklite.Entities.Task;
import com.example.tasklite.Entities.User;

import java.util.List;

public class TaskLiteRepository {
    private final TaskDAO taskDAO;

    private final LiveData<List<User>> allUsers;
    private final LiveData<List<Task>> allTasks;


    public TaskLiteRepository(Application application){
        AppDataBase dataBase = AppDataBase.taskInstance(application);
        taskDAO = dataBase.getTaskDAO();
        this.allUsers = taskDAO.getAllUsers();
        this.allTasks = taskDAO.getAllTasks();

    }

    //Newer method for running process in background thread
    public void insertUser(User user){
        AppDataBase.databaseExecutor.execute(() -> taskDAO.insert(user));
    }

    public void updateUser(User user){
        AppDataBase.databaseExecutor.execute(() -> taskDAO.update(user));
    }

    public void deleteUser(User user){
        AppDataBase.databaseExecutor.execute(() -> taskDAO.delete(user));
    }

    public void deleteAllUsers(){
        AppDataBase.databaseExecutor.execute(taskDAO::deleteAllUsers);
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    public User getUser(int id){return taskDAO.getUserByUserId(id);}

    public User getUser(String username){return taskDAO.getUserByUsername(username);}


    public void insertTask(Task task){
        AppDataBase.databaseExecutor.execute(() -> taskDAO.insert(task));
    }

    public void updateTask(Task task){
        AppDataBase.databaseExecutor.execute(() -> taskDAO.update(task));
    }

    public void deleteTask(Task task){
        AppDataBase.databaseExecutor.execute(() -> taskDAO.delete(task));
    }

    public void deleteAllTasks(){
        AppDataBase.databaseExecutor.execute(taskDAO::deleteAllTasks);
    }
    public void deleteAllTasksById(int id){
        AppDataBase.databaseExecutor.execute(() -> taskDAO.deleteAllTasksById(id));
    }
    public LiveData<Task> getTask(int id){return taskDAO.getTasksById(id);}

    public LiveData<List<Task>> getAllTasksById(int id){return taskDAO.getAllTasksById(id);}

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    //Deprecated way of executing multi-thread operations
    /*private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;

        private InsertUserAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.insert(users[0]);
            return null;
        }
    }
    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;

        private UpdateUserAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.update(users[0]);
            return null;
        }
    }
    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;

        private DeleteUserAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.delete(users[0]);
            return null;
        }
    }
    private static class DeleteAllUsersUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;

        private DeleteAllUsersUserAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.deleteAllUsers();
            return null;
        }
    }*/



}
