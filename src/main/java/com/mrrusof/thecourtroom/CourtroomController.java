package com.mrrusof.thecourtroom;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class CourtroomController {

    private Courtroom courtroom;

    @Autowired
    CourtroomController(Courtroom courtroom) {
        this.courtroom = courtroom;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/judges/{language}/trial", method=RequestMethod.POST)
    public Ruling judge(@PathVariable(value="language") String language,
                         @RequestBody TrialParams params)
        throws Exception {
        return courtroom.trial(params.getProblemId(),
                               params.getSrc(),
                               language);
    }
}
