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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasklite.DB.AppDataBase;
import com.example.tasklite.DB.TaskViewModel;
import com.example.tasklite.Entities.User;
import com.example.tasklite.DB.TaskDAO;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView title;
    EditText username;
    EditText password;
    Button submit;

    String mUsername;
    String mPassword;
    private static User mUser = new User("","",false);

    private TaskDAO taskDAO;

    private TaskViewModel taskViewModel;
    private SharedPreferences mPreferences;
    private static final String PREFERENCES_KEY = "PREFERENCES_KEY";
    private static final String USER_ID_KEY = "USER_ID_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        title = findViewById(R.id.loginTitle);
        username = findViewById(R.id.l_name);
        password = findViewById(R.id.l_password);
        submit = findViewById(R.id.l_submit);

        hideTitle();
        getDatabase();
        getViewModel();



        submit.setOnClickListener(view -> {
            getValuesFromDisplay();
            mUser = taskViewModel.getUserByUsername(mUsername);
            if(mUser != null) {
                if (checkForUserInDatabase()) {
                    loginUser(mUser.getUserId());
                    Intent intent = UserActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                    Toast.makeText(LoginActivity.this, mUser.getUsername(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("isAdmin", mUser.isAdmin());
                    intent.putExtra("user_id", mUser.getUserId());
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    public void hideTitle() {
        Objects.requireNonNull(getSupportActionBar()).hide();
    }



    private boolean validatePassword() {
        return mUser.getPassword().equals(mPassword);
    }

    private void getValuesFromDisplay() {
        mUsername = username.getText().toString().trim();
        mPassword = password.getText().toString().trim();
    }

    private void getDatabase(){
        taskDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getTaskDAO();
    }


    private void getViewModel(){
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    }

    private boolean checkForUserInDatabase() {
        return mUser != null && validatePassword();
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void loginUser(int userId) {
        mUser = taskViewModel.getUserById(userId);
        addUserToPreference(userId);
        invalidateOptionsMenu();
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



}