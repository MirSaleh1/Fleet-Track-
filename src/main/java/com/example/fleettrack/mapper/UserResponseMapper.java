package com.example.fleettrack.mapper;

import com.example.fleettrack.entity.User;
import com.example.fleettrack.model.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponse toUserResponse(User user);
}
