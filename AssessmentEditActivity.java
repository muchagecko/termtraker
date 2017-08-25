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

public class AssessmentEditActivity extends AppCompatActivity {
    private long courseId;
    private long assessmentId;
    private Assessment oldAssessment;
    private Spinner typeDropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        typeDropDown = (Spinner) findViewById(R.id.typeDropDown);
        DatePicker.startDatePicker((EditText) findViewById(R.id.assessmentDueEt));
        List<String> dropDownItems = new ArrayList<String>();
        dropDownItems.add("Objective");
        dropDownItems.add("Performance");

        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dropDownItems);
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDropDown.setAdapter(dropDownAdapter);
        setTitle(getString(R.string.assessment_edit));
        Bundle extras = getIntent().getExtras();
        assessmentId = extras.getLong("id");
        courseId = extras.getLong("courseId");

        if (assessmentId > 0) {
            DataManager dataManager = new DataManager(this);
            Assessment assessment = dataManager.getAssessment(assessmentId);
            courseId = assessment.getCourseId();
            if (assessment != null) {
                oldAssessment = dataManager.getAssessment(assessmentId);

                ((EditText) findViewById(R.id.assessmentNameEt)).setText(assessment.getAssessmentName());
                ((EditText) findViewById(R.id.assessmentDueEt)).setText(assessment.getAssessmentDue());
                typeDropDown.setSelection(dropDownAdapter.getPosition(assessment.getAssessmentType()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveAssessment:
                addAssessment();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addAssessment() {

        if (oldAssessment != null) {
            DataManager dataManager = new DataManager(this);
            String assessmentName = ((EditText) findViewById(R.id.assessmentNameEt)).getText().toString();
            String assessmentDue = ((EditText) findViewById(R.id.assessmentDueEt)).getText().toString();
            String assessmentType = typeDropDown.getSelectedItem().toString();

            oldAssessment.setAssessmentName(assessmentName);
            oldAssessment.setAssessmentDue(assessmentDue);
            oldAssessment.setAssessmentType(assessmentType);
            oldAssessment.setCourseId(courseId);
            dataManager.updateAssessment(oldAssessment);
            finish();

        } else {
            DataManager dataManager = new DataManager(this);
            String assessmentName = ((EditText) findViewById(R.id.assessmentNameEt)).getText().toString();
            String assessmentDue = ((EditText) findViewById(R.id.assessmentDueEt)).getText().toString();
            String assessmentType = typeDropDown.getSelectedItem().toString();
            Assessment assessment = new Assessment(assessmentName, assessmentDue, assessmentType);
            assessment.setCourseId(courseId);
            dataManager.addAssessment(assessment);
            finish();
        }
    }

    @Override
    public void finish() {
        if (assessmentId == 0) {
            Intent intent = new Intent(this, AssessmentActivity.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AssessmentDetailActivity.class);
            intent.putExtra("id", assessmentId);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void setDue(View view) throws ParseException {
        try {
            String title = ((EditText) findViewById(R.id.assessmentNameEt)).getText().toString();
            String alert = title + " is due to be completed today!";
            int id = 3;

            String date = ((EditText) findViewById(R.id.assessmentDueEt)).getText().toString();
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
            Toast.makeText(this, "Assessment Due Date Alert Set", Toast.LENGTH_SHORT).show();
        } catch (ParseException p2) {
            Toast.makeText(this, "Assessment Due Date is Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToTerms(View v) {
        Toast.makeText(AssessmentEditActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}