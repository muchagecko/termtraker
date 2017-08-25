package com.example.termtraker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AssessmentDetailActivity extends AppCompatActivity {
    private long courseId;
    private long assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.assessment_ID));
        Bundle extras = getIntent().getExtras();
        assessmentId = extras.getLong("id");
        courseId = extras.getLong("courseId");

        if (assessmentId > 0) {
            DataManager dataManager = new DataManager(this);
            Assessment assessment = dataManager.getAssessment(assessmentId);

            if (assessment != null) {
                courseId = assessment.getCourseId();
                assessmentId = assessment.getAssessmentId();
                ((TextView) findViewById(R.id.assessmentNameTv)).setText(assessment.getAssessmentName());
                ((TextView) findViewById(R.id.assessmentDueTv)).setText(assessment.getAssessmentDue());
                ((TextView) findViewById(R.id.assessmentTypeTv)).setText(assessment.getAssessmentType());

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAssessment:
                deleteAssessment();
                return true;
            case R.id.editAssessment:
                editAssessment();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteAssessment() {
        DataManager dataManager = new DataManager(getApplicationContext());
        dataManager.deleteAssessment(assessmentId);
        finish();
    }

    public void editAssessment() {
        Intent intent = new Intent(
                getApplicationContext(), AssessmentEditActivity.class);
        intent.putExtra("id", assessmentId);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    @Override
    public void finish() {
        if (courseId == 0) {
            Intent intent = new Intent(this, AssessmentActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AssessmentActivity.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void openNotesToAdd(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("assessmentId", assessmentId);
        intent.putExtra("activityId", "assessment");
        startActivity(intent);
    }

    public void backToTerms(View v) {
        Toast.makeText(AssessmentDetailActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}