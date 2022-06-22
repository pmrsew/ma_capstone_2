package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();
    User findByUsername(String username);
    User findByAccountId(long accountId);
    int findIdByUsername(String username); //added
    boolean create(String username, String password);

}
