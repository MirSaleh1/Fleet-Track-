package com.example.fleettrack.service;


import com.example.fleettrack.entity.Authority;
import com.example.fleettrack.entity.User;
import com.example.fleettrack.exception.BusinessException;
import com.example.fleettrack.exception.ExceptionConstants;
import com.example.fleettrack.mapper.UserResponseMapper;
import com.example.fleettrack.model.response.UserResponse;
import com.example.fleettrack.repository.AuthorityRepository;
import com.example.fleettrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;

    @Transactional
    @CacheEvict(value="users", key="#result.email")
    public UserResponse assignRoleToUser(Long userId, Long authorityId){
        Authority role = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.USER_NOT_FOUND));
        user.getAuthorities().add(role);
        return userResponseMapper.toUserResponse(userRepository.save(user));
    }
    @Transactional
    @CacheEvict(value="users", key="#result.email")
    public UserResponse removeRoleFromUser(Long userId, Long roleId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.USER_NOT_FOUND));
        Authority role = authorityRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));
        user.getAuthorities().remove(role);
        return userResponseMapper.toUserResponse(userRepository.save(user));
    }

}
