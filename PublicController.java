package com.restApi.journalApp.Controller;

import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Service.UserDetailImpl;
import com.restApi.journalApp.Service.UserEntryService;
import com.restApi.journalApp.Utilis.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailImpl userDetail;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/signup")
    public void createUser(@RequestBody User user){
        userEntryService.saveNew(user);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetail.loadUserByUsername(user.getUserName());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Eexception ocurred while creating token",e);
            return new ResponseEntity<>("Incorrect password",HttpStatus.BAD_REQUEST);
        }

    }
}
