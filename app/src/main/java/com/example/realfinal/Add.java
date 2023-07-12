package com.example.realfinal;

public class Add {

    String courseTitle;
    String courseProfessor;
    String courseTime;
    String courseRoom;

    public Add(String courseTitle, String courseProfessor, String courseTime, String courseRoom) {
        this.courseTitle = courseTitle;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
    }

    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    public String getCourseProfessor() {
        return courseProfessor;
    }
    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }
    public String getCourseTime() {
        return courseTime;
    }
    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }
    public String getCourseRoom() {
        return courseRoom;
    }
    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }


}
