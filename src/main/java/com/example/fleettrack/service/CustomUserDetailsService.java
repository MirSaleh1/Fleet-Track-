package com.example.fleettrack.service;



import com.example.fleettrack.entity.Authority;
import com.example.fleettrack.entity.User;
import com.example.fleettrack.exception.ExceptionConstants;
import com.example.fleettrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "users",key = "#email")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new UsernameNotFoundException(ExceptionConstants.USER_NOT_FOUND.getMessage()));

        Set<Authority> unproxiedAuthorities = new HashSet<>();
        for (Authority a : user.getAuthorities()) {
            Authority authority = new Authority();
            authority.setId(a.getId());
            authority.setType(a.getType());
            unproxiedAuthorities.add(authority);
        }
        User cachedUser = new User();
        cachedUser.setId(user.getId());
        cachedUser.setName(user.getName());
        cachedUser.setEmail(user.getEmail());
        cachedUser.setPassword(user.getPassword());
        cachedUser.setAuthorities(unproxiedAuthorities);

        return cachedUser;
    }
}
