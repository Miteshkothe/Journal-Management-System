package com.restApi.journalApp.Controller;

import com.restApi.journalApp.Entity.Email;
import com.restApi.journalApp.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Health_Check {
    @Autowired
    private EmailService emailService;
    @GetMapping("/ok")
    public String healthCheck(){
        return "OK";
    }
    @PostMapping("/mail")
    public ResponseEntity<?> sendMail(@RequestBody Email mail){
        emailService.sendEmail(mail.getTo(),mail.getSubject(),mail.getBody());
        return new ResponseEntity<>("Send", HttpStatus.OK);
    }
}
