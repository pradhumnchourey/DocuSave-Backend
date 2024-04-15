package com.docuSave.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.User;
import com.docuSave.demo.model.UserPrincipal;
import com.docuSave.demo.repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("Invalid username or password!");
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserPrincipal(user);
    }
}
