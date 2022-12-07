package com.example.taskappproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappproject.ui.notifications.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private ArrayList<NewTask> taskArrayList;
    private String[] taskHeading;
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

        dataInitialize();

        recyclerview = view.findViewById((R.id.tasksRecyclerView));
        recyclerview.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerview.setHasFixedSize(true);
        TaskAdapter taskAdapter = new TaskAdapter(getContext(),taskArrayList);
        recyclerview.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        taskArrayList = new ArrayList<>();
        taskHeading = new String[]{
                getString(R.string.title_notifications )
        };

        for(int i = 0; i< taskHeading.length;i++ )
        {
            NewTask task = new NewTask(taskHeading[i]);
            taskArrayList.add(task);
            taskArrayList.add(task);
            taskArrayList.add(task);
            taskArrayList.add(task);
            taskArrayList.add(task);
            taskArrayList.add(task);
            taskArrayList.add(task);
            taskArrayList.add(task);
            taskArrayList.add(task);

        }
    }
}

