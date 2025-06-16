package com.zosh.service;

import com.zosh.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String mail) throws Exception;
}
