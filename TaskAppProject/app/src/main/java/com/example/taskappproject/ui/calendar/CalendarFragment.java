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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.taskappproject.DataBaseHelper;
import com.example.taskappproject.MainActivity;
import com.example.taskappproject.R;
import com.example.taskappproject.SwipeDetector;
import com.example.taskappproject.TaskCreationActivity;
import com.example.taskappproject.TaskInformationModel;
import com.example.taskappproject.databinding.FragmentCalendarBinding;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    DataBaseHelper db;

    private ArrayList<String> taskNames;
    private ArrayList<TaskInformationModel> tasksDueToday;
    private ArrayList<TaskInformationModel> incompleteTasks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DataBaseHelper(container.getContext());

        final TextView textView = binding.textCalendar;
        calendarViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView todaysList = view.findViewById(R.id.TaskView);
        CalendarView calenderView = view.findViewById(R.id.calendarView);

        // Displays tasks due on current day when opening calendar tab
        tasksDueToday = DataBaseHelper.instance.getTasksDueToday();
        incompleteTasks = new ArrayList<>();
        taskNames = new ArrayList<String>();
        for (int i = 0; i < tasksDueToday.size(); i ++){
            if (!tasksDueToday.get(i).getComplete()){
                incompleteTasks.add(tasksDueToday.get(i));
                taskNames.add(tasksDueToday.get(i).getTaskName());
            }
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, taskNames);
        todaysList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Displays tasks due on selected date on calendar
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                tasksDueToday = DataBaseHelper.instance.getTasksDueToday();
                incompleteTasks = new ArrayList<>();
                taskNames = new ArrayList<String>();
                for (int i = 0; i < tasksDueToday.size(); i ++){
                    if (!tasksDueToday.get(i).getComplete()){
                        incompleteTasks.add(tasksDueToday.get(i));
                        taskNames.add(tasksDueToday.get(i).getTaskName());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        // Open edit page + Swipe gestures
        SwipeDetector swipeDetector = new SwipeDetector();
        todaysList.setOnTouchListener(swipeDetector);
        todaysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskInformationModel taskInformationModel = incompleteTasks.get(position);
                if (swipeDetector.swipeDetected()) {
                    // Swipe right to left (Delete)
                    if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        // ADD DELETE CODE/METHOD HERE
                        int i = taskInformationModel.getId();
                        taskInformationModel = db.getTask(i);
                        db.deleteTask(taskInformationModel);
                        Toast.makeText(getContext(), "Right to Left", Toast.LENGTH_SHORT).show();
                    }
                    // Swipe left to right (Mark as complete)
                    if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        // ADD TASK COMPLETE CODE/METHOD HERE
                        int i = taskInformationModel.getId();
                        taskInformationModel = db.getTask(i);
                        db.updateComplete(taskInformationModel);
                        Toast.makeText(getContext(), "Left to Right", Toast.LENGTH_SHORT).show();
                    }
                }

                // Click (Edit task)
                else {
                    int i = taskInformationModel.getId();
                    Intent intent = new Intent(getActivity(), TaskCreationActivity.class);
                    intent.putExtra("Mode", "Edit");
                    intent.putExtra("Id", i);
                    startActivity(intent);
                }

            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}