package com.example.taskappproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskappproject.R;
import com.example.taskappproject.databinding.FragmentHomeBinding;

import java.time.DayOfWeek;
import java.time.Month;
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
        String str = DayOfWeek.of(dayOfWeek + 1).name() + ",\n" + Month.of(month).name() + " " + dayOfMonth;
        TextView todaysDate = view.findViewById(R.id.todaysDateText);
        todaysDate.setText(str);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}