package com.example.tasklite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasklite.DB.AppDataBase;
import com.example.tasklite.DB.TaskViewModel;
import com.example.tasklite.DB.TaskDAO;
import com.example.tasklite.Entities.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    public static final int ADD_TASK_REQUEST = 1;
    private Task mTask;
    private static final String USER_ID_KEY = "USER_ID_KEY";
    private SharedPreferences mPreferences = null;
    private int mUserId;

    boolean admin;

    private TaskViewModel taskViewModel;

    TextView username;
    TextView admin_text;

    TaskDAO mtaskDAO;
    private static final String PREFERENCES_KEY = "PREFERENCES_KEY";


    List<Task> mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        admin = intent.getExtras().getBoolean("isAdmin");
        mUserId = intent.getExtras().getInt("user_id");

        FloatingActionButton addTask = findViewById(R.id.newTask);
        addTask.setOnClickListener(v -> {
            Intent thisIntent = new Intent(UserActivity.this, AddTaskActivity.class);
            startActivityForResult(thisIntent, ADD_TASK_REQUEST);
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getDatabase();
        getViewModel();

        final TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
        taskViewModel.getAllTasksById(mUserId).observe(this, adapter::setTasks);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TaskViewModel.delete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(UserActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        username = findViewById(R.id.wName);
        admin_text = findViewById(R.id.adminText);

        checkForUser();
        addUserToPreference(mUserId);
        setWelcome();
        setAdminText(admin);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem adminTools = menu.findItem(R.id.admin);
        if (taskViewModel.getUserById(mUserId).isAdmin()) {
            adminTools.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Options", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.admin:
                taskViewModel.deleteAllUsers();
                Toast.makeText(this, "All Users Deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_all_notes:
                taskViewModel.deleteAllTasks();
                Toast.makeText(this, "Deleted all tasks", Toast.LENGTH_SHORT).show();
            case R.id.logout:
                logoutUser();
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddTaskActivity.Extra_Title);
            String description = data.getStringExtra(AddTaskActivity.Extra_Description);
            int priority = data.getIntExtra(AddTaskActivity.Extra_PRIORITY, 1);


            Task task = new Task(title, description, priority, mUserId, false);
            TaskViewModel.insert(task);

            Toast.makeText(this, "Task Created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task creation failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void getViewModel() {
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    }

    private void setWelcome() {
        username.setText(mtaskDAO.getUserByUserId(mUserId).getUsername());
    }

    private void setAdminText(boolean bool) {
        if (bool) {
            admin_text.setText("You are an Admin!");
        }
    }


    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }

    private void getDatabase() {
        mtaskDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getTaskDAO();

    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
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

    public void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if (mUserId != -1) {
            return;
        }

        if (mPreferences == null) {
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        if (mUserId != -1) {
            return;
        }

        Intent intent = MainActivity.intentFactory(this);
        startActivity(intent);
    }

    public void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);


        clearUserFromIntent();
        clearUserFromPref();
        mUserId = -1;
        checkForUser();
        mainActivity();


//    private void refreshDisplay(){
//        mTasks = mtaskDAO.getAllTasks();
//
//        StringBuilder sb = new StringBuilder();
//        for(Task task : mTasks){
//            sb.append(task);
//            sb.append("\n");
//        }
//    }

    }
}
