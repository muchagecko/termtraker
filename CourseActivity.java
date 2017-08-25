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

public class CourseActivity extends AppCompatActivity {
    private DataManager dataManager;
    private android.widget.SimpleCursorAdapter adapter;
    private long termId;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.courses));
        ListView listView = (ListView) findViewById(R.id.courseLv);
        dataManager = new DataManager(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            termId = extras.getLong("id");
            Term term = dataManager.getTerm(termId);

            if (term != null) {
                cursor = dataManager.getCoursesForTermsCursor(term);
                startManagingCursor(cursor);
                termId = term.getId();
            }
        } else {
            cursor = dataManager.getCoursesCursor();
            startManagingCursor(cursor);
        }

        adapter = new android.widget.SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{DataManager.COLUMN_COURSE_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(
                                getApplicationContext(), CourseDetailActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("termId", termId);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addCourse:
                Intent intent = new Intent(this, CourseEditActivity.class);
                intent.putExtra("termId", termId);
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backToTerms(View v) {
        Toast.makeText(CourseActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}
