package com.example.termtraker;

public class Assessment {
    private long assessmentId;
    private String assessmentName;
    private String assessmentDue;
    private String assessmentType;
    private long courseId;

    public Assessment() {
    }

    public Assessment(String assessmentName, String assessmentDue, String assessmentType) {
        this.assessmentName = assessmentName;
        this.assessmentDue = assessmentDue;
        this.assessmentType = assessmentType;
    }

    public Assessment(String assessmentName, String assessmentDue, String assessmentType, long courseId) {
        this.assessmentName = assessmentName;
        this.assessmentDue = assessmentDue;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    public long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentDue() {
        return assessmentDue;
    }

    public void setAssessmentDue(String assessmentDue) {
        this.assessmentDue = assessmentDue;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}