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

    public Verdict trial(Long problemId, String src, String lang) throws Exception {
        Long totalCentis = 0l;
        Judge j = getJudge(lang);
        for(TestCase tc : getTestCases(problemId)) {
            Verdict v = j.rule(src, tc);
            if(! v.getVerdict().equals(Verdict.ACCEPTED)) return v;
            totalCentis += v.getCentiseconds();
        }
        return new Verdict(Verdict.ACCEPTED, totalCentis);
    }

    private Judge getJudge(String lang) throws Exception {
        String judgecmd = "judge." + lang + ".judgecmd";
        String mainfile = "judge." + lang + ".mainfile";
        if(env.containsProperty(judgecmd) && env.containsProperty(mainfile)) {
            return new Judge(env.getProperty(judgecmd), env.getProperty(mainfile));
        }
        throw new Exception("There is no Judge for language '" + lang + "'.");
    }

    private List<TestCase> getTestCases(Long problemId) throws Exception {
        List<TestCase> tcs = testCases.findByProblemId(problemId);
        if(tcs.size() > 0) return tcs;
        throw new Exception("There is no problem with id '" + problemId + "'.");
    }
}
