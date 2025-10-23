package com.restApi.journalApp.Service;

import com.restApi.journalApp.Entity.JournalEntry;
import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;
    @Autowired
    UserEntryService userEntryService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
        User user=userEntryService.findByUserName(userName);
        journalEntry.setDate(LocalDate.now());
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournalEntryList().add(saved);
        userEntryService.saveEntry(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("this is error",e);
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public boolean deleteById(ObjectId id, String userName){
        boolean b=false;
        User user =userEntryService.findByUserName(userName);
        b=user.getJournalEntryList().removeIf(a->a.getId().equals(id));
        if(b){
        userEntryService.saveEntry(user);
        journalEntryRepository.deleteById(id);}
        return b;
    }

}
