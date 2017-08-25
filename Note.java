package com.example.termtraker;

public class Note {
    private long noteId;
    private String noteTitle;
    private String noteDescription;
    private byte[] noteBitmap;

    public byte[] getNoteBitmap() {
        return noteBitmap;
    }

    public void setNoteBitmap(byte[] noteBitmap) {
        this.noteBitmap = noteBitmap;
    }

    private long courseId;
    private long assessmentId;

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, long courseId, long assessmentId) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.courseId = courseId;
        this.assessmentId = assessmentId;
    }

    public Note(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }
}