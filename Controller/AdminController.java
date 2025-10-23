package com.restApi.journalApp.Controller;

import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserEntryService userEntryService;
    @GetMapping("/all-user")
    public  ResponseEntity<?> getAllUser(){
        List<User> all = userEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user){
        userEntryService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
