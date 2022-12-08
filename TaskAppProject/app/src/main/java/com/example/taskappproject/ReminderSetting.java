package com.example.taskappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReminderSetting extends AppCompatActivity {



        EditText datePicker;
        EditText timePicker;
        EditText date_time;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            datePicker=findViewById(R.id.datePicker);
            timePicker=findViewById(R.id.timePicker);
            date_time=findViewById(R.id.date_time);

            date_time.setInputType(InputType.TYPE_NULL);
            datePicker.setInputType(InputType.TYPE_NULL);
            timePicker.setInputType(InputType.TYPE_NULL);

            datePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDateDialog(datePicker);
                }
            });

            timePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimeDialog(timePicker);
                }
            });

            date_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDateTimeDialog(date_time);
                }
            });
        }

        private void showDateTimeDialog(final EditText date_time_in) {
            final Calendar calendar=Calendar.getInstance();
            DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR,year);
                    calendar.set(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                    TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            calendar.set(Calendar.MINUTE,minute);

                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd-y HH:mm");

                            date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                        }
                    };

                    new TimePickerDialog(MainActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                }
            };

            new DatePickerDialog(MainActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

        }

        private void showTimeDialog(final EditText time_in) {
            final Calendar calendar=Calendar.getInstance();

            TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    calendar.set(Calendar.MINUTE,minute);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                    time_in.setText(simpleDateFormat.format(calendar.getTime()));
                }
            };

            new TimePickerDialog(MainActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
        }

        private void showDateDialog(final EditText date_in) {
            final Calendar calendar=Calendar.getInstance();
            DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR,year);
                    calendar.set(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd-y");
                    date_in.setText(simpleDateFormat.format(calendar.getTime()));

                }
            };

            new DatePickerDialog(MainActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
        }

    }




}


}
