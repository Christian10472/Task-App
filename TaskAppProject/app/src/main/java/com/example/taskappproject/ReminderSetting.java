package com.example.taskappproject;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReminderSetting extends AppCompatActivity implements View.OnClickListener {

    private int notificationId = 1;
    EditText date_time;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set onClick Listener
        findViewById(R.id.reminder_button).setOnClickListener(this);
        findViewById(R.id.cancel_button).setOnClickListener(this);
        findViewById(R.id.repeat_button).setOnClickListener(this);
        date_time.setOnClickListener(v -> showDateTimeDialog(date_time));

        date_time.setInputType(InputType.TYPE_NULL);

    }

    private void showDateTimeDialog(EditText date_time) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        date_time.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
            }

            @Override
            public void onClick(View view) {

                EditText editText = findViewById(R.id.editText);
                @SuppressLint("WrongViewCast") TimePicker timePicker = findViewById(R.id.timePicker);

                // Intent
                Intent intent = new Intent(ReminderSetting.this, AlarmReceiver.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("message", editText.getText().toString());

                // PendingIntent
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        ReminderSetting.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                );

                // AlarmManager
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                switch (view.getId()) {
                    case R.id.reminder_button:
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();

                        // Create time.
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, hour);
                        startTime.set(Calendar.MINUTE, minute);
                        startTime.set(Calendar.SECOND, 0);
                        long alarmStartTime = startTime.getTimeInMillis();

                        // Set Alarm
                        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.cancel_button:
                        alarmManager.cancel(pendingIntent);
                        Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

    }

    @Override
    public void onClick(View view) {

    }
}