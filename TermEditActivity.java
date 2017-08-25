package com.example.termtraker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TermEditActivity extends AppCompatActivity {
    private long termId;
    private Term oldTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        setTitle(getString(R.string.term_edit));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DatePicker.startDatePicker((EditText) findViewById(R.id.termStartEt));
        DatePicker.startDatePicker((EditText) findViewById(R.id.termEndEt));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            termId = extras.getLong("id");
            DataManager dataManager = new DataManager(this);
            Term term = dataManager.getTerm(termId);

            if (term != null) {
                oldTerm = dataManager.getTerm(termId);
                ((EditText) findViewById(R.id.termNameEt)).setText(term.getTermName());
                ((EditText) findViewById(R.id.termStartEt)).setText(term.getTermStart());
                ((EditText) findViewById(R.id.termEndEt)).setText(term.getTermEnd());
                DatePicker.startDatePicker((EditText) findViewById(R.id.termStartEt));
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveTerm:
                addTerm();
                return true;
            case android.R.id.home:
                onUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addTerm() {

        if (oldTerm != null) {
            DataManager dataManager = new DataManager(this);
            String termName = ((EditText) findViewById(R.id.termNameEt)).getText().toString();
            String termStart = ((EditText) findViewById(R.id.termStartEt)).getText().toString();
            String termEnd = ((EditText) findViewById(R.id.termEndEt)).getText().toString();
            oldTerm.setTermName(termName);
            oldTerm.setTermStart(termStart);
            oldTerm.setTermEnd(termEnd);
            dataManager.updateTerm(oldTerm);
            finish();
        } else {
            DataManager dataManager = new DataManager(this);
            String termName = ((EditText) findViewById(R.id.termNameEt)).getText().toString();
            String termStart = ((EditText) findViewById(R.id.termStartEt)).getText().toString();
            String termEnd = ((EditText) findViewById(R.id.termEndEt)).getText().toString();
            Term term = new Term(termName, termStart, termEnd);
            dataManager.addTerm(term);
            finish();
        }
    }

    @Override
    public void finish() {
        if (termId == 0) {
            Intent intent = new Intent(this, TermActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, TermDetailActivity.class);
            intent.putExtra("id", termId);
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
        Toast.makeText(TermEditActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}