package com.restApi.journalApp.Service;

import com.restApi.journalApp.Entity.JournalEntry;
import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Repository.JournalEntryRepository;
import com.restApi.journalApp.Repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepository userRepo;
    public void saveEntry(User journalEntry){
        userRepo.save(journalEntry);
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
