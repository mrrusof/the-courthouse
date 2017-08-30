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

public class Judge {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String judgeCmd;

    public Judge(String judgeCmd) {
        this.judgeCmd = judgeCmd;
    }

    public Ruling rule(String src, TestCase tc) throws IOException, InterruptedException {

        log.info(tc.toString());

        JudgeParams jp = new JudgeParams(tc, src);

        log.info(jp.toString());

        String output = execJudge(jp);

        log.info("Ruling " + output.trim());

        JSONObject json = new JSONObject(output);

        return buildRuling(json);
    }

    private String execJudge(JudgeParams jp) throws IOException, InterruptedException {
        String output = "";
        try {
            Process p = new ProcessBuilder(judgeCmd).start();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            writer.write(jp.toJsonString());
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

    public Ruling buildRuling(JSONObject json) {
        return new Ruling(json.getString("ruling"), json.getString("wallTime"));
    }
}
