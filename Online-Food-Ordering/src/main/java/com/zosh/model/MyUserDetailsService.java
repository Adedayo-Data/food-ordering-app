package com.zosh.model;

import com.zosh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

//    @Autowired
//    User user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         user = new User();
        User user = userRepo.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found!");
        }
        return new UserPrincipal(user);
    }
}
