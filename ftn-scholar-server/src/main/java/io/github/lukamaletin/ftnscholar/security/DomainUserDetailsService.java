package io.github.lukamaletin.ftnscholar.security;

import io.github.lukamaletin.ftnscholar.model.user.BaseUser;
import io.github.lukamaletin.ftnscholar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        final BaseUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user with username %s found!", username)));

        return createSpringSecurityUser(user);
    }

    private User createSpringSecurityUser(BaseUser user) {
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
