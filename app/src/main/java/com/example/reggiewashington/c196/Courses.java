package com.example.reggiewashington.c196;

import android.widget.ArrayAdapter;

public class Courses{

    private int courseid;
    private String courseTitle, courseStartDate, antiEndDate, status, mentor, mentorNumber, mentorEmail, notes;
    boolean selected;

    public Courses(int courseid,String courseTitle, String courseStartDate, String antiEndDate, String status, String mentor, String mentorNumber, String mentorEmail, String notes, boolean selected){
        this.setCourseid(courseid);
        this.setCourseTitle(courseTitle);
        this.setCourseStartDate(courseStartDate);
        this.setAntiEndDate(antiEndDate);
        this.setStatus(status);
        this.setMentor(mentor);
        this.setMentorNumber(mentorNumber);
        this.setMentorEmail(mentorEmail);
        this.setNotes(notes);
        this.selected = selected;
    }
    public Courses(String courseTitle){
        this.setCourseid(courseid);
        this.setCourseTitle(courseTitle);
        this.setCourseStartDate(courseStartDate);
        this.setAntiEndDate(antiEndDate);
        this.setStatus(status);
        this.setMentor(mentor);
        this.setMentorNumber(mentorNumber);
        this.setMentorEmail(mentorEmail);
        this.setNotes(notes);
        this.selected = selected;
    }
    public Courses(int courseid, String courseTitle){
        this.setCourseid(courseid);
        this.setCourseTitle(courseTitle);
        this.setCourseStartDate(courseStartDate);
        this.setAntiEndDate(antiEndDate);
        this.setStatus(status);
        this.setMentor(mentor);
        this.setMentorNumber(mentorNumber);
        this.setMentorEmail(mentorEmail);
        this.setNotes(notes);
        this.selected = selected;
    }
    public Courses(String courseTitle, boolean selected){
        this.setCourseid(courseid);
        this.setCourseTitle(courseTitle);
        this.setCourseStartDate(courseStartDate);
        this.setAntiEndDate(antiEndDate);
        this.setStatus(status);
        this.setMentor(mentor);
        this.setMentorNumber(mentorNumber);
        this.setMentorEmail(mentorEmail);
        this.setNotes(notes);
        this.selected = selected;
    }

    public Courses(){
        this.setCourseid(courseid);
        this.setCourseTitle(courseTitle);
        this.setCourseStartDate(courseStartDate);
        this.setAntiEndDate(antiEndDate);
        this.setStatus(status);
        this.setMentor(mentor);
        this.setMentorNumber(mentorNumber);
        this.setMentorEmail(mentorEmail);
        this.setNotes(notes);
        this.selected = selected;

    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getAntiEndDate() {
        return antiEndDate;
    }

    public void setAntiEndDate(String antiEndDate) {
        this.antiEndDate = antiEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getMentorNumber() {
        return mentorNumber;
    }

    public void setMentorNumber(String mentorNumber) {
        this.mentorNumber = mentorNumber;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public String toString(){

        String c = courseTitle + "\t" + courseStartDate + "\t" + antiEndDate + "\t" + status + "\t" + mentor + "\t" + mentorNumber + "\t" + mentorEmail + "\t" + notes;
        return c;
    }
}
