package com.example.taskappproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskappproject.DataBaseHelper;
import com.example.taskappproject.R;
import com.example.taskappproject.TaskCreationActivity;
import com.example.taskappproject.TaskInformationModel;
import com.example.taskappproject.databinding.FragmentHomeBinding;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        // Set heading to display today's date
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        String str = DayOfWeek.of(dayOfWeek).name() + ",\n" + Month.of(month + 1).name() + " " + dayOfMonth;
        TextView todaysDate = view.findViewById(R.id.todaysDateText);
        todaysDate.setText(str);

        // Populate list of items due today
        ArrayList<TaskInformationModel> tasksDueToday = DataBaseHelper.instance.getTasksDueToday();
        ArrayList<String> tasksDueTodayNames = new ArrayList<String>();
        ArrayList<String> tasksDueSoonNames = new ArrayList<String>();
        ListView todaysList = view.findViewById(R.id.todayItemsList);
        for (int i = 0; i < tasksDueToday.size(); i ++){
            if (tasksDueToday.get(i).getComplete()) continue;
            tasksDueTodayNames.add(tasksDueToday.get(i).getTaskName());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tasksDueTodayNames);
        todaysList.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        // Populate list of items due soon
        ArrayList<TaskInformationModel> tasksDueSoon = DataBaseHelper.instance.getTasksDueSoon();
        ListView soonList = view.findViewById(R.id.upcomingItemsList);
        for (int i = 0; i < tasksDueSoon.size(); i ++){
            if (tasksDueSoon.get(i).getComplete()) continue;
            tasksDueSoonNames.add(tasksDueSoon.get(i).getTaskName());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tasksDueSoonNames);
        soonList.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}