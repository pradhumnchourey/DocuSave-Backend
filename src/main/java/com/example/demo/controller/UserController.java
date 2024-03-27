package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {

    @Autowired
    UserService service;

    List<User> users = new ArrayList<>(List.of(
        new User("Pradhumn", 12345, "pradhumn@gmail", "p@123"),
        new User("Purvy", 67890, "purvy@gmail", "p@123")
    ));
    
    @GetMapping("/hello")
    public String greet(){
        return "Hello Hotie!";
    }

    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return users;
    }

    @PostMapping("/SignUpForm")
    public void addUsers(@RequestBody User user){
        service.saveUser(user);
    }

    @PostMapping("/LoginForm")
    public String login(@RequestParam String username, @RequestParam String password){
        
        return "redirect:/hello";
    }
}
