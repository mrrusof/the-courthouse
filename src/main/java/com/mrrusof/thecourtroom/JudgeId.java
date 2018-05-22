package com.mrrusof.thecourtroom;

public class JudgeId {

    private final String id;
    private final String name;
    private final String codemirrormode;
    private final String notes;

    public JudgeId(String id, String name, String codemirrormode, String notes) {
        this.id = id;
        this.name = name;
        this.codemirrormode = codemirrormode;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCodemirrormode() {
        return codemirrormode;
    }

    public String getNotes() {
        return notes;
    }
}
