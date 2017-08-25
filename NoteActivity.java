package com.example.termtraker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    private DataManager dataManager;
    private android.widget.SimpleCursorAdapter adapter;
    private long courseId;
    private long assessmentId;
    private String activityFrom;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ListView listView = (ListView) findViewById(R.id.noteLv);
        dataManager = new DataManager(this);
        setTitle(getString(R.string.notes));

        Bundle extras = getIntent().getExtras();
        assessmentId = extras.getLong("assessmentId");
        courseId = extras.getLong("id");
        activityFrom = extras.getString("activityId");

        if (activityFrom.equals("course")) {
            Course course = dataManager.getCourse(courseId);
            cursor = dataManager.getNotesForCoursesCursor(course);
            startManagingCursor(cursor);
            courseId = course.getCourseId();
        }
        if (activityFrom.equals("assessment")) {
            Assessment assessment = dataManager.getAssessment(assessmentId);
            cursor = dataManager.getNotesForAssessmentsCursor(assessment);
            startManagingCursor(cursor);
            assessmentId = assessment.getAssessmentId();
        }

        adapter = new android.widget.SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{DataManager.COLUMN_NOTE_TITLE},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(
                                getApplicationContext(), NoteDetailActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("courseId", courseId);
                        intent.putExtra("assessmentId", assessmentId);
                        intent.putExtra("activityId", activityFrom);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNote:
                Intent intent = new Intent(this, NoteEditActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("assessmentId", assessmentId);
                intent.putExtra("activityId", activityFrom);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onUp();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        onUp();
    }

    public void onUp() {
        if (activityFrom.equals("course")) {
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        }
        if (activityFrom.equals("assessment")) {
            Intent intent = new Intent(this, AssessmentDetailActivity.class);
            intent.putExtra("id", assessmentId);
            startActivity(intent);
        }
    }

    public void backToTerms(View v) {
        Toast.makeText(NoteActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}