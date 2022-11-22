package com.example.taskappproject.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.taskappproject.R;
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
        CalendarView calendarview = view.findViewById(R.id.calendarView);
        calendarview.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view){
               ArrayList<TaskInformationModel> tasksDueToday = DataBaseHelper.instance.getTasksDueToday();
               ListView todaysList = view.findViewById(R.id.TaskView);
               ArrayList<String> taskNames = new ArrayList<String>();
               for (int i = 0; i < tasksDueToday.size(); i ++){
                   taskNames.add(tasksDueToday.get(i).getTaskName());
               }
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, taskNames);
               todaysList.setAdapter(adapter);
               adapter.notifyDataSetChanged();
           }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}