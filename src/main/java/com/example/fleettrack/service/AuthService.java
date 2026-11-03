package com.example.fleettrack.service;



import com.example.fleettrack.entity.Authority;
import com.example.fleettrack.entity.User;
import com.example.fleettrack.exception.BusinessException;
import com.example.fleettrack.exception.ExceptionConstants;
import com.example.fleettrack.model.RoleType;
import com.example.fleettrack.model.request.LoginRequest;
import com.example.fleettrack.model.request.RegisterRequest;
import com.example.fleettrack.model.request.TokenRequest;
import com.example.fleettrack.model.response.TokenResponse;
import com.example.fleettrack.repository.AuthorityRepository;
import com.example.fleettrack.repository.UserRepository;
import com.example.fleettrack.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AuthorityRepository authorityRepository;


    public TokenResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException(ExceptionConstants.USER_ALREADY_EXISTS);
        }
        Authority authority = authorityRepository.findAllByType(RoleType.ROLE_FLEET_MANAGER)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authorities(Set.of(authority))
                .build();

        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user =  (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(TokenRequest request) {
        String reqRefreshToken = request.refreshToken();
        String username = jwtUtil.extractUsername(reqRefreshToken);
        
        if (username != null) {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new BusinessException(ExceptionConstants.USER_NOT_FOUND));
            
            if (jwtUtil.validateToken(reqRefreshToken, user.getEmail())) {
                String newAccessToken = jwtUtil.generateToken(user.getEmail());
                return new TokenResponse(newAccessToken, request.refreshToken());
            }
        }
        throw new BusinessException(ExceptionConstants.INVALID_REFRESH_TOKEN);
    }
}
