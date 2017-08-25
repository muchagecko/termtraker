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

public class AssessmentActivity extends AppCompatActivity {
    private DataManager dataManager;
    private android.widget.SimpleCursorAdapter adapter;
    private long courseId;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ListView listView = (ListView) findViewById(R.id.assessmentLv);
        dataManager = new DataManager(this);
        setTitle(getString(R.string.assess));
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            courseId = extras.getLong("id");
            Course course = dataManager.getCourse(courseId);

            if (course != null) {
                cursor = dataManager.getAssessmentsForCoursesCursor(course);
                startManagingCursor(cursor);
                courseId = course.getCourseId();
            }
        } else {
            cursor = dataManager.getAssessmentCursor();
            startManagingCursor(cursor);
        }

        adapter = new android.widget.SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{DataManager.COLUMN_ASSESSMENT_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(
                                getApplicationContext(), AssessmentDetailActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addAssessment:
                Intent intent = new Intent(this, AssessmentEditActivity.class);
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
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    public void backToTerms(View v) {
        Toast.makeText(AssessmentActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}