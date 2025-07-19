package com.restApi.journalApp.Controller;

import com.restApi.journalApp.Entity.JournalEntry;
import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Service.JournalEntryService;
import com.restApi.journalApp.Service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;
    @GetMapping
    public List<User> getAllUser(){
        return userEntryService.getAll();
    }
    @PostMapping
    public void createUser(@RequestBody User user){
        userEntryService.saveEntry(user);
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
        User userId=userEntryService.findByUserName(userName);
        if(userId!=null){
            userId.setUserName(user.getUserName());
            userId.setPassword(user.getPassword());
            userEntryService.saveEntry(userId);
        }return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry> l=user.getJournalEntryList();
        for(JournalEntry i:l){
            ObjectId id=i.getId();
            journalEntryService.deleteById(id);
        }
        userEntryService.deleteByUsername(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
