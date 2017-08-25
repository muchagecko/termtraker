package com.example.termtraker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManager extends SQLiteOpenHelper {
    //*** Constants for db name and version
    private static final String DATABASE_NAME = "termtraker.db";
    private static final int DATABASE_VERSION = 1;
    //*** table names
    private static final String TABLE_TERMS = "Terms";
    private static final String TABLE_COURSES = "Courses";
    private static final String TABLE_MENTORS = "Mentors";
    public static final String TABLE_ASSESS = "Assessments";
    public static final String TABLE_NOTES = "Notes";

    //*** table terms columns
    //static final String TABLE_TERM = "Terms";
    static final String COLUMN_TERM_ID = "_id";
    static final String COLUMN_TERM_NAME = "Name";
    static final String COLUMN_TERM_START = "StartDate";
    static final String COLUMN_TERM_END = "EndDate";
    static final String[] ALL_COLUMNS_TERM = {COLUMN_TERM_ID, COLUMN_TERM_NAME, COLUMN_TERM_START, COLUMN_TERM_END};
    private static final String CREATE_TERM_TABLE =
            "CREATE TABLE " + TABLE_TERMS + " (" +
                    COLUMN_TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TERM_NAME + " TEXT, " +
                    COLUMN_TERM_START + " TEXT, " +
                    COLUMN_TERM_END + " TEXT" +
                    ")";

    //*** table courses columns
    //static final String TABLE_COURSE = "Courses";
    static final String COLUMN_COURSE_ID = "_id";
    static final String COLUMN_COURSE_TERM_ID = "TermId";
    static final String COLUMN_COURSE_NAME = "Name";
    static final String COLUMN_COURSE_START = "StartDate";
    static final String COLUMN_COURSE_END = "EndDate";
    static final String COLUMN_COURSE_STATUS = "Status";
    static final String[] ALL_COLUMNS_COURSE = {COLUMN_COURSE_ID, COLUMN_COURSE_TERM_ID, COLUMN_COURSE_NAME, COLUMN_COURSE_START, COLUMN_COURSE_END, COLUMN_COURSE_STATUS};
    private static final String CREATE_COURSE_TABLE =
            "CREATE TABLE " + TABLE_COURSES + " (" +
                    COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COURSE_TERM_ID + " INTEGER, " +
                    COLUMN_COURSE_NAME + " TEXT, " +
                    COLUMN_COURSE_START + " TEXT, " +
                    COLUMN_COURSE_END + " TEXT, " +
                    COLUMN_COURSE_STATUS + " TEXT" +
                    ")";

    //*** table mentor columns
    //static final String TABLE_MENTORS = "Mentors";
    static final String COLUMN_MENTOR_ID = "_id";
    static final String COLUMN_MENTOR_NAME = "Name";
    static final String COLUMN_MENTOR_PHONE = "Phone";
    static final String COLUMN_MENTOR_EMAIL = "Email";
    static final String COLUMN_MENTOR_COURSE_ID = "CourseId";
    static final String[] ALL_COLUMNS_MENTOR = {COLUMN_MENTOR_ID, COLUMN_MENTOR_NAME, COLUMN_MENTOR_PHONE, COLUMN_MENTOR_EMAIL, COLUMN_MENTOR_COURSE_ID};
    private static final String CREATE_MENTOR_TABLE =
            "CREATE TABLE " + TABLE_MENTORS + " (" +
                    COLUMN_MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MENTOR_NAME + " TEXT, " +
                    COLUMN_MENTOR_PHONE + " TEXT, " +
                    COLUMN_MENTOR_EMAIL + " TEXT, " +
                    COLUMN_MENTOR_COURSE_ID + " INTEGER" +
                    ")";

    //*** table assess columns
    //static final String TABLE_ASSESSMENTS = "Assessments";
    static final String COLUMN_ASSESSMENT_ID = "_id";
    static final String COLUMN_ASSESSMENT_NAME = "Name";
    static final String COLUMN_ASSESSMENT_DUE = "Due";
    static final String COLUMN_ASSESSMENT_TYPE = "EndDate";
    static final String COLUMN_ASSESSMENT_COURSE_ID = "CourseId";
    static final String[] ALL_COLUMNS_ASSESSMENT = {COLUMN_ASSESSMENT_ID, COLUMN_ASSESSMENT_NAME, COLUMN_ASSESSMENT_DUE, COLUMN_ASSESSMENT_TYPE, COLUMN_ASSESSMENT_COURSE_ID};
    private static final String CREATE_ASSESSMENT_TABLE =
            "CREATE TABLE " + TABLE_ASSESS + " (" +
                    COLUMN_ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ASSESSMENT_NAME + " TEXT, " +
                    COLUMN_ASSESSMENT_DUE + " TEXT, " +
                    COLUMN_ASSESSMENT_TYPE + " TEXT, " +
                    COLUMN_ASSESSMENT_COURSE_ID + " INTEGER" +
                    ")";

    //*** table notes columns
    //static final String TABLE_NOTES = "Notes";
    static final String COLUMN_NOTE_ID = "_id";
    static final String COLUMN_NOTE_TITLE = "Title";
    static final String COLUMN_NOTE_DESCRIPTION = "Description";
    static final String COLUMN_NOTE_IMAGE = "Image";
    static final String COLUMN_NOTE_COURSE_ID = "CourseId";
    static final String COLUMN_NOTE_ASSESSMENT_ID = "AssessmentId";
    static final String[] ALL_COLUMNS_NOTES = {COLUMN_NOTE_ID, COLUMN_NOTE_TITLE, COLUMN_NOTE_DESCRIPTION, COLUMN_NOTE_IMAGE, COLUMN_NOTE_COURSE_ID, COLUMN_NOTE_ASSESSMENT_ID};
    private static final String CREATE_NOTE_TABLE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOTE_TITLE + " TEXT, " +
                    COLUMN_NOTE_DESCRIPTION + " TEXT, " +
                    COLUMN_NOTE_IMAGE + " BLOB, " +
                    COLUMN_NOTE_COURSE_ID + " INTEGER, " +
                    COLUMN_NOTE_ASSESSMENT_ID + " INTEGER" +
                    ")";

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TERM_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_MENTOR_TABLE);
        db.execSQL(CREATE_ASSESSMENT_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public Term addTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TERM_NAME, term.getTermName());
        values.put(COLUMN_TERM_START, term.getTermStart());
        values.put(COLUMN_TERM_END, term.getTermEnd());
        long id = db.insert(TABLE_TERMS, null, values);
        term.setId(id);
        db.close();
        return term;
    }

    public Term getTerm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TERMS, ALL_COLUMNS_TERM, COLUMN_TERM_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Term term = new Term(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
            term.setId(cursor.getLong(0));
            return term;
        }
        return null;
    }

    public Cursor getTermsCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_TERMS;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public int updateTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TERM_NAME, term.getTermName());
        contentValues.put(COLUMN_TERM_START, term.getTermStart());
        contentValues.put(COLUMN_TERM_END, term.getTermEnd());
        return db.update(TABLE_TERMS, contentValues, COLUMN_TERM_ID + " = ?", new String[]{String.valueOf(term.getId())});
    }

    public void deleteTerm(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TERMS, COLUMN_TERM_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Course addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TERM_NAME, course.getCourseName());
        values.put(COLUMN_TERM_START, course.getCourseStart());
        values.put(COLUMN_TERM_END, course.getCourseEnd());
        values.put(COLUMN_COURSE_STATUS, course.getCourseStatus());
        values.put(COLUMN_COURSE_TERM_ID, course.getCourseTermId());
        long id = db.insert(TABLE_COURSES, null, values);
        course.setCourseId(id);
        db.close();
        return course;
    }

    public Course getCourse(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COURSES, ALL_COLUMNS_COURSE, COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Course course = new Course(
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getLong(1));
            course.setCourseId(cursor.getLong(0));
            return course;
        }
        return null;
    }

    public Cursor getCoursesCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_COURSES;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getCoursesForTermsCursor(Term term) {
        long termId = term.getId();
        String selectQuery = "SELECT * FROM " + TABLE_COURSES + " WHERE " + COLUMN_COURSE_TERM_ID + "=" + termId;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);


    }

    public int updateCourses(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_NAME, course.getCourseName());
        contentValues.put(COLUMN_COURSE_START, course.getCourseStart());
        contentValues.put(COLUMN_COURSE_END, course.getCourseEnd());
        contentValues.put(COLUMN_COURSE_STATUS, course.getCourseStatus());
        contentValues.put(COLUMN_COURSE_TERM_ID, course.getCourseTermId());
        return db.update(TABLE_COURSES, contentValues, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(course.getCourseId())});
    }

    public void deleteCourse(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSES, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Mentor addMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MENTOR_NAME, mentor.getMentorName());
        values.put(COLUMN_MENTOR_PHONE, mentor.getMentorPhone());
        values.put(COLUMN_MENTOR_EMAIL, mentor.getMentorEmail());
        values.put(COLUMN_MENTOR_COURSE_ID, mentor.getCourseId());
        long id = db.insert(TABLE_MENTORS, null, values);
        mentor.setMentorId(id);
        db.close();
        return mentor;
    }

    public Mentor getMentor(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENTORS, ALL_COLUMNS_MENTOR, COLUMN_MENTOR_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Mentor mentor = new Mentor(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4));
            mentor.setMentorId(cursor.getLong(0));
            return mentor;
        }
        return null;
    }

    public Cursor getMentorCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_MENTORS;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getMentorsForCoursesCursor(Course course) {
        long courseId = course.getCourseId();
        String selectQuery = "SELECT * FROM " + TABLE_MENTORS + " WHERE " + COLUMN_MENTOR_COURSE_ID + "=" + courseId;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public int updateMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MENTOR_NAME, mentor.getMentorName());
        contentValues.put(COLUMN_MENTOR_PHONE, mentor.getMentorPhone());
        contentValues.put(COLUMN_MENTOR_EMAIL, mentor.getMentorEmail());
        contentValues.put(COLUMN_MENTOR_EMAIL, mentor.getMentorEmail());
        contentValues.put(COLUMN_MENTOR_COURSE_ID, mentor.getCourseId());
        return db.update(TABLE_MENTORS, contentValues, COLUMN_MENTOR_ID + " = ?", new String[]{String.valueOf(mentor.getMentorId())});
    }

    public void deleteMentor(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENTORS, COLUMN_MENTOR_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Assessment addAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ASSESSMENT_NAME, assessment.getAssessmentName());
        values.put(COLUMN_ASSESSMENT_DUE, assessment.getAssessmentDue());
        values.put(COLUMN_ASSESSMENT_TYPE, assessment.getAssessmentType());
        values.put(COLUMN_ASSESSMENT_COURSE_ID, assessment.getCourseId());
        long id = db.insert(TABLE_ASSESS, null, values);
        assessment.setAssessmentId(id);
        db.close();
        return assessment;
    }

    public Assessment getAssessment(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ASSESS, ALL_COLUMNS_ASSESSMENT, COLUMN_ASSESSMENT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Assessment assessment = new Assessment(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4));
            assessment.setAssessmentId(cursor.getLong(0));
            return assessment;
        }
        return null;
    }

    public Cursor getAssessmentCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_ASSESS;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAssessmentsForCoursesCursor(Course course) {
        long courseId = course.getCourseId();
        String selectQuery = "SELECT * FROM " + TABLE_ASSESS + " WHERE " + COLUMN_ASSESSMENT_COURSE_ID + "=" + courseId;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public int updateAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ASSESSMENT_NAME, assessment.getAssessmentName());
        contentValues.put(COLUMN_ASSESSMENT_DUE, assessment.getAssessmentDue());
        contentValues.put(COLUMN_ASSESSMENT_TYPE, assessment.getAssessmentType());
        contentValues.put(COLUMN_ASSESSMENT_COURSE_ID, assessment.getCourseId());

        return db.update(TABLE_ASSESS, contentValues, COLUMN_ASSESSMENT_ID + " = ?", new String[]{String.valueOf(assessment.getAssessmentId())});
    }

    public void deleteAssessment(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSESS, COLUMN_ASSESSMENT_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Note addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(COLUMN_NOTE_DESCRIPTION, note.getNoteDescription());
        values.put(COLUMN_NOTE_COURSE_ID, note.getCourseId());
        values.put(COLUMN_NOTE_ASSESSMENT_ID, note.getAssessmentId());
        long id = db.insert(TABLE_NOTES, null, values);
        note.setNoteId(id);
        db.close();
        return note;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, ALL_COLUMNS_NOTES, COLUMN_NOTE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Note note = new Note(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(4),
                    cursor.getLong(5));
            note.setNoteId(cursor.getLong(0));
            note.setNoteBitmap(cursor.getBlob(3));
            return note;
        }
        return null;
    }

    public Cursor getNotesForCoursesCursor(Course course) {
        long courseId = course.getCourseId();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_NOTE_COURSE_ID + "=" + courseId;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getNotesForAssessmentsCursor(Assessment assessment) {
        long assessmentId = assessment.getAssessmentId();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_NOTE_ASSESSMENT_ID + "=" + assessmentId;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        contentValues.put(COLUMN_NOTE_DESCRIPTION, note.getNoteDescription());
        contentValues.put(COLUMN_NOTE_IMAGE, note.getNoteBitmap());
        contentValues.put(COLUMN_NOTE_COURSE_ID, note.getCourseId());
        contentValues.put(COLUMN_NOTE_ASSESSMENT_ID, note.getAssessmentId());

        return db.update(TABLE_NOTES, contentValues, COLUMN_NOTE_ID + " = ?", new String[]{String.valueOf(note.getNoteId())});
    }

    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_NOTE_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}