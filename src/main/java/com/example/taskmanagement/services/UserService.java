package com.example.taskmanagement.services;

import com.example.taskmanagement.configs.CustomUserDetails;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
}
