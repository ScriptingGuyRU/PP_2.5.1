package com.boot.pp25.service;

import com.boot.pp25.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getUserById(Long id);

    void saveUser(User user);

    void deleteById(Long id);
}
