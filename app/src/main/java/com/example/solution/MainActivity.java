package com.example.solution;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.solution.adapters.NotesAdapter;
import com.example.solution.database.DatabaseHelper;
import com.example.solution.database.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.example.solution.asynctask.GetJsonFromUrlTask;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    List<Note> noteList;

    private String GET_NOTES_API_URL="https://akirachixnotesapi.herokuapp.com/api/v1/notes";
    private String TAG="NOTES_API_RESPONSE";

    String url = "https://akirachixnotesapi.herokuapp.com/api/v1/notes/";

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

    private List<Note> getNotes () {
        final List<Note> noteList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_NOTES_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("tag",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String noteText = jsonObject.getString("noteText");
                        Note note = new Note(id, title, noteText);
                        noteList.add(note);
                    }
                }
                catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error", volleyError.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(stringRequest);
        return noteList;
    }
}
