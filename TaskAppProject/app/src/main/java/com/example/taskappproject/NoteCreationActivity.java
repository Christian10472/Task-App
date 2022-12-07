package com.example.taskappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteCreationActivity extends AppCompatActivity {

    //variables
    EditText et_name,et_body;
    Button doneButton;
    String name, body;
    boolean isEditMode = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);

        //References
        et_name = findViewById(R.id.noteName);
        et_body = findViewById(R.id.noteBody);
        doneButton = findViewById(R.id.noteDoneButton);

        doneButton.setOnClickListener(view -> {
            NoteHelper helper = new NoteHelper(NoteCreationActivity.this);
            boolean success = false;
            Note note;
            name = et_name.getText().toString();
            if (name.matches("")) {
                name = "New Note";
            }
            body = et_body.getText().toString();
            if (isEditMode){
                try {
                    note = new Note(intent.getIntExtra("id", 0), name,body);
                    helper.updateNote(note);
                }catch (Exception e) {
                    Toast.makeText(NoteCreationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }else {

                note = new Note(-1, name, body);
                Toast.makeText(NoteCreationActivity.this, "New Note '" + name + "' Created", Toast.LENGTH_SHORT).show();

                success = helper.addOne(note);
                Toast.makeText(NoteCreationActivity.this, "Success: " + success, Toast.LENGTH_SHORT).show();
            }
            openMainActivity();
        });

    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}