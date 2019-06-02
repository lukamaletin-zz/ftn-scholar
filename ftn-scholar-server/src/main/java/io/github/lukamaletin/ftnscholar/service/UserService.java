package io.github.lukamaletin.ftnscholar.service;

import io.github.lukamaletin.ftnscholar.controller.exception.BadRequestException;
import io.github.lukamaletin.ftnscholar.controller.exception.ForbiddenException;
import io.github.lukamaletin.ftnscholar.controller.exception.NotFoundException;
import io.github.lukamaletin.ftnscholar.model.user.BaseUser;
import io.github.lukamaletin.ftnscholar.repository.UserInfoRepository;
import io.github.lukamaletin.ftnscholar.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public BaseUser save(BaseUser user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new BadRequestException("Username taken!");
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserInfo(userInfoRepository.save(user.getUserInfo()));

        return userRepository.save(user);
    }

    public BaseUser findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    public BaseUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Wrong username or password!"));
    }

    public List<BaseUser> findAll() {
        return userRepository.findAll();
    }

    public BaseUser getCurrentUser() {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ForbiddenException("User is not authenticated!");
        }

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByUsername(userDetails.getUsername());
    }
}
