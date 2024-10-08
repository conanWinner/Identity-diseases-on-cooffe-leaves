package com.identify.leavescoffee.mapper;

import com.identify.leavescoffee.dto.response.UserResponse;
import com.identify.leavescoffee.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse  toUserResponse(User user);

    List<UserResponse> toUserResponse(List<User> user);

}
