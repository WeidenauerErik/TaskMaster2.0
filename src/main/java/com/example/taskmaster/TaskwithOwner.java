package com.example.taskmaster;

import java.io.IOException;

public class TaskwithOwner {
    private String title;
    private String deadline;
    private String info;
    private String index;
    private String owner;

    public TaskwithOwner(String title, String info, String deadline, String index,String owner) throws IOException {
        this.title = title;
        this.deadline = deadline;
        this.info = info;
        this.index = index;
        this.owner = owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "[" + title + "][" + deadline + "][" + info + "][" + index + "]";
    }
}
