package com.restApi.journalApp.Service;

import com.restApi.journalApp.Entity.JournalEntry;
import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Repository.JournalEntryRepository;
import com.restApi.journalApp.Repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepository userRepo;
    private static final PasswordEncoder passwordEncode=new BCryptPasswordEncoder();
    private static final Logger logger= LoggerFactory.getLogger(UserEntryService.class);
    public void saveEntry(User journalEntry){
        userRepo.save(journalEntry);
    }
    public void saveNew(User journalEntry){
        try{
        journalEntry.setPassword(passwordEncode.encode(journalEntry.getPassword()));
        journalEntry.setRoles(Arrays.asList("USER"));
        userRepo.save(journalEntry);}
        catch (Exception e){
            logger.error("same username",e);
        }
    }
    public void saveAdmin(User user){
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","Admin"));
        userRepo.save(user);
    }
    public List<User> getAll(){
        return userRepo.findAll();
    }
    public Optional<User> getById(ObjectId id){
        return userRepo.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }
    public void deleteByUsername(String userName){
        User user=userRepo.findByUserName(userName);
        userRepo.delete(user);
    }
    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
}
