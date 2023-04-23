package com.example.tasklite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tasklite.DB.AppDataBase;
import com.example.tasklite.DB.TaskViewModel;
import com.example.tasklite.Entities.User;
import com.example.tasklite.DB.TaskDAO;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String PREFERENCES_KEY = "PREFERENCE_KEY";

    TextView mMainDisplay;
    Button login;
    Button create;
    private int mUserId = -1;
    private TaskDAO taskDAO;

    private TaskViewModel taskViewModel;
    private SharedPreferences mPreferences = null;

    private User mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainDisplay = findViewById(R.id.header);
        login = findViewById(R.id.login);
        create = findViewById(R.id.createAccount);

        getDatabase();
        getViewModel();
        hideTitle();
        checkForUser();
//        User testuser1 = new User("testuser1", "testuser1", false);
       User admin2 = new User("admin2", "admin2", true);
//        taskViewModel.insert(testuser1);
       taskViewModel.insert(admin2);









        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccount();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAccount();
            }
        });


    }
    public void openAccount(){
        Intent intent = new Intent(this, AccountCreationActivity.class);
        startActivity(intent);
    }
    public void loginAccount(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void hideTitle() {
        getSupportActionBar().hide();
    }

    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if(mUserId != -1){
            return;
        }

        if(mPreferences == null){
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        if(mUserId != -1){
            return;
        }


    }
    private void getDatabase(){
        taskDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getTaskDAO();
    }

    private void getViewModel(){
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TaskViewModel.class);
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}