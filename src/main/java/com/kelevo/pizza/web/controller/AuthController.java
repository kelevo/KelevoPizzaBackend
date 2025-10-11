package com.kelevo.pizza.web.controller;

import com.kelevo.pizza.services.dto.LoginDTO;
import com.kelevo.pizza.web.config.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDto) {

        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        );

        Authentication authentication = this.authenticationManager.authenticate(login);
        System.out.println("Authentication => " + authentication.isAuthenticated());
        System.out.println("Usuario => " + authentication.getPrincipal());

        String jwt = this.jwtUtils.create(loginDto.getUsername());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();

    }

}
