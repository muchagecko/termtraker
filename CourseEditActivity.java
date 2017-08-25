package com.example.termtraker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseEditActivity extends AppCompatActivity {
    private Long courseId;
    private Long termId;
    private Course oldCourse;
    private Spinner statusDropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        DatePicker.startDatePicker((EditText) findViewById(R.id.courseStartEt));
        DatePicker.startDatePicker((EditText) findViewById(R.id.courseEndEt));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        statusDropDown = (Spinner) findViewById(R.id.courseDropDown);
        List<String> dropDownItems = new ArrayList<String>();
        dropDownItems.add("Completed");
        dropDownItems.add("In-Progress");
        dropDownItems.add("Dropped");
        dropDownItems.add("Plan to take");
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dropDownItems);
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusDropDown.setAdapter(dropDownAdapter);
        setTitle(getString(R.string.edit_course));
        Bundle extras = getIntent().getExtras();
        courseId = extras.getLong("id");
        termId = extras.getLong("termId");

        if (courseId > 0) {
            DataManager dataManager = new DataManager(this);
            Course course = dataManager.getCourse(courseId);
            termId = course.getCourseTermId();
            if (course != null) {
                oldCourse = dataManager.getCourse(courseId);

                ((EditText) findViewById(R.id.courseNameEt)).setText(course.getCourseName());
                ((EditText) findViewById(R.id.courseStartEt)).setText(course.getCourseStart());
                ((EditText) findViewById(R.id.courseEndEt)).setText(course.getCourseEnd());
                statusDropDown.setSelection(dropDownAdapter.getPosition(course.getCourseStatus()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveCourse:
                addCourse();
                return true;
            case android.R.id.home:
                onUp();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addCourse() {

        if (oldCourse != null) {
            DataManager dataManager = new DataManager(this);
            String courseName = ((EditText) findViewById(R.id.courseNameEt)).getText().toString();
            String courseStart = ((EditText) findViewById(R.id.courseStartEt)).getText().toString();
            String courseEnd = ((EditText) findViewById(R.id.courseEndEt)).getText().toString();
            String courseStatus = statusDropDown.getSelectedItem().toString();
            oldCourse.setCourseName(courseName);
            oldCourse.setCourseStart(courseStart);
            oldCourse.setCourseEnd(courseEnd);
            oldCourse.setCourseStatus(courseStatus);
            oldCourse.setCourseTermId(termId);
            dataManager.updateCourses(oldCourse);
            finish();

        } else {
            DataManager dataManager = new DataManager(this);
            String courseName = ((EditText) findViewById(R.id.courseNameEt)).getText().toString();
            String courseStart = ((EditText) findViewById(R.id.courseStartEt)).getText().toString();
            String courseEnd = ((EditText) findViewById(R.id.courseEndEt)).getText().toString();
            String courseStatus = statusDropDown.getSelectedItem().toString();
            Course course = new Course(courseName, courseStart, courseEnd, courseStatus);
            course.setCourseTermId(termId);
            dataManager.addCourse(course);
            finish();
        }
    }

    @Override
    public void finish() {
        if (courseId == 0) {
            onUp();
        } else {
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void onUp() {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void setEnd(View view) throws ParseException {
        try {
            String title = ((EditText) findViewById(R.id.courseNameEt)).getText().toString();
            String alert = title + " is due to be completed today!";
            int id = 1;

            String date = ((EditText) findViewById(R.id.courseEndEt)).getText().toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date alarmDate = simpleDateFormat.parse(date);
            Calendar cal = simpleDateFormat.getCalendar();

            Long alertTime = cal.getTimeInMillis();
            Intent alertIntent = new Intent(this, AlertReceiver.class);
            alertIntent.putExtra("title", title);
            alertIntent.putExtra("alert", alert);
            alertIntent.putExtra("id", id);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            Toast.makeText(this, "Course End Date Alert Set", Toast.LENGTH_SHORT).show();
        } catch (ParseException p2) {
            Toast.makeText(this, "Course End Date is Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void setStart(View view) throws ParseException {
        try {
            String title = ((EditText) findViewById(R.id.courseNameEt)).getText().toString();
            String alert = title + " is due to be started today!";
            int id = 2;

            String date = ((EditText) findViewById(R.id.courseStartEt)).getText().toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date alarmDate = simpleDateFormat.parse(date);
            Calendar cal = simpleDateFormat.getCalendar();

            Long alertTime = cal.getTimeInMillis();
            Intent alertIntent2 = new Intent(this, AlertReceiver.class);
            alertIntent2.putExtra("title", title);
            alertIntent2.putExtra("alert", alert);
            alertIntent2.putExtra("id", id);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 2, alertIntent2, PendingIntent.FLAG_UPDATE_CURRENT));
            Toast.makeText(this, "Course Start Date Alert Set", Toast.LENGTH_SHORT).show();
        } catch (ParseException p1) {
            Toast.makeText(this, "Course Start Date is Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToTerms(View v) {
        Toast.makeText(CourseEditActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}