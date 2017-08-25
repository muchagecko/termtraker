package com.example.termtraker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TermDetailActivity extends AppCompatActivity {
    long termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        setTitle(getString(R.string.term_detail));
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            termId = extras.getLong("id");
            DataManager dataManager = new DataManager(this);
            Term term = dataManager.getTerm(termId);

            if (term != null) {
                ((TextView) findViewById(R.id.termNameTv)).setText(term.getTermName());
                ((TextView) findViewById(R.id.termStartTv)).setText(term.getTermStart());
                ((TextView) findViewById(R.id.termEndTv)).setText(term.getTermEnd());

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteTerm:
                deleteTerm();
                return true;
            case R.id.editTerm:
                editTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTerm() {
        DataManager dataManager = new DataManager(getApplicationContext());
        Term termToCheck = dataManager.getTerm(termId);
        Cursor cursor = dataManager.getCoursesForTermsCursor(termToCheck);
        if (cursor.getCount() == 0) {
            dataManager.deleteTerm(termId);
            finish();
        } else {
            Toast.makeText(this, "Term contains courses. Please delete the associated courses before removing this Term.", Toast.LENGTH_SHORT).show();
        }
    }

    public void editTerm() {
        Intent intent = new Intent(
                getApplicationContext(), TermEditActivity.class);
        intent.putExtra("id", termId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void openCoursesToAdd(View view) {
        Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
        intent.putExtra("id", termId);
        startActivity(intent);
    }

    /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener((view) -> {
            Toast.makeText(TermDetailActivity.this,"FAB",Toast.LENGTH_SHORT).show();
        });*/
}