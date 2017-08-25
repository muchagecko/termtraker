package com.example.termtraker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CourseDetailActivity extends AppCompatActivity {
    private long courseId;
    private long termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.course_detail));
        Bundle extras = getIntent().getExtras();
        courseId = extras.getLong("id");
        termId = extras.getLong("termId");

        if (courseId > 0) {
            DataManager dataManager = new DataManager(this);
            Course course = dataManager.getCourse(courseId);

            if (course != null) {
                termId = course.getCourseTermId();
                ((TextView) findViewById(R.id.courseNameTv)).setText(course.getCourseName());
                ((TextView) findViewById(R.id.courseStartTv)).setText(course.getCourseStart());
                ((TextView) findViewById(R.id.courseEndTv)).setText(course.getCourseEnd());
                ((TextView) findViewById(R.id.courseStatusTv)).setText(course.getCourseStatus());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteCourse:
                deleteCourse();
                return true;
            case R.id.editCourse:
                editCourse();
                return true;
            case android.R.id.home:
                onUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteCourse() {
        DataManager dataManager = new DataManager(getApplicationContext());
        dataManager.deleteCourse(courseId);
        finish();
    }

    public void editCourse() {
        Intent intent = new Intent(
                getApplicationContext(), CourseEditActivity.class);
        intent.putExtra("id", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (termId == 0) {
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CourseActivity.class);
            intent.putExtra("id", termId);
            startActivity(intent);
        }
    }

    public void onUp() {
        if (termId == 0) {
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CourseActivity.class);
            intent.putExtra("id", termId);
            startActivity(intent);
        }
    }

    public void openMentorsToAdd(View view) {
        Intent intent = new Intent(getApplicationContext(), MentorActivity.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    public void openAssessmentsToAdd(View view) {
        Intent intent = new Intent(getApplicationContext(), AssessmentActivity.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    public void openNotesToAdd(View view) {
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        intent.putExtra("id", courseId);
        intent.putExtra("activityId", "course");
        startActivity(intent);
    }

    public void backToTerms(View v) {
        Toast.makeText(CourseDetailActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}