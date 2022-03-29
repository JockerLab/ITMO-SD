package ru.shaldin.sd.mvc.model;

public class Task {
    private int id;
    private String value;
    private boolean done;

    public Task() {}

    public Task(int id, String value, boolean done) {
        this.id = id;
        this.value = value;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
