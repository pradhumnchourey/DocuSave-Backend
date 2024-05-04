package com.docuSave.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.User;
import com.docuSave.demo.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public User createUser(User user){
        if(userRepository.findByEmail(user.getEmail())!=null){
            throw new DataIntegrityViolationException("User with same already exists!");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        // User newUser = new User(name, phoneNumber, email, hashedPassword);
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }
    
    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String string) {
        return userRepository.findByEmail(string);
    }
}