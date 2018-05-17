package com.mrrusof.thecourtroom;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
@Lazy
public class Courtroom {

    private TestCases testCases;
    private Environment env;

    @Autowired
    public Courtroom(TestCases testCases, Environment env) {
        this.testCases = testCases;
        this.env = env;
    }

    public Ruling trial(String problemId, String src, String lang) throws Exception {
        Long totalCentis = 0l;
        Judge j = new Judge(lang, src, env);
        Ruling compileRuling = j.buildProgram();
        if(! compileRuling.getRuling().equals(Ruling.ACCEPTED)) {
            return compileRuling;
        }
        for(TestCase tc : getTestCases(problemId)) {
            Ruling v = j.rule(tc);
            totalCentis += v.getCentiseconds();
            if(! v.getRuling().equals(Ruling.ACCEPTED))
                return new Ruling(v.getRuling(), totalCentis);
        }
        return new Ruling(Ruling.ACCEPTED, totalCentis);
    }

    private List<TestCase> getTestCases(String problemId) throws Exception {
        List<TestCase> tcs = testCases.findByProblemId(problemId);
        if(tcs.size() > 0) return tcs;
        throw new Exception("There are no test cases for problem id '" + problemId + "'.");
    }
}
