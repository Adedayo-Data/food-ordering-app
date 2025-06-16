package com.zosh.service;

import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        System.out.println("JwtToken hit!");
        System.out.println("The token being passed: " + jwt);
        String email = jwtService.extractUsername(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String mail) throws Exception {

        User user = userRepository.findByEmail(mail);
        if(user == null){
            throw new Exception("User not found!");
        }
        return user;
    }


}
