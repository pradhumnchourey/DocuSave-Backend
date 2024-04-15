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
        try {
            userService.createUser(user); // Call separate method in userService
            return ResponseEntity.status(HttpStatus.CREATED).body("User sign up successful.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) { // Handle other potential exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        // userService.saveUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());
        // return ResponseEntity.status(HttpStatus.CREATED).body("User sign up successful.");
    }

    @PostMapping("/LoginForm")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Perform validation and error handling if needed
        if (user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        }

        User loggedInUser = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if(loggedInUser != null){
            return ResponseEntity.ok(loggedInUser.getName());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
