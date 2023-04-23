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

public class AccountCreationActivity extends AppCompatActivity {

    private int mUserId;
    private User mUser;
    private TaskDAO taskDAO;
    private SharedPreferences mPreferences = null;
    private TaskViewModel taskViewModel;
    TextView mCreateDisplay;
    EditText mName;
    EditText mPassword;
    Button mRegister;
    String mUsername;
    String mPass_word;

    TaskDAO mtaskDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitle();
        setContentView(R.layout.activity_accountcreation);
        getDatabase();
        getViewModel();
        wireupDisplay();


    }

    private void wireupDisplay() {
        mCreateDisplay = findViewById(R.id.accountCreation);
        mName = findViewById(R.id.c_name);
        mPassword = findViewById(R.id.c_password);
        mRegister = findViewById(R.id.register);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                mUser = new User(mUsername, mPass_word, false);
                if (!checkForUserInDatabase()) {
                    TaskViewModel.insert(mUser);
                    Intent intent = LoginActivity.intentFactory(getApplicationContext());
                    startActivity(intent);

                } else {Toast.makeText(AccountCreationActivity.this, "User already exists...", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getValuesFromDisplay() {
        mUsername = mName.getText().toString().trim();
        mPass_word = mPassword.getText().toString().trim();
    }

    private boolean checkForUserInDatabase() {
        User check = taskViewModel.getUserByUsername(mUsername);
        if (check == null) {
            Toast.makeText(AccountCreationActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            Toast.makeText(this, "Username is already in use.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AccountCreationActivity.class);
            startActivity(intent);
            return true;
        }
    }


    private void getDatabase() {
        mtaskDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getTaskDAO();

    }

    private void getViewModel(){
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    }

    public void hideTitle() {
        getSupportActionBar().hide();
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AccountCreationActivity.class);

        return intent;
    }




}