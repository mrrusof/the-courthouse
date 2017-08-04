package com.mrrusof.thecourtroom;

public class TrialParams {

    private String src;
    private String problemId;

    public TrialParams(){}

    public TrialParams(String src, String problemId) {
        this.src = src;
        this.problemId = problemId;
    }

    public String getSrc() {
        return src;
    }

    public String getProblemId() {
        return problemId;
    }

}
