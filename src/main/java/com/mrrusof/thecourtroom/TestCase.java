package com.mrrusof.thecourtroom;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import org.json.JSONObject;

@Entity(name="test_cases")
public class TestCase {

    @Id @GeneratedValue
    private Long id;
    private String problemId;
    private String input;
    private String output;

    public TestCase(){}

    public TestCase(String problemId, String input, String output) {
        super();
        this.problemId = problemId;
        this.input = input;
        this.output = output;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "TestCase { \"id\":" + id +
            ", \"problem_id\":" + problemId +
            ", \"input\":" + JSONObject.quote(input) +
            ", \"output\":" + JSONObject.quote(output) + " }";
    }
}

@Lazy
interface TestCases extends JpaRepository <TestCase,Long>{
    List<TestCase> findByProblemId(@Param("problem_id") String problemId);
}
