package com.example.demooauth2.service;

import com.example.demooauth2.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username);
}
