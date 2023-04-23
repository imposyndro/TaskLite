package com.example.tasklite.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tasklite.Entities.Task;
import com.example.tasklite.Entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Task.class}, version = 10)
@TypeConverters({DateConverter.class})
public abstract class AppDataBase extends RoomDatabase {

    public static final String DB_NAME = "TASKLITE_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String TASK_TABLE = "TASK_TABLE";

    public static final String REMINDER_TABLE = "TASK_TABLE";

    public abstract TaskDAO getTaskDAO();

    private static volatile AppDataBase instance;
    public static final int THREADS = 4;
    public static final ExecutorService databaseExecutor
            = Executors.newFixedThreadPool(THREADS);

    public static synchronized AppDataBase taskInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDataBase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallBack)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }



    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseExecutor.execute(() ->{
                int id = -1;
                TaskDAO taskDAO = instance.getTaskDAO();
                taskDAO.deleteAllUsers();
                taskDAO.deleteAllTasks();
                User testUser = new User("testuser1", "testuser1", false);
            });
        }

    };


}

