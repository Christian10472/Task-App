package com.example.taskappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);

        //References
        et_name = findViewById(R.id.noteName);
        et_body = findViewById(R.id.noteBody);
        doneButton = findViewById(R.id.noteDoneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                body = et_body.getText().toString();
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    String[] permissions  = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //show popup for runtime permission

                }
            }
        });
    }
}