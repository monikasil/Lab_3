package com.example.third_assignment_template;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteNoteActivity extends AppCompatActivity {

    Spinner deleteSelected;
    private ArrayAdapter listAdapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        deleteSelected = findViewById(R.id.spSelectionForDelete);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        notesList = new ArrayList<String>(sp.getStringSet("notes", new HashSet<String>()));
        listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, notesList);
        deleteSelected.setAdapter(listAdapter);
    }



    public void onDeleteNoteClick(View view) {

        String selectedNote = deleteSelected.getSelectedItem().toString();

        //https://stackoverflow.com/questions/14034803/misbehavior-when-trying-to-store-a-string-set-using-sharedpreferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spEd = sp.edit();
        Set<String> currentNotes = sp.getStringSet("notes", new HashSet<String>());

        //remove selected
        currentNotes.remove(selectedNote);

        //clear SharedPreference
        spEd.clear();
        spEd.commit();

        //add current list to SharedPreference
        spEd.putStringSet("notes", currentNotes);
        spEd.apply();

        //show after deletion
        notesList.clear();
        notesList.addAll(currentNotes);
        listAdapter.notifyDataSetChanged();
    }
}

