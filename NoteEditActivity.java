package com.example.termtraker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEditActivity extends AppCompatActivity {
    private long courseId;
    private long assessmentId;
    private long noteId;
    private String activityFrom;
    private Note oldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.edit_note));
        Bundle extras = getIntent().getExtras();
        assessmentId = extras.getLong("assessmentId");
        courseId = extras.getLong("courseId");
        noteId = extras.getLong("id");
        activityFrom = extras.getString("activityId");

        if (noteId > 0) {
            DataManager dataManager = new DataManager(this);
            Note note = dataManager.getNote(noteId);

            if (note != null) {
                oldNote = dataManager.getNote(noteId);
                ((EditText) findViewById(R.id.noteTitleEt)).setText(note.getNoteTitle());
                ((EditText) findViewById(R.id.noteDescriptionEt)).setText(note.getNoteDescription());
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNote:
                addNote();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addNote() {

        if (oldNote != null) {
            DataManager dataManager = new DataManager(this);
            String noteTitle = ((EditText) findViewById(R.id.noteTitleEt)).getText().toString();
            String noteDescription = ((EditText) findViewById(R.id.noteDescriptionEt)).getText().toString();

            oldNote.setNoteTitle(noteTitle);
            oldNote.setNoteDescription(noteDescription);
            oldNote.setCourseId(courseId);
            oldNote.setAssessmentId(assessmentId);
            dataManager.updateNote(oldNote);
            finish();

        } else {
            DataManager dataManager = new DataManager(this);
            String noteTitle = ((EditText) findViewById(R.id.noteTitleEt)).getText().toString();
            String noteDescription = ((EditText) findViewById(R.id.noteDescriptionEt)).getText().toString();

            Note note = new Note(noteTitle, noteDescription);
            note.setCourseId(courseId);
            note.setAssessmentId(assessmentId);
            dataManager.addNote(note);
            finish();
        }
    }

    @Override
    public void finish() {
        if (noteId == 0) {
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("activityId", activityFrom);
            intent.putExtra("assessmentId", assessmentId);
            intent.putExtra("id", courseId);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra("activityId", activityFrom);
            intent.putExtra("id", noteId);
            intent.putExtra("courseId", courseId);
            intent.putExtra("assessmentId", assessmentId);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void backToTerms(View v) {
        Toast.makeText(NoteEditActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}