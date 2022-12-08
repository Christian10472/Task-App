package com.example.taskappproject.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappproject.MainActivity;
import com.example.taskappproject.Note;
import com.example.taskappproject.NoteCreationActivity;
import com.example.taskappproject.NoteHelper;
import com.example.taskappproject.R;
import com.example.taskappproject.TaskCreationActivity;
import com.example.taskappproject.databinding.FragmentNotesBinding;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private FragmentNotesBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotesViewModel notesViewModel =
                new ViewModelProvider(this).get(NotesViewModel.class);

        binding = FragmentNotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        ListView noteList = view.findViewById(R.id.lv_noteList);
        ArrayList<Note> notes = NoteHelper.instance.getAll();
        ArrayAdapter<Note> noteAA = new ArrayAdapter<Note>(getContext(), android.R.layout.simple_list_item_1, notes);
        noteList.setAdapter(noteAA);
        noteAA.notifyDataSetChanged();

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Note note = notes.get(i);
                Intent intent = new Intent(getActivity(), NoteCreationActivity.class);
                intent.putExtra("Mode", "Edit");
                intent.putExtra("Id", note.getId());
                startActivity(intent);

                ArrayAdapter<Note> noteAA2 = new ArrayAdapter<Note>(getContext(), android.R.layout.simple_list_item_1, NoteHelper.instance.getAll());
                noteList.setAdapter(noteAA2);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}