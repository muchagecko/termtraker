package com.example.termtraker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private long termId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.yinyangfull_action);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);
        loadPreferences();
    }

    /** menu items **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openTerms(View view) {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
    }

    public void openCourses(View view) {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void openMentors(View view) {
        Intent intent = new Intent(this, MentorActivity.class);
        startActivity(intent);
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*boolean isBackgroundDark = sharedPreferences.getBoolean("background_color", false);
        if (isBackgroundDark) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.mainPageLayout);
            mainLayout.setBackgroundColor(Color.parseColor("#311b92"));
        }*/
    }
}