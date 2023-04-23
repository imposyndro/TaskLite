package com.example.tasklite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {
    public static final String Extra_Title = "com.example.tasklite.EXTRA_TITLE";
    public static final String Extra_Description = "com.example.tasklite.EXTRA_Description";
    public static final String Extra_PRIORITY = "com.example.tasklite.EXTRA_PRIORITY";
    public static final String Extra_ID = "com.example.tasklite.EXTRA_ID";
    private EditText editTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.priority_picker);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(3);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Task");
    }

    private void saveTask(){
        String title = editTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please add a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Extra_Title, title);
        data.putExtra(Extra_Description, description);
        data.putExtra(Extra_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_task:
                saveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AddTaskActivity.class);
        return intent;
    }
}