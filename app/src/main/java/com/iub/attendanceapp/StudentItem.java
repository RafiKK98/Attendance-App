package com.iub.attendanceapp;

public class StudentItem {
    private long sid;
    private int id;
    private String name;
    private String status;

    public StudentItem(long sid, int id, String name) {
        this.sid = sid;
        this.id = id;
        this.name = name;
        this.status = "";
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
