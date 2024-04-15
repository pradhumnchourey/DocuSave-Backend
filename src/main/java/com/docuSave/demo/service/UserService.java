package com.docuSave.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.User;
import com.docuSave.demo.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public User saveUser(String name, long phoneNumber, String email, String password){
        if(userRepository.findByEmail(email)!=null){
            throw new IllegalArgumentException("User with same already exists!");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(name, phoneNumber, email, hashedPassword);
        return userRepository.save(newUser);
    }
    
    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null & passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
