package com.example.reggiewashington.c196;

public class Assessments {
    private int id;
    private String type, name, date;
    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Assessments(int id, String type, String name, String date, boolean selected){
        this.setId(id);
        this.setType(type);
        this.setName(name);
        this.setDate(date);
        this.selected = selected;
    }

    public Assessments(String type, String name, String date){
        this.setId(id);
        this.setType(type);
        this.setName(name);
        this.setDate(date);
        this.selected = selected;
    }

    public Assessments(int id, String name){
        this.setId(id);
        this.setType(type);
        this.setName(name);
        this.setDate(date);
        this.selected = selected;
    }

    public Assessments(){
        this.setId(id);
        this.setType(type);
        this.setName(name);
        this.setDate(date);
        this.selected = selected;
    }

    public Assessments(String name){
        this.setId(id);
        this.setType(type);
        this.setName(name);
        this.setDate(date);
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
