package com.carproduction.demo.demo.controllers;


import com.carproduction.demo.demo.entities.UserInfo;
import com.carproduction.demo.demo.requests.AuthRequest;
import com.carproduction.demo.demo.services.JwtService;
import com.carproduction.demo.demo.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController //ResponseBody + Controller
@RequestMapping("/auth")
public class userController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserInfoService userInfoService;

    public userController(AuthenticationManager authenticationManager, JwtService jwtService, UserInfoService userInfoService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/welcome")
    public String notSecureMethodApi(){
        return "This is not secured";
    }

    @GetMapping
    public String securedMethodApi(){
        return "This is securedApi";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }








}
