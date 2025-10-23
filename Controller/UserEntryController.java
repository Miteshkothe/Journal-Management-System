package com.restApi.journalApp.Controller;

import com.restApi.journalApp.Entity.JournalEntry;
import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Entity.Weather;
import com.restApi.journalApp.Repository.JournalEntryRepository;
import com.restApi.journalApp.Repository.UserEntryRepository;
import com.restApi.journalApp.Service.EmailService;
import com.restApi.journalApp.Service.JournalEntryService;
import com.restApi.journalApp.Service.UserEntryService;
import com.restApi.journalApp.Service.weatherApi;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserEntryRepository userEntryRepository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private weatherApi weatherApi;
    @Autowired
    private EmailService emailService;
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        User userId=userEntryService.findByUserName(userName);
            userId.setUserName(user.getUserName());
            userId.setPassword(user.getPassword());
            userEntryService.saveNew(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry> l=user.getJournalEntryList();
        if(!l.isEmpty()){
        journalEntryRepository.deleteAll(l);}
        userEntryRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/city/{Mycity}")
    public ResponseEntity<?> greet(@PathVariable String Mycity){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        Weather weather=weatherApi.getWeather(Mycity);
        return new ResponseEntity<>("Hi"+" "+userName+" ,Weather feels like"+ weather.getCurrent().temperature,HttpStatus.OK);
    }

}
