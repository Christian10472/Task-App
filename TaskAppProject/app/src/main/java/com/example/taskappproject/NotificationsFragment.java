package com.example.taskappproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappproject.databinding.FragmentCalendarBinding;
import com.example.taskappproject.ui.notifications.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NotificationsFragment extends Fragment {


    private ArrayList<NewTask> taskArrayList;
    private FragmentCalendarBinding binding;
    private String[] taskHeading;
    ListView lt;
    private RecyclerView recyclerview;
    DataBaseHelper db;

    public NotificationsFragment(){

    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_notifications,container,false);
    }
    /*public class NotificationFragments extends AppCompatActivity {
        private RecyclerView tasksRecyclerView;
        private List<ToDoModel> taskList;
        private FloatingActionButton fab;
        private ToDoAdapter taskAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_notifications);
            getSupportActionBar().hide();

            taskList = new ArrayList<>();

            tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            taskAdapter = new ToDoAdapter(this);
            tasksRecyclerView.setAdapter(taskAdapter);
            fab = findViewById(R.id.fab);

            ToDoModel task = new ToDoModel();
            task.setTask("This is a test");
            task.setStatus(0);
            task.setId(1);

            taskList.add(task);
        }
    }

     */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //dataInitialize();

        lt = view.findViewById((R.id.tasksRecyclerView));
       // recyclerview.setLayoutManager(new LinearLayoutManager((getContext())));
        //recyclerview.setHasFixedSize(true);
        TaskAdapter taskAdapter = new TaskAdapter(getContext(),taskArrayList);
        //recyclerview.setAdapter(taskAdapter);
        //taskAdapter.notifyDataSetChanged();
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        String str = DayOfWeek.of(dayOfWeek).name() + ",\n" + Month.of(month + 1).name() + " " + dayOfMonth;
        TextView todaysDate = view.findViewById(R.id.todayText);
        todaysDate.setText("Due Today: ");
        TextView tommorrowsDate = view.findViewById(R.id.tomorrowText);
        tommorrowsDate.setText("Due This Week: ");
        ListView tomorrowsList = view.findViewById(R.id.tomorrowRecycleView);
        ListView todaysList = view.findViewById(R.id.tasksRecyclerView);
        //CalendarView calenderView = view.findViewById(R.id.calendarView);

        // Displays tasks due on current day when opening calendar tab
        ArrayList<TaskInformationModel> tasksDueToday = DataBaseHelper.instance.getTasksDueToday();
        ArrayList<String> taskNames = new ArrayList<String>();
        if(tasksDueToday.size() != 0) {
            for (int i = 0; i < tasksDueToday.size(); i++) {
                taskNames.add(tasksDueToday.get(i).getTaskName());
            }
            todaysDate.setText("Due Today: ");
            todaysDate.setTextSize(16F);
            todaysDate.setTextColor(Color.BLACK);
        }
        else
        {
            todaysDate.setText("No Tasks Due Today");
            todaysDate.setTextSize(32F);
            todaysDate.setTextColor(Color.GREEN);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, taskNames);
        todaysList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ArrayList<TaskInformationModel> tasksduesoon = DataBaseHelper.instance.getTasksDueSoon();
        ArrayList<String> taskNames2 = new ArrayList<String>();
        if(tasksduesoon.size() != 0) {
            for (int i = 0; i < tasksduesoon.size(); i++) {
                taskNames2.add(tasksduesoon.get(i).getTaskName());
            }
        }
        else
        {
            tommorrowsDate.setText("");
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, taskNames2);
        tomorrowsList.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        SwipeDetector swipeDetector = new SwipeDetector();
        todaysList.setOnTouchListener(swipeDetector);
        todaysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskInformationModel taskInformationModel= tasksduesoon.get(position);
                TaskInformationModel taskInformationModel2= tasksduesoon.get(position);
                if (swipeDetector.swipeDetected()) {
                    // Swipe right to left (Delete)
                    if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        // ADD DELETE CODE/METHOD HERE
                        int i = taskInformationModel.getId();
                        int z = taskInformationModel2.getId();
                        taskInformationModel = db.getTask(i);
                        taskInformationModel = db.getTask(z);
                        db.deleteTask(taskInformationModel);
                        db.deleteTask(taskInformationModel2);
                        Toast.makeText(getContext(), "Right to Left", Toast.LENGTH_SHORT).show();
                    }
                    // Swipe left to right (Mark as complete)
                    if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        // ADD TASK COMPLETE CODE/METHOD HERE
                        int i = taskInformationModel.getId();
                        int z = taskInformationModel2.getId();
                        taskInformationModel = db.getTask(i);
                        taskInformationModel = db.getTask(z);
                        db.updateComplete(taskInformationModel);
                        db.updateComplete(taskInformationModel2);
                        Toast.makeText(getContext(), "Left to Right", Toast.LENGTH_SHORT).show();
                    }
                }

                // Click (Edit task)
                else {
                    int i = taskInformationModel.getId();
                    int z = taskInformationModel.getId();
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

