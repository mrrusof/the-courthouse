package com.mrrusof.thecourtroom;

public class JudgeId {

    private final String id;
    private final String name;
    private final String codemirrormode;

    public JudgeId(String id, String name, String codemirrormode) {
        this.id = id;
        this.name = name;
        this.codemirrormode = codemirrormode;
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

}
