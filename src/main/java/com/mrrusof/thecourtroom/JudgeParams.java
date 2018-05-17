package com.mrrusof.thecourtroom;

import org.json.JSONObject;

public abstract class JudgeParams {
    private TestCase tc;
    private String program;

    public JudgeParams(String program, TestCase tc) {
        this.tc = tc;
        this.program = program;
    }

    public abstract String programKey();

    public String toJsonString() {
        return "{ " + JSONObject.quote(programKey()) + ":" + JSONObject.quote(program) +
            ", \"input\":" + JSONObject.quote(tc.getInput()) + " }";
    }

    public String toString() {
        return "JudgeParams " + toJsonString();
    }
}
