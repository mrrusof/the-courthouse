package com.mrrusof.thecourtroom;

import java.io.File;
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

    private final String WORKDIR = "/tmp/judge";
    private final File workdir = new File(WORKDIR);
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String judgeCmd;
    private final String srcFilename;

    public Judge(String judgeCmd, String srcFilename) {
        this.judgeCmd = judgeCmd;
        this.srcFilename = srcFilename;
    }

    public Verdict rule(String src, TestCase tc) throws IOException, InterruptedException {

        log.info(tc.toString());

        setupWorkdir(src, tc);

        String output = execJudge();

        log.info("Verdict " + output.trim());

        JSONObject json = new JSONObject(output);

        return buildVerdict(json);
    }

    private void setupWorkdir(String src, TestCase tc) throws IOException {
        workdir.mkdir();

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(WORKDIR + srcFilename))) {
            writer.write(src);
        }
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(WORKDIR + "/in"))) {
            writer.write(tc.getInput());
        }
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(WORKDIR + "/out"))) {
            writer.write(tc.getOutput());
        }
    }

    private String execJudge() throws IOException, InterruptedException {
        String output = "";
        try {
            Process p = new ProcessBuilder(judgeCmd).directory(workdir).start();
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

    public Verdict buildVerdict(JSONObject json) {
        return new Verdict(json.getString("verdict"), json.getString("time"));
    }
}
