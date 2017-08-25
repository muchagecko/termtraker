package com.example.termtraker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MentorEditActivity extends AppCompatActivity {
    private long courseId;
    private long mentorId;
    private Mentor oldMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.mentor_edit));
        Bundle extras = getIntent().getExtras();
        mentorId = extras.getLong("id");
        courseId = extras.getLong("courseId");

        if (mentorId > 0) {
            DataManager dataManager = new DataManager(this);
            Mentor mentor = dataManager.getMentor(mentorId);
            courseId = mentor.getCourseId();
            if (mentor != null) {
                oldMentor = dataManager.getMentor(mentorId);
                ((EditText) findViewById(R.id.mentorNameEt)).setText(mentor.getMentorName());
                ((EditText) findViewById(R.id.mentorPhoneEt)).setText(mentor.getMentorPhone());
                ((EditText) findViewById(R.id.mentorEmailEt)).setText(mentor.getMentorEmail());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mentor_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMentor:
                addMentor();
                return true;
            case android.R.id.home:
                onUp();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addMentor() {

        if (oldMentor != null) {
            DataManager dataManager = new DataManager(this);
            String mentorName = ((EditText) findViewById(R.id.mentorNameEt)).getText().toString();
            String mentorPhone = ((EditText) findViewById(R.id.mentorPhoneEt)).getText().toString();
            String mentorEmail = ((EditText) findViewById(R.id.mentorEmailEt)).getText().toString();

            oldMentor.setMentorName(mentorName);
            oldMentor.setMentorPhone(mentorPhone);
            oldMentor.setMentorEmail(mentorEmail);
            oldMentor.setCourseId(courseId);
            oldMentor.setMentorId(mentorId);
            dataManager.updateMentor(oldMentor);
            finish();

        } else {
            DataManager dataManager = new DataManager(this);
            String mentorName = ((EditText) findViewById(R.id.mentorNameEt)).getText().toString();
            String mentorPhone = ((EditText) findViewById(R.id.mentorPhoneEt)).getText().toString();
            String mentorEmail = ((EditText) findViewById(R.id.mentorEmailEt)).getText().toString();
            Mentor mentor = new Mentor(mentorName, mentorPhone, mentorEmail);
            mentor.setCourseId(courseId);

            dataManager.addMentor(mentor);
            finish();
        }
    }

    @Override
    public void finish() {
        if (courseId == 0) {
            Intent intent = new Intent(this, MentorActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MentorActivity.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onUp() {
        finish();
    }

    public void backToTerms(View v) {
        Toast.makeText(MentorEditActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}