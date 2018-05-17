package com.mrrusof.thecourtroom;

public class InterpretedJudgeParams extends JudgeParams {
    public InterpretedJudgeParams(String program, TestCase tc) {
        super(program, tc);
    }

    public String programKey() {
        return "interpretedProgram";
    }
}
