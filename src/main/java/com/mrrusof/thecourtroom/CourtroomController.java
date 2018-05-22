package com.mrrusof.thecourtroom;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@RestController
public class CourtroomController {

    private Courtroom courtroom;
    private Environment env;

    @Autowired
    CourtroomController(Courtroom courtroom, Environment env) {
        this.courtroom = courtroom;
        this.env = env;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/judges", method=RequestMethod.GET)
    public List<JudgeId> judges() {
        List<String> judgeIds = Arrays.asList(env.getProperty("judges").split(","));
        List<JudgeId> judges = new ArrayList<>();
        for(String id : judgeIds) {
            String name = env.getProperty("judge." + id + ".name");
            String codemirrormode = env.getProperty("judge." + id + ".codemirrormode");
            String notes = env.getProperty("judge." + id + ".notes");
            judges.add(new JudgeId(id, name, codemirrormode, notes));
        }
        return judges;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/judges/{id}", method=RequestMethod.GET)
    public JudgeId judge(@PathVariable(value="id") String id) throws Exception {
        if(Arrays.asList(env.getProperty("judges").split(",")).indexOf(id) == -1)
            throw new Exception("There is no Judge for language '" + id + "'.");
        String name = env.getProperty("judge." + id + ".name");
        String codemirrormode = env.getProperty("judge." + id + ".codemirrormode");
        String notes = env.getProperty("judge." + id + ".notes");
        return new JudgeId(id, name, codemirrormode, notes);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/judges/{language}/trial", method=RequestMethod.POST)
    public Ruling trial(@PathVariable(value="language") String language,
                        @RequestBody TrialParams params)
        throws Exception {
        return courtroom.trial(params.getProblemId(),
                               params.getSrc(),
                               language);
    }
}
