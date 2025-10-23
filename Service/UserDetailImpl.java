package com.restApi.journalApp.Service;

import com.restApi.journalApp.Entity.User;
import com.restApi.journalApp.Repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailImpl implements UserDetailsService {
    @Autowired
    private UserEntryRepository userEntryRepository;
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        User user=userEntryRepository.findByUserName(userName);
        if(user==null) {
            throw new UsernameNotFoundException(userName);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

}
