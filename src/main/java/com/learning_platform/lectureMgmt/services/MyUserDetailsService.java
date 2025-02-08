package com.learning_platform.lectureMgmt.services;


import com.learning_platform.lectureMgmt.config.ApiClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    ApiClientConfig apiClientConfig;

    private UserDetails fetchUsersFromAuthService(String username){
       System.out.println(apiClientConfig);
       return User.builder()
               .username(username)
               .password("test")
               .roles("ADMIN")
               .build();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // call the auth service to get the user info
        return this.fetchUsersFromAuthService(username);


    }
}
