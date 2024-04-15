package com.docuSave.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.docuSave.demo.model.User;
import com.docuSave.demo.service.UserService;

@RestController
@CrossOrigin("http://192.168.29.43:8080")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping({"/","/home"})
    public String greet(){
        return "Welcome!";
    }

    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @PostMapping("/SignUpForm")
    public ResponseEntity<String> signup(@RequestBody User user){
        userService.saveUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User sign up successful.");
    }

    @PostMapping("/LoginForm")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Perform validation and error handling if needed

        User loggedInUser = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if(loggedInUser != null){
            return ResponseEntity.ok(loggedInUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
