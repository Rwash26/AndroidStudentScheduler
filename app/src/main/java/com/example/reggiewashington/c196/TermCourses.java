package com.example.reggiewashington.c196;

public class TermCourses {
    int id, tid, cid;
    String title;

    public TermCourses(int id, int tid, int cid, String title){
        this.setId(id);
        this.setTid(tid);
        this.setCid(cid);
        this.setTitle(title);
    }

    public TermCourses(int tid, int cid, String title){
        this.setId(id);
        this.setTid(tid);
        this.setCid(cid);
        this.setTitle(title);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
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
