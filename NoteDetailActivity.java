package com.example.termtraker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class NoteDetailActivity extends AppCompatActivity {
    private long courseId;
    private long assessmentId;
    private long noteId;
    private String activityFrom;
    public final static int CAMERA_REQUEST_CODE = 1001;
    private byte[] noteImage;
    private Note note;
    private Button photoBtn;
    private ImageView thumbNail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        setTitle(getString(R.string.note_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        thumbNail = (ImageView) findViewById(R.id.previewImage);
        photoBtn = new Button(this);
        photoBtn.setText(R.string.take_not_picture);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relNoteLayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.previewImage);
        params.setMargins(16, 16, 16, 16);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        photoBtn.setId(R.id.takePhotoBtn);
        layout.addView(photoBtn, params);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        Bundle extras = getIntent().getExtras();
        noteId = extras.getLong("id");
        courseId = extras.getLong("courseId");
        assessmentId = extras.getLong("assessmentId");
        activityFrom = extras.getString("activityId");

        if (noteId > 0) {
            DataManager dataManager = new DataManager(this);
            note = dataManager.getNote(noteId);

            if (note != null) {
                if (activityFrom.equals("course")) {
                    Course course = dataManager.getCourse(courseId);
                    courseId = course.getCourseId();
                }
                if (activityFrom.equals("assessment")) {
                    Assessment assessment = dataManager.getAssessment(assessmentId);
                    assessmentId = assessment.getAssessmentId();
                }
                noteImage = note.getNoteBitmap();
                ((TextView) findViewById(R.id.noteTitleTv)).setText(note.getNoteTitle());
                ((TextView) findViewById(R.id.noteDescriptionTv)).setText(note.getNoteDescription());
                if (noteImage != null) {
                    ((ImageView) findViewById(R.id.previewImage)).setImageBitmap(getNoteImage(noteImage));
                }
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteNote:
                deleteNote();
                return true;
            case R.id.editNote:
                editNote();
                return true;
            case android.R.id.home:
                finishDetails();
                return true;
            case R.id.emailNote:
                sendEmail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteNote() {
        DataManager dataManager = new DataManager(getApplicationContext());
        dataManager.deleteNote(noteId);
        finish();
    }

    public void editNote() {
        Intent intent = new Intent(
                getApplicationContext(), NoteEditActivity.class);
        intent.putExtra("id", noteId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("assessmentId", assessmentId);
        intent.putExtra("activityId", activityFrom);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishDetails();
    }

    public void finishDetails() {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("activityId", activityFrom);
        intent.putExtra("assessmentId", assessmentId);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    protected void sendEmail() {
        String subject = ((TextView) findViewById(R.id.noteTitleTv)).getText().toString();
        String message = ((TextView) findViewById(R.id.noteDescriptionTv)).getText().toString();
        String[] to = {""};
        String[] cc = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("*/*");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email.."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Email app not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                thumbNail.setImageBitmap(imageBitmap);
                noteImage = getByteArray(imageBitmap);
                String noteTitle = ((TextView) findViewById(R.id.noteTitleTv)).getText().toString();
                String noteDescription = ((TextView) findViewById(R.id.noteDescriptionTv)).getText().toString();

                DataManager dataManager = new DataManager(this);
                Note noteUpdate = dataManager.getNote(noteId);
                noteUpdate.setNoteId(noteId);
                noteUpdate.setNoteTitle(noteTitle);
                noteUpdate.setNoteDescription(noteDescription);
                noteUpdate.setNoteBitmap(noteImage);
                noteUpdate.setCourseId(courseId);
                dataManager.updateNote(noteUpdate);
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    public static byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getNoteImage(byte[] noteImage) {
        return BitmapFactory.decodeByteArray(noteImage, 0, noteImage.length);
    }

    public void backToTerms(View v) {
        Toast.makeText(NoteDetailActivity.this,"FAB",Toast.LENGTH_SHORT).show();
    }
}