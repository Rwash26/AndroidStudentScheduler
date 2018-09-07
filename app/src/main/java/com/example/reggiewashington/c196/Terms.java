package com.example.reggiewashington.c196;

import java.util.Date;

public class Terms {

    private int id;
    private String term, start, end;

    public Terms(int id, String term, String start, String end){
        this.setId(id);
        this.setTerm(term);
        this.setStart(start);
        this.setEnd(end);
    }

    public Terms(){
        this.setId(id);
        this.setTerm(term);
        this.setStart(start);
        this.setEnd(end);
    }

    public Terms(String term, String start, String end){
        this.setId(id);
        this.setTerm(term);
        this.setStart(start);
        this.setEnd(end);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {

        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String toString(){
        String s = term + "\t" + start + "\t" + end;
        return s;
    }
}

