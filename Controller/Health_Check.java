package com.restApi.journalApp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health_Check {
    @GetMapping("/ok")
    public String healthCheck(){
        return "OK";
    }
}
