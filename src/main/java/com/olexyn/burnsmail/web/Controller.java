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

    @PostMapping("/read")
    public  ResponseEntity<String> postConfig() {
        try {
            emailService.readEmails();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/expunge")
    public  ResponseEntity<String> expunge() {
        try {
            emailService.expunge();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


}
