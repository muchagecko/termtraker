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

public class MentorActivity extends AppCompatActivity {
    private DataManager dataManager;
    private android.widget.SimpleCursorAdapter adapter;
    private long courseId;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ListView listView = (ListView) findViewById(R.id.mentorLv);
        dataManager = new DataManager(this);
        setTitle(getString(R.string.mentors));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            courseId = extras.getLong("id");
            Course course = dataManager.getCourse(courseId);

            if (course != null) {
                cursor = dataManager.getMentorsForCoursesCursor(course);
                startManagingCursor(cursor);
                courseId = course.getCourseId();
            }

        } else {
            cursor = dataManager.getMentorCursor();
            startManagingCursor(cursor);
        }

        adapter = new android.widget.SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{DataManager.COLUMN_MENTOR_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(
                                getApplicationContext(), MentorDetailActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mentor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMentor:
                Intent intent = new Intent(this, MentorEditActivity.class);
                intent.putExtra("courseId", courseId);
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
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    public void onUp() {
        if (courseId == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        }
    }

    public void backToTerms(View v) {
        Toast.makeText(MentorActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}