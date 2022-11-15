package com.example.taskappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class TaskCreationActivity extends AppCompatActivity {

    //variables
    EditText et_name;
    DatePickerDialog datePickerDialog;
    Button dateButton, timeButton, createButton, reminderButton;
    int hour, minute, year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);

        //Text reference
        et_name = findViewById(R.id.et_name);

        //Date Button reference
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());

        //Time Button reference
        timeButton = findViewById(R.id.timeButton);

        //Task type spinner Reference
        Spinner spinner = findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.taskType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Task priority spinner Reference
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this, R.array.taskPriority, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        //Create Button reference
        createButton = findViewById(R.id.createButton);

        //Create Task Information
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TaskInformationModel taskInformationModel;

                try {
                    taskInformationModel = new TaskInformationModel(-1, et_name.getText().toString(), "family", "High", month, day, year, hour, minute );
                    Toast.makeText(TaskCreationActivity.this, taskInformationModel.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(TaskCreationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    taskInformationModel = new TaskInformationModel(-1,"error", "error", "error", 0, 0, 0, 0 ,0);

                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(TaskCreationActivity.this);

                boolean success = dataBaseHelper.addOne(taskInformationModel);
                Toast.makeText(TaskCreationActivity.this, "Success" + success, Toast.LENGTH_SHORT).show();

            }
        });


        //Reminder Button
        reminderButton = (Button) findViewById(R.id.reminderButton);
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReminderSetting();
            }
        });

    }

    //Methods for Date Button
    private String getTodayDate(){
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
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
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
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
}