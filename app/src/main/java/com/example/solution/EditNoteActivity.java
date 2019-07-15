package com.example.solution;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.solution.database.DatabaseHelper;
import com.example.solution.database.Note;

public class EditNoteActivity extends AppCompatActivity {

    Button btnEdit;
    int noteId;
    EditText etTitle;
    EditText etNoteText;
    String title;
    String noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getNoteId();
        etTitle = findViewById(R.id.etTitle);
        etNoteText = findViewById(R.id.etNote);
        btnEdit = findViewById(R.id.btnEdit);
        displayNote();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = etTitle.getText().toString();
                noteText = etNoteText.getText().toString();

                Note note = new Note(noteId, title, noteText);
                DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(), "notes", null, 1);
                long rows = databaseHelper.updateNote(note);
                finish();
            }
        });

        }

        public void getNoteId (){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            noteId = bundle.getInt("NOTE_ID", 0);
        }
        }

        public void displayNote() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(),"notes", null, 1);
        Note note = databaseHelper.getNoteById(noteId);
        etTitle.setText(note.getTitle());
        etNoteText.setText(note.getNoteText());
    }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }


}
