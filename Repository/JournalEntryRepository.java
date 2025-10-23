package com.restApi.journalApp.Repository;

import com.restApi.journalApp.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.io.ObjectInput;

@Component
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}

