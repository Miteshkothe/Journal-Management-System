package com.restApi.journalApp.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Email {
    private String to;
    private String subject;
    private String body;
}
