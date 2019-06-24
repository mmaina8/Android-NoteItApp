package com.example.solution;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.solution.database.DatabaseHelper;
import com.example.solution.database.Note;

public class ViewNote extends AppCompatActivity {

    TextView tvTitle;
    TextView tvNoteText;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getNoteId();

        tvTitle = findViewById(R.id.tvTitle);
        tvNoteText = findViewById(R.id.tvNoteText);
        displayNote();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getNoteId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            noteId = bundle.getInt("NOTE_ID", 0);
        }
    }

    public void displayNote() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(),"notes", null, 1);
        Note note = databaseHelper.getNoteById(noteId);
        tvTitle.setText(note.getTitle());
        tvNoteText.setText(note.getNoteText());
    }

}
