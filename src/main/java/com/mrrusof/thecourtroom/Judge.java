package com.mrrusof.thecourtroom;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class Judge {

    private final Integer MAX_LOG_LEN = 1000;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Environment env;
    private final String lang;
    private final String src;
    private String program;

    public Judge(String lang, String src, Environment env) throws Exception {
        this.env = env;
        this.lang = lang;

        if(! env.containsProperty(envKey("name"))) {
            throw new Exception("There is no Judge for language '" + lang + "'.");
        }

        this.src = src;
    }

    private String envValue(String key) {
        return env.getProperty(envKey(key));
    }

    private String envKey(String key) {
        return "judge." + lang + "." + key;
    }

    public Ruling buildProgram() throws IOException, InterruptedException {
        if(isCompiled()) {
            String output = compile();
            if(output.length() == 0) {
                return new Ruling(Ruling.RUNTIME_ERROR, "0m 0.00s");
            }
            JSONObject json = new JSONObject(output);
            int exitCode = json.getInt("exitCode");
            String wallTime = json.getString("wallTime");
            if(exitCode != 0) {
                return new Ruling(Ruling.RUNTIME_ERROR, wallTime);
            }
            program = json.getString("binaryProgram");
            return new Ruling(Ruling.ACCEPTED, wallTime);
        } else {
            program = src;
            return new Ruling(Ruling.ACCEPTED, "0m 0.00s");
        }
    }

    private boolean isCompiled() {
        return envValue("compiled").equals("true");
    }

    private String compile() throws IOException, InterruptedException {
        log.info("*** Compile ***");
        log.info(truncate("compile input: " + compileInput().trim()));
        String output = run(compileCommand(), compileInput());
        log.info(truncate("sandbox output: " + output.trim()));
        return output;
    }

    private String runCommand() {
        String sandbox = envValue("sandbox");
        return "the-witness-stand " + sandbox + " run-" + lang;
    }

    private String compileCommand() {
        String compiler = envValue("compiler");
        return "the-witness-stand " + compiler + " compile-" + lang;
    }

    private String compileInput() {
        return "{ \"source\":" + JSONObject.quote(src) + " }";
    }

    public Ruling rule(TestCase tc) throws IOException, InterruptedException {
        log.info("*** Rule ***");
        log.info(truncate(tc.toString()));

        JudgeParams jp;
        if(isCompiled()) {
            jp = new BinaryJudgeParams(program, tc);
        } else {
            jp = new InterpretedJudgeParams(program, tc);
        }

        log.info(truncate(jp.toString()));

        String output = run(runCommand(), jp.toJsonString());

        log.info(truncate("sandbox output: " + output.trim()));

        Ruling ruling = buildRuling(output, tc);

        log.info(truncate(ruling.toString()));

        return ruling;
    }

    private String run(String cmd, String input) throws IOException, InterruptedException {
        String output = "";
        try {
            Process p = new ProcessBuilder(cmd.split(" ")).start();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            writer.write(input);
            writer.flush();
            writer.close();

            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int ch;
            while((ch = reader.read()) != -1) {
                output += Character.toString((char) ch);
            }
        } catch(IOException e) {
            throw e;
        }
        return output;
    }

    public Ruling buildRuling(String output, TestCase tc) {
        if(output.length() == 0) {
            return new Ruling(Ruling.TIMEOUT, "0m 10.00s");
        }
        JSONObject json = new JSONObject(output);
        String actualOutput = json.getString("actualOutput");
        int exitCode = json.getInt("exitCode");
        String wallTime = json.getString("wallTime");
        String ruling;
        if(exitCode == 0) {
            if(actualOutput.equals(tc.getOutput())) {
                ruling = Ruling.ACCEPTED;
            } else {
                ruling = Ruling.WRONG_ANSWER;
            }
        } else {
            ruling = Ruling.RUNTIME_ERROR;
        }
        return new Ruling(ruling, wallTime);
    }

    private String truncate(String s) {
        if(s.length() > MAX_LOG_LEN) {
            return s.substring(0, MAX_LOG_LEN - 1) + "... (truncated)";
        }
        return s;
    }
}
