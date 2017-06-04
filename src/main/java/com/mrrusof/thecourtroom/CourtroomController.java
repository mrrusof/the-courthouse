package com.mrrusof.thecourtroom;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class CourtroomController {

    private Courtroom courtroom;

    @Autowired
    CourtroomController(Courtroom courtroom) {
        this.courtroom = courtroom;
    }

    @RequestMapping(value="/problem/{problemId}/judge", method=RequestMethod.POST)
    public Verdict judge(@PathVariable("problemId") Long problemId,
                         @RequestParam(value="src") String src,
                         @RequestParam(value="lang") String lang)
        throws Exception {
        return courtroom.trial(problemId, src, lang);
    }
}
