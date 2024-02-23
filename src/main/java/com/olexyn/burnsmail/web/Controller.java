package com.olexyn.burnsmail.web;


import com.olexyn.burnsmail.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final EmailService emailService;

    @Autowired
    public Controller(
        EmailService emailService
    ){
        this.emailService = emailService;
    }

    @PostMapping("/triage/sbb")
    public ResponseEntity<String> triageSbb() {
        try {
            emailService.doSbbTriage();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/classify/spam")
    public ResponseEntity<String> postConfig() {
        try {
            emailService.classifyAsSpam();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


}
