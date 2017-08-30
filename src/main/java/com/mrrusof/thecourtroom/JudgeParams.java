package com.mrrusof.thecourtroom;

import org.json.JSONObject;

public class JudgeParams {
    private TestCase tc;
    private String src;

    public JudgeParams(TestCase tc, String src) {
        this.tc = tc;
        this.src = src;
    }

    public String toJsonString() {
        return "{ \"program\":" + JSONObject.quote(src) +
            ", \"input\":" + JSONObject.quote(tc.getInput()) +
            ", \"output\":" + JSONObject.quote(tc.getOutput()) + " }";
    }

    public String toString() {
        return "JudgeParams " + toJsonString();
    }
}
