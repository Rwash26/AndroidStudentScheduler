package com.example.reggiewashington.c196;

public class CourseAssessments {
    int id, cid, aid;
    String title;

    public CourseAssessments(int id, int cid, int aid, String title){
        this.setId(id);
        this.setCid(cid);
        this.setAid(aid);
        this.setTitle(title);
    }

    public CourseAssessments(int cid, int aid, String title){
        this.setId(id);
        this.setCid(cid);
        this.setAid(aid);
        this.setTitle(title);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int tid) {
        this.aid = aid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
