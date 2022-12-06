package com.example.taskappproject.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.taskappproject.DataBaseHelper;
import com.example.taskappproject.MainActivity;
import com.example.taskappproject.R;
import com.example.taskappproject.TaskCreationActivity;
import com.example.taskappproject.TaskInformationModel;
import com.example.taskappproject.databinding.FragmentCalendarBinding;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCalendar;
        calendarViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView todaysList = view.findViewById(R.id.TaskView);
        CalendarView calenderView = view.findViewById(R.id.calendarView);

        // Displays tasks due on current day when opening calendar tab
        ArrayList<TaskInformationModel> tasksDueToday = DataBaseHelper.instance.getTasksDueToday();
        ArrayList<String> taskNames = new ArrayList<String>();
        for (int i = 0; i < tasksDueToday.size(); i ++){
            taskNames.add(tasksDueToday.get(i).getTaskName());
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, taskNames);
        todaysList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Displays tasks due on selected date on calendar
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                ArrayList<TaskInformationModel> tasksDueToday = DataBaseHelper.instance.getTasksDueOn(dayOfMonth, month + 1, year);
                ArrayList<String> taskNames = new ArrayList<String>();
                for (int i = 0; i < tasksDueToday.size(); i ++){
                    taskNames.add(tasksDueToday.get(i).getTaskName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, taskNames);
                todaysList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        todaysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TaskCreationActivity.class);
                intent.putExtra("isEditMode", true);
                intent.putExtra("Id", todaysList.getId());
                startActivity(intent);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}