package com.carproduction.demo.demo.controllers;


import com.carproduction.demo.demo.entities.UserInfo;
import com.carproduction.demo.demo.requests.AuthRequest;
import com.carproduction.demo.demo.services.JwtService;
import com.carproduction.demo.demo.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController //ResponseBody + Controller
@RequestMapping("/auth")
public class userController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/welcome")
    public String notSecureMethodApi(){
        return "This is not secured";
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile(Authentication authentication) {
        // Spring Security, token'ı doğruladıktan sonra bu metoda erişim izni verir
        // ve 'Authentication' nesnesini bizim için doldurur.
        // Bu nesneden kullanıcı adını alabiliriz.
        return "Welcome to your profile, " + authentication.getName();
    }

    @GetMapping("/admin/profile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
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
