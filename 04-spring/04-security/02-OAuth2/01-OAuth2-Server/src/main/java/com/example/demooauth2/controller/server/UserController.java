package com.example.demooauth2.controller.server;

import com.example.demooauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("#oauth2.hasScope('WRITE') AND hasRole('admin')")
    public ResponseEntity listUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('read_profile')")
    public ResponseEntity getProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(userService.findByUsername(username));
    }
}
