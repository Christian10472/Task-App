package com.example.taskappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Calendar;

public class ReminderSetting extends AppCompatActivity {

 int remindYear, remindDay, remindMonth, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);

        private String getDate() {
            Calendar cal = Calendar.getInstance();
            remindYear = cal.get(Calendar.YEAR);
            remindMonth = cal.get(Calendar.MONTH) + 1;
            remindDay = cal.get(Calendar.DAY_OF_MONTH);
            String s = remindMonth + "/" + remindDay + "/" + remindYear;
            return s;
        }
        private int getTime(){

        }

    }
}
