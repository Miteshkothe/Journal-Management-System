package com.restApi.journalApp.Controller;

import com.restApi.journalApp.Entity.JournalEntry;
import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Service.JournalEntryService;
import com.restApi.journalApp.Service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    UserEntryService userEntryService;
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalOfUser(@PathVariable String userName){
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry> all=user.getJournalEntryList();
        if(all!=null&& !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
        try{
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{Myid}")
    public ResponseEntity<?> getById(@PathVariable ObjectId Myid){
        Optional<JournalEntry> journalEntry=journalEntryService.getById(Myid);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{userName}/{Myid}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId Myid,@PathVariable String userName){
        journalEntryService.deleteById(Myid,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/id/{userName}/{Myid}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId Myid,@RequestBody JournalEntry myData,@PathVariable String userName){
        JournalEntry old=journalEntryService.getById(Myid).orElse(null);
        if(old!=null){
            old.setContent(myData.getContent()!=null&& !myData.getContent().equals("")? myData.getContent(): old.getContent());
            old.setTitle(myData.getTitle()!=null&& !myData.getTitle().equals("")? myData.getTitle(): old.getTitle());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
