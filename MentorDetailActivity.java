package com.example.termtraker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MentorDetailActivity extends AppCompatActivity {
    private long courseId;
    private long mentorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.mentor_detail));
        Bundle extras = getIntent().getExtras();
        mentorId = extras.getLong("id");
        courseId = extras.getLong("courseId");

        if (courseId <= 0) {

        }
        if (mentorId > 0) {
            DataManager dataManager = new DataManager(this);
            Mentor mentor = dataManager.getMentor(mentorId);
            if (mentor != null) {
                courseId = mentor.getCourseId();
                ((TextView) findViewById(R.id.mentorNameTv)).setText(mentor.getMentorName());
                ((TextView) findViewById(R.id.mentorPhoneTv)).setText(mentor.getMentorPhone());
                ((TextView) findViewById(R.id.mentorEmailTv)).setText(mentor.getMentorEmail());
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mentor_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteMentor:
                deleteMentor();
                return true;
            case R.id.editMentor:
                editMentor();
                return true;
            case android.R.id.home:
                onUp();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteMentor() {
        DataManager dataManager = new DataManager(getApplicationContext());
        dataManager.deleteMentor(mentorId);
        finish();
    }

    public void editMentor() {
        Intent intent = new Intent(
                getApplicationContext(), MentorEditActivity.class);
        intent.putExtra("id", mentorId);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        onUp();
    }

    public void onUp() {
        if (courseId == 0) {
            Intent intent = new Intent(this, MentorActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MentorActivity.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        }
    }

    public void backToTerms(View v) {
        Toast.makeText(MentorDetailActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}