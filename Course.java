package com.example.termtraker;


public class Course {
    private long courseId;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;

    public long getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(long courseTermId) {
        this.courseTermId = courseTermId;
    }

    private long courseTermId;

    public Course() {
    }

    ;

    public Course(String courseName, String courseStart, String courseEnd, String courseStatus) {
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
    }

    public Course(String courseName, String courseStart, String courseEnd, String courseStatus, long courseTermId) {
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseTermId = courseTermId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
