package com.example.taskappproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.taskappproject.ui.notifications.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskCreationActivity extends AppCompatActivity {

    //variables
    DataBaseHelper db;
    TaskInformationModel taskInformationModel;
    EditText et_name;
    DatePickerDialog datePickerDialog;
    Button dateButton, timeButton, createButton, reminderButton, cancelButton;
    Switch reminderSwitch;
    String taskName;
    int hour, minute, year, month, day, taskYear,taskMonth, taskDay;
    Intent intent;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);

        //References
        et_name = findViewById(R.id.et_name);
        reminderButton = findViewById(R.id.reminderButton);
        timeButton = findViewById(R.id.timeButton);
        Spinner spinner = findViewById(R.id.typeSpinner);
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        createButton = findViewById(R.id.createButton);
        reminderSwitch = findViewById(R.id.reminderSwitch);
        cancelButton = findViewById(R.id.cancelButton);
        dateButton = findViewById(R.id.datePickerButton);
        db = new DataBaseHelper(this);

        initDatePicker();

        //Make Type & Priority Spinner work
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.taskType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this, R.array.taskPriority, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        //For Edit when implemented in Home Menu
        intent = getIntent();
        String s = intent.getStringExtra("Mode");
        if (s.equals("Edit")){
            isEditMode = true;
            taskInformationModel = db.getTask(intent.getIntExtra("Id", -1));
            et_name.setText(taskInformationModel.getTaskName());
            day = taskInformationModel.getDay();
            month = taskInformationModel.getMonth();
            year = taskInformationModel.getYear();
            hour = taskInformationModel.getHour();
            minute = taskInformationModel.getMinute();
            taskMonth= month;
            taskDay = day;
            taskYear = year;
            dateButton.setText(makeDateString(day, month, year));
            timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            spinner.setSelection(adapter.getPosition(taskInformationModel.getTaskType()));
            prioritySpinner.setSelection(priorityAdapter.getPosition(taskInformationModel.getTaskPriority()));
        }else{
            isEditMode = false;
            dateButton.setText(getTodayDate());
        }

        //Make Reminder Active
        reminderSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> reminderButton.setEnabled(isChecked));

        reminderButton.setOnClickListener(view -> openReminderSetting());

        cancelButton.setOnClickListener(view -> openMainActivity());

        //Create Task Information
        createButton.setOnClickListener(view -> {
            boolean success = false;
            taskName = et_name.getText().toString();
            if (taskName.matches("")){
                taskName = "New Task";
            }
            if (isEditMode){
                try {
                    taskInformationModel = new TaskInformationModel(intent.getIntExtra("Id", -1), taskName
                            , spinner.getSelectedItem().toString(), prioritySpinner.getSelectedItem().toString()
                            , taskMonth, taskDay, taskYear, hour, minute, false);;
                            db.updateTask(taskInformationModel);
                    openMainActivity();
                }catch (Exception e) {
                    Toast.makeText(TaskCreationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }else{
                try {
                    taskInformationModel = new TaskInformationModel(-1, taskName
                            , spinner.getSelectedItem().toString(), prioritySpinner.getSelectedItem().toString()
                            , taskMonth, taskDay, taskYear, hour, minute, false);
                    Toast.makeText(TaskCreationActivity.this, taskInformationModel.toString(), Toast.LENGTH_LONG).show();
                    success = DataBaseHelper.instance.addOne(taskInformationModel);
                    openMainActivity();
                }catch (Exception e) {
                    Toast.makeText(TaskCreationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(TaskCreationActivity.this, "Success: " + success, Toast.LENGTH_SHORT).show();
            }

        });

    }

    //Methods for Date Buttons
    private String getTodayDate(){
        Calendar cal = Calendar.getInstance();
        taskYear = cal.get(Calendar.YEAR);
        taskMonth = cal.get(Calendar.MONTH) + 1;
        taskDay = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(taskDay, taskMonth, taskYear);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            taskYear = year;
            taskMonth = month;
            taskDay = day;
            dateButton.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return month + "/" + day + "/" + year;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    //Methods for Time Button
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    //Method for Reminder Button
    public void openReminderSetting(){
        Intent intent = new Intent(this, ReminderSetting.class);
        startActivity(intent);
    }

    //Method for Create Button
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}