package com.carproduction.demo.demo.services;

import com.carproduction.demo.demo.entities.UserInfo;
import com.carproduction.demo.demo.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find the user by email first, then by name.
        // This allows logging in with either email or username.
        Optional<UserInfo> userInfo = repository.findByEmail(username);
        if (userInfo.isEmpty()) {
            userInfo = repository.findByName(username);
        }

        // If user is not found by either email or name, throw an exception.
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully!";
    }
}
