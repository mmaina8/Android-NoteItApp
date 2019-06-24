package com.example.solution;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.solution.adapters.NotesAdapter;
import com.example.solution.database.DatabaseHelper;
import com.example.solution.database.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),AddNoteActivity.class));
            }
        });

        listview = findViewById(R.id.lvListView);
    }

    private void displayNotes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(),"notes",null,1);
        noteList = new ArrayList<Note>();
        noteList = databaseHelper.getNotes();
        Log.d("mynotes","My database has " + noteList.size() + " notes");
        NotesAdapter notesAdapter = new NotesAdapter(getBaseContext(),0,noteList);
        listview.setAdapter(notesAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note clickedNote = noteList.get(position);
                Intent intent = new Intent(getBaseContext(), ViewNote.class);
                intent.putExtra("NOTE_ID", clickedNote.getId());
                startActivity(intent);
            }
        });
    }

    private void displayNames() {
        List<String>namesList = new ArrayList<String>();
        namesList.add("Cynthia Anyango");
        namesList.add("Grace Nyokabi");
        namesList.add("Zakia Mustafa");
        namesList.add("Tiffany Jeruto");
        namesList.add("Carolyne Mwende");
        namesList.add("Consolata Akoth");
        namesList.add("Natasha Nasambu");
        namesList.add("Joan Aluka");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,namesList);
        listview.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayNotes();
//        displayNames();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
