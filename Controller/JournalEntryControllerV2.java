package com.restApi.journalApp.Controller;

import com.restApi.journalApp.Entity.JournalEntry;
import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Service.JournalEntryService;
import com.restApi.journalApp.Service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping
    public ResponseEntity<?> getAllJournalOfUser(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        User user=userEntryService.findByUserName(auth.getName());
        List<JournalEntry> all=user.getJournalEntryList();
        if(all!=null&& !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication auth=SecurityContextHolder.getContext().getAuthentication();
            String userName= auth.getName();
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{Myid}")
    public ResponseEntity<?> getById(@PathVariable ObjectId Myid){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry> u1=user.getJournalEntryList();
        int t=0;
        for(JournalEntry i:u1){
            if(i.getId().equals(Myid)){
                t=1;
                break;
            }
        }
        if(t==1){
        Optional<JournalEntry> l=journalEntryService.getById(Myid);
        return new ResponseEntity<>(l.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{Myid}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId Myid){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        boolean b=journalEntryService.deleteById(Myid,userName);
        if(b){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/id/{Myid}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId Myid,@RequestBody JournalEntry myData){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry> l=user.getJournalEntryList();
        JournalEntry old=null;
        boolean b=false;
        for(JournalEntry i:l){
            if(i.getId().equals(Myid)){
                b=true;
                old=i;
                break;
            }
        }
        if(b){
            old.setContent(myData.getContent()!=null&& !myData.getContent().equals("")? myData.getContent(): old.getContent());
            old.setTitle(myData.getTitle()!=null&& !myData.getTitle().equals("")? myData.getTitle(): old.getTitle());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
