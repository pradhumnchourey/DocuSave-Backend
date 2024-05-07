package com.docuSave.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.docuSave.demo.model.AuthUser;
import com.docuSave.demo.model.User;
import com.docuSave.demo.service.UserService;

@RestController
@CrossOrigin("http://192.168.29.43:8080")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/SignUpForm")
    public ResponseEntity<String> signup(@RequestBody User user){
        try {
            userService.createUser(user); // Call separate method in userService
            return ResponseEntity.status(HttpStatus.CREATED).body("User sign up successful.");
        } catch (DataIntegrityViolationException e) {
            System.out.println("User With Same email already exist!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ": User With Same email already exist!");
        } catch (Exception e) { // Handle other potential exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Perform validation and error handling if needed
        if (user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        }
        User loggedInUser = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());        
        if(loggedInUser != null){
            AuthUser authUser = new AuthUser(loggedInUser.getUserId(), loggedInUser.getName());
            // User newUser= new User(loggedInUser.getUserId(), loggedInUser.getName());
            return ResponseEntity.ok(authUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}