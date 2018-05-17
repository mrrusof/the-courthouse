package com.mrrusof.thecourtroom;

public class BinaryJudgeParams extends JudgeParams {
    public BinaryJudgeParams(String program, TestCase tc) {
        super(program, tc);
    }

    public String programKey() {
        return "binaryProgram";
    }
}
