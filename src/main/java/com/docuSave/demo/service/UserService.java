package com.docuSave.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.User;
import com.docuSave.demo.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo repo;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }
    public List<User> findAll() {
        return repo.findAll();
    }
}
