package io.github.lukamaletin.ftnscholar.controller;


import io.github.lukamaletin.ftnscholar.controller.exception.NotFoundException;
import io.github.lukamaletin.ftnscholar.dto.AuthenticationRequest;
import io.github.lukamaletin.ftnscholar.dto.AuthenticationResponse;
import io.github.lukamaletin.ftnscholar.model.user.BaseUser;
import io.github.lukamaletin.ftnscholar.security.TokenUtils;
import io.github.lukamaletin.ftnscholar.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final TokenUtils tokenUtils;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }


    @PostMapping("signin")
    public ResponseEntity signin(@RequestBody @Valid AuthenticationRequest dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (AuthenticationException e) {
            throw new NotFoundException("Wrong username or password!");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        final BaseUser user = userService.findByUsername(userDetails.getUsername());
        final String token = tokenUtils.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(user, token), HttpStatus.OK);
    }
}
