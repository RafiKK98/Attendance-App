package com.iub.attendanceapp;

public class CourseItem {

    private long cid;
    private String courseId;
    private String courseName;

    public CourseItem(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public CourseItem(long cid, String courseId, String courseName) {
        this.cid = cid;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }
}
